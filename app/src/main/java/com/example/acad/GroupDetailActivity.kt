package com.example.acad

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.acad.adapters.GroupDetailsPagerAdapter
import com.example.acad.data.UserData
import com.example.acad.data.toMember
import com.example.acad.models.Group
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.GroupRepository
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
class GroupDetailActivity : AppCompatActivity() {

    private var group: Group? = null

    private lateinit var titleTextView: TextView

    @Inject
    lateinit var repository: GroupRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var userData: UserData

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_group_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: MaterialButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        // Récupération de l'objet Program à partir de l'intent
        val groupId = intent.getParcelableExtra("groupId", String::class.java)

        lifecycleScope.launch { launchRequest(groupId) }

        titleTextView = findViewById(R.id.groupName)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val users = userData.list
        val members = group?.members?.map { users.find { user -> user.id.toLong() == it.toLong() } }
            ?.map {
                Log.d(TAG, "onCreate: $it")
                it!!.toMember() } ?: emptyList()

        // Configuration de l'adapter pour ViewPager2
        val pagerAdapter = GroupDetailsPagerAdapter(this, group, members)
        viewPager.adapter = pagerAdapter

        // Liaison du TabLayout avec le ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Description"
                1 -> "Membres"
                else -> throw IllegalStateException("Position inconnue: $position")
            }
        }.attach()
    }

    private suspend fun launchRequest(groupId: String?) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            repository.getGroup(token, groupId)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    group = response
                    titleTextView.text = group?.name
                }
        }
    }
}