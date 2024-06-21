package com.example.acad

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.acad.repositories.AuthRepository
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.requests.LoginRequest
import com.example.acad.utils.enums.HttpStatus
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var emailTextEdit: EditText
    private lateinit var passwordTextEdit: EditText

    private var _state = MutableStateFlow(HttpStatus.INITIAL)

    @Inject
    lateinit var repository: AuthRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin: MaterialButton = findViewById(R.id.loginButton)
        emailTextEdit = findViewById(R.id.email)
        passwordTextEdit = findViewById(R.id.password)

        btnLogin.setOnClickListener {
            // Handle login button click
            val request =
                LoginRequest(emailTextEdit.text.toString(), passwordTextEdit.text.toString())
            lifecycleScope.launch {
                launchRequest(request)
            }
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
        }

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        backBtn.setOnClickListener {
            finish()
        }

    }

    private suspend fun launchRequest(request: LoginRequest) = withContext(Dispatchers.IO) {
        repository.loginUser(request)
            .catch { exception ->
                if (exception is HttpException) {
                    Log.e(TAG, "loginUser: ${exception.message()}", exception)
                }
                _state.value = HttpStatus.ERROR
            }
            .collect { response ->
                Log.d(TAG, "launchRequest: $response")
                _state.value = HttpStatus.LOADED
                dataStoreRepository.saveAccessToken(response.access)
                dataStoreRepository.saveRefreshToken(response.refresh)
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
    }
}