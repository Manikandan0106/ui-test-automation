package com.framework.pages;

import org.openqa.selenium.By;

public class CartWorkflowPage extends BasePage {
    // Locators
    private final By backpackLink = By.linkText("Sauce Labs Backpack");
    private final By addToCartButton = By.xpath("//button[text()='ADD TO CART']");
    private final By cartIcon = By.xpath("//a[contains(@class,'shopping_cart_link')]");
    private final By checkoutLink = By.xpath("//a[text()='CHECKOUT']");
    private final By cancelLink = By.xpath("//a[text()='CANCEL']");
    private final By removeButton = By.xpath("//button[text()='REMOVE']");
    private final By continueShoppingLink = By.xpath("//a[@class='btn_secondary']");

    public CartWorkflowPage() {
        super();
    }

    public void clickBackpack() {
        click(backpackLink, "Sauce Labs Backpack Link");
    }

    public void addToCart() {
        click(addToCartButton, "Add to Cart Button");
    }

    public void goToCart() {
        click(cartIcon, "Cart Icon");
    }

    public void clickCheckout() {
        click(checkoutLink, "Checkout Link");
    }

    public void clickCancel() {
        click(cancelLink, "Cancel Link");
    }

    public void clickRemove() {
        click(removeButton, "Remove Button");
    }

    public void clickContinueShopping() {
        click(continueShoppingLink, "Continue Shopping Link");
    }
} 