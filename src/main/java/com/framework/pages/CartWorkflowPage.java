package com.framework.pages;

import org.openqa.selenium.By;

public class CartWorkflowPage extends BasePage {
    // Locators
    private static final By backpackLink = By.linkText("Sauce Labs Backpack");
    private static final By addToCartButton = By.xpath("//button[text()='ADD TO CART']");
    private static final By cartIcon = By.xpath("//a[contains(@class,'shopping_cart_link')]");
    private static final By checkoutLink = By.xpath("//a[text()='CHECKOUT']");
    private static final By cancelLink = By.xpath("//a[text()='CANCEL']");
    private static final By removeButton = By.xpath("//button[text()='REMOVE']");
    private static final By continueShoppingLink = By.xpath("//a[@class='btn_secondary']");

    public static void clickBackpack() {
        click(backpackLink, "Sauce Labs Backpack Link");
    }

    public static void addToCart() {
        click(addToCartButton, "Add to Cart Button");
    }

    public static void goToCart() {
        click(cartIcon, "Cart Icon");
    }

    public static void clickCheckout() {
        click(checkoutLink, "Checkout Link");
    }

    public static void clickCancel() {
        click(cancelLink, "Cancel Link");
    }

    public static void clickRemove() {
        click(removeButton, "Remove Button");
    }

    public static void clickContinueShopping() {
        click(continueShoppingLink, "Continue Shopping Link");
    }
} 