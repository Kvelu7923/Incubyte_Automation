package core;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public final class SeleniumActions {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private final JavascriptExecutor js;

    public SeleniumActions() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }


    // ================== BASIC INTERACTIONS ================== //

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public void pressEscapeKey() {
        try {
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        } catch (Exception e) {
            System.out.println("ESC key press failed: " + e.getMessage());
        }
    }


    public void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }


    public List<String> getTextFromElements(By locator) {
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void handleAdIfPresent(By adCloseButtonSelector, int timeoutInSeconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement closeBtn = shortWait.until(ExpectedConditions.elementToBeClickable(adCloseButtonSelector));
            closeBtn.click();
            System.out.println("Ad closed successfully.");
        } catch (TimeoutException e) {
            System.out.println("No ad popup appeared.");
        } catch (Exception e) {
            System.out.println("Error closing ad popup: " + e.getMessage());
        }
    }


    public boolean waitUntilVisible(By locator, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitUntilClickable(By locator, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            customWait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    public String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public String getAttribute(By locator, String attribute) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
    }

    // ================== DROPDOWN HANDLING ================== //

    public void selectByVisibleText(By locator, String text) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).selectByIndex(index);
    }

    // ================== VISIBILITY CHECKS ================== //

    public boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================== MOUSE ACTIONS ================== //

    public void hover(By locator) {
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).perform();
    }

    public void doubleClick(By locator) {
        actions.doubleClick(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).perform();
    }

    public void rightClick(By locator) {
        actions.contextClick(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).perform();
    }

    public void dragAndDrop(By source, By target) {
        WebElement sourceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
        WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(target));
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    // ================== JAVASCRIPT ACTIONS ================== //

    public void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickWithJS(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].click();", element);
    }

    public void setAttribute(By locator, String attribute, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attribute, value);
    }

    // ================== WINDOW/FRAME HANDLING ================== //

    public void switchToFrame(By locator) {
        driver.switchTo().frame(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToWindow(int index) {
        String handle = driver.getWindowHandles().toArray()[index].toString();
        driver.switchTo().window(handle);
    }

    // ================== UTILITY METHODS ================== //

    public List<WebElement> getElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void waitForPageLoad() {
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    // ================== COMPOSITE ACTIONS ================== //

    public void login(String username, String password, By userLocator, By passLocator, By loginBtnLocator) {
        type(userLocator, username);
        type(passLocator, password);
        click(loginBtnLocator);
    }

    public void uploadFile(By fileInputLocator, String filePath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fileInputLocator))
                .sendKeys(filePath);
    }


}