package com.github.creme332.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Screen;
import com.github.creme332.utils.FuzzySearching;
import com.github.creme332.view.tutorial.TutorialCard;
import com.github.creme332.view.tutorial.TutorialCenter;
import com.github.creme332.view.tutorial.AbstractTutorial;

/**
 * Main controller for TutorialScreen.
 */
public class TutorialScreenController {
    private TutorialCenter view;
    private String currentView = "tutorialCenter";

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
        List<AbstractTutorial> tutorialScreens = view.getTutorialScreens();

        for (int i = 0; i < tutorialCards.size(); i++) {
            TutorialCard tutorialCard = tutorialCards.get(i);

            final int index = i;
            tutorialCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    AbstractTutorial tutorialPanel = tutorialScreens.get(index);
                    view.showTutorial(tutorialPanel.getTitle());
                    currentView = tutorialPanel.getTitle();
                }
            });

            AbstractTutorial tutorialPanel = tutorialScreens.get(index);
            tutorialPanel.getBackButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    view.showTutorial("tutorialCenter");
                }
            });

        }

        initializeKeyBindings(appState);
    }

    private void initializeKeyBindings(AppState appState) {
        JComponent rootPane = (JComponent) view.getRootPane();

        // Bind the "Esc" key to go back
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                "goBack");
        rootPane.getActionMap().put("goBack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tutorialShown()) {
                    view.showTutorial("tutorialCenter");
                    currentView = "tutorialCenter";
                } else {
                    appState.switchScreen(Screen.MAIN_SCREEN);
                }
            }
        });
    }

    /**
     * 
     * @return True if expanded tutorial is currently being shown. False otherwise
     *         if tutorial grid
     *         is shown.
     */
    public boolean tutorialShown() {
        return !currentView.equals("tutorialCenter");
    }

    /**
     * Determines which cards should be displayed based on search criteria and
     * updates view.
     */
    private void handleSearch() {
        final String searchQuery = view.getSearchField().getText().trim();

        final boolean showAllCards = searchQuery.length() < 3
                || searchQuery.isEmpty();

        if (showAllCards) {
            view.refreshGrid(view.getTutorialCards());
            return;
        }

        final ArrayList<TutorialCard> visibleTutorialCards = new ArrayList<>();

        for (int i = 0; i < view.getTutorialScreens().size(); i++) {
            AbstractTutorial tutorial = view.getTutorialScreens().get(i);

            if (FuzzySearching.match(searchQuery, tutorial.getModel().getKeywords())) {
                visibleTutorialCards.add(view.getTutorialCards().get(i));
            }
        }

        view.refreshGrid(visibleTutorialCards);
    }
}
