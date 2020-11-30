package ercanduman.visualizerestaurant.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ercanduman.visualizerestaurant.R
import org.junit.Rule
import org.junit.Test

/**
 * UI test cases for MainActivity.
 *
 * @author ERCAN DUMAN
 * @since 28.11.2020
 */
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

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

    /**
     * Search functionality related test cases
     */
    @Test
    fun test_search_button_visibility_then_click_and_check_if_search_layout_displayed() {
        onView(withId(R.id.main_toolbar_search_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.main_toolbar_search_icon)).perform(click())
        onView(withId(R.id.main_toolbar_search_cancel)).check(matches(isDisplayed()))
    }

    @Test
    fun test_check_search_layout_displayed_and_type_text_and_check_text_visibility() {
        onView(withId(R.id.main_toolbar_search_icon)).perform(click())
        onView(withId(R.id.main_toolbar_search_text_field)).check(matches(isDisplayed()))

        val someText = "SOME_TEXT"
        onView(withId(R.id.main_toolbar_search_text_field)).perform(typeText(someText))
        onView(withText(someText)).check(matches(isDisplayed()))
    }

    @Test
    fun test_search_by_invalid_text_and_check_not_found_view_displayed() {
        onView(withId(R.id.main_toolbar_search_icon)).perform(click())

        val invalidText = "INVALID_TEXT"
        onView(withId(R.id.main_toolbar_search_text_field)).perform(typeText(invalidText))
        onView(withId(R.id.main_not_found)).check(matches(isDisplayed()))
    }

    /**
     * RecyclerView related test cases
     */
    @Test
    fun test_check_if_recyclerView_displayed() {
        onView(withId(R.id.main_recycler_view_restaurants)).check(matches(isDisplayed()))
    }

    @Test
    fun test_check_is_recycler_view_items_can_be_clicked() {
        onView(withId(R.id.main_recycler_view_restaurants))
            .perform(actionOnItemAtPosition<AppAdapter.ItemViewHolder>(0, click()))
    }

    @Test
    fun test_search_by_invalid_text_and_check_recycler_view_hidden_and_NOT_FOUND_view_displayed() {
        onView(withId(R.id.main_toolbar_search_icon)).perform(click())

        val invalidText = "INVALID_TEXT"
        onView(withId(R.id.main_toolbar_search_text_field)).perform(typeText(invalidText))

        onView(withId(R.id.main_recycler_view_restaurants))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.main_not_found)).check(matches(isDisplayed()))
    }

    @Test
    fun test_search_for_restaurant_name_ROTI_and_check_if_item_displayed_on_recyclerview() {
        onView(withId(R.id.main_toolbar_search_icon)).perform(click())

        val inputText = "Roti"
        val restaurantName = "Roti Shop"
        onView(withId(R.id.main_toolbar_search_text_field)).perform(typeText(inputText))
        onView(withText(restaurantName)).check(matches(isDisplayed()))
    }
}