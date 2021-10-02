package com.dvt.dvtweatherapp.model

sealed class LocationException{
    data class PermissionDeniedException(val errorMessage: String):LocationException()
    data class RationaleException(val errorMessage: String):LocationException()
}