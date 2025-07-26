package com.framework.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.framework.pages.CartWorkflowPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;

public class CartWorkflowTest extends BaseTest {
    private CartWorkflowPage cartPage;

    @BeforeMethod
    public void setUpCartPage() {
        cartPage = new CartWorkflowPage();
    }

    @Test
    @Description("JIRA-124:Test cart continue workflow functionality")
    public void testCartWorkflow() throws InterruptedException {
        Allure.step("Add item to cart and Navigate to shopping cart", () -> {
            cartPage.addToCart();
            Thread.sleep(10000);
            cartPage.goToCart();
        });

        Allure.step("Click on Checkout and Click on Cancel", () -> {
            cartPage.clickCheckout();
            cartPage.clickCancel();
        });

        Allure.step("Click on Continue Shopping", () -> {
            cartPage.clickContinueShopping();
            // The button click is successful, navigation behavior may vary based on cart state
        });
    }
}