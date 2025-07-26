package com.framework.pages;

import org.openqa.selenium.By;

public class CartWorkflowPage extends BasePage {
    // Locators - Updated based on actual HTML structure
    private final By backpackLink = By.id("item_4_title_link");
    private final By addToCartButton = By.cssSelector(".btn_primary.btn_inventory");
    private final By cartIcon = By.cssSelector(".shopping_cart_link");
    private final By checkoutLink = By.cssSelector(".btn_action.checkout_button");
    private final By cancelLink = By.cssSelector(".btn_secondary.cart_cancel_link");
    private final By removeButton = By.cssSelector(".btn_secondary.btn_inventory");
    private final By continueShoppingLink = By.cssSelector(".btn_secondary");

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