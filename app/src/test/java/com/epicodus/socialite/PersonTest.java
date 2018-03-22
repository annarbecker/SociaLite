package com.epicodus.socialite;

import com.epicodus.socialite.models.Person;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by arbecker on 3/21/18.
 */

public class PersonTest {

    @Test
    public void test_basicPersonAssertions() {
        String name = "Joe";
        String event = "Birthday Party";
        Person newPerson = new Person(name, event);

        assertThat(newPerson, hasProperty("name", is(name)));
        assertThat(newPerson, hasProperty("event", is(event)));
        // A new Person is instantiated with rsvp set to false
        assertThat(newPerson, hasProperty("rsvp", is(false)));
    }
}
