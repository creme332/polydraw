package com.github.creme332.tests.utils;

import static org.junit.Assert.*;
import org.junit.Test;
import com.github.creme332.utils.FuzzySearching;

public class FuzzySearchingTest {

    @Test
    public void testLevenshteinDistance() {
        // Identical strings
        assertEquals(0, FuzzySearching.levenshteinDistance("kitten", "kitten"));
        
        // Completely different strings
        assertEquals(6, FuzzySearching.levenshteinDistance("kitten", "puppy"));
        
        // One empty string
        assertEquals(6, FuzzySearching.levenshteinDistance("kitten", ""));
        assertEquals(6, FuzzySearching.levenshteinDistance("", "kitten"));
        
        // Strings differing by one character
        assertEquals(1, FuzzySearching.levenshteinDistance("kitten", "sitten"));
        
        // Strings of different lengths
        assertEquals(3, FuzzySearching.levenshteinDistance("kitten", "kit"));
    }

    @Test
    public void testIsSimilar() {
        // Identical strings
        assertTrue(FuzzySearching.isSimilar("kitten", "kitten"));
        
        // Small difference (distance ≤ 3)
        assertTrue(FuzzySearching.isSimilar("kitten", "sitten"));
        assertTrue(FuzzySearching.isSimilar("kitten", "kitt"));
        
        // Larger difference (distance > 3)
        assertFalse(FuzzySearching.isSimilar("kitten", "puppy"));
        
        // One empty string
        assertFalse(FuzzySearching.isSimilar("kitten", ""));
        assertFalse(FuzzySearching.isSimilar("", "kitten"));
    }

    @Test
    public void testMatch() {
        String[] keywords = {"kitten", "puppy", "dog"};
        
        // Exact match
        assertTrue(FuzzySearching.match("kitten", keywords));
        
        // Similar match
        assertTrue(FuzzySearching.match("kitt", keywords));
        
        // No match
        assertFalse(FuzzySearching.match("elephant", keywords));
        
        // Case insensitivity
        assertTrue(FuzzySearching.match("KITTEN", keywords));
        
    }
}
