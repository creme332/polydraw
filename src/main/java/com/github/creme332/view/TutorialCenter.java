package com.github.creme332.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.github.creme332.view.tutorial.GridItem;

public class TutorialCenter extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JPanel gridPanel;
    private List<GridItem> gridItems;

    public TutorialCenter() {
        setLayout(new BorderLayout());

        // Create the top panel with back button and search field
        JPanel topPanel = new JPanel(new BorderLayout());
        backButton = new JButton("<"); // Back button with arrow icon
        JPanel searchPanel = new JPanel(new BorderLayout());

        searchField = new JTextField("Search Polydraw Tutorials");
        searchField.setPreferredSize(new Dimension(200, 30)); // Adjusted size
        searchField.setForeground(Color.GRAY);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search Polydraw Tutorials")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search Polydraw Tutorials");
                }
            }
        });

        searchButton = new JButton("Enter");
        searchButton.setPreferredSize(new Dimension(75, 30)); // Adjusted size

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterGridItems(searchField.getText());
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Create the grid panel to hold GridItems
        gridPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns, 10px horizontal and vertical gaps
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Make grid lines black
        gridItems = new ArrayList<>();

        // Create GridItems and add them to the grid panel
        gridItems.add(new GridItem("Getting Started", "path/to/getting_started_image.png"));
        gridItems.add(new GridItem("Draw Line", "path/to/draw_line_image.png"));
        gridItems.add(new GridItem("Draw Circle", "path/to/draw_circle_image.png"));
        gridItems.add(new GridItem("Draw Polygon", "path/to/draw_polygon_image.png"));
        gridItems.add(new GridItem("Rotating Polygons", "path/to/rotating_polygons_image.png"));
        gridItems.add(new GridItem("Translating Polygons", "path/to/translating_polygons_image.png"));

        for (GridItem item : gridItems) {
            gridPanel.add(item);
        }

        // Wrap gridPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Ensure the grid panel is scrollable by setting a preferred size
        gridPanel.setPreferredSize(new Dimension(800, 600)); // Adjust size as necessary
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
                if (item.getHeading().toLowerCase().contains(query.toLowerCase())) {
                    gridPanel.add(item);
                }
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}
