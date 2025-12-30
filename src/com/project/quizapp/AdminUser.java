package com.project.quizapp;

public class AdminUser extends User{
    String adminLevel;

    public AdminUser(String userId, String username, String password, String adminLevel) {
        super(userId, username, password, "ADMIN");
        this.adminLevel = adminLevel;
    }

    @Override
    public boolean canAccessAdminFeature() {
        return true;
    }

    @Override
    public boolean canSaveProgress() {
        return true;
    }
    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
}