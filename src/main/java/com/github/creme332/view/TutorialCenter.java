package com.github.creme332.view;

import javax.swing.*;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.github.creme332.view.tutorial.GridItem;

public class TutorialCenter extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JPanel gridPanel;
    private List<GridItem> gridItems = new ArrayList<>();

    public JButton createBackButton() {
        JButton btn = new JButton(); // Back button with arrow icon
        btn.setBorderPainted(false);
        FontIcon icon = FontIcon.of(BootstrapIcons.ARROW_LEFT_CIRCLE);
        icon.setIconSize(40);
        btn.setIcon(icon);
        return btn;
    }

    public JTextField createSearchField() {
        JTextField field = new JTextField("Search Polydraw Tutorials", 100);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals("Search Polydraw Tutorials")) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText("Search Polydraw Tutorials");
                }
            }
        });

        return field;
    }

    public JButton createSearchButton() {
        JButton btn = new JButton("Search");
        FontIcon icon = FontIcon.of(BootstrapIcons.SEARCH);
        icon.setIconSize(30);
        btn.setIcon(icon);
        return btn;
    }

    public TutorialCenter() {
        setLayout(new BorderLayout());

        // Create the top panel with back button and search field
        JPanel topPanel = new JPanel(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        // create top panel components
        backButton = createBackButton();
        topPanel.add(backButton, BorderLayout.WEST);

        searchField = createSearchField();
        topPanel.add(searchField, BorderLayout.CENTER);

        searchButton = createSearchButton();
        topPanel.add(searchButton, BorderLayout.EAST);

        // Create the grid panel to hold GridItems
        gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Create GridItems and add them to the grid panel
        gridItems.add(new GridItem("Getting Started", null));
        gridItems.add(new GridItem("Draw Line", null));
        gridItems.add(new GridItem("Draw Circle", null));
        gridItems.add(new GridItem("Draw Polygon", null));
        gridItems.add(new GridItem("Rotating Polygons", null));
        gridItems.add(new GridItem("Translating Polygons", null));

        for (GridItem item : gridItems) {
            gridPanel.add(item);
        }

        // Wrap gridPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void filterGridItems(String query) {
        gridPanel.removeAll();
        if (query == null || query.trim().isEmpty()) {
            // If the query is empty, display all items
            for (GridItem item : gridItems) {
                gridPanel.add(item);
            }
        } else {
            // Otherwise, filter the items based on the query
            for (GridItem item : gridItems) {
                // if (item.getHeading().toLowerCase().contains(query.toLowerCase())) {
                // gridPanel.add(item);
                // }
            }
        }
        gridPanel.repaint();
    }
}
