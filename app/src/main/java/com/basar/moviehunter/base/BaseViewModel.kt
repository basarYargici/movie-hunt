package com.basar.moviehunter.base

import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.NavOption
import com.basar.moviehunter.util.model.ServiceError
import com.basar.moviehunter.util.model.Tuple3
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    var navigateDirection =
        MutableSharedFlow<Tuple3<NavDirections, NavOptions?, Navigator.Extras?>>()
    var navigateDestination = MutableSharedFlow<ActivityNavigator.Destination>()
    var popBackStack = MutableSharedFlow<Pair<Int, Boolean>>()

    fun navigate(destination: ActivityNavigator.Destination) = launch {
        navigateDestination.emit(destination)
    }

    fun navigate(
        navDirections: NavDirections,
        navOption: NavOption? = NavOption.ENTER_FROM_RIGHT,
        navigatorExtras: Navigator.Extras? = null
    ) = launch {
        val navOptions = navOption?.let {
            NavOptions.Builder()
                .setEnterAnim(navOption.enter)
                .setExitAnim(navOption.exit)
                .setPopEnterAnim(navOption.popEnter)
                .setPopExitAnim(navOption.popExit)
                .build()
        } ?: run { null }

        navigateDirection.emit(Tuple3(navDirections, navOptions, navigatorExtras))
    }

    fun popBack(@IdRes destinationId: Int, inclusive: Boolean = false) = launch {
        popBackStack.emit(Pair(destinationId, inclusive))
    }

    private fun getError(t: Throwable) = when (t) {
        else -> ServiceError.NetworkError
    }

    val exceptionHandler = CoroutineExceptionHandler { _, e ->
        val error = getError(e)
        Timber.v("exceptionHandler : $error")
    }
}