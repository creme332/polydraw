package com.github.creme332.controller;


import com.github.creme332.model.Screen;
import com.github.creme332.view.TutorialCenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialController {
    private TutorialCenter view;

    public TutorialController(TutorialCenter view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        // Add action listener for the back button
        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackButton();
            }
        });

        // Add action listener for the search field
        view.getSearchField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });
    }

    private void handleBackButton() {
        // TODO: Go back to the main screen
        // Screen.MAIN_SCREEN;
    }

    private void handleSearch() {
        String query = view.getSearchField().getText();
        view.filterGridItems(query);
    }
}
