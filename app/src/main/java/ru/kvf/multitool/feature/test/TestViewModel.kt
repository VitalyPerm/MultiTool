package ru.kvf.multitool.feature.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withTimeout
import ru.kvf.multitool.core.App
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun tickerFlow(
    period: Duration = DEFAULT_TICKER_PERIOD,
    initialDelay: Duration = period,
    timeout: Long? = null,
): Flow<Unit> {
    suspend fun FlowCollector<Unit>.runTimer() {
        delay(initialDelay)
        while (currentCoroutineContext().isActive) {
            emit(Unit)
            delay(period)
        }
    }

    return flow {
        if (timeout != null) withTimeout(timeout) { runTimer() } else runTimer()
    }
}

private val DEFAULT_TICKER_PERIOD = 1.seconds

@Singleton
class TestRepository @Inject constructor() {

    val scope =
        CoroutineScope(
            Dispatchers.IO +
                SupervisorJob() +
                CoroutineExceptionHandler { _, t -> App.print("error - ${t.message}") }
        )

    private var orderPollingJob: Job? = null

    private val sFlow = MutableStateFlow<Int?>(null)

    fun start() {
        orderPollingJob?.cancel()
        orderPollingJob = tickerFlow()
            .onEach {
                try {
                    val code = Random.nextInt(9999)
                    when {
                        code % 3 == 0 -> throw RuntimeException("surprise motherfucker")
                        code % 2 == 0 -> {
                            App.print("emit null")
                            sFlow.tryEmit(null)
                        }
                        else -> {
                            App.print("emit $code")
                            sFlow.tryEmit(code)
                        }
                    }
                }
                catch (e: Exception) {
                    App.print("error -$e")
                }
            }
            .launchIn(scope)
    }

    fun stop() {
        orderPollingJob?.cancel()
    }

    fun getFlow() = sFlow.filterNotNull()
}

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repo: TestRepository
) : ViewModel() {

    var value by mutableIntStateOf(0)

    init {
        repo.getFlow()
            .onEach { data ->
                value = data
            }.launchIn(viewModelScope)
    }

    fun start() {
        repo.start()
    }

    fun stop() {
        repo.stop()
    }
}
