package com.framework.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");

    public LoginPage() {
        super();
    }

    public void enterUsername(String username) {
        enterText(usernameField, username, "Username Field");
    }

    public void enterPassword(String password) {
        enterText(passwordField, password, "Password Field");
    }

    public void clickLogin() {
        click(loginButton, "Login Button");
    }

    public void login(String username, String password) {
        logger.info("Starting login process for user: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        logger.info("Login process completed");
    }
    
    // Getter methods for locators (for testing purposes)
    public By getUsernameField() {
        return usernameField;
    }
    
    public By getPasswordField() {
        return passwordField;
    }
    
    public By getLoginButton() {
        return loginButton;
    }
} 