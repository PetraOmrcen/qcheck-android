package com.example.myapplication.ui

import com.example.myapplication.ui.search.SearchFragment
import junit.framework.TestCase
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.myapplication.R
import com.example.myapplication.atPosition
import org.junit.Before
import org.junit.Test

class SearchFragmentTest : TestCase() {

    private lateinit var scenario: FragmentScenario<SearchFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun testSearching() {
        //Espresso Matcher and Action
        onView(withId(R.id.simpleSearchView)).perform(typeText("NSK"))
        Espresso.closeSoftKeyboard()

        //Assertion
        onView(withId(R.id.listview))
            .check(matches(atPosition(0, withText("NSK"))))
    }
}