package com.github.creme332.tests.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import com.github.creme332.model.TutorialModel;

public class TutorialModelTest {

    @Test
    public void testTitle() {
        String title = "Java Programming Basics";
        TutorialModel tutorial = new TutorialModel(title);
        assertEquals(title, tutorial.getTitle());
    }

    @Test
    public void testKeywordsFromTitle() {
        String title = "Java Programming Basics";
        TutorialModel tutorial = new TutorialModel(title);
        Set<String> expectedKeywords = new HashSet<>(Arrays.asList("java", "programming", "basics"));
        Set<String> actualKeywords = new HashSet<>(Arrays.asList(tutorial.getKeywords()));
        assertEquals(expectedKeywords, actualKeywords);
    }

    @Test
    public void testAddValidKeyword() {
        TutorialModel tutorial = new TutorialModel("Test Title");
        tutorial.addKeyword("keyword");
        Set<String> expectedKeywords = new HashSet<>(Arrays.asList("test", "title", "keyword"));
        Set<String> actualKeywords = new HashSet<>(Arrays.asList(tutorial.getKeywords()));
        assertEquals(expectedKeywords, actualKeywords);
    }

    @Test
    public void testAddInvalidKeyword() {
        TutorialModel tutorial = new TutorialModel("Test Title");
        tutorial.addKeyword("kw");
        Set<String> expectedKeywords = new HashSet<>(Arrays.asList("test", "title"));
        Set<String> actualKeywords = new HashSet<>(Arrays.asList(tutorial.getKeywords()));
        assertEquals(expectedKeywords, actualKeywords);
    }

    @Test
    public void testCaseInsensitivityOfKeywords() {
        TutorialModel tutorial = new TutorialModel("Test Title");
        tutorial.addKeyword("Keyword");
        tutorial.addKeyword("KEYWORD");
        tutorial.addKeyword("keyword");
        Set<String> expectedKeywords = new HashSet<>(Arrays.asList("test", "title", "keyword"));
        Set<String> actualKeywords = new HashSet<>(Arrays.asList(tutorial.getKeywords()));
        assertEquals(expectedKeywords, actualKeywords);
    }
}
