package com.framework.tests;

import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import com.framework.pages.CartWorkflowPage;
import org.testng.Assert;
import io.qameta.allure.Step;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class CartWorkflowTest extends BaseTest {

    @Test
    @Description("JIRA-124:Test cart continue workflow functionality")
    public void testCartWorkflow() throws InterruptedException {
        CartWorkflowPage cartPage = new CartWorkflowPage();
        Allure.step("Add item to cart and Navigate to shopping cart", () -> {
            cartPage.addToCart();
            Thread.sleep(10000);
            cartPage.goToCart();
        });

        Allure.step("Click on Checkout and Click on Cancel", () -> {
            cartPage.clickCheckout();
            cartPage.clickCancel();
        });

        Allure.step("Click on Remove and Click on Continue Shoping", () -> {
            try {
                if (driver.findElements(By.xpath("//button[text()='REMOVE']")).size() > 0) {
                    cartPage.clickRemove();
                }
            } catch (NoSuchElementException e) {
                // Log or ignore if not present
            }
            cartPage.clickContinueShopping();
            // Assert we're back on the inventory page
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not return to inventory page");
        });

    }
}