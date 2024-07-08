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
import com.example.acad.R
import com.example.acad.repositories.AuthRepository
import com.example.acad.requests.UserRequest
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
class RegisterActivity : AppCompatActivity() {

    private lateinit var emailTextEdit: EditText
    private lateinit var passwordTextEdit: EditText
    private lateinit var usernameTextEdit: EditText
    private lateinit var confirmPasswordTextEdit: EditText

    private var _state = MutableStateFlow(HttpStatus.INITIAL)

    @Inject
    lateinit var repository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        emailTextEdit = findViewById(R.id.email)
        usernameTextEdit = findViewById(R.id.username)
        passwordTextEdit = findViewById(R.id.password)
        confirmPasswordTextEdit = findViewById(R.id.confirmPassword)

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        val registerBtn: MaterialButton = findViewById(R.id.registerButton)
        backBtn.setOnClickListener {
            finish()
        }

        registerBtn.setOnClickListener {
            // Handle register button click
            val request = UserRequest(
                email = emailTextEdit.text.toString(),
                username = usernameTextEdit.text.toString(),
                password = passwordTextEdit.text.toString(),
                passwordConfirm = confirmPasswordTextEdit.text.toString(),
            )

            lifecycleScope.launch {
                launchRequest(request)
            }
        }
    }

    private suspend fun launchRequest(request: UserRequest) {
        return withContext(Dispatchers.IO) {
            _state.value = HttpStatus.LOADING
            repository.registerUser(request)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "registerUser: ${exception.message()}", exception)
                    }
                    _state.value = HttpStatus.ERROR
                }
                .collect {
                    Log.d(TAG, "launchRequest: $it")
                    _state.value = HttpStatus.LOADED
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
        }
    }
}