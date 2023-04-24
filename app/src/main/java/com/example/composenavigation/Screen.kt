package com.example.composenavigation

sealed class Screen(val route: String){
    object Feed: Screen(AppDestinations.FEED)
    object Adopt: Screen("{dogID}/adopt") {
        fun createRoute(dogId: String) = "$dogId/adopt"
    }
}

// nested graphs
sealed class DogScreen(val route: String) {
    object Adopt: DogScreen("dog/{dogId}/adopt") {
        fun createRoute(dogId: String) = "dog/$dogId/adopt"
    }
    object ContactDetails: DogScreen("dog/{dogId}/contactDetails") {
        fun createRoute(dogId: String) = "dog/$dogId/contactDetails"
    }
}
