package karrel.com.mvvmsample.viewmodel

import android.content.Context
import io.reactivex.Observable


/**
 * Created by Rell on 2018. 7. 6..
 */
interface LoginViewModel {

    val input : Input
    val output : Output

    interface Input {
        fun attemptLogin(context: Context, email: String, password: String)
    }

    interface Output {
        fun passwordErrorObservable(): Observable<String>
        fun emailErrorObservable(): Observable<String>
        fun passwordFocusObservable(): Observable<Boolean>
        fun emailFocusObservable(): Observable<Boolean>
        fun loginObservable(): Observable<Boolean>
    }
}