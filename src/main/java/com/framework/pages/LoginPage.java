package com.framework.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    // Locators
    private static final By usernameField = By.id("user-name");
    private static final By passwordField = By.id("password");
    private static final By loginButton = By.id("login-button");

    public static void enterUsername(String username) {
        enterText(usernameField, username, "Username Field");
    }

    public static void enterPassword(String password) {
        enterText(passwordField, password, "Password Field");
    }

    public static void clickLogin() {
        click(loginButton, "Login Button");
    }

    public static void login(String username, String password) {
        System.out.println("Starting login process for user: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        System.out.println("Login process completed");
    }
} 