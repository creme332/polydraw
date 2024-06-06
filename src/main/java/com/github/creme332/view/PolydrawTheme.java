package com.github.creme332.view;

import com.formdev.flatlaf.FlatLightLaf;

public class PolydrawTheme extends FlatLightLaf {
    public static boolean setup() {
        // look for properties file in theme folder (relative to resource)
        registerCustomDefaultsSource("theme");
        return setup(new PolydrawTheme());
    }

    @Override
    public String getName() {
        return "PolydrawTheme";
    }
}
