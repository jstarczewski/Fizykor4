package com.clakestudio.pc.fizykor.equations

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.clakestudio.pc.fizykor.R
import com.clakestudio.pc.fizykor.flashcards.FlashCardsActivity
import com.clakestudio.pc.fizykor.util.InfoActivity
import com.clakestudio.pc.fizykor.util.obtainViewModel
import com.clakestudio.pc.fizykor.util.replaceFragmentInActivity
import com.clakestudio.pc.fizykor.util.setupActionBar
import kotlinx.android.synthetic.main.activity_equations.*
import kotlinx.android.synthetic.main.app_bar_equations.*

class EquationsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var equationsViewModel: EquationsViewModel
    private var checkedItemId: Int = R.id.kinematyka

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equations)
        setSupportActionBar(toolbar)


        setupNavigationDrawer()
        setupViewFragment()

        equationsViewModel = obtainViewModel().apply {

            flashCardsEvent.observe(this@EquationsActivity, Observer<Void> {
                this@EquationsActivity.openFlashCards()
            })

        }

        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            title = resources.getString(R.string.kinematyka)
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.equations, menu)
        return true
    }

    private fun openFlashCards() {
        val intent = Intent(this, FlashCardsActivity::class.java)
        intent.putExtra("Filtering", (findViewById<NavigationView>(R.id.nav_view).checkedItem?.title.toString()))
        intent.putExtra("CheckedItemIndex", checkedItemId)
        startActivity(intent)
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: replaceFragmentInActivity(EquationsFragment.newInstance(), R.id.contentFrame)
    }


    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setUpDrawerContent(findViewById(R.id.nav_view))
    }

    @SuppressLint("RestrictedApi")
    private fun setUpDrawerContent(navigationView: NavigationView) {

        navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.stale -> fab.hide()
                R.id.przedrostki -> fab.hide()
                else -> fab.show()
            }
            equationsViewModel.filterEquations(menuItem.title.toString())
            menuItem.isChecked = true
            toolbar.title = menuItem.title.toString()
            checkedItemId = menuItem.itemId
            drawerLayout.closeDrawers()
            true
        }
        navigationView.menu.findItem(R.id.stale_i_przedrostki).isVisible = true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                android.R.id.home -> {
                    drawerLayout.openDrawer(GravityCompat.START)
                    true
                }
                R.id.action_info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    fun obtainViewModel(): EquationsViewModel = obtainViewModel(EquationsViewModel::class.java)


}
