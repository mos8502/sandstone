package hu.nemi.sandstone.util

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.google.android.gms.tasks.Task as GsmTask
import com.google.android.play.core.tasks.Task as PlayTask

suspend fun <T : Any> GsmTask<T>.await(): T =
    suspendCoroutine { cont ->
        addOnCompleteListener { task ->
            try {
                cont.resume(requireNotNull(task.result) {
                    "result cannot be null"
                })
            } catch (error: Throwable) {
                cont.resumeWithException(error)
            }
        }
    }

suspend fun <T : Any> PlayTask<T>.await(): T =
    suspendCoroutine { cont ->
        addOnCompleteListener { task ->
            try {
                cont.resume(requireNotNull(task.result) {
                    "result cannot be null"
                })
            } catch (error: Throwable) {
                cont.resumeWithException(error)
            }
        }
    }

fun <T : Any> PlayTask<T>.deferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()
    addOnCompleteListener { task ->
        try {
            deferred.complete(requireNotNull(task.result) {
                "result cannot be null"
            })
        } catch (error: Throwable) {
            deferred.completeExceptionally(error)
        }
    }
    return deferred
}
