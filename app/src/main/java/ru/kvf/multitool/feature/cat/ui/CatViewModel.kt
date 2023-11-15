package ru.kvf.multitool.feature.cat.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kvf.multitool.feature.cat.domain.CatRepository
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ContainerHost<CatState, CatSideEffect>, ViewModel() {

    override val container: Container<CatState, CatSideEffect> = container(CatState.initial)

    fun getCats() = intent {
        reduce { state.copy(isLoading = true) }
        delay(3333)
        val cats = catRepository.getCats(5)
        reduce { state.copy(cats = cats, isLoading = false) }
        postSideEffect(CatSideEffect.Toast("Loaded"))
    }
}