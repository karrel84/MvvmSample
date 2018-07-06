package karrel.com.mvvmsample.viewmodel

import android.content.Context
import android.text.TextUtils
import io.reactivex.subjects.PublishSubject
import karrel.com.mvvmsample.R
import karrel.com.mvvmsample.data.LoginDataManager

/**
 * Created by Rell on 2018. 7. 5..
 */
object LoginViewModelImpl {

    val progressViewModel = ProgressViewModelImpl

    val input: LoginViewModel.Input = Input()
    val output: LoginViewModel.Output = Output()

    class Input : LoginViewModel.Input {

        private val logindataManager = LoginDataManager()

        // 로그인시도
        override fun attemptLogin(context: Context, email: String, password: String) {

            var validSuccess = checkPasswordValid(password, context) // 패스워드 유효성 검사
            validSuccess = validSuccess and checkEmailValid(email, context) // 이메일 유효성 검사

            if (validSuccess) {
                progressViewModel.input.showProgress()
                logindataManager
                        .login(email, password)
                        .subscribe {
                            output.loginObservable().onNext(it)
                            progressViewModel.input.hideProgress()
                        }
            }
        }

        private fun checkEmailValid(email: String, context: Context): Boolean {
            if (TextUtils.isEmpty(email)) { // 이 메일이 빈값이면
                output.emailErrorObservable().onNext(context.getString(R.string.error_field_required))
                output.emailFocusObservable().onNext(true)
                return false
            } else if (!isEmailValid(email)) { // 이메일의 형식에 맞는지 확인
                output.emailErrorObservable().onNext(context.getString(R.string.error_invalid_email))
                output.emailFocusObservable().onNext(true)
                return false
            } else {
                output.emailErrorObservable().onNext(null.toString())
            }
            return true
        }

        // 패스워드 유효성검사
        private fun checkPasswordValid(password: String, context: Context): Boolean {
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) { // 패스워드의 형식에 맞지 않으면
                val errorMessage = context.getString(R.string.error_invalid_password)
                output.passwordErrorObservable().onNext(errorMessage)
                output.passwordFocusObservable().onNext(true)
                return false
            } else {
                output.passwordErrorObservable().onNext(null.toString())
            }
            return true
        }

        // 패스워드 유효성검사
        private fun isPasswordValid(password: String): Boolean {
            return password.length > 4
        }

        // 이메일 형식확인
        private fun isEmailValid(email: String): Boolean {
            return email.contains("@")
        }

    }

    class Output : LoginViewModel.Output {
        private val emailErrorObservable = PublishSubject.create<String>()
        private val passwordErrorObservable = PublishSubject.create<String>()
        private val passwordFocusObservable = PublishSubject.create<Boolean>()
        private val emailFocusObservable = PublishSubject.create<Boolean>()
        private val loginObservable = PublishSubject.create<Boolean>()

        override fun passwordErrorObservable(): PublishSubject<String> {
            return passwordErrorObservable
        }

        override fun emailErrorObservable(): PublishSubject<String> {
            return emailErrorObservable
        }

        override fun passwordFocusObservable(): PublishSubject<Boolean> {
            return passwordFocusObservable
        }

        override fun emailFocusObservable(): PublishSubject<Boolean> {
            return emailFocusObservable
        }

        override fun loginObservable(): PublishSubject<Boolean> {
            return loginObservable
        }
    }

}