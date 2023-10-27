package com.example.housemaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.housemaster.databinding.ActivityMainBinding
import com.example.housemaster.databinding.FragmentWelcomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        //initiate the firebase object
        firebaseAuth = FirebaseAuth.getInstance()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment, R.id.signOut),
            mainBinding.drawerLayout
        )

        setSupportActionBar(mainBinding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        mainBinding.bottomNav.setupWithNavController(navController)
        mainBinding.navView.setupWithNavController(navController)
        mainBinding.navView.setNavigationItemSelectedListener {
            val id = it.itemId


            if (id == R.id.signOut) {
                MaterialAlertDialogBuilder(this).setTitle("Success")
                    .setCancelable(false)
                    .setMessage("You have been signed out successfully.")
                    .setPositiveButton("Done") { dialog_, which ->

                        firebaseAuth.signOut()

                        mainBinding.drawerLayout.closeDrawers();

                        val action = NavGraphDirections.actionGlobalSignInFragment()
                        navController.navigate(action)


                    }.show()
            }

            return@setNavigationItemSelectedListener true
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.termsAndConditions) {
            val action = NavGraphDirections.actionGlobalTermsFragment()
            navController.navigate(action)
            true
        } else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}