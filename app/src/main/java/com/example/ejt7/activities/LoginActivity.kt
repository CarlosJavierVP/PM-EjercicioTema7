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
    private var preferences: SharedPreferences? = null
    private var user: String? = ""
    private var password: String? = ""
    private var remember = false
    private lateinit var binding: ActivityLoginBinding

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

        preferences = getSharedPreferences("Film Pro Manager", MODE_PRIVATE)
        setValues()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.editText.toString()
            val password = binding.etPassword.editText.toString()
            if(login(email, password)) goToMain()
            savePreferences(email, password)
        }

    }

    private fun setValues(){
        val email = preferences?.getString("email", "")
        val password = preferences?.getString("password", "")
        val remember = preferences?.getBoolean("remember", false)
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            binding.etEmail.editText?.setText(email)
            binding.etPassword.editText?.setText(password)
            if (remember != null) {
                binding.switch1.isChecked = remember
            }
        }
    }
    private fun savePreferences(email: String, password: String){
        val edit = preferences?.edit()
        if(binding.switch1.isChecked){
            edit?.putString("email", email)
            edit?.putString("password", password)
            edit?.putBoolean("remember", true)
            edit?.apply()
        }else{
            edit?.clear()
            edit?.putBoolean("remember", false)
            edit?.apply()
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