package com.project.quizapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenu {
    private static final Logger logger = LogManager.getLogger(MainMenu.class);
    DataManager metaData;
    AuthenticationManager auth;
    public MainMenu(AuthenticationManager auth, DataManager metaData) {
        this.metaData = metaData;
        this.auth = auth;
    }

    public void start() {
        logger.info("Main menu started");
        while (true) {
            if (auth.isNotAuthenticated()) {
                if (!auth.authMenu()) {
                    logger.info("User chose to exit application");
                    break;
                }
            }
            User currentUser = auth.getCurrentUser();

            if (currentUser == null) continue;
            ColorCode.clearScreen();
            if (currentUser.canAccessAdminFeature()) {
                logger.info("Admin panel accessed by: " + currentUser.getUserId());
                new AdminPanel(metaData, auth).showMenu();
            }
            else {
                logger.info("User menu accessed by: " + currentUser.getUserId());
                new UserMenu(metaData, auth).show();
            }

            if (auth.isNotAuthenticated()) {
                continue;
            }
        }
    }
}
