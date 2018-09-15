package me.pirrate.fintechcharity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import me.pirrate.fintechcharity.api.FintechAPI
import me.pirrate.fintechcharity.api.models.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login screen that offers login via username/password.
 */
class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var loginInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.
        password_field.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        sign_in_button.setOnClickListener { attemptLogin() }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (loginInProgress) {
            return
        }

        // Reset errors.
        username_field.error = null
        password_field.error = null

        // Store values at the time of the login attempt.
        val username = username_field.text.toString()
        val password = password_field.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            password_field.error = getString(R.string.error_field_required)
            focusView = password_field
            cancel = true
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            username_field.error = getString(R.string.error_field_required)
            focusView = username_field
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            FintechAPI.service.login(username, password).enqueue(object: Callback<LoginResult> {
                override fun onFailure(call: Call<LoginResult>?, t: Throwable?) {
                    t?.printStackTrace()
                    runOnUiThread {
                        showProgress(false)
                        if(t != null)
                            Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call<LoginResult>?, response: Response<LoginResult>?) {
                    runOnUiThread {
                        showProgress(false)
                        if (response?.isSuccessful == true) {
                            Globals.sessionId = response.body()?.SessionId
                            Globals.userDetails = response.body()?.UserDetails
                            Log.d("LoginActivity", response.body()?.toString())
                            startActivity(Intent(this@LoginActivity, IntroActivity::class.java))
                            finish()
                        } else {
                            password_field.error = getString(R.string.error_incorrect_password)
                            password_field.requestFocus()
                        }
                    }
                }
            })
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
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

    companion object {
    }
}
