package io.realworld.android

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import io.realworld.android.databinding.ActivityMainBinding
import io.realworld.android.ui.article.ArticleFragment
import io.realworld.android.ui.article.CreateArticleFragment
import io.realworld.android.ui.article.UpdateArticleFragment
import io.realworld.android.ui.profile.ProfileFragment
import io.realworld.android.ui.profile.SettingsFragment
import io.realworld.api.models.entities.User

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFS_FILE_AUTH= "prefs_auth"
        const val PREFS_TOKEN="token"
        const val PREFS_USER="username"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel:AuthViewModel
    private lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences=getSharedPreferences(PREFS_FILE_AUTH, Context.MODE_PRIVATE)
        authViewModel=ViewModelProvider(this).get(AuthViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_feed, R.id.nav_my_feed, R.id.nav_auth
            ), drawerLayout
        )
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when((destination as FragmentNavigator.Destination).className) {
                ArticleFragment::class.qualifiedName,
                SettingsFragment::class.qualifiedName,
                CreateArticleFragment::class.qualifiedName,
                UpdateArticleFragment::class.qualifiedName,
                ProfileFragment::class.qualifiedName-> {
                    binding.appBarMain.fab.visibility = View.GONE
                }
                else -> {
                   sharedPreferences.getString(PREFS_TOKEN,null).let {
                        if(it!=null)
                            binding.appBarMain.fab.visibility =View.VISIBLE
                        else
                            binding.appBarMain.fab.visibility =View.GONE
                    }
                }
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        sharedPreferences.getString(PREFS_TOKEN,null)?.let{
            authViewModel.getCurrentUser(it)
        }
        authViewModel.user.observe({lifecycle}) {
            updateMenu(it)
            it?.token?.let {  t->
                sharedPreferences.edit {
                    putString(PREFS_TOKEN, t)
                }
            } ?: run {
                sharedPreferences.edit {
                    remove(PREFS_TOKEN)
                }
            }

            it?.username?.let { t->
                sharedPreferences.edit {
                    putString(PREFS_USER,t)
                }
            } ?: run {
                sharedPreferences.edit {
                    remove(PREFS_USER)
                }
            }
            navController.navigateUp()
        }

        binding.appBarMain.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment_content_main).
                    navigate(R.id.nav_create_article)
        }
    }

    private fun updateMenu(user: User?) {
        when(user){
            is User ->{
                binding.appBarMain.fab.visibility =View.VISIBLE
                binding.navView.menu.clear()
                binding.navView.inflateMenu(R.menu.menu_main_user)
            }
            else ->{
                binding.appBarMain.fab.visibility =View.GONE
                binding.navView.menu.clear()
                binding.navView.inflateMenu(R.menu.menu_main_guest)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_logout-> {
                authViewModel.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}