[![Android CI](https://github.com/sammymutahigicheru/DVT-Weather-App/actions/workflows/main.yml/badge.svg)](https://github.com/sammymutahigicheru/DVT-Weather-App/actions/workflows/main.yml)


Writing DTV Weather App using [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/), in 100% Kotlin, using Android Jetpack Components :rocket:

## Pre-Requisities

- [A valid Google Maps Api Key](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
- [A valid Open Weather API Key](https://openweathermap.org/appid)

## Setup
- Add `MAP_KEY` to your `local.properties` file
- Paste your open weather API key to ``API_KEY`` on `Constants Class Object`

### Unit Tests
Run ```script .\gradlew connectedCheck``` to generate Unit test Jacoco Reports
Run ```script .\gradlew connectedDebugAndroidTest``` to generate Instrumented  test Jacoco Reports

#### Spek

This allows us to easily define specifications in a clear, understandable, human-readable way. This framework allows you to describe tests and expected behaviors in a more readable way.

To run tests in Android Studio you need to install Spek Framework plugin (search for Spek Framework).

The UI test run normally, either on a device or an emulator, without any special plugin or dependency.


### A few hiccups

When mocking webserver response MockWebserver times out for some reason i.e socket timeout exception


### How it's built

* Technologies used
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
    * [KOIN](https://insert-koin.io/)
    * [Retrofit](https://square.github.io/retrofit/)
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room)
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Timber](https://github.com/JakeWharton/timber)

* Architecture
    * MVVM - Model View View Model

* Tests
    * [JUnit5](https://junit.org/junit5/)
    * [Spek](https://www.spekframework.org/)
    * [MockK](https://github.com/mockk/mockk)
    * [Truth](https://github.com/google/truth)

* Gradle
    * Plugins
        * [jacoco](https://github.com/jacoco/jacoco)
        * [Detekt](https://github.com/detekt/detekt)

* CI/CD
    * Github Actions

### Screenshots

I added some screenshots in the `screenshots` folder, in the root directory of the project.

Current Weather | Favourites | Maps
--- | --- | ---
<img src="https://github.com/sammymutahigicheru/DVT-Weather-App/blob/main/screenshots/current_weather.jpg" width="280"/> | <img src="https://github.com/sammymutahigicheru/DVT-Weather-App/blob/main/screenshots/favourites.jpg" width="280"/> | <img src="https://github.com/sammymutahigicheru/DVT-Weather-App/blob/main/screenshots/maps.jpg" width="280"/>