package commons;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {
    /* Web Browser */
    public void openUrl(WebDriver driver, String url){
        driver.get(url);
    }
    public String getPageTitle(WebDriver driver){
        return driver.getTitle();
    }
    public String getPageUrl(WebDriver driver){
        return driver.getCurrentUrl();
    }
    public String getPageSourceCode(WebDriver driver){
        return driver.getPageSource();
    }
    public void backToPage(WebDriver driver){
        driver.navigate().back();
    }
    public void refreshPage(WebDriver driver){
        driver.navigate().refresh();
    }
    public void forwardToPage(WebDriver driver){
        driver.navigate().forward();
    }
    public Alert waitForAlertPresence(WebDriver driver){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }
    public void acceptAlert(WebDriver driver){
        waitForAlertPresence(driver).accept();
    }
    public void cancelAlert(WebDriver driver){
        waitForAlertPresence(driver).dismiss();
    }
    public String getAlertText(WebDriver driver){
        return waitForAlertPresence(driver).getText();
    }
    public void sendKeyToAlert(WebDriver driver, String value){
        waitForAlertPresence(driver).sendKeys(value);
    }
    public void switchToWindowByID(WebDriver driver, String windowPageID){
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            if (!window.equals(windowPageID)){
                driver.switchTo().window(window);
            }
        }
    }
    public void switchToWindowByPageTitle(WebDriver driver, String windowPageTitle){
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            driver.switchTo().window(window);
            sleepInSecond(2);
            String actualPageTitle = driver.getTitle();
            if (actualPageTitle.equals(windowPageTitle)){
                break;
            }
        }
    }
    public void closeAllWindowsWithoutParent(WebDriver driver, String parentPageID){
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            if (!window.equals(parentPageID)){
                driver.switchTo().window(window);
                sleepInSecond(1);
                driver.close();
            }
        }
        driver.switchTo().window(parentPageID);
        sleepInSecond(1);
    }

    /* Web Element */
    public By getByxPath(String xpathExpression){
        return By.xpath(xpathExpression);
    }
    public WebElement getWebElement(WebDriver driver, String xpathExpression){
        return driver.findElement(getByxPath(xpathExpression));
    }
    public List<WebElement> getWebElements(WebDriver driver, String xpathExpression){
        return driver.findElements(getByxPath(xpathExpression));
    }
    public void clickToElement(WebDriver driver, String xpathExpression){
        getWebElement(driver,xpathExpression).click();
    }
    public void sendKeyToElement(WebDriver driver, String xpathExpression, String value){
        getWebElement(driver,xpathExpression).clear();
        getWebElement(driver,xpathExpression).sendKeys(value);
    }
    public String getElementText(WebDriver driver, String xpathExpression){
        return getWebElement(driver,xpathExpression).getText();
    }
    public void selectItemInDefaultDropdown(WebDriver driver, String xpathExpression, String itemText){
        new Select(getWebElement(driver,xpathExpression)).selectByVisibleText(itemText);
    }
    public String getSelectedTextInDefaultDropdown(WebDriver driver, String xpathExpression){
        return new Select(getWebElement(driver, xpathExpression)).getFirstSelectedOption().getText();
    }
    public boolean isDefaultDropdownMultiple(WebDriver driver, String xpathExpression){
        return new Select(getWebElement(driver, xpathExpression)).isMultiple();
    }
    public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem){
        getWebElement(driver, parentLocator).click();
        sleepInSecond(1);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByxPath(childItemLocator)));
        for (WebElement item : allItems){
            if (item.getText().trim().equals(expectedItem)){
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("argument[0].scrollIntoView(True);", item);
                sleepInSecond(1);
                item.click();
                sleepInSecond(1);
                break;
            }
        }
    }
    public String getElementAttribute(WebDriver driver, String xpathExpression, String attributeName){
        return getWebElement(driver,xpathExpression).getAttribute(attributeName);
    }
    public String getElementCssValue(WebDriver driver, String xpathExpression, String propertyName){
        return getWebElement(driver, xpathExpression).getCssValue(propertyName);
    }
    public String getHexaColorByRgbaColor(String rgbaColor){
        return Color.fromString(rgbaColor).asHex();
    }
    public int getElementsSize(WebDriver driver, String xpathExpression){
        return getWebElements(driver, xpathExpression).size();
    }
    public void checkToRadioOrCheckbox(WebDriver driver, String xpathExpression){
        if (!getWebElement(driver, xpathExpression).isSelected()){
            getWebElement(driver, xpathExpression).click();
        }
    }
    public void uncheckToCheckbox(WebDriver driver, String xpathExpression){
        if (getWebElement(driver, xpathExpression).isSelected()){
            getWebElement(driver, xpathExpression).click();
        }
    }
    public boolean isElementDisplayed(WebDriver driver, String xpathExpression){
        return getWebElement(driver, xpathExpression).isDisplayed();
    }
    public boolean isElementEnable(WebDriver driver, String xpathExpression){
        return getWebElement(driver, xpathExpression).isEnabled();
    }
    public boolean isElementSelected(WebDriver driver, String xpathExpression){
        return getWebElement(driver, xpathExpression).isSelected();
    }
    public void switchToFrame(WebDriver driver, String xpathExpression){
        driver.switchTo().frame(getWebElement(driver,xpathExpression));
    }
    public void switchToDefaultContentPage(WebDriver driver){
        driver.switchTo().defaultContent();
    }
    public void hoverMouseToElement(WebDriver driver, String xpathExpression){
        new Actions(driver).moveToElement(getWebElement(driver,xpathExpression)).perform();
    }
    public void pressKeyboardToElement(WebDriver driver, String xpathExpression, Keys key){
        new Actions(driver).sendKeys(getWebElement(driver, xpathExpression)).perform();
    }
    public Object executeForBrowser(WebDriver driver, String javaScript){
        return ((JavascriptExecutor) driver).executeScript(javaScript);
    }
    public String getInnterText(WebDriver driver){
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }
    public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected){
        String textActual = (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }
    public void scrollToBottomPage(WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    public void navigateToUrlByJS(WebDriver driver, String url){
        ((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
    }
    public void highlightElement(WebDriver driver, String xpathpression){
        WebElement element = getWebElement(driver, xpathpression);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], argument[2])", element, "style","border: 2x solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], argument[2])", element, "style", originalStyle);
    }
    public void clickToElementByJS(WebDriver driver, String xpathExpression){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, xpathExpression));
    }
    public void scrollToElement(WebDriver driver, String xpathExpression){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollToView(true);", getWebElement(driver, xpathExpression));
    }
    public void sendkeyToElementByJS(WebDriver driver, String xpathExpression, String value){
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value','" + value + "')",getWebElement(driver, xpathExpression));
    }
    public  void removeAttributeInDOM(WebDriver driver, String xpathExpression, String attributeRemove){
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, xpathExpression));
    }
    public boolean areJQueryAndJSLoadedSucsess(WebDriver driver){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e){
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }
    public boolean isImageLoaded(WebDriver driver, String xpathExpression){
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].natualWidth > 0", getWebElement(driver, xpathExpression));
        if (status){
            return true;
        } else {
            return false;
        }
    }

    public void sleepInSecond(long timeoutInSecond){
        try {
            Thread.sleep(timeoutInSecond*1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}