package com.github.creme332.view.tutorial;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.common.BackButton;

public class TutorialCenter extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private ArrayList<TutorialCard> tutorialCards = new ArrayList<>();
    private ArrayList<AbstractTutorial> tutorialScreens = new ArrayList<>();
    JPanel gridPanel;
    /**
     * Layout used for screenContainer for swapping between tutorial center and
     * tutorial page.
     */
    private CardLayout cl = new CardLayout();

    /**
     * A container for all screens.
     */
    private JPanel screenContainer = new JPanel(cl);

    /**
     * Container for tutorial cards and search bar.
     */
    JPanel mainTutorialScreen;

    public static final String SEARCH_PLACEHOLDER = "Search Polydraw Tutorials";

    /**
     * Number of columns in tutorial card grid.
     */
    int gridColCount = 2;

    /**
     * Gap between tutorial cards
     */
    static final int TUTORIAL_CARD_GAP = 50;

    public TutorialCenter() {
        setLayout(new BorderLayout());

        initTutorialScreens();
        initTutorialCards();
        createTutorialListGrid();

        screenContainer.add(mainTutorialScreen, "tutorialCenter");

        // add tutorials
        for (AbstractTutorial screen : tutorialScreens) {
            screenContainer.add(screen, screen.getTitle());
        }

        add(screenContainer);

        mainTutorialScreen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gridColCount = (int) Math
                        .floor(mainTutorialScreen.getWidth() / (TutorialCard.DIMENSION.getWidth() + TUTORIAL_CARD_GAP));
                gridColCount = Math.max(gridColCount, 1);
                refreshGrid(getTutorialCards());
            }
        });
    }

    public void showTutorial(String tutorialName) {
        cl.show(screenContainer, tutorialName);
    }

    private void initTutorialScreens() {
        try {
            tutorialScreens.add(new GettingStartedTutorial());
            tutorialScreens.add(new DrawLineTutorial());
            tutorialScreens.add(new DrawCircleTutorial());
            tutorialScreens.add(new KeyboardTutorial());
            tutorialScreens.add(new DrawEllipseTutorial());
            tutorialScreens.add(new DrawPolygonTutorial());
        } catch (InvalidPathException | InvalidIconSizeException | BadLocationException e) {
            e.printStackTrace();
            System.exit(ABORT);
        }
    }

    private void initTutorialCards() {
        for (AbstractTutorial screen : tutorialScreens) {
            tutorialCards.add(new TutorialCard(screen.getTitle(), screen.getPreviewIcon()));
        }
    }

    /**
     * Must be called after initTutorialCards.
     */
    private void createTutorialListGrid() {
        mainTutorialScreen = new JPanel();
        mainTutorialScreen.setLayout(new BorderLayout());

        // Create the top panel with back button and search field
        JPanel topPanel = new JPanel(new BorderLayout());
        mainTutorialScreen.add(topPanel, BorderLayout.NORTH);
        topPanel.setBorder(new EmptyBorder(new Insets(10, 0, 0, 0)));

        // create top panel components
        backButton = new BackButton();
        topPanel.add(backButton, BorderLayout.WEST);

        searchField = createSearchField();
        topPanel.add(searchField, BorderLayout.CENTER);

        // Create the grid panel to hold GridItems
        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(Color.white);
        gridPanel.setBorder(new EmptyBorder(new Insets(10, 10, 0, 0)));

        // add tutorial cards to grid
        refreshGrid(tutorialCards);

        // Wrap gridPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainTutorialScreen.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshGrid(List<TutorialCard> visibleTutorialCards) {
        gridPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, TUTORIAL_CARD_GAP, TUTORIAL_CARD_GAP, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        int col = 0;

        for (TutorialCard card : visibleTutorialCards) {
            gbc.gridx = col;
            gbc.gridy = row;

            gridPanel.add(card, gbc);

            col++;
            if (col == gridColCount) {
                col = 0;
                row++;
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();

        /**
         * sync toolkit to prevent frame rate issues on linux.
         * 
         * Reference:
         * https://stackoverflow.com/questions/46626715/how-do-i-properly-render-at-a-high-frame-rate-in-pure-java
         */
        Toolkit.getDefaultToolkit().sync();
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

    public List<AbstractTutorial> getTutorialScreens() {
        return tutorialScreens;
    }
}
