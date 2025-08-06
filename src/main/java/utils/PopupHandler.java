package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import java.util.Random;

public class PopupHandler {
    private final WebDriver driver;
    private final JavascriptExecutor jsExecutor;
    private final Actions actions;
    private final Random random;

    public PopupHandler(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.random = new Random();
    }

    public void tryToClosePopupWithRandomClicks() {
        try {
            System.out.println("🔍 Trying to close popup with random clicks...");

            // Ensure the browser is maximized to get correct screen dimensions
            driver.manage().window().maximize();

            // Get the full page dimensions
            int width = ((Number) jsExecutor.executeScript("return window.innerWidth")).intValue();
            int height = ((Number) jsExecutor.executeScript("return window.innerHeight")).intValue();

            for (int i = 0; i < 3; i++) {
                int x = random.nextInt(width - 100) + 50;  // Avoid edges
                int y = random.nextInt(height - 150) + 75;

                try {
                    actions.moveByOffset(x, y).click().perform();
                    System.out.println("Clicked at: (" + x + ", " + y + ")");
                    Thread.sleep(500);
                    actions.moveByOffset(-x, -y).perform();  // Reset mouse position
                } catch (MoveTargetOutOfBoundsException e) {
                    System.out.println(" Click skipped: target out of bounds at (" + x + ", " + y + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Error during popup close clicks: " + e.getMessage());
        }
    }
}
