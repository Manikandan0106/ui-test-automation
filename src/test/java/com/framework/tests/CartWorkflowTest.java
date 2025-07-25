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

@Listeners({ AllureTestNg.class })
public class CartWorkflowTest extends BaseTest {

    @Test
    @Description("JIRA-124:Test cart continue workflow functionality")
    public void testCartWorkflow() throws InterruptedException {
        Allure.step("Add item to cart and Navigate to shopping cart", () -> {
            CartWorkflowPage.addToCart();
            Thread.sleep(10000);
            CartWorkflowPage.goToCart();
        });

        Allure.step("Click on Checkout and Click on Cancel", () -> {
            CartWorkflowPage.clickCheckout();
            CartWorkflowPage.clickCancel();
        });

        Allure.step("Click on Remove and Click on Continue Shoping", () -> {
            try {
                if (driver.findElements(By.xpath("//button[text()='REMOVE']")).size() > 0) {
                    CartWorkflowPage.clickRemove();
                }
            } catch (NoSuchElementException e) {
                // Log or ignore if not present
            }
            CartWorkflowPage.clickContinueShopping();
            // Assert we're back on the inventory page
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not return to inventory page");
        });

    }
}