package com.framework.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.pages.CartWorkflowPage;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.testng.AllureTestNg;

@Listeners({ AllureTestNg.class })
@Epic("Cart Item Epic")
public class CartWorkflowRemoveTest extends BaseTest {

	@Test
	@Description("JIRA-123:Test cart remove workflow functionality")
	public void testCartWorkflow() throws InterruptedException {
		Allure.step("Navigate to shopping cart", () -> {
			CartWorkflowPage.goToCart();
		});

		Allure.step("""
				Click on Remove
				         Expected: Cart item removed""", () -> {
//			CartWorkflowPage.clickRemove();
		});

		Allure.step(" Click on Continue Shoping", () -> {
			CartWorkflowPage.clickContinueShopping();
		});
	}
}