package com.example.acad.models

import com.google.gson.annotations.SerializedName

sealed class HttpResponse {
    data class SuccessResponse<T>(
        val message: String,
        val data: T,
        val status: String
    ) : HttpResponse()

    data class ErrorResponse(
        @SerializedName("message") val message: String,
        @SerializedName("status") val status: String,
        @SerializedName("validations") val validations: List<ValidationError>?,
        @SerializedName("reason") val reason: String
    ) : HttpResponse() {
        constructor() : this("", "", null, "")
    }

    data class GeocodingResponse(val results: List<GeocodingResult>)

    data class GeocodingResult(val address_components: List<AddressComponent>)

    data class AddressComponent(
        val long_name: String,
        val short_name: String,
        val types: List<String>
    )

    data class ValidationError(val field: String, val defaultMessage: String)
}