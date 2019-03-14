package com.clakestudio.pc.fizykor.flashcards

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.clakestudio.pc.fizykor.R
import com.clakestudio.pc.fizykor.util.InfoActivity
import com.clakestudio.pc.fizykor.util.obtainViewModel
import com.clakestudio.pc.fizykor.util.replaceFragmentInActivity
import com.clakestudio.pc.fizykor.util.setupActionBar
import kotlinx.android.synthetic.main.activity_flash_cards.*
import kotlinx.android.synthetic.main.app_bar_flash_cards.*

class FlashCardsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var flashCardsViewModel: FlashCardsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_cards)
        setSupportActionBar(toolbar)

        flashCardsViewModel = obtainViewModel().apply {

            // Interactions
        }
        setupViewFragment()
        setupNavigationDrawer()
        retriveIntentData()

        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            title = flashCardsViewModel.filtering
            setDisplayHomeAsUpEnabled(true)
        }


    }

    private fun retriveIntentData() {
        (findViewById<NavigationView>(R.id.nav_view)).setCheckedItem(intent.getIntExtra("CheckedItemIndex", 0))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupViewFragment() {

        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: replaceFragmentInActivity(FlashCardsFragment.newInstance(), R.id.contentFrame)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.flash_cards, menu)
        return true
    }

    private fun setupNavigationDrawer() {


        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))
    }

    private fun setupDrawerContent(navigationView: NavigationView) {


        navigationView.setNavigationItemSelectedListener { menuItem ->

            flashCardsViewModel.filterFlashCards(menuItem.title.toString())
            menuItem.isChecked = true
            toolbar.title = menuItem.title.toString()
            drawerLayout.closeDrawers()
            true

        }
        navigationView.menu.findItem(R.id.stale_i_przedrostki).isVisible = false

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.action_info -> {
                startActivity(Intent(this, InfoActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun obtainViewModel(): FlashCardsViewModel = obtainViewModel(FlashCardsViewModel::class.java)


}
