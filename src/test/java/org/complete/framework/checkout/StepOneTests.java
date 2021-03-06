package org.complete.framework.checkout;

import base.BaseTest;
import data.DataProviders;
import models.ItemModel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.checkout.CartPage;
import pageobjects.checkout.StepOnePage;
import pageobjects.checkout.StepTwoPage;

import static data.DataProviders.BAD_PERSONAL_INFO_DP;

public class StepOneTests extends BaseTest {
    private StepOnePage stepOnePage;
    private StepTwoPage stepTwoPage;
    private CartPage cartPage;
    private final ItemModel item = new DataProviders().getSingleItem();

    @BeforeMethod(alwaysRun = true, description = "setup preconditions")
    public void setUp() {
        commonFlows.loginValidUser();
        commonFlows.addItemsAndGoToStepOne(item);
    }

    @Test(groups = {SMOKE})
    public void verifyStepOnePageTest() {
        stepOnePage.verifyPage();
    }

    @Test(groups = {SMOKE})
    public void validDataTest() {
        var personalInfo = new DataProviders().getPersonalInfo();
        stepOnePage.fillForm(personalInfo.getFirstName(), personalInfo.getLastName(),
                personalInfo.getZipCode());
        stepTwoPage.waitPageToLoad();
    }

    @Test(groups = {REGRESSION})
    public void clickOnCancelReturnsCartPage() {
        stepOnePage.clickOnCancelButton();
        cartPage.waitPageToLoad();
        cartPage.verifyPage();
    }

    @Test(groups = {REGRESSION}, dataProvider = BAD_PERSONAL_INFO_DP, dataProviderClass = DataProviders.class)
    public void invalidDataTest(String firstName, String lastName, String zipCode, String errorMessage) {
        stepOnePage.fillForm(firstName, lastName, zipCode);
        stepOnePage.verifyErrorMessage(errorMessage);
    }

    @Override
    protected void initPages(WebDriver webDriver) {
        stepOnePage = new StepOnePage(webDriver);
        stepTwoPage = new StepTwoPage(webDriver);
        cartPage = new CartPage(webDriver);
    }
}
