package utilities;

import data.DataProviders;
import models.ItemModel;
import models.PersonalInformationModel;
import org.openqa.selenium.WebDriver;
import pageobjects.bars.BurgerMenu;
import pageobjects.bars.Footer;
import pageobjects.bars.Header;
import pageobjects.checkout.CartPage;
import pageobjects.checkout.StepOnePage;
import pageobjects.checkout.StepTwoPage;
import pageobjects.checkout.SuccessPage;
import pageobjects.credentials.LoginPage;
import pageobjects.shopping.HomeShoppingPage;
import pageobjects.shopping.ItemDetailPage;

public class CommonFlows {
    private final WebDriver driver;

    public CommonFlows(WebDriver driver) {
        this.driver = driver;
    }

    public void goToIndex() {
        var login = new LoginPage(driver);
        login.goToIndex();
        login.waitPageToLoad();
    }

    public void loginValidUser() {
        var credentials = new DataProviders().getValidCredentials();
        var login = new LoginPage(driver);
        var homeShopping = new HomeShoppingPage(driver);
        var header = new Header(driver);
        var footer = new Footer(driver);

        login.fillCredentials(credentials.getUsername(), credentials.getPassword());
        homeShopping.waitPageToLoad();
        header.waitPageToLoad();
        footer.waitPageToLoad();
    }

    public void addSingleItemToCart(ItemModel item) {
        var homeShoppingPage = new HomeShoppingPage(driver);
        var itemDetailPage = new ItemDetailPage(driver);

        homeShoppingPage.goToItemDetail(item.getItemName());
        itemDetailPage.waitPageToLoad();
        itemDetailPage.verifyCorrectItemDisplay(item.getItemName(), item.getPrice());
        itemDetailPage.verifyPage();
        itemDetailPage.clickingOnAddItemToCart();
        itemDetailPage.clickOnBackToProducts();
        homeShoppingPage.waitPageToLoad();
    }

    public void addItemsToCart() {
        var listItem = new DataProviders().itemsDataProvider();

        for (ItemModel item : listItem) {
            addSingleItemToCart(item);
        }
    }

    public void openMenuBurger() {
        var header = new Header(driver);
        var burgerMenu = new BurgerMenu(driver);

        header.openBurgerMenu();
        burgerMenu.waitPageToLoad();
    }

    public void verifyFooterHeader() {
        var footer = new Footer(driver);
        var header = new Header(driver);

        footer.verifyPage();
        header.verifyPage();
    }

    public void addItemsAndGoToCart(ItemModel item) {
        var cartPage = new CartPage(driver);
        var header = new Header(driver);

        addSingleItemToCart(item);
        header.clickOnCheckoutCart();
        cartPage.waitPageToLoad();
    }

    public void addItemsAndGoToStepOne(ItemModel item) {
        var cartPage = new CartPage(driver);
        var stepOnePage = new StepOnePage(driver);
        addItemsAndGoToCart(item);
        cartPage.clickOnContinueCheckout();
        stepOnePage.waitPageToLoad();
    }

    public void addItemsAndGoToStepTwo(ItemModel item) {
        var stepOnePage = new StepOnePage(driver);
        var stepTwoPage = new StepTwoPage(driver);
        var personalInfo = new PersonalInformationModel();
        addItemsAndGoToStepOne(item);
        stepOnePage.fillForm(personalInfo.getFirstName(), personalInfo.getLastName(), personalInfo.getZipCode());
        stepTwoPage.waitPageToLoad();
    }

    public void addItemsFinishShopping(ItemModel item) {
        var stepTwoPage = new StepTwoPage(driver);
        var successPage = new SuccessPage(driver);

        addItemsAndGoToStepTwo(item);
        stepTwoPage.clickOnFinish();
        successPage.waitPageToLoad();
    }
}
