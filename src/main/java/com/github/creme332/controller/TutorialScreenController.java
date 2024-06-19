package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Screen;
import com.github.creme332.model.TutorialScreenModel;
import com.github.creme332.utils.FuzzySearching;
import com.github.creme332.view.TutorialCenter;
import com.github.creme332.view.tutorial.TutorialCard;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for TutorialScreen.
 */
public class TutorialScreenController {
    private TutorialCenter view;
    private TutorialScreenModel model;
    private AppState appState;

    public TutorialScreenController(AppState appState, TutorialCenter view) {
        this.view = view;
        this.model = appState.getTutorialScreenModel();
        this.appState = appState;
        initialize();
    }

    private void initialize() {
        // Add action listener for the back button
        view.getBackButton().addActionListener(e -> appState.switchScreen(Screen.MAIN_SCREEN));

        // Add action listener for the search field and search button
        view.getSearchField().addActionListener(e -> handleSearch());
        view.getSearchButton().addActionListener(e -> handleSearch());
    }

    private void handleSearch() {
        String query = view.getSearchField().getText().trim();
        filterGridItems(query);
    }

    private void filterGridItems(String query) {
        List<TutorialCard> filteredItems;
        if (query.isEmpty() || query.equals("Search Polydraw Tutorials")) {
            filteredItems = model.getGridItems();
        } else {
            filteredItems = model.getGridItems().stream()
                    .filter(item -> FuzzySearching.isSimilar(item.getHeading().toLowerCase(), query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        view.updateGrid(filteredItems);
    }
}
