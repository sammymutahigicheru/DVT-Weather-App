package com.dvt.dvtweatherapp.utils.helpers

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class DateHelperTest{
    @Test
    fun `given current time in long return the day` (){
        //disclaimer at the point of testing this it was on as saturday
        val time:Long = 1633172400
        val day = convertToDay(time)
        Truth.assertThat(day).isEqualTo("Saturday")
    }
}