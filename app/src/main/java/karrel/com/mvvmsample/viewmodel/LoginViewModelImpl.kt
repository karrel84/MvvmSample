package karrel.com.mvvmsample.viewmodel

import android.content.Context
import android.text.TextUtils
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import karrel.com.mvvmsample.R
import karrel.com.mvvmsample.data.LoginDataManager

/**
 * Created by Rell on 2018. 7. 5..
 */
object LoginViewModelImpl : LoginViewModel {
    override val input: LoginViewModel.Input = Input()
    override val output: LoginViewModel.Output = Output()
    val progressViewModel: ProgressViewModel = ProgressViewModelImpl

    private val emailErrorObservable = PublishSubject.create<String>()
    private val passwordErrorObservable = PublishSubject.create<String>()
    private val passwordFocusObservable = PublishSubject.create<Boolean>()
    private val emailFocusObservable = PublishSubject.create<Boolean>()
    private val loginObservable = PublishSubject.create<Boolean>()

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
                            loginObservable.onNext(it)
                            progressViewModel.input.hideProgress()
                        }
            }
        }

        private fun checkEmailValid(email: String, context: Context): Boolean {
            if (TextUtils.isEmpty(email)) { // 이 메일이 빈값이면
                emailErrorObservable.onNext(context.getString(R.string.error_field_required))
                emailFocusObservable.onNext(true)
                return false
            } else if (!isEmailValid(email)) { // 이메일의 형식에 맞는지 확인
                emailErrorObservable.onNext(context.getString(R.string.error_invalid_email))
                emailFocusObservable.onNext(true)
                return false
            } else {
                emailErrorObservable.onNext(null.toString())
            }
            return true
        }

        // 패스워드 유효성검사
        private fun checkPasswordValid(password: String, context: Context): Boolean {
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) { // 패스워드의 형식에 맞지 않으면
                val errorMessage = context.getString(R.string.error_invalid_password)
                passwordErrorObservable.onNext(errorMessage)
                passwordFocusObservable.onNext(true)
                return false
            } else {
                passwordErrorObservable.onNext(null.toString())
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

        override fun passwordErrorObservable(): Observable<String> {
            return passwordErrorObservable
        }

        override fun emailErrorObservable(): Observable<String> {
            return emailErrorObservable
        }

        override fun passwordFocusObservable(): Observable<Boolean> {
            return passwordFocusObservable
        }

        override fun emailFocusObservable(): Observable<Boolean> {
            return emailFocusObservable
        }

        override fun loginObservable(): Observable<Boolean> {
            return loginObservable
        }
    }

}