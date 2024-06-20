package com.github.creme332.view.tutorial;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.github.creme332.view.BackButton;

public class TutorialPanel extends JPanel {
    protected String title;
    protected JButton backButton = new BackButton();

    protected transient JTextPane textPane = new JTextPane();
    protected transient StyledDocument doc = textPane.getStyledDocument();

    // Define and set the default style
    protected transient Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    protected transient Style regular;
    protected transient Style bold;
    protected transient Style italic;
    protected transient Style imageStyle;

    public TutorialPanel(String title) {
        setLayout(new BorderLayout());
        this.title = title;

        textPane.setEditable(false); // Make it non-editable
        JScrollPane scrollPane = new JScrollPane(textPane);

        this.add(backButton, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

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
