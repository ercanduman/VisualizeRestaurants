package ercanduman.visualizerestaurant.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ercanduman.visualizerestaurant.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI test cases for MainActivity.
 *
 * @author ERCAN DUMAN
 * @since 28.11.2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    /**
     * Creating a rule for activity scenario will run Before() methods, then the Test method, and finally any After() methods.
     *  This way no need to use below code for all test cases:
     *      ActivityScenario.launch(MainActivity::class.java)
     */
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_launch_main_activity() {
        /**
         * ActivityScenario provides functionality to start an Activity for testing.
         *
         * Launch() : Launches an activity of a given class and constructs ActivityScenario with the activity.
         */
        // ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_launch_activity_and_check_its_title_view_displayed() {
        onView(withId(R.id.main_material_toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_launch_activity_and_check_its_title_has_correct_text() {
        onView(withId(R.id.main_material_toolbar)).check(matches(isDisplayed()))
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
    }
}