package com.tav.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
    private Cat cat;
    private List<String> hobbies;
    private List<Integer> grades;
    private List<Show> favouriteTVShows;
}
