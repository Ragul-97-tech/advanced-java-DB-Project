package com.project.quizapp;

public class GuestUser extends User {
    int sessionScore;
    public GuestUser(String guestId) {
        super(guestId, "Guest_" + guestId, "", "GUEST");
        this.sessionScore = 0;
    }

    @Override
    public boolean canAccessAdminFeature() {
        return false;
    }

    @Override
    public boolean canSaveProgress() {
        return false;
    }

    public int getSessionScore() {
        return sessionScore;
    }
    public void addSessionScore(int score) {
        this.sessionScore += score;
    }
}
