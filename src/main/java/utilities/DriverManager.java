package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;

public class DriverManager {
    private final String screenshotPath = "src/test/resources/screenshots";
    private final Logs logs = new Logs();
    private String browserName;

    public WebDriver buildLocalDriver() {
        browserName = System.getProperty("browser");

        if (browserName == null) {
            logs.debug("Setting default local browser to CHROME");
            browserName = "CHROME";
        }

        switch (browserName) {
            case "CHROME":
                logs.debug("Starting chrome driver");
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "EDGE":
                logs.debug("Starting edge driver");
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                logs.debug(browserName + " not supported");
                return null;
        }
    }

    public WebDriver buildRemoteDriver() {
        //TODO
        return null;
    }

    public void getScreenshot(WebDriver driver, String screenshotName) {
        logs.debug("Taking screenshot");
        var screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        var path = String.format("%s/%s.png", screenshotPath, screenshotName);
        try {
            FileUtils.copyFile(screenshotFile, new File(path));
        } catch (IOException ioException) {
            logs.error("Failed creating screenshot");
            logs.error(ioException.getLocalizedMessage());
        }
    }

    @Attachment(value = "Screenshot failure", type = "image/png")
    public byte[] getAllureScreenshot(WebDriver driver) {
        logs.debug("Taking allure screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void deleteScreenshotsFolder() {
        try {
            logs.debug("Deleting screenshots directory");
            FileUtils.deleteDirectory(new File(screenshotPath));
        } catch (IOException ioException) {
            logs.error("Failed deleting screenshots folder");
            logs.error(ioException.getLocalizedMessage());
        }
    }
}
