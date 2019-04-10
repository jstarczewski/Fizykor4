package com.clakestudio.pc.fizykor.equations

import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.clakestudio.pc.fizykor.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class EquationsActivityTest {


    @Rule
    @JvmField
    var activityRule = ActivityTestRule(EquationsActivity::class.java)


    @Test
    fun clickedHomeIconDrawerOpened() {
        checkDrawerIsNotOpen()
        clickHomeToOpenDrawer()
        checkDrawerIsOpen()
    }

    @Test
    fun backPressButtonClickedTwiceWhenDrawerIsOpenedToCloseApp() {
        clickHomeToOpenDrawer()
        onView(isRoot()).perform(ViewActions.pressBack())
        checkDrawerIsNotOpen()
        assertEquals(activityRule.activity.isFinishing, false)
        assertEquals(activityRule.activity.isDestroyed, false)
    }

    @Test
    fun drawerItemSelectedThenToolbarTextChanged() {
        clickHomeToOpenDrawer()
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.dynamika))

        /**
         * Dirty solution by hardcoding movie genre
         * */

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar)))).check(matches(withText("Dynamika")))
    }

    private fun clickHomeToOpenDrawer() {
        onView(withContentDescription(activityRule.activity.findViewById<Toolbar>(R.id.toolbar).navigationContentDescription as String)).perform(
            click()
        )
    }

    private fun checkDrawerIsOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.START)))
    }

    private fun checkDrawerIsNotOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START)))
    }

}