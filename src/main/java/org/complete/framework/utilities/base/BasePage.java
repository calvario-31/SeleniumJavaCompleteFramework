package org.complete.framework.utilities.base;

import org.complete.framework.utilities.Logs;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    private WebDriverWait wait;
    protected SoftAssert softAssert;
    protected Logs log = new Logs();
    public static String mainUrl = "https://www.saucedemo.com";

    private void initWait(int timeOut) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    }

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        softAssert = new SoftAssert();
        initWait(5);
    }

    protected BasePage(WebDriver driver, int timeOut) {
        this(driver);
        initWait(timeOut);
    }

    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findAllElements(By locator) {
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        findElement(locator).click();
    }

    protected void typeText(By locator, String text) {
        findElement(locator).sendKeys(text);
    }

    protected Select getSelect(By locator) {
        return new Select(findElement(locator));
    }

    protected String getHref(By locator) {
        return findElement(locator).getAttribute("href");
    }

    protected String getText(By locator) {
        return findElement(locator).getText();
    }

    protected void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean verifyIsDisplayed(By locator) {
        try {
            waitForVisibility(locator);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    protected boolean verifyIsNotDisplayed(By locator) {
        try {
            waitForInvisibility(locator);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    protected boolean verifyIsClickable(By locator) {
        try {
            waitForClickable(locator);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    protected void waitPage(By locator, String pageName) {
        log.info("Waiting " + pageName + " to load");
        waitForVisibility(locator);
        log.info(pageName + " loaded successfully");
    }

    public void goToIndex() {
        log.info("Going to the main url");
        driver.get(mainUrl);
    }

    public abstract void waitPageToLoad();

    public abstract void verifyPage();
}