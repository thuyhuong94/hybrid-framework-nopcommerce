package com.jQuery.dataTable;

import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObects.*;
import pageObjectsjQuery.HomePageObject;
import pageObjectsjQuery.PageGeneratorManager;

import java.util.List;
//@Listeners(commons.MethodListener.class)
public class Topic_09_DataTable_HandleTableGrid extends BaseTest {
    private WebDriver driver;
    private HomePageObject homePage;
    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(String browserName, String urlValue){
        driver = getBrowserDriver(browserName);
        driver.get(urlValue);
        homePage = PageGeneratorManager.getHomePage(driver);
    }
    @Test
    public void TC_01_SelectPageAndVerifyPageIsSelected(){
        homePage.openPagingByNumber("10");
        verifyTrue(homePage.isPageNumberactived("10"));
        homePage.openPagingByNumber("18");
        verifyFalse(homePage.isPageNumberactived("18"));
        homePage.openPagingByNumber("8");
        verifyTrue(homePage.isPageNumberactived("8"));
    }
    @Test
    public void TC_02_SearchItemsByEnterToHeader(){
        homePage.refreshPage(driver);
        homePage.enterToHeaderTextBox("Country","Armenia");
        homePage.enterToHeaderTextBox("Females","15999");
        homePage.enterToHeaderTextBox("Males","16456");
        homePage.enterToHeaderTextBox("Total","32487");
        homePage.sleepInSecond(1);
        homePage.enterToHeaderTextBox("Country","Angola");
        homePage.enterToHeaderTextBox("Females","276880");
        homePage.enterToHeaderTextBox("Males","276472");
        homePage.enterToHeaderTextBox("Total","553353");
        homePage.sleepInSecond(1);

    }
    @Test
    public void TC_03_GetAllValueInColumn(){
        homePage.refreshPage(driver);
        List<String> allvalue = homePage.getValueByColumnEachRowAtAllPage("country");
        System.out.println(allvalue);
    }
    @AfterClass
    public void afterClass(){
        driver.close();
    }
}
