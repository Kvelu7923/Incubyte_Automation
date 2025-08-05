package core;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SeleniumActions {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public SeleniumActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    // ========== BASIC ACTIONS ==========
    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // ========== ADVANCED ACTIONS ==========
    public void hover(By locator) {
        actions.moveToElement(driver.findElement(locator)).perform();
    }

    public void selectDropdown(By locator, String visibleText) {
        new Select(driver.findElement(locator)).selectByVisibleText(visibleText);
    }

    public void scrollTo(By locator) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);",
                driver.findElement(locator)
        );
    }

    // ========== UTILITIES ==========
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}