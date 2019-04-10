package com.clakestudio.pc.fizykor.flashcards

import android.content.Intent
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

class FlashCardsActivityTest {

    @Rule
    @JvmField
    var activityRule = object: ActivityTestRule<FlashCardsActivity>(FlashCardsActivity::class.java) {

        override fun getActivityIntent() = Intent().apply {
            putExtra("Filtering", "Dynamika")
            putExtra("CheckedItemIndex", 1)
        }

    }


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
    fun isCorrectTitleDisplayedOnToolbarAfterReceivingIntent() {
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