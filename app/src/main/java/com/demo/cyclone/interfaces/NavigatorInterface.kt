package com.demo.cyclone.interfaces

import androidx.navigation.NavOptions
import androidx.navigation.Navigator

interface NavigatorInterface {
    fun navigateUp()

    fun navigateBack(
        destinationId: Int? = null,
        inclusive: Boolean? = null
    )

    fun navigate(
        route: String,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    )
}