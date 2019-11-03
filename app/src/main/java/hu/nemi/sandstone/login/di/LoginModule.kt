package hu.nemi.sandstone.login.di

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import dagger.Module
import dagger.Provides
import hu.nemi.sandstone.login.network.LoginService
import retrofit2.Retrofit

@Module
object GoogleSignInModule {
    @Provides
    @JvmStatic
    fun googleSignInClient(application: Application): GoogleSignInClient =
        GoogleSignIn.getClient(
            application,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Scope("https://www.googleapis.com/auth/tasks"))
                .requestEmail()
                .requestServerAuthCode("217631243337-8hm1vtvoup2oott64ai5kfpau71bnegk.apps.googleusercontent.com")
                .build()
        )

    @Provides
    @JvmStatic
    fun loginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)
}