package com.github.creme332.utils;

import com.formdev.flatlaf.FlatLightLaf;

public class PolydrawTheme extends FlatLightLaf {
    public static boolean setup() {
        // do not embed menu bar into the window title pane
        System.setProperty("flatlaf.menuBarEmbedded", "false");

        // look for properties file in theme folder (relative to resource)
        registerCustomDefaultsSource("theme");

        return setup(new PolydrawTheme());
    }

    @Override
    public String getName() {
        return "PolydrawTheme";
    }
}
