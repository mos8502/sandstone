package hu.nemi.sandstone.app

import com.google.firebase.auth.FirebaseUser
import hu.nemi.sandstone.util.Lce

data class ApplicationState(val account: Lce<FirebaseUser?>)