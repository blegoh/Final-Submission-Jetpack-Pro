package me.yusufeka.moviecatalogue

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
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

class SeriesFragmentTest {

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
    fun loadSeries() {
        onView(withId(R.id.series)).perform(click())
        onView(allOf(withId(R.id.rvMovies), isDisplayed())).perform(
            scrollToPosition<ViewHolder>(10)
        )
    }

    @Test
    fun loadDetailSeries() {
        onView(withId(R.id.series)).perform(click())
        onView(allOf(withId(R.id.rvMovies), isDisplayed())).perform(
            actionOnItemAtPosition<ViewHolder>(0, click())
        )
        onView(withText(R.string.language))
            .check(matches(isDisplayed()))
        onView(withText(R.string.country))
            .check(matches(isDisplayed()))
        onView(withText(R.string.release))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvEpisode))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rvSeason))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadSearchSeries() {
        onView(withId(R.id.series)).perform(click())
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_src_text))
            .perform(ViewActions.replaceText("efgwefw qweqwe qwe"))
            .perform(ViewActions.pressImeActionButton())

        onView(withText(R.string.empty_search)).check(matches(isDisplayed()))

        onView(withId(R.id.search_src_text))
            .perform(ViewActions.replaceText("silicon"))
            .perform(ViewActions.pressImeActionButton())

        onView(allOf(withId(R.id.rvMovies), isDisplayed())).check(RecyclerViewNotEmptyAssertion())
    }

    @Test
    fun loadFav() {
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.viewPagerFavorite)).perform(swipeRight())

    }
}