package com.example.facebook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnBackToLogin: Button
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        edtEmail = findViewById(R.id.edtEmail)
        btnRegister = findViewById(R.id.btnRegister)
        btnBackToLogin = findViewById(R.id.btnBackToLogin)

        api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        btnRegister.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val email = edtEmail.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            api.register(username, password, email).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    val body = response.body()
                    if (body?.success == true) {
                        Toast.makeText(this@RegisterActivity, body.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, body?.message ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Lỗi: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnBackToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
