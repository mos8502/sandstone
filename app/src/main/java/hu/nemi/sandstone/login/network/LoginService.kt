package hu.nemi.sandstone.login.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
    /**
     * Logs in the user with [authCode] returning the id token
     *
     * @param authCode server auth code
     * @return idToken
     */
    @GET("/login")
    suspend fun login(@Query("authCode") authCode: String): String
}