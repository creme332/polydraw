package com.github.creme332.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.view.tutorial.DrawCircleTutorial;
import com.github.creme332.view.tutorial.DrawLineTutorial;
import com.github.creme332.view.tutorial.GettingStartedTutorial;
import com.github.creme332.view.tutorial.TutorialCard;
import com.github.creme332.view.tutorial.TutorialPanel;

public class TutorialCenter extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private ArrayList<TutorialCard> tutorialCards = new ArrayList<>();
    private ArrayList<TutorialPanel> tutorialScreens = new ArrayList<>();

    /**
     * Layout used for screenContainer for swapping between tutorial center and
     * tutorial page.
     */
    private CardLayout cl = new CardLayout();

    /**
     * A container for all screens.
     */
    private JPanel screenContainer = new JPanel(cl);

    JPanel tutorialGrid;

    public static final String SEARCH_PLACEHOLDER = "Search Polydraw Tutorials";

    public TutorialCenter() {
        setLayout(new BorderLayout());

        initTutorialScreens();
        initTutorialCards();
        createTutorialListGrid();

        screenContainer.add(tutorialGrid, "tutorialCenter");

        // add tutorials
        for (TutorialPanel screen : tutorialScreens) {
            screenContainer.add(screen, screen.getTitle());
        }

        add(screenContainer);
    }

    public void showTutorial(String tutorialName) {
        cl.show(screenContainer, tutorialName);
    }

    private void initTutorialScreens() {
        tutorialScreens.add(new GettingStartedTutorial());
        tutorialScreens.add(new DrawLineTutorial());
        tutorialScreens.add(new DrawCircleTutorial());

    }

    private void initTutorialCards() {
        for (TutorialPanel screen : tutorialScreens) {
            tutorialCards.add(new TutorialCard(screen.getTitle(), null));
        }
    }

    /**
     * Must be called after initTutorialCards.
     */
    private void createTutorialListGrid() {
        tutorialGrid = new JPanel();
        tutorialGrid.setLayout(new BorderLayout());

        // Create the top panel with back button and search field
        JPanel topPanel = new JPanel(new BorderLayout());
        tutorialGrid.add(topPanel, BorderLayout.NORTH);

        // create top panel components
        backButton = new BackButton();
        topPanel.add(backButton, BorderLayout.WEST);

        searchField = createSearchField();
        topPanel.add(searchField, BorderLayout.CENTER);

        // Create the grid panel to hold GridItems
        final int GRID_GAP = 10; // px
        final int IDEAL_COL_COUNT = 2;
        final int ROW_COUNT = (int) Math.ceil(tutorialCards.size() / (double) IDEAL_COL_COUNT);

        JPanel gridPanel = new JPanel(new GridLayout(ROW_COUNT, IDEAL_COL_COUNT, GRID_GAP, GRID_GAP));
        gridPanel.setBorder(new EmptyBorder(new Insets(GRID_GAP, GRID_GAP, 0, 0)));

        // add tutorial cards to grid
        for (TutorialCard card : tutorialCards) {
            gridPanel.add(card);
        }

        // Wrap gridPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tutorialGrid.add(scrollPane, BorderLayout.CENTER);
    }

    private JTextField createSearchField() {
        JTextField field = new JTextField(SEARCH_PLACEHOLDER, 100);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(SEARCH_PLACEHOLDER)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(SEARCH_PLACEHOLDER);
                }
            }
        });

        return field;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public List<TutorialCard> getTutorialCards() {
        return tutorialCards;
    }

    public List<TutorialPanel> getTutorialScreens() {
        return tutorialScreens;
    }
}
