package com.example.facebook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var edtUser: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnCreateAccount: Button
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtUser = findViewById(R.id.etEmail)
        edtPass = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)

        api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        btnLogin.setOnClickListener {
            val usernameOrEmail = edtUser.text.toString().trim()
            val password = edtPass.text.toString().trim()
            if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            api.login(usernameOrEmail, password).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    val body = response.body()
                    if (body != null && body.success) {
                        Toast.makeText(this@LoginActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, body?.message ?: "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Lỗi: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
