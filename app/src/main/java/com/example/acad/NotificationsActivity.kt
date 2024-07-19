package com.example.acad

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.acad.adapters.NotificationsPagerAdapter
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.NotificationRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: NotificationRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notifications)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: MaterialButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val adapter = NotificationsPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Tout"
                1 -> "En Attente"
                else -> null
            }
        }.attach()

        lifecycleScope.launch { launchRequest() }
    }

    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            repository.listNotification(it)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    withContext(Dispatchers.Main) {
//                        notificationsAdapter.updateData(response)
                    }
                }
        }
    }
}