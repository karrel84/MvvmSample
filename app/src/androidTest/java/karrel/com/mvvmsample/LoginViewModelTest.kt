package karrel.com.mvvmsample

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import karrel.com.mvvmsample.viewmodel.LoginViewModelImpl
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    private val appContext = InstrumentationRegistry.getTargetContext()
    private val loginViewModel = LoginViewModelImpl

    @Test
    fun testShortPassword() {
        val errorMessage = appContext.getString(R.string.error_invalid_password)
        loginViewModel.output.passwordErrorObservable()
                .test()
                .awaitCount(1)
                .assertValue(errorMessage)

        loginViewModel.input.attemptLogin(appContext, "karrel@naver.com", "0")
    }

    @Test
    fun testNotInvalidEmail() {
        val errorMessage = appContext.getString(R.string.error_invalid_email)
        loginViewModel.output.emailErrorObservable()
                .test()
                .awaitCount(1)
                .assertValue(errorMessage)

        loginViewModel.input.attemptLogin(appContext, "karrelnaver.com", "0")
    }

    @Test
    fun testSuccessLogin() {
        loginViewModel.output.loginObservable()
                .test()
                .awaitCount(1)
                .assertValue(true)

        loginViewModel.input.attemptLogin(appContext, "karrel@naver.com", "1234")
    }
}