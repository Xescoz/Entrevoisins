
package com.openclassrooms.entrevoisins.neighbour_list;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.contrib.ViewPagerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withDrawable;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    private NeighbourApiService mApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getNeighbourApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0))
                .check(matches(hasMinimumChildCount(1)));

    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click on a neighbour the detail is open
     */
    @Test
    public void myNeighboursList_selectAction_shouldOpenDetailActivity(){
        Neighbour neighbour = mApiService.getNeighbours().get(0);
        // Given : We open the detail activity at position 0 when we click on the layout
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : the name in the activity is the same as the layout
        onView(withId(R.id.item_avatar_name)).check(matches(withText(neighbour.getName())));
    }

    /**
     * Favorite tab should only contain favorites neighbour
     */
    @Test
    public void myNeighboursList_favoriteTab_shouldOnlyContainFavoriteNeighbour(){
        // First we click on position 0
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // in the detail activity it open we click on the add favorite button
        onView(withId(R.id.add_favorite)).perform(click());
        // then we click on the back button
        onView(withId(R.id.item_detail_back_button)).perform(click());
        // we scroll right to open the other tab
        onView(withId(R.id.container)).perform(ViewPagerActions.scrollRight());
        // we click on position 0
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // the star of the button is yellow so it's a favorite
        onView(withId(R.id.add_favorite)).check(matches(withDrawable(R.drawable.ic_star_yellow_24dp)));
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };

    }
}