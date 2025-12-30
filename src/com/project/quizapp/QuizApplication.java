package com.project.quizapp;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuizApplication {
    Scanner userInputs = new Scanner(System.in);

    public static void main(String[] args) {
        QuizApplication quizApp = new QuizApplication();
        System.out.println("""
\033c
  /$$$$$$  /$$   /$$ /$$$$$$ /$$$$$$$$       /$$      /$$  /$$$$$$   /$$$$$$  /$$$$$$$$ /$$$$$$$$ /$$$$$$$\s
 /$$__  $$| $$  | $$|_  $$_/|_____ $$       | $$$    /$$$ /$$__  $$ /$$__  $$|__  $$__/| $$_____/| $$__  $$
| $$  \\ $$| $$  | $$  | $$       /$$/       | $$$$  /$$$$| $$  \\ $$| $$  \\__/   | $$   | $$      | $$  \\ $$
| $$  | $$| $$  | $$  | $$      /$$/        | $$ $$/$$ $$| $$$$$$$$|  $$$$$$    | $$   | $$$$$   | $$$$$$$/
| $$  | $$| $$  | $$  | $$     /$$/         | $$  $$$| $$| $$__  $$ \\____  $$   | $$   | $$__/   | $$__  $$
| $$/$$ $$| $$  | $$  | $$    /$$/          | $$\\  $ | $$| $$  | $$ /$$  \\ $$   | $$   | $$      | $$  \\ $$
|  $$$$$$/|  $$$$$$/ /$$$$$$ /$$$$$$$$      | $$ \\/  | $$| $$  | $$|  $$$$$$/   | $$   | $$$$$$$$| $$  | $$
 \\____ $$$ \\______/ |______/|________/      |__/     |__/|__/  |__/ \\______/    |__/   |________/|__/  |__/
      \\__/                                                                                                \s
                                                                                                                                                     \s
       \s""");

        System.out.println();        
        System.out.println(ColorCode.colored("white", ColorCode.boxDouble("      Welcome to Advanced Quiz Application      ")));
        System.out.println();

        DataManager metaData = new DataManager();
        metaData.initializeData();

        AuthenticationManager auth = new AuthenticationManager(metaData);

        MainMenu menu = new MainMenu(auth, metaData);
        menu.start();

        quizApp.userInputs.close();
        System.out.println(ColorCode.colored("green", "\nThank you for using Quiz Application! GoodBye."));
    }

    public int getInt(String title) {
        while (true) {
            try {
                System.out.print(title);
                int value = userInputs.nextInt();
                userInputs.nextLine();
                return value;
            }
            catch (InputMismatchException e) {
                userInputs.nextLine();
                System.out.println(ColorCode.colored("Red", "Invalid input! Please enter a number."));
            }
        }
    }

    public String getString() {
        return userInputs.nextLine();
    }

    public String getString(String title) {
        System.out.print(title);
        return getString();
    }

    public int getIntInRange(String title, int min, int max) {
        while (true) {
            int value = getInt(title);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println(ColorCode.colored("red", "Please enter a number between " + min + " and " + max));
        }
    }
    public boolean getYesNo(String title) {
        System.out.print(title);
        String response = getString();
        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }
 }
