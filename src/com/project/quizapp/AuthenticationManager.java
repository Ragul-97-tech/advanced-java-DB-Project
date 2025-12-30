package com.project.quizapp;

public class AuthenticationManager {
    DataManager metaData;
    User currentUser;
    QuizApplication quizApp;

    public AuthenticationManager(DataManager dataManager) {
        metaData = dataManager;
        this.currentUser = null;
        this.quizApp = new QuizApplication();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean authMenu() {
        System.out.println(ColorCode.colored("Cyan", ColorCode.boxDouble("    Login / Registration    ")));
        System.out.println(ColorCode.colored("yellow", " 1. \uD83D\uDD11 Login"));
        System.out.println(ColorCode.colored("yellow", " 2. \uD83D\uDCDD Register New Account"));
        System.out.println(ColorCode.colored("yellow", " 3. \uD83D\uDC64 Continue as Guest"));
        System.out.println(ColorCode.colored("yellow", " 0. \uD83D\uDEAA Exit"));
        System.out.println(ColorCode.separator(50));

        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Enter choice (0-3): "), 0, 3);
        ColorCode.clearScreen();

        String username, password;
        switch (choice) {
            case 1:
                System.out.println(ColorCode.colored("cyan", ColorCode.boxSingle("       LOGIN       ")));
                System.out.println(ColorCode.colored("red", "0. Cancel"));
                username = quizApp.getString(ColorCode.colored("cyan", "\nEnter username: "));
                if (username.trim().equals("0")) return false;
                password = quizApp.getString(ColorCode.colored("cyan", "Enter password: "));
                if (password.trim().equals("0")) return false;

                User u = metaData.findUserByUsername(username);

                if (login(password, u)) {
                    System.out.println(ColorCode.right("Login Successfully! welcome, " + username));
                    return true;
                }
                else {
                    System.out.println(ColorCode.error("Invalid credentials!"));
                    return false;
                }

            case 2:
                System.out.println(ColorCode.colored("cyan", ColorCode.boxSingle("    REGISTRATION    ")));
                System.out.println(ColorCode.colored("red", "0. Cancel"));
                username = quizApp.getString(ColorCode.colored("cyan", "\nEnter username: "));
                if (username.trim().equals("0")) return false;

                if (username.length() < 3) {
                    System.out.println(ColorCode.error("Username must be at least 3 characters!"));
                    return false;
                }

                if (register(username)) {
                    password = quizApp.getString(ColorCode.colored("cyan", "Enter password: "));
                    if (password.length() < 4) {
                        System.out.println(ColorCode.error("Password must be at least 4 characters!"));
                        return false;
                    }
                    String userId = "USER" + String.format("%03d", metaData.getUsers().size() + 1);
                    RegisteredUser newUser = new RegisteredUser(userId, username, password);
                    metaData.addUser(newUser);
                    currentUser = newUser;
                    System.out.println(ColorCode.right("Registration successfully! Welcome, " + username));
                    return true;
                }
                else {
                    System.out.println(ColorCode.error("Username already exist!"));
                    return false;
                }

            case 3:
                String guestId = ""+System.currentTimeMillis();
                currentUser = new GuestUser(guestId);
                System.out.println(ColorCode.right("Continuing as Guest"));
                System.out.println(ColorCode.warning("Note: Progress will not be saved!"));
                return true;

            case 0:
                return false;

            default:
                System.out.println(ColorCode.colored("red", "Invalid choice! Try again"));
                return false;
        }
    }

    boolean login(String password, User user) {
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println(ColorCode.colored("yellow", "\n\uD83D\uDC4B Goodbye, " + currentUser.getUserName() + "!"));
            currentUser = null;
        }
    }

    boolean register(String username) {
        return metaData.findUserByUsername(username) == null;
    }

    boolean isNotAuthenticated() {
        return currentUser == null;
    }
}
