package org.complete.framework.shopping;

import org.complete.framework.BaseTest;
import org.complete.framework.pageobjects.shopping.HomeShoppingPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShoppingTests extends BaseTest {
    private HomeShoppingPage homeShoppingPage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        commonFlows.loginValidUser();
    }

    @Test(groups = {SMOKE})
    public void verifyPageTest() {
        homeShoppingPage.verifyPage();
    }

    @Test(groups = {REGRESSION})
    public void comboBoxNameFilterDescendantTest() {
        homeShoppingPage.filterByName(false);
        homeShoppingPage.verifyItemNameOrder(false);
    }

    @Test(groups = {REGRESSION})
    public void comboBoxNameFilterAscendantTest() {
        homeShoppingPage.filterByName(true);
        homeShoppingPage.verifyItemNameOrder(true);
    }

    @Test(groups = {REGRESSION})
    public void comboBoxPriceFilterDescendantTest() {
        homeShoppingPage.filterByPrice(false);
        homeShoppingPage.verifyItemPriceOrder(false);
    }

    @Test(groups = {REGRESSION})
    public void comboBoxPriceFilterAscendantTest() {
        homeShoppingPage.filterByPrice(true);
        homeShoppingPage.verifyItemPriceOrder(true);
    }

    @Override
    protected void initPages() {
        homeShoppingPage = new HomeShoppingPage(driver);
    }
}
