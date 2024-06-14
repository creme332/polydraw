package com.github.creme332.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.model.MenuItemModel;
import com.github.creme332.model.MenuModel;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class MenuBar extends JMenuBar {
    private JButton sidebarButton;
    private JButton guidelinesButton; // Button to toggle guidelines

    public MenuBar(MenuModel[] menus) {
        // add menus to menubar
        for (MenuModel menuModel : menus) {
            JMenu menu = new JMenu();
            menu.setIcon(menuModel.getActiveItem().getIcon());

            for (MenuItemModel item : menuModel.getItems()) {
                JMenuItem menuItem = new JMenuItem(item.getName(), item.getIcon());
                menu.add(menuItem);
            }

            this.add(menu);
        }

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftPanel.setOpaque(false);

        // undo button
        JButton btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);

        // redo button
        btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);

        // guidelines button
        guidelinesButton = new JButton();
        guidelinesButton.setIcon(FontIcon.of(BootstrapIcons.GRID_3X3, 37));
        guidelinesButton.setBorderPainted(false);
        leftPanel.add(guidelinesButton);

        // sidebar menu button
        sidebarButton = new JButton();
        sidebarButton.setIcon(FontIcon.of(BootstrapIcons.LIST, 40));
        sidebarButton.setBorderPainted(false);
        leftPanel.add(sidebarButton);

        this.add(leftPanel);
    }

    public JButton getSideBarButton() {
        return sidebarButton;
    }

    public JButton getGuidelinesButton() {
        return guidelinesButton;
    }
}
