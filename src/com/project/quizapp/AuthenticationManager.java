package com.project.quizapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationManager {
    private static final Logger logger = LogManager.getLogger(AuthenticationManager.class);
    DataManager metaData;
    User currentUser;
    QuizApplication quizApp;
    BCrypt crypt = new BCrypt();

    public AuthenticationManager(DataManager dataManager) {
        this.metaData = dataManager;
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
                if (username.trim().equals("0")) {
                    logger.info("Login cancelled");
                    return authMenu();
                }
                password = quizApp.getString(ColorCode.colored("cyan", "Enter password: "));
                if (password.trim().equals("0")) {
                    logger.info("Login cancelled");
                    return authMenu();
                }

                User u = metaData.findUserByUsername(username);

                if (login(password, u)) {
                    System.out.println(ColorCode.right("Login Successfully! welcome, " + username));
                    logger.info("User logged in: " + u.getUserId());
                    return true;
                }
                else {
                    System.out.println(ColorCode.error("Invalid credentials!"));
                    logger.warn("Failed login attempt for username: " + username);
                    return authMenu();
                }

            case 2:
                System.out.println(ColorCode.colored("cyan", ColorCode.boxSingle("    REGISTRATION    ")));
                System.out.println(ColorCode.colored("red", "0. Cancel"));
                username = quizApp.getString(ColorCode.colored("cyan", "\nEnter username: "));
                if (username.trim().equals("0")) {
                    logger.info("Registration cancelled");
                    return authMenu();
                }

                if (username.length() < 3) {
                    System.out.println(ColorCode.error("Username must be at least 3 characters!"));
                    logger.warn("Registration failed: username too short");
                    return authMenu();
                }

                if (register(username)) {
                    password = quizApp.getString(ColorCode.colored("cyan", "Enter password: "));
                    if (password.length() < 4) {
                        System.out.println(ColorCode.error("Password must be at least 4 characters!"));
                        logger.warn("Registration failed: password too short");
                        return authMenu();
                    }
                    RegisteredUser newUser = new RegisteredUser("", username, password);
                    if (metaData.addUser(newUser)) {
                        currentUser = newUser;
                        System.out.println(ColorCode.right("Registration successfully! Welcome, " + username));
                        logger.info("New user registered: " + currentUser.getUserId());
                        return true;
                    }
                }
                else {
                    System.out.println(ColorCode.error("Username already exist!"));
                    logger.warn("Registration failed: username already exists - " + username);
                    return authMenu();
                }

            case 3:
                String guestId = ""+System.currentTimeMillis();
                currentUser = new GuestUser(guestId);
                System.out.println(ColorCode.right("Continuing as Guest"));
                System.out.println(ColorCode.warning("Note: Progress will not be saved!"));
                logger.info("Guest user started session");
                return true;

            case 0:
                logger.info("User exited authentication");
                return false;

            default:
                System.out.println(ColorCode.colored("red", "Invalid choice! Try again"));
                return false;
        }
    }

    boolean login(String password, User user) {
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println(ColorCode.colored("yellow", "\n\uD83D\uDC4B Goodbye, " + currentUser.getUserName() + "!"));
            logger.info("User logged out: " + currentUser.getUserName());
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
