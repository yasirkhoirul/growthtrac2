package com.example.growthtrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.growthtrack.databinding.ActivityMainBinding
import com.example.growthtrack.pindahan.UserModel
import com.example.growthtrack.ui.ArticleActivity
import com.example.growthtrack.ui.SignInActivity
import com.example.growthtrack.ui.SplashScreenActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // AppBarConfiguration berisi kumpulan id yang ada di dalam menu BottomNavigation,
        // khususnya yang ingin dikonfigurasi AppBar-nya supaya berbentuk Menu. Jika id tidak Anda
        // tambahkan di sini, maka AppBar akan menampilkan tanda panah kembali.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_rumahsakit, R.id.navigation_diagnose,R.id.navigation_history,R.id.navigation_profile
            )
        )
        //setupActionBarWithNavController digunakan untuk mengatur judul AppBar agar sesuai dengan Fragment yang ditampilkan.
        setupActionBarWithNavController(navController, appBarConfiguration)
        //setupWithNavController digunakan supaya Bottom Navigation menampilkan Fragment yang sesuai ketika menu dipilih.
        navView.setupWithNavController(navController)
        binding.logout.setOnClickListener {
            val moveToAdd = Intent(this, SignInActivity::class.java)
            startActivity(moveToAdd)
        }

    }
    private fun showDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.logout))
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            mainViewModel.logout()

            startActivity(Intent(this, SplashScreenActivity::class.java))
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }
}