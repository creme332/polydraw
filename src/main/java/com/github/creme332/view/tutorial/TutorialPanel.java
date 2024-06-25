package com.github.creme332.view.tutorial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.view.BackButton;

public class TutorialPanel extends JPanel {
    /**
     * Tutorial title.
     */
    private String title;

    protected transient TutorialModel model;

    protected JButton backButton = new BackButton();
    protected JPanel bodyPanel;
    protected transient JTextPane textPane = new JTextPane();
    protected transient StyledDocument doc = textPane.getStyledDocument();

    // Define and set the default style
    protected transient Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    protected transient Style regular;
    protected transient Style bold;
    protected transient Style italic;
    protected transient Style imageStyle;

    protected transient Icon mainIcon;

    public TutorialPanel(TutorialModel model, Icon icon) {
        setLayout(new BorderLayout());

        this.model = model;
        this.mainIcon = icon;

        this.title = model.getTitle();

        // use center alignment for text
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        initStyles();
        textPane.setEditable(false); // Make it non-editable

        JPanel headerPanel = createHeaderPanel();
        bodyPanel = createBodyPanel();
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(bodyPanel, BorderLayout.CENTER);
    }

    public JPanel createBodyPanel() {
        bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBorder(new EmptyBorder(new Insets(0, 100, 0, 100)));

        JScrollPane scrollPane = new JScrollPane(textPane);
        bodyPanel.add(scrollPane, BorderLayout.CENTER);

        return bodyPanel;
    }

    public JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // add components to header
        headerPanel.setBorder(new EmptyBorder(new Insets(10, 0, 0, 0)));
        headerPanel.add(backButton, BorderLayout.WEST);

        JPanel titleContainer = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty("FlatLaf.styleClass", "h4");
        titleContainer.add(titleLabel);

        headerPanel.add(titleContainer);
        return headerPanel;
    }

    public void initStyles() {
        // Define and set the default style
        defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        imageStyle = textPane.addStyle("imageStyle", null);

        // Add some styles to the text pane
        regular = textPane.addStyle("regular", defaultStyle);
        StyleConstants.setFontFamily(regular, "SansSerif");

        bold = textPane.addStyle("bold", regular);
        StyleConstants.setBold(bold, true);

        italic = textPane.addStyle("italic", regular);
        StyleConstants.setItalic(italic, true);
    }

    public TutorialModel getModel() {
        return model;
    }

    public Icon getMainIcon() {
        return mainIcon;
    }

    /**
     * 
     * @return Title of a tutorial
     */
    public String getTitle() {
        return title;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
