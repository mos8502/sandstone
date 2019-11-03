package hu.nemi.sandstone.app

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val fbAuth: FirebaseAuth) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val user = fbAuth.currentUser
        return if (user == null) {
            Response.Builder().code(401).build()
        } else {
            val response = chain.proceedWithAuthHeader(false, user)
            if (response.code == 401) {
                response.close()
                chain.proceedWithAuthHeader(true, user)
            } else {
                response
            }

        }
    }

    private fun Chain.proceedWithAuthHeader(
        refreshToken: Boolean,
        user: FirebaseUser
    ): Response =
        proceed(
            request()
                .newBuilder()
                .header(
                    "Authorization",
                    "Bearer ${Tasks.await(user.getIdToken(refreshToken)).token}"
                ).build()
        )
}