package com.rafael.proffy.ui.forgot

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.rafael.proffy.R
import com.rafael.proffy.databinding.ActivityForgotBinding
import com.rafael.proffy.ui.finallyComponent.FinallyActivity

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val enabledButtonColor = ContextCompat.getColor(this, R.color.green)
        val disabledButtonColor = ContextCompat.getColor(this, R.color.shape_disable)

        val buttonGoBack = binding.buttonGoBack
        val layoutEmail = binding.textInputLayoutEmail
        val editTextEmail = binding.textInputEditEmail
        val buttonSendEmail = binding.buttonSendEmail

        setButtonState(false, enabledButtonColor, disabledButtonColor)

        buttonGoBack.setOnClickListener {
            goBack()
        }

        editTextEmail.addTextChangedListener {
            val email = editTextEmail.text?.toString()?.trim() ?: ""
            val isValid = isValidEmail(email)

            if (email.isEmpty()) {
                editTextEmail.error = null
                layoutEmail.error = null
                layoutEmail.isErrorEnabled = false
            } else if (!isValid) {
                editTextEmail.error = getString(R.string.error_invalid_email)
                layoutEmail.isErrorEnabled = true
                layoutEmail.error = getString(R.string.error_invalid_email)
            } else {
                editTextEmail.error = null
                layoutEmail.error = null
                layoutEmail.isErrorEnabled = false
            }

            setButtonState(
                enabled = isValid,
                enabledColor = enabledButtonColor,
                disabledColor = disabledButtonColor
            )
        }

        editTextEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = editTextEmail.text?.toString()?.trim() ?: ""
                if (email.isNotEmpty() && !isValidEmail(email)) {
                    editTextEmail.error = getString(R.string.error_invalid_email)
                    layoutEmail.isErrorEnabled = true
                    layoutEmail.error = getString(R.string.error_invalid_email)
                }
            }
        }

        buttonSendEmail.setOnClickListener {
            val email = editTextEmail.text?.toString()?.trim() ?: ""
            if (isValidEmail(email)) {
                handleSendEmail()
            } else {
                val errorMsg = if (email.isEmpty()) {
                    getString(R.string.error_empty_email)
                } else {
                    getString(R.string.error_invalid_email)
                }
                editTextEmail.error = errorMsg
                layoutEmail.isErrorEnabled = true
                layoutEmail.error = errorMsg
            }
        }
    }

    private fun goBack() {
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setButtonState(enabled: Boolean, enabledColor: Int, disabledColor: Int) {
        val buttonSendEmail = binding.buttonSendEmail
        buttonSendEmail.isEnabled = enabled

        val color = if (enabled) enabledColor else disabledColor
        buttonSendEmail.backgroundTintList = ColorStateList.valueOf(color)

        val textColor = if (enabled) {
            ContextCompat.getColor(this, R.color.shape_01_white)
        } else {
            ContextCompat.getColor(this, R.color.text_complement)
        }
        buttonSendEmail.setTextColor(textColor)

        buttonSendEmail.text = if (enabled) {
            getString(R.string.btn_send)
        } else {
            getString(R.string.btn_enter)
        }
    }

    private fun handleSendEmail() {
        val intent = Intent(this, FinallyActivity::class.java)
        intent.putExtra("title", getString(R.string.forgot_done_title))
        intent.putExtra("subtitle", getString(R.string.forgot_done_subtitle))
        intent.putExtra("buttonText", getString(R.string.forgot_done_button))
        startActivity(intent)
        finish()
    }
}