package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PopupHandler {

    private WebDriver driver;
    private WebDriverWait wait;

    public PopupHandler(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void closeAdPopupIfPresent() {
        try {
            Thread.sleep(10000);
            // Find all iframes on the page
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

            for (WebElement iframe : iframes) {
                try {
                    driver.switchTo().frame(iframe);

                    // Common ad close button selectors
                    List<By> closeButtonSelectors = List.of(
                            By.cssSelector("button[aria-label='Close ad']"),
                            By.cssSelector("div[aria-label='Close']"),
                            By.cssSelector(".close-button"),
                            By.cssSelector(".ad_close"),
                            By.cssSelector("#dismiss-button"),
                            By.cssSelector("button[title='Close']")
                    );

                    for (By selector : closeButtonSelectors) {
                        List<WebElement> closeButtons = driver.findElements(selector);
                        if (!closeButtons.isEmpty()) {
                            WebElement closeButton = closeButtons.get(0);
                            wait.until(ExpectedConditions.elementToBeClickable(closeButton));
                            closeButton.click();
                            System.out.println("Ad popup closed.");
                            driver.switchTo().defaultContent();
                            return;
                        }
                    }

                } catch (NoSuchElementException | ElementNotInteractableException ignored) {
                    // Ignore and move on to next iframe
                } finally {
                    driver.switchTo().defaultContent();
                }
            }

            // ✅ Fallback: press ESC if no popup was found
            System.out.println("Trying ESC key fallback...");
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();

        } catch (Exception e) {
            System.out.println("Popup not found or couldn't be closed: " + e.getMessage());
        }
    }
}
