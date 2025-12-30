package com.project.quizapp;

public class ColorCode {

    //== RESET / BASE ==
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String DOUBLE_UNDERLINE = "\u001B[21m";
    public static final String BLINK = "\u001B[5m";
    public static final String REVERSE = "\u001B[7m";
    public static final String HIDDEN = "\u001B[8m";

    //== console 8 basic ==
    public static final String BLACK = "\u001B[90m";
    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[94m";
    public static final String MAGENTA = "\u001B[95m";
    public static final String CYAN = "\u001B[96m";
    public static final String WHITE = "\u001B[97m";

    //== RGB Direct ==
    public static String rgb(int r,int g,int b) {
        return "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
    }

    //== rainbow 7 ==
    public static String[] cRAINBOW = {RED, YELLOW, GREEN, BLUE, MAGENTA, CYAN};

     private static final String[] RAINBOW = {
             rgb(255,0,0),
             rgb(255,165,0),
             rgb(255,255,0),
             rgb(0,128,0),
             rgb(0,0,255),
             rgb(75,0,130),
             rgb(143,0,255)
     };

    public static String rainbow(String txt) {
        StringBuilder sb = new StringBuilder(txt.length()*12);
        int idx = 0;
        for (char c : txt.toCharArray()) {
            if (c==' ') sb.append(" ");
            else {
                sb.append(BOLD).append(cRAINBOW[idx % cRAINBOW.length]).append(c).append(RESET);
                idx++;
            }
        }
        return sb.toString();
    }

    //== box double line universal (correct width with ANSI safe)
    public static String boxDouble(String s) {
        String plain = s.replaceAll("\u001B\\[[;\\d]*m", ""); // remove ANSI for width
        int w = plain.length();
        String top ="╔" + "═".repeat(w) + "╗";
        String mid ="║" + s + "║";
        String bot ="╚" + "═".repeat(w) + "╝";
        return top+"\n"+mid+"\n"+bot;
    }

    public static String boxSingle(String s) {
        String plain = s.replaceAll("\u001B\\[[;\\d]*m", "");
        int w = plain.length();
        String top = "┌" + "─".repeat(w) + "┐";
        String mid = "│" + s + "│";
        String bot = "└" + "─".repeat(w) + "┘";
        return top + "\n" + mid + "\n" + bot;
    }

    //== header with color name (ordinary switch) ==
    public static String header(String colorName, String s) {
        String c = getColor(colorName);
        return BOLD + UNDERLINE + c + s + RESET;
    }

    public static String colored(String colorName, String s) {
        String c = getColor(colorName);
        return BOLD + c + s + RESET;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String separator(int count) {
        return CYAN + "═".repeat(count) + RESET;
    }

    public static String thinSeparator(int count) {
        return CYAN + "─".repeat(count) + RESET;
    }

    private static String getColor(String colorName) {
        switch (colorName.toUpperCase()) {
            case "BLACK": return BLACK;
            case "RED": return RED;
            case "GREEN": return GREEN;
            case "YELLOW": return YELLOW;
            case "BLUE": return BLUE;
            case "MAGENTA": return MAGENTA;
            case "CYAN": return CYAN;
            case "WHITE": return WHITE;
            default: return RESET;
        }
    }

    public static String tag(String label,String txt) {
        return BOLD+"["+label+"] "+RESET+txt;
    }

    public static String error(String text) {
        return RED + BOLD + "✗ " + text + RESET;
    }

    public static String right(String text) {
        return GREEN + BOLD + "✓ " + text + RESET;
    }

    public static String warning(String text) {
        return YELLOW + BOLD + "⚠ " + text + RESET;
    }
}
