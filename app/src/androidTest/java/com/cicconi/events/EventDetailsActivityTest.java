package com.cicconi.events;

import android.content.Context;
import android.content.Intent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.cicconi.events.database.Event;
import com.google.android.exoplayer2.SimpleExoPlayer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class) public class EventDetailsActivityTest {

    private static final String EVENT_TITLE = "This is an event";
    private static final String EVENT_DESCRIPTION = "This is the description of the event";

    @Rule
    public ActivityTestRule<EventDetailsActivity> mActivityTestRule =
        new ActivityTestRule<EventDetailsActivity>(EventDetailsActivity.class) {
            @Override
            protected Intent getActivityIntent() {
                Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                Intent result = new Intent(targetContext, EventDetailsActivity.class);
                result.putExtra(Constants.EXTRA_EVENT, mockEvent());
                return result;
            }
        };

    @Test
    public void verifyStepData_isPresent() {
        onView(withId(R.id.event_title)).check(matches(withText(EVENT_TITLE)));
        onView(withId(R.id.event_description)).check(matches(withText(EVENT_DESCRIPTION)));
    }

    private Event mockEvent() {
        return new Event(
            1,
            null,
            null,
            "Place de la République",
            null,
            null,
            "Animations",
            EVENT_TITLE,
            null,
            1594909941L,
            1597536000L,
            null,
            EVENT_DESCRIPTION,
            "contact@contact.com",
            null,
            null,
            "Paris",
            "gratuit",
            "www.url.com",
            null,
            "75001",
            "10 euros",
            "0102030405",
            "www.contact.com",
            "access@contact.com",
            "www.facebook.com",
            "This is the contact name",
            "0102030406",
            "Ligne 10",
            false
        );
    }
}
