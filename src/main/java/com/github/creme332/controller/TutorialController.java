package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Screen;
import com.github.creme332.model.TutorialScreenModel;
import com.github.creme332.view.TutorialCenter;
import com.github.creme332.view.tutorial.GridItem;
import java.util.List;
import java.util.stream.Collectors;

public class TutorialController {
    private TutorialCenter view;
    private TutorialScreenModel model;
    private AppState appState;

    public TutorialController(AppState appState, TutorialCenter view) {
        this.view = view;
        this.model = appState.getTutorialScreenModel();
        this.appState = appState;
        initialize();
    }

    private void initialize() {
        // Add action listener for the back button
        view.getBackButton().addActionListener(e -> appState.switchScreen(Screen.MAIN_SCREEN));

        // Add action listener for the search field
        view.getSearchField().addActionListener(e -> handleSearch());
    }

    private void handleSearch() {
        String query = view.getSearchField().getText();
        filterGridItems(query);
    }

    private void filterGridItems(String query) {
        List<GridItem> filteredItems;
        if (query == null || query.trim().isEmpty()) {
            filteredItems = model.getGridItems();
        } else {
            filteredItems = model.getGridItems().stream()
                    .filter(item -> item.getHeading().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        view.updateGrid(filteredItems);
    }
}
