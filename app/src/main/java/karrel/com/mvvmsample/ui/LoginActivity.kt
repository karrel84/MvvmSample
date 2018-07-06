package karrel.com.mvvmsample.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import karrel.com.mvvmsample.R
import karrel.com.mvvmsample.extensions.AutoClearedDisposable
import karrel.com.mvvmsample.extensions.plusAssign
import karrel.com.mvvmsample.viewmodel.LoginViewModelImpl
import karrel.com.mvvmsample.viewmodel.ProgressViewModel
import karrel.com.mvvmsample.viewmodel.ProgressViewModelImpl

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val loginViewModel = LoginViewModelImpl
    private val progressViewModel = ProgressViewModelImpl

    private val disposables = AutoClearedDisposable(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lifecycle += disposables

        setupProgressEvent()
        setupLoginVieModelEvents()
        setupButtonEvents() // 버튼이벤트
    }

    private fun setupProgressEvent() {
        progressViewModel.output.progressObservable().subscribe { showProgress(it) }
    }

    private fun setupLoginVieModelEvents() {
        disposables += loginViewModel.output.passwordErrorObservable().subscribe {
            if (it == null.toString()) {
                password.error = null
            } else {
                password.error = it
            }
        }
        disposables += loginViewModel.output.passwordFocusObservable().subscribe { password.requestFocus() }

        disposables += loginViewModel.output.emailErrorObservable().subscribe {
            if (it == null.toString()) {
                email.error = null
            } else {
                email.error = it
            }
        }
        disposables += loginViewModel.output.emailFocusObservable().subscribe { email.requestFocus() }
    }

    private fun setupButtonEvents() {
        // 패스워드
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        // 로그인 버튼
        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    // 로그인 시도
    private fun attemptLogin() {
        loginViewModel.input.attemptLogin(this, email.text.toString(), password.text.toString())
    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }
}
