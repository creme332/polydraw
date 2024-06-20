package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Screen;
import com.github.creme332.utils.FuzzySearching;
import com.github.creme332.view.TutorialCenter;
import com.github.creme332.view.tutorial.TutorialCard;
import com.github.creme332.view.tutorial.TutorialPanel;

/**
 * Controller for TutorialScreen.
 */
public class TutorialScreenController {
    private TutorialCenter view;

    public TutorialScreenController(AppState appState, TutorialCenter view) {
        this.view = view;

        // Add action listener for the back button
        view.getBackButton().addActionListener(e -> appState.switchScreen(Screen.MAIN_SCREEN));

        // Add action listener for the search field and search button
        view.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleSearch();
            }
        });

        List<TutorialCard> tutorialCards = view.getTutorialCards();
        List<TutorialPanel> tutorialScreens = view.getTutorialScreens();

        for (int i = 0; i < tutorialCards.size(); i++) {
            TutorialCard tutorialCard = tutorialCards.get(i);

            final int index = i;
            tutorialCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    TutorialPanel tutorialPanel = tutorialScreens.get(index);
                    view.showTutorial(tutorialPanel.getTitle());
                }
            });

            TutorialPanel tutorialPanel = tutorialScreens.get(index);
            tutorialPanel.getBackButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    view.showTutorial("tutorialCenter");
                }
            });

        }

    }

    private void handleSearch() {
        String query = view.getSearchField().getText().trim();

        boolean showAllCards = query.length() < 3 ||
                query.equals(TutorialCenter.SEARCH_PLACEHOLDER)
                || query.isEmpty();

        for (TutorialCard card : view.getTutorialCards()) {
            card.setVisible(
                    showAllCards ||
                            FuzzySearching.isSimilar(card.getHeading().toLowerCase(), query.toLowerCase()));
        }
    }
}
