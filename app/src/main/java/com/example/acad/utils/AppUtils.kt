package com.example.acad.utils

import android.content.ContentValues
import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//const val SERVER_IP_BASE = "192.168.1.79:8000"
const val SERVER_IP_BASE = "ec2-13-53-40-36.eu-north-1.compute.amazonaws.com:8000"
//const val SERVER_IP_BASE = "ec2-13-60-35-3.eu-north-1.compute.amazonaws.com:8000"

//const val SERVER_IP_BASE = "10.2.30.240:8000"
//const val SERVER_IP_BASE = "192.168.0.108:8000"
const val WEB_CLIENT_ID = ""

fun Date.format(format: String, date: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, date)
    return formatter.format(this)
}

fun String.formatDate(format: String, locale: Locale = Locale.getDefault()): String =
    if (this.isBlank()) ""
    else {
        val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
        val localeDate = formatter.parse(this)
        "${localeDate?.format(format)}"
    }

fun String.parseDate(format: String, locale: Locale = Locale.getDefault()): Date? {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.parse(this)
}

//fun Long.toDate(format: String, locale: Locale = Locale.getDefault()): String = try {
//    val date = Date.from(Instant.ofEpochMilli(this))
//    val formatter = SimpleDateFormat(format, locale)
//    formatter.format(date)
//} catch (e: Exception) {
//    e.printStackTrace()
//    ""
//}

fun getJwtUserId(token: String): Any {
    try {
        val decodedJWT = JWT.decode(token)
        val objet = gson.fromJson(decodedJWT.subject, Any::class.java)
        Log.d(ContentValues.TAG, "getJwtUserId: $decodedJWT")
        return objet
    } catch (exception: Exception) {
        // Invalid signature/claims
        throw JWTVerificationException("Invalid token")
    }
}

val gson: Gson = GsonBuilder()
    .setLenient()
    .create()
//
//val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
//    .setFilterByAuthorizedAccounts(true)
//    .setServerClientId(WEB_CLIENT_ID)
//    .setAutoSelectEnabled(true)
//    .build()
//
//val request: GetCredentialRequest = Builder()
//    .addCredentialOption(googleIdOption)
//    .build()
//
//
//
//fun handleSignIn(result: GetCredentialResponse) {
//    // Handle the successfully returned credential.
//    val credential = result.credential
//
//    when (credential) {
//
//        // Password credential
//        is PasswordCredential -> {
//            // Send ID and password to your server to validate and authenticate.
//            val username = credential.id
//            val password = credential.password
//        }
//
//        // GoogleIdToken credential
//        is CustomCredential -> {
//            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                try {
//                    // Use googleIdTokenCredential and extract id to validate and
//                    // authenticate on your server.
//                    val googleIdTokenCredential = GoogleIdTokenCredential
//                        .createFrom(credential.data)
//                } catch (e: GoogleIdTokenParsingException) {
//                    Log.e(TAG, "Received an invalid google id token response", e)
//                }
//            } else {
//                // Catch any unrecognized custom credential type here.
//                Log.e(TAG, "Unexpected type of credential")
//            }
//        }
//
//        else -> {
//            // Catch any unrecognized credential type here.
//            Log.e(TAG, "Unexpected type of credential")
//        }
//    }
//}