package com.rafael.proffy.ui.finallyComponent

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rafael.proffy.R
import com.rafael.proffy.databinding.ActivityFinallyBinding
import com.rafael.proffy.ui.login.LoginActivity

class FinallyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinallyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinallyBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recebe os textos enviados pela ForgotActivity ou RegisterStepTwoActivity
        val title = intent.getStringExtra("title") ?: getString(R.string.forgot_done_title)
        val subtitle = intent.getStringExtra("subtitle") ?: getString(R.string.forgot_done_subtitle)
        val buttonText = intent.getStringExtra("buttonText") ?: getString(R.string.forgot_done_button)

        // ⚠️ Confira no seu activity_finally.xml os IDs exatos desses 3 componentes:
        binding.textViewTitle.text = title
        binding.textViewSubtitle.text = subtitle
        binding.buttonAction.text = buttonText

        binding.buttonAction.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}