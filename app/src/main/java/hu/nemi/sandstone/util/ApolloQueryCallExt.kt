package hu.nemi.sandstone.util

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApolloResponseException(val errors: List<Error>) : ApolloException("data was null")

suspend fun <T : Any> ApolloQueryCall<T>.await(): Response<T> = suspendCoroutine { cont ->
    val callback = object : ApolloCall.Callback<T>() {
        override fun onFailure(e: ApolloException) {
            cont.resumeWithException(e)
        }

        @Suppress("ThrowableNotThrown")
        override fun onResponse(response: Response<T>) {
            val data = response.data()
            if (data == null) {
                cont.resumeWithException(ApolloResponseException(response.errors()))
            } else {
                cont.resume(response)
            }
        }
    }
    enqueue(callback)
}