package org.complete.framework.checkout;

import base.BaseTest;
import data.DataProviders;
import models.ItemModel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.checkout.StepTwoPage;
import pageobjects.shopping.HomeShoppingPage;

public class StepTwoTests extends BaseTest {
    private HomeShoppingPage homeShoppingPage;
    private StepTwoPage stepTwoPage;
    private final ItemModel item = new DataProviders().getSingleItem();

    @BeforeMethod(alwaysRun = true, description = "setup preconditions")
    public void setUp() {
        commonFlows.loginValidUser();
        commonFlows.addItemsAndGoToStepTwo(item);
    }

    @Test(groups = {SMOKE})
    public void verifyStepTwoTest() {
        stepTwoPage.verifyPage();
        commonFlows.verifyFooterHeader();
    }

    @Test(groups = {SMOKE})
    public void verifyItemContentsTest() {
        stepTwoPage.verifyItemContents(item.getItemName(), item.getPrice());
    }

    @Test(groups = {REGRESSION})
    public void verifyReturnToStepOne() {
        stepTwoPage.clickOnCancel();
        homeShoppingPage.waitPageToLoad();
        homeShoppingPage.verifyPage();
        commonFlows.verifyFooterHeader();
    }

    @Override
    protected void initPages(WebDriver webDriver) {
        homeShoppingPage = new HomeShoppingPage(webDriver);
        stepTwoPage = new StepTwoPage(webDriver);
    }
}
