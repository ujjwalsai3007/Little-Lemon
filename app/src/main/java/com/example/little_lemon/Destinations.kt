package com.example.little_lemon

interface Destinations {
    val route: String
    
    companion object {
        const val HOME = "home"
        const val ONBOARDING = "onboarding"
        const val PROFILE = "profile"
    }
}

object onboarding : Destinations {
    override val route = Destinations.ONBOARDING
}

object Home : Destinations {
    override val route = Destinations.HOME
}

object Profile : Destinations {
    override val route = Destinations.PROFILE
}