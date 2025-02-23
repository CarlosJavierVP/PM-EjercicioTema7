package com.example.ejt7.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejt7.R
import com.example.ejt7.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityLoginBinding
    private var email: String?=""
    private var password: String?=""
    private var remember: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.title=getString(R.string.login)
        preferences = getSharedPreferences("app", MODE_PRIVATE)
        setPreferences()
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            if(login(email, password)) goToMain()
            savePreferences(email, password)
        }

    }

    private fun setPreferences(){
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            binding.etEmail.editText?.setText(email)
            binding.etPassword.editText?.setText(password)
            remember = preferences.getBoolean("remember", false)
            binding.switch1.isChecked = remember
            if (remember) {
                if(preferences.contains("email")){
                    email = preferences.getString("email", null)
                    binding.emailEdt.setText(email)
                }
                if(preferences.contains("pass")){
                    password = preferences.getString("pass", null)
                    binding.passwordEdt.setText(password)
                }
            }
        }
    }
    private fun savePreferences(email: String, password: String){
        val edit = preferences.edit()
        edit.putBoolean("remember",binding.switch1.isChecked)
        if(binding.switch1.isChecked){
            edit.putString("email", email)
            edit.putString("password", password)
            edit.putBoolean("remember", true)
            edit.apply()
        }else{
            edit.clear()
            edit.putBoolean("remember", false)
            edit.apply()
        }
    }

    private fun eMailValidate(email: String):Boolean{
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun passwordValidate(password: String):Boolean{
        return !TextUtils.isEmpty(password) && password.length>7
    }

    private fun login(email:String, password: String): Boolean{
        var validate = false
        if (!eMailValidate(email)){
            Toast.makeText(this,
                "Invalid e-mail. Enter a correct e-mail",
                Toast.LENGTH_SHORT).show()
        }else if (!passwordValidate(password)){
            Toast.makeText(this,
                "Invalid password. The e-mail must be at least 8 character",
                Toast.LENGTH_SHORT).show()
        }else {
            validate = true
        }
        return validate
    }

    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}