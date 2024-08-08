package com.github.creme332.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.github.creme332.view.common.RoundedMenu;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class MenuBar extends JMenuBar {
    private JButton sidebarButton;
    private JButton helpButton;
    private JButton redoButton;
    private JButton undoButton;

    private List<JMenu> jMenusList = new ArrayList<>();
    public static final int HEIGHT = 70;

    public MenuBar(MenuModel[] menus) {

        setPreferredSize(new Dimension(getWidth(), HEIGHT));
        setBorder(new EmptyBorder(new Insets(7, 0, 7, 0)));
        putClientProperty("FlatLaf.style", "background: #f8f8f8");

        // add menus to menubar
        for (MenuModel menuModel : menus) {
            JMenu menu = new RoundedMenu();
            menu.setOpaque(false);
            jMenusList.add(menu);
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
        undoButton = createTransparentButton(FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40));
        leftPanel.add(undoButton);
        undoButton.setToolTipText("Undo");

        // redo button
        redoButton = createTransparentButton(FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40));
        redoButton.setToolTipText("Redo");
        leftPanel.add(redoButton);

        // help button
        helpButton = createTransparentButton(FontIcon.of(BootstrapIcons.QUESTION_CIRCLE, 37));
        helpButton.setToolTipText("Help");
        leftPanel.add(helpButton);

        // sidebar menu button
        sidebarButton = createTransparentButton(FontIcon.of(BootstrapIcons.LIST, 40));
        sidebarButton.setToolTipText("Toggle sidebar");
        leftPanel.add(sidebarButton);

        this.add(leftPanel);
    }

    public JButton createTransparentButton(FontIcon icon) {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        // Show hand cursor when hovered on
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        return button;
    }

    /**
     * Use this method to get the i-th JMenu in the menubar. Do not use the default
     * getMenu() as it does not account for empty spaces and counts buttons.
     * 
     * @param i
     * @return
     */
    public JMenu getMyMenu(int i) {
        return jMenusList.get(i);
    }

    /**
     * Get all JMenuItems from a given JMenu.
     * 
     * @param menu the JMenu to get items from
     * @return a list of JMenuItems
     */
    public List<JMenuItem> getAllMenuItems(int menuIndex) {
        JMenu menu = getMyMenu(menuIndex);
        List<JMenuItem> menuItems = new ArrayList<>();
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem menuItem = menu.getItem(i);
            if (menuItem != null) {
                menuItems.add(menuItem);
            }
        }
        return menuItems;
    }

    public JButton getSideBarButton() {
        return sidebarButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }

    private void updateButtonColor(JButton button, boolean enabled) {
        FontIcon currentIcon = (FontIcon) button.getIcon();
        if (enabled) {
            currentIcon.setIconColor(Color.black);
        } else {
            currentIcon.setIconColor(new Color(153, 153, 153));
        }
        button.setIcon(currentIcon);
        button.repaint();
    }

    public void setRedoEnabled(boolean enabled) {
        updateButtonColor(redoButton, enabled);
    }

    public void setUndoEnabled(boolean enabled) {
        updateButtonColor(undoButton, enabled);
    }

    public void handleRedo(ActionListener listener) {
        redoButton.addActionListener(listener);
    }

    public void handleUndo(ActionListener listener) {
        undoButton.addActionListener(listener);
    }

}
