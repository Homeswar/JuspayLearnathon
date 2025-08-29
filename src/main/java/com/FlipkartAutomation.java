package com.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Scanner;
import java.io.File;
import java.time.Duration;
import java.util.List;

public class FlipkartAutomation {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    
    public static void main(String[] args) {
        WebDriverManager.firefoxdriver().setup();        
        String profilePath = "firefox-profile";
        File profileDir = new File(profilePath);
        if (!profileDir.exists()) {
            profileDir.mkdirs();
        }
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--profile", profilePath);
        options.addPreference("browser.cache.disk.enable", true);
        options.addPreference("browser.cache.memory.enable", true);
        options.addPreference("browser.cache.disk.capacity", 1048576);
        options.addPreference("browser.sessionstore.enabled", true);
        options.addPreference("browser.sessionstore.resume_from_crash", true);
        options.addPreference("browser.sessionstore.restore_on_demand", false);
        options.addPreference("browser.sessionstore.max_tabs_undo", 20);
        options.addPreference("browser.sessionstore.interval", 10000);
        options.addPreference("network.cookie.cookieBehavior", 0);
        options.addPreference("network.cookie.lifetimePolicy", 0);
        options.addPreference("network.http.use-cache", true);
        options.addPreference("privacy.clearOnShutdown.cache", false);
        options.addPreference("privacy.clearOnShutdown.cookies", false);
        options.addPreference("privacy.clearOnShutdown.sessions", false);
        options.addPreference("privacy.clearOnShutdown.history", false);
        options.addPreference("privacy.clearOnShutdown.downloads", false);
        options.addPreference("privacy.clearOnShutdown.formdata", false);
        options.addPreference("privacy.sanitize.sanitizeOnShutdown", false);
        options.addPreference("browser.privatebrowsing.autostart", false);
        options.addPreference("dom.storage.enabled", true);
        options.addPreference("dom.indexedDB.enabled", true);
        options.addPreference("security.mixed_content.block_active_content", false);
        options.addPreference("security.mixed_content.block_display_content", false);
        options.addPreference("browser.safebrowsing.enabled", false);
        options.addPreference("browser.safebrowsing.malware.enabled", false);
        options.addPreference("browser.safebrowsing.phishing.enabled", false);
        options.addPreference("browser.startup.homepage_override.mstone", "ignore");
        options.addPreference("startup.homepage_welcome_url", "");
        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            driver.get("https://www.flipkart.com");
            driver.manage().window().maximize();
            Thread.sleep(3000);
            closeLoginPopup();
            searchProduct("trimmer men");
            selectFirstProduct();
            goToCheckout();
            clickDeliverHere();
            clickContinue();
            clickAcceptAndContinue();
            handleCardPayment();
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
    
    private static void closeLoginPopup() {
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'_2KpZ6l _2doB4z')]")));
            closeButton.click();
            Thread.sleep(100);
        } catch (Exception e) {
        }
    }
    
    private static void searchProduct(String productName) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@name='q']")));
            
            searchBox.clear();
            searchBox.sendKeys(productName);
            searchBox.sendKeys(Keys.ENTER);
            
            Thread.sleep(3000);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void selectFirstProduct() {
        try {
            Thread.sleep(2000);
            
            List<WebElement> productLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@data-id]//a[contains(@href,'/p/')]")));
            
            if (productLinks.size() > 0) {
                WebElement firstProduct = productLinks.get(0);
                
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstProduct);
                Thread.sleep(1000);
                
                firstProduct.click();
                
                Thread.sleep(3000);
            } else {
                throw new Exception("No products found in search results");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void goToCheckout() {
        try {
            Thread.sleep(3000);
            
            WebElement buyNowButton = null;
            
            String[] buyNowSelectors = {
                ".QqFHMw.vslbG\\+._3Yl67G._7Pd1Fp",
                "button.QqFHMw.vslbG\\+._3Yl67G._7Pd1Fp",
                ".QqFHMw",
                "//button[@class='QqFHMw vslbG+ _3Yl67G _7Pd1Fp']",
                "//button[contains(@class,'QqFHMw') and contains(@class,'vslbG+')]",
                "//button[contains(text(),'BUY NOW')]",
                "//button[text()='BUY NOW']",
                "//button[@class='_2KpZ6l _2U9uOA ihZ75k _3v1-ww']",
                "//button[contains(@class,'_2KpZ6l') and contains(text(),'BUY NOW')]",
                "button[class*='_2KpZ6l'][class*='_2U9uOA']",
                "//form//button[contains(text(),'BUY NOW')]"
            };
            
            for (String selector : buyNowSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        buyNowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        buyNowButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (buyNowButton != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buyNowButton);
                Thread.sleep(1000);
                
                buyNowButton.click();
                
                Thread.sleep(3000);
                
            } else {
                WebElement addToCartButton = null;
                String[] addToCartSelectors = {
                    "//button[contains(text(),'ADD TO CART')]",
                    "//button[contains(text(),'Add to Cart')]",
                    "//button[contains(@class,'_2KpZ6l _2U9uOA _3v1-ww')]"
                };
                
                for (String selector : addToCartSelectors) {
                    try {
                        addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                        break;
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                if (addToCartButton != null) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
                    Thread.sleep(1000);
                    
                    addToCartButton.click();
                    Thread.sleep(2000);
                    
                    try {
                        WebElement goToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(@href,'/checkout/cart')]")));
                        goToCartButton.click();
                    } catch (Exception e) {
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void clickDeliverHere() {
        try {
            Thread.sleep(3000);
            
            WebElement deliverHereButton = null;
            
            String[] deliverHereSelectors = {
                ".QqFHMw.FA45gW._7Pd1Fp",
                "button.QqFHMw.FA45gW._7Pd1Fp",
                ".QqFHMw.FA45gW",
                ".QqFHMw",
                "//button[@class='QqFHMw FA45gW _7Pd1Fp']",
                "//button[contains(@class,'QqFHMw') and contains(@class,'FA45gW')]",
                "//button[contains(text(),'Deliver Here')]",
                "//button[contains(text(),'DELIVER HERE')]",
                "//span[contains(text(),'Deliver Here')]/parent::button",
                "//div[contains(text(),'Deliver Here')]/parent::button"
            };
            
            for (String selector : deliverHereSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        deliverHereButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        deliverHereButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (deliverHereButton != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deliverHereButton);
                Thread.sleep(1000);
                
                deliverHereButton.click();
                
                Thread.sleep(3000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void clickContinue() {
        try {
            Thread.sleep(3000);
            
            WebElement continueButton = null;
            
            String[] continueSelectors = {
                ".QqFHMw.VuSC8m._7Pd1Fp",
                "button.QqFHMw.VuSC8m._7Pd1Fp",
                ".QqFHMw.VuSC8m",
                ".QqFHMw",
                "//button[@class='QqFHMw VuSC8m _7Pd1Fp']",
                "//button[contains(@class,'QqFHMw') and contains(@class,'VuSC8m')]",
                "//button[contains(text(),'Continue')]",
                "//button[contains(text(),'CONTINUE')]",
                "//span[contains(text(),'Continue')]/parent::button",
                "//div[contains(text(),'Continue')]/parent::button"
            };
            
            for (String selector : continueSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (continueButton != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
                Thread.sleep(1000);
                
                continueButton.click();
                
                Thread.sleep(3000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void clickAcceptAndContinue() {
        try {
            Thread.sleep(3000);
            
            WebElement acceptContinueButton = null;
            
            String[] acceptContinueSelectors = {
                ".QqFHMw._0ofT-K.M5XAsp",
                "button.QqFHMw._0ofT-K.M5XAsp",
                ".QqFHMw._0ofT-K",
                ".QqFHMw",
                "//button[@class='QqFHMw _0ofT-K M5XAsp']",
                "//button[contains(@class,'QqFHMw') and contains(@class,'_0ofT-K')]",
                "//button[contains(text(),'Accept & Continue')]",
                "//button[contains(text(),'ACCEPT & CONTINUE')]",
                "//button[contains(text(),'Accept')]",
                "//span[contains(text(),'Accept & Continue')]/parent::button",
                "//div[contains(text(),'Accept & Continue')]/parent::button"
            };
            
            for (String selector : acceptContinueSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        acceptContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        acceptContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (acceptContinueButton != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", acceptContinueButton);
                Thread.sleep(1000);
                
                acceptContinueButton.click();
                
                Thread.sleep(3000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void handleCardPayment() {
        try {
            Thread.sleep(3000);
            
            clickCreditDebitCard();
            enterCardNumber();
            enterExpiryDate();
            enterCVV();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void clickCreditDebitCard() {
        try {
            WebElement cardOption = null;
            
            String[] cardSelectors = {
                ".EtFGuU",
                "div.EtFGuU",
                "//div[@class='EtFGuU']",
                "//div[contains(@class,'EtFGuU')]",
                "//div[contains(text(),'Credit') or contains(text(),'Debit') or contains(text(),'ATM')]",
                "//span[contains(text(),'Credit') or contains(text(),'Debit')]/parent::div",
                "//label[contains(text(),'Credit') or contains(text(),'Debit')]"
            };
            
            for (String selector : cardSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        cardOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        cardOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (cardOption != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardOption);
                Thread.sleep(1000);
                
                cardOption.click();
                Thread.sleep(2000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void enterCardNumber() {
        try {
            WebElement cardNumberField = null;
            
            String[] cardNumberSelectors = {
                ".R5qjeY",
                "input.R5qjeY",
                "//input[@class='R5qjeY']",
                "//input[contains(@class,'R5qjeY')]",
                "//input[@placeholder*='Card' or @placeholder*='card']",
                "//input[@name*='card' or @name*='Card']",
                "//input[@type='text'][contains(@class,'R5qjeY')]"
            };
            
            for (String selector : cardNumberSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        cardNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        cardNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (cardNumberField != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardNumberField);
                Thread.sleep(1000);
                
                cardNumberField.clear();
                String dummyCardNumber = "4591560066181405";
                cardNumberField.sendKeys(dummyCardNumber);
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void enterExpiryDate() {
        try {
            WebElement expiryField = null;
            
            String[] expirySelectors = {
                ".E5H8ws",
                "input.E5H8ws",
                "//input[@class='E5H8ws']",
                "//input[contains(@class,'E5H8ws')]",
                "//input[@placeholder*='MM/YY' or @placeholder*='Expiry' or @placeholder*='Valid']",
                "//input[@name*='expiry' or @name*='valid']",
                "//input[@type='text'][contains(@class,'E5H8ws')]"
            };
            
            for (String selector : expirySelectors) {
                try {
                    if (selector.startsWith("//")) {
                        expiryField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        expiryField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (expiryField != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", expiryField);
                Thread.sleep(1000);
                
                expiryField.clear();
                expiryField.sendKeys("0426");
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void enterCVV() {
        try {
            WebElement cvvField = null;
            
            String[] cvvSelectors = {
                "//input[@placeholder='CVV']",
                ".R5qjeY",
                "input.R5qjeY",
                "//input[@class='R5qjeY']",
                "//input[contains(@class,'R5qjeY')]",
                "//input[@placeholder*='CVV' or @placeholder*='cvv']",
                "//input[@name*='cvv' or @name*='CVV']",
                "//input[@type='text'][contains(@class,'R5qjeY')]",
                "//input[@type='password'][contains(@class,'R5qjeY')]"
            };
            
            for (String selector : cvvSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        cvvField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        cvvField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (cvvField != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cvvField);
                Thread.sleep(1000);
                
                cvvField.clear();
                cvvField.sendKeys("405");
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
