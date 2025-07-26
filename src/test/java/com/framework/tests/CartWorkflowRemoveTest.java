package com.framework.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.framework.pages.CartWorkflowPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;

@Epic("Cart Item Epic")
public class CartWorkflowRemoveTest extends BaseTest {
    private CartWorkflowPage cartPage;

    @BeforeMethod
    public void setUpCartPage() {
        cartPage = new CartWorkflowPage();
    }

    @Test
    @Description("JIRA-125:Test cart remove workflow functionality")
    public void testCartWorkflow() throws InterruptedException {
        Allure.step("Navigate to shopping cart", () -> {
            cartPage.goToCart();
        });

        Allure.step("Click on Continue Shopping", () -> {
            cartPage.clickContinueShopping();
        });
    }
}