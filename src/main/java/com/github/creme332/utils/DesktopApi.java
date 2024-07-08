package com.github.creme332.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * An API that allows you to browse files on the OS. Unlike Java Desktop, it
 * tries to be compatible with all common OS like Linux, Windows, Mac.
 * <p>
 * Adapted from MightyPork (2013): https://stackoverflow.com/a/18004334/17627866
 * </p>
 */
public class DesktopApi {
    /**
     * Whether to output messages such as errors in the terminal.
     */
    private static boolean enableLogging = false;

    private DesktopApi() {
    }

    public static boolean browse(URI uri) {

        if (openSystemSpecific(uri.toString()))
            return true;

        return browseDESKTOP(uri);
    }

    public static boolean open(File file) {

        if (openSystemSpecific(file.getPath()))
            return true;

        return openDESKTOP(file);
    }

    public static boolean edit(File file) {

        // you can try something like
        // runCommand("gimp", "%s", file.getPath())
        // based on user preferences.

        if (openSystemSpecific(file.getPath()))
            return true;

        return editDESKTOP(file);
    }

    private static boolean openSystemSpecific(String what) {

        EnumOS os = getOs();

        if (os.isLinux()) {
            if (runCommand("kde-open", "%s", what))
                return true;
            if (runCommand("gnome-open", "%s", what))
                return true;
            if (runCommand("xdg-open", "%s", what))
                return true;
        }

        if (os.isMac() && runCommand("open", "%s", what)) {
            return true;
        }

        return os.isWindows() && runCommand("explorer", "%s", what);
    }

    private static boolean browseDESKTOP(URI uri) {

        logOut("Trying to use Desktop.getDesktop().browse() with " + uri.toString());
        try {
            if (!Desktop.isDesktopSupported()) {
                logErr("Platform is not supported.");
                return false;
            }

            if (!Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                logErr("BROWSE is not supported.");
                return false;
            }

            Desktop.getDesktop().browse(uri);

            return true;
        } catch (Exception t) {
            logErr("Error using desktop browse.", t);
            return false;
        }
    }

    private static boolean openDESKTOP(File file) {

        logOut("Trying to use Desktop.getDesktop().open() with " + file.toString());
        try {
            if (!Desktop.isDesktopSupported()) {
                logErr("Platform is not supported.");
                return false;
            }

            if (!Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                logErr("OPEN is not supported.");
                return false;
            }

            Desktop.getDesktop().open(file);

            return true;
        } catch (Exception t) {
            logErr("Error using desktop open.", t);
            return false;
        }
    }

    private static boolean editDESKTOP(File file) {

        logOut("Trying to use Desktop.getDesktop().edit() with " + file);
        try {
            if (!Desktop.isDesktopSupported()) {
                logErr("Platform is not supported.");
                return false;
            }

            if (!Desktop.getDesktop().isSupported(Desktop.Action.EDIT)) {
                logErr("EDIT is not supported.");
                return false;
            }

            Desktop.getDesktop().edit(file);

            return true;
        } catch (Exception t) {
            logErr("Error using desktop edit.", t);
            return false;
        }
    }

    private static boolean runCommand(String command, String args, String file) {

        logOut("Trying to exec:\n   cmd = " + command + "\n   args = " + args + "\n   %s = " + file);

        String[] parts = prepareCommand(command, args, file);

        try {
            Process p = Runtime.getRuntime().exec(parts);
            if (p == null)
                return false;

            try {
                int retval = p.exitValue();
                if (retval == 0) {
                    logErr("Process ended immediately.");
                    return false;
                } else {
                    logErr("Process crashed.");
                    return false;
                }
            } catch (IllegalThreadStateException itse) {
                logErr("Process is running.");
                return true;
            }
        } catch (IOException e) {
            logErr("Error running command.", e);
            return false;
        }
    }

    private static String[] prepareCommand(String command, String args, String file) {

        List<String> parts = new ArrayList<String>();
        parts.add(command);

        if (args != null) {
            for (String s : args.split(" ")) {
                s = String.format(s, file); // put in the filename thing

                parts.add(s.trim());
            }
        }

        return parts.toArray(new String[parts.size()]);
    }

    private static void logErr(String msg, Exception t) {
        if (!enableLogging)
            return;
        System.err.println(msg);
        t.printStackTrace();
    }

    private static void logErr(String msg) {
        if (enableLogging)
            System.err.println(msg);
    }

    private static void logOut(String msg) {
        if (enableLogging)
            System.out.println(msg);
    }

    public enum EnumOS {
        LINUX, MACOS, SOLARIS, UNKNOWN, WINDOWS;

        public boolean isLinux() {

            return this == LINUX || this == SOLARIS;
        }

        public boolean isMac() {

            return this == MACOS;
        }

        public boolean isWindows() {

            return this == WINDOWS;
        }
    }

    public static EnumOS getOs() {

        String s = System.getProperty("os.name").toLowerCase();

        if (s.contains("win")) {
            return EnumOS.WINDOWS;
        }

        if (s.contains("mac")) {
            return EnumOS.MACOS;
        }

        if (s.contains("solaris")) {
            return EnumOS.SOLARIS;
        }

        if (s.contains("sunos")) {
            return EnumOS.SOLARIS;
        }

        if (s.contains("linux")) {
            return EnumOS.LINUX;
        }

        if (s.contains("unix")) {
            return EnumOS.LINUX;
        } else {
            return EnumOS.UNKNOWN;
        }
    }

    /**
     * 
     * @return True if application is running on Windows OS.
     */
    public static boolean isWindows() {
        return getOs() == EnumOS.WINDOWS;
    }
}