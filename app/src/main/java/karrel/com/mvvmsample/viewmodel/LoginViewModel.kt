package karrel.com.mvvmsample.viewmodel

import android.content.Context
import io.reactivex.subjects.PublishSubject


/**
 * Created by Rell on 2018. 7. 6..
 */
interface LoginViewModel {
    interface Input {
        fun attemptLogin(context: Context, email: String, password: String)
    }

    interface Output {
        fun passwordErrorObservable(): PublishSubject<String>
        fun emailErrorObservable(): PublishSubject<String>
        fun passwordFocusObservable(): PublishSubject<Boolean>
        fun emailFocusObservable(): PublishSubject<Boolean>
        fun loginObservable(): PublishSubject<Boolean>
    }
}