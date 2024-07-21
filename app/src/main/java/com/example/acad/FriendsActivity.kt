package com.example.acad

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.acad.adapters.FriendPagerAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendsActivity : AppCompatActivity() {
//    private lateinit var friendAdapter: FriendAdapter
//    private lateinit var friendRecyclerView: RecyclerView
//
//    @Inject
//    lateinit var repository: FriendRepository
//
//    @Inject
//    lateinit var dataStoreRepository: DataStoreRepository

    // Example data
//    private val friends = emptyList<Friend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_friends)
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

        // Configuration de l'adapter pour ViewPager2
        val pagerAdapter = FriendPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // Liaison du TabLayout avec le ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Utilisateurs"
                1 -> "Mes Amis"
                else -> throw IllegalStateException("Position inconnue: $position")
            }
        }.attach()
        // Initialize the adapter and RecyclerView
//        friendRecyclerView = findViewById(R.id.friendRecyclerView)
//        friendAdapter = FriendAdapter(friends)
//        friendRecyclerView.layoutManager = LinearLayoutManager(this)
//        friendRecyclerView.adapter = friendAdapter


//        lifecycleScope.launch { launchRequest() }
    }


//    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
//        dataStoreRepository.readAccessToken().collect {
//            repository.listFriend(it)
//                .catch { exception ->
//                    if (exception is HttpException) {
//                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
//                    }
////                _state.value = HttpStatus.ERROR
//                }
//                .collect { response ->
//                    Log.d(TAG, "launchRequest: $response")
//                    withContext(Dispatchers.Main) {
//                        friendAdapter.updateData(response)
//                    }
//                }
//        }
//    }
}