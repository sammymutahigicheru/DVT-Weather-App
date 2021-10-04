package com.dvt.dvtweatherapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvt.dvtweatherapp.R
import com.dvt.dvtweatherapp.ui.weather.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun test_CurrentWeatherIsVisible() {
        Thread.sleep(2000)
        onView(withId(R.id.temperature_textview))
            //  .check(matches(withText("17.59 ℃"))) //at the time of testing the temp was 17.59 modify it current weather
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_WeeklyWeatherForecastAreVisible() {
        Thread.sleep(2000)
        onView(withId(R.id.weekly_forecast_recyclerview))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_favouritesActionButton_WorksAsExpected() {
        Thread.sleep(2000)
        onView(withId(R.id.favorites_floating_action_button))
            .perform(click())
    }

    @Test
    fun test_saveFavorites_WorksAsExpected() {
        Thread.sleep(2000)
        onView(withId(R.id.favourite_icon))
            .perform(click())
        onView(withText(R.string.save_success_message)).check(
            matches(
                isDisplayed()
            )
        )

    }
}