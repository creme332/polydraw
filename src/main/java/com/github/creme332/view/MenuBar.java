package com.github.creme332.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Mode;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class MenuBar extends JMenuBar {
    private JButton sidebarButton;
    private JButton helpButton;

    private List<JMenu> jmenus = new ArrayList<>();
    public static final int HEIGHT = 70;

    public MenuBar(MenuModel[] menus) {

        setPreferredSize(new Dimension(getWidth(), HEIGHT));
        setBorder(new EmptyBorder(new Insets(7, 0, 7, 0)));
        setBackground(new Color(248, 248, 248));

        // add menus to menubar
        for (MenuModel menuModel : menus) {
            JMenu menu = new RoundedMenu();
            menu.setOpaque(false);
            jmenus.add(menu);
            menu.setIcon(menuModel.getActiveItem().getIcon());
            menu.setToolTipText(menuModel.getActiveItem().getTitle());

            for (Mode item : menuModel.getItems()) {
                JMenuItem menuItem = new JMenuItem(item.getTitle(), item.getIcon());
                menu.add(menuItem);
                menuItem.addActionListener(e -> menu.setToolTipText(item.getTitle()));
            }
            this.add(Box.createHorizontalStrut(10));
            this.add(menu);
        }

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftPanel.setOpaque(false);

        // undo button
        JButton btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);
        btn.setToolTipText("Undo");

        // redo button
        btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);
        btn.setToolTipText("Redo");

        // help button
        helpButton = new JButton();
        helpButton.setIcon(FontIcon.of(BootstrapIcons.QUESTION_CIRCLE, 37));
        helpButton.setBorderPainted(false);
        helpButton.setToolTipText("Help");
        leftPanel.add(helpButton);

        // sidebar menu button
        sidebarButton = new JButton();
        sidebarButton.setIcon(FontIcon.of(BootstrapIcons.LIST, 40));
        sidebarButton.setBorderPainted(false);
        leftPanel.add(sidebarButton);
        sidebarButton.setToolTipText("Toggle sidebar");

        this.add(leftPanel);
    }

    /**
     * Use this method to get the i-th JMenu in the menubar. Do not use the default
     * getMenu() as it does not account for empty spaces and counts buttons.
     * 
     * @param i
     * @return
     */
    public JMenu getMyMenu(int i) {
        return jmenus.get(i);
    }

    public JButton getSideBarButton() {
        return sidebarButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }
}
