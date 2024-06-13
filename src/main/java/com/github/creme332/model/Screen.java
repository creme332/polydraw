package com.github.creme332.model;

public enum Screen {
    SPLASH_SCREEN("SPLASH_SCREEN"),
    MAIN_SCREEN("MAIN_SCREEN"),
    TUTORIAL_SCREEN("TUTORIAL_SCREEN");

    private String screen;

    Screen(String str) {
        screen = str;
    }

    @Override
    public String toString() {
        return screen;
    }
}
