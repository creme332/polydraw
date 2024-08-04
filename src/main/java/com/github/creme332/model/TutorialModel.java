package com.github.creme332.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Information about a particular tutorial.
 */
public class TutorialModel {
    /**
     * Tutorial title.
     */
    private String title;

    /**
     * Keywords for tutorial. These keywords are used by the search engine to
     * identify a tutorial. All keywords are in lowercase.
     */
    private Set<String> keywords = new HashSet<>();

    public TutorialModel(String title) {
        this.title = title;

        // add each word in title as keyword
        for (String word : title.split(" ")) {
            addKeyword(word);
        }
    }

    /**
     * Adds a keyword to keyword list. Keyword must have at least 3 characters to be
     * added.
     * 
     * @param keyword
     */
    public void addKeyword(String keyword) {
        if (keyword.length() < 3)
            return;
        keywords.add(keyword.toLowerCase().trim());
    }

    public String getTitle() {
        return title;
    }

    public String[] getKeywords() {
        return keywords.toArray(new String[0]);
    }
}
