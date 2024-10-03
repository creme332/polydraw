package com.github.creme332.utils;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

/**
 * Focuses a component in a JOptionPane. By default, it is not possible to
 * request focus to a component inside a JOptionPane.
 * Reference:
 * https://bugs.openjdk.org/browse/JDK-5018574?focusedId=12217314&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#comment-12217314
 * 
 */
public class RequestFocusListener implements HierarchyListener {

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        final Component c = e.getComponent();
        if (c.isShowing() && (e.getChangeFlags() &
                HierarchyEvent.SHOWING_CHANGED) != 0) {
            Window toplevel = SwingUtilities.getWindowAncestor(c);
            toplevel.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    c.requestFocus();
                }
            });

        }
    }
}
