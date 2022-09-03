package com.tav;

import com.tav.util.JsonUtil;
import com.tav.model.Cat;
import com.tav.model.Person;
import com.tav.model.Show;

import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        final Person person = new Person(
                "Tav",
                26,
                new Cat("Hatul", 2.45f),
                new ArrayList<>(Arrays.asList("Hiking", "Surfing")),
                new ArrayList<>(Arrays.asList(90, 75)),
                new ArrayList<>(Arrays.asList(
                        new Show("Mr. Robot", 2015),
                        new Show("Game of Thrones", 2011)
                ))
        );

        String result = null;
        try {
            result = JsonUtil.serialize(person);
        } catch (final Exception e) {
            System.err.printf("Failed to serialize object: %s%n", person);
        }

        System.out.println(result);
    }
}
