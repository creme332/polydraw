package com.github.creme332.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;
import java.awt.*;
import java.util.List;
import com.github.creme332.view.tutorial.TutorialCard;
import com.github.creme332.model.TutorialScreenModel;

public class TutorialCenter extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JPanel gridPanel;
    private JScrollPane scrollPane;

    public TutorialCenter(TutorialScreenModel model) {
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
        final int GRID_GAP = 10; // px
        gridPanel = new JPanel(new GridLayout(2, 2, GRID_GAP, GRID_GAP));
        gridPanel.setBorder(new EmptyBorder(new Insets(GRID_GAP, GRID_GAP, 0, 0)));
        populateGrid(model.getGridItems());

        // Wrap gridPanel in a JScrollPane to make it scrollable
        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createBackButton() {
        JButton btn = new JButton(); // Back button with arrow icon
        btn.setBorderPainted(false);
        FontIcon icon = FontIcon.of(BootstrapIcons.ARROW_LEFT_CIRCLE);
        icon.setIconSize(40);
        btn.setIcon(icon);
        return btn;
    }

    private JTextField createSearchField() {
        String placeholder = "Search Polydraw Tutorials";
        JTextField field = new JTextField(placeholder, 100);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });

        return field;
    }

    private JButton createSearchButton() {
        JButton btn = new JButton("Search");
        FontIcon icon = FontIcon.of(BootstrapIcons.SEARCH);
        icon.setIconSize(30);
        btn.setIcon(icon);
        return btn;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void updateGrid(List<TutorialCard> gridItems) {
        gridPanel.removeAll();
        populateGrid(gridItems);
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void populateGrid(List<TutorialCard> gridItems) {
        for (TutorialCard item : gridItems) {
            gridPanel.add(item);
        }
    }
}
