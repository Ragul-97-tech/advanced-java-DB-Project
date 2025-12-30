package com.project.quizapp;

public class MainMenu {
    DataManager metaData;
    AuthenticationManager auth;
    public MainMenu(AuthenticationManager auth, DataManager metaData) {
        this.metaData = metaData;
        this.auth = auth;
    }

    public void start() {
        boolean running = true;
        while (true) {
            if (auth.isNotAuthenticated()) {
                if (!auth.authMenu()) {
                    running = false;
                    break;
                }
            }
            User currentUser = auth.getCurrentUser();

            if (currentUser == null) continue;
            ColorCode.clearScreen();
            if (currentUser.canAccessAdminFeature())
                new AdminPanel(metaData, auth).showMenu();
            else
                new UserMenu(metaData, auth).show();

            if (auth.isNotAuthenticated()) {
                continue;
            }
        }
    }
}
