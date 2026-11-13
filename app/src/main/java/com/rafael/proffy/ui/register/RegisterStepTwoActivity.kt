package com.rafael.proffy.ui.register

import android.content.Context
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
import com.rafael.proffy.databinding.ActivityRegisterStepTwoBinding
import org.intellij.lang.annotations.Pattern

class RegisterStepTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterStepTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterStepTwoBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()

        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonGoBackStepOne = binding.buttonGoBack
        val textInputEmail = binding.textInputEditEmail
        val textInputPassword = binding.textInputEditPassword
        val buttonFinish = binding.buttonFinish

        buttonGoBackStepOne.setOnClickListener {
            goBack()
        }
        buttonFinish.setOnClickListener {
            handleNextStep()
        }

        val enabledButtonColor = ContextCompat.getColor(this, R.color.green)
        val disabledButtonColor = ContextCompat.getColor(this, R.color.shape_disable)

        textInputEmail.addTextChangedListener {
            val fistName = textInputEmail.text?.toString()?.trim() ?: ""
            val lastName = textInputPassword.text?.toString()?.trim() ?: ""
            val validInputs = fistName.isNotEmpty() && lastName.isNotEmpty()
            setButtonState(validInputs, enabledButtonColor, disabledButtonColor)
        }

        textInputPassword.addTextChangedListener {
            val fistName = textInputEmail.text?.toString()?.trim() ?: ""
            val lastName = textInputPassword.text?.toString()?.trim() ?: ""
            val validInputs = fistName.isNotEmpty() && lastName.isNotEmpty()
            setButtonState(validInputs, enabledButtonColor, disabledButtonColor)
        }
    }

    private fun goBack() {
        finish()
    }

    private fun setButtonState(enabled: Boolean, enabledColor: Int, disabledColor: Int) {
        binding.buttonFinish.isEnabled = enabled
        val color = if (enabled) enabledColor else disabledColor

        binding.buttonFinish.backgroundTintList = ColorStateList.valueOf(color)
        val textColor = if (enabled)
            ContextCompat.getColor(this, R.color.shape_01_white)
        else
            ContextCompat.getColor(this, R.color.text_complement)
        binding.buttonFinish.setTextColor(textColor)
    }

    private fun handleNextStep() {
        val email = binding.textInputEditEmail.text.toString().trim()
        val password = binding.textInputEditPassword.text.toString().trim()

        if (isValidEmail(email) && password.length > 4) {
            val intent = Intent(this, com.rafael.proffy.ui.finallyComponent.FinallyActivity::class.java)
            intent.putExtra("title", getString(R.string.register_done_title))
            intent.putExtra("subtitle", getString(R.string.register_done_subtitle))
            intent.putExtra("buttonText", getString(R.string.register_done_button))
            startActivity(intent)
            finish()
        } else {
            if (!isValidEmail(email)) {
                binding.textInputEditEmail.error = getString(R.string.error_invalid_email)
            }
            if (password.length <= 4) {
                binding.textInputEditPassword.error = "Deve conter pelo menos 5 caracteres."
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}