package me.yusufeka.moviecatalogue

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieFragmentTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(allOf(withId(R.id.rvMovies), isDisplayed())).perform(
            scrollToPosition<ViewHolder>(19)
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(allOf(withId(R.id.rvMovies), isDisplayed())).perform(
            actionOnItemAtPosition<ViewHolder>(0, click())
        )
        onView(withText(R.string.language))
            .check(matches(isDisplayed()))
        onView(withText(R.string.revenue))
            .check(matches(isDisplayed()))
        onView(withText(R.string.release))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadSearchMovie() {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_src_text))
            .perform(replaceText("efgwefw qweqwe qwe"))
            .perform(pressImeActionButton())

        onView(withText(R.string.empty_search)).check(matches(isDisplayed()))

        onView(withId(R.id.search_src_text))
            .perform(replaceText("doraemon"))
            .perform(pressImeActionButton())

        onView(allOf(withId(R.id.rvMovies), isDisplayed())).check(RecyclerViewNotEmptyAssertion())
    }

    @Test
    fun loadFav() {
        onView(withId(R.id.favorite)).perform(click())
        onView(allOf(withId(R.id.rvMovies), isDisplayed())).check(matches(isDisplayed()))
    }

}