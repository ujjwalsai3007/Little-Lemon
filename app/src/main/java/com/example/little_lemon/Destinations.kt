package com.example.little_lemon

interface Destinations{
    val route:String
}

object onboarding:Destinations{
    override val route="Onboarding"
}
object Home:Destinations{
    override val route="Home"
}
object Profile:Destinations{
    override val route="Profile"
}