package com.nopcommerce.user;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObects.*;
import commons.PageGeneratorManager;

public class Topic_08_Register_login_SwitchPage_DynamicLocator extends BaseTest {
    private WebDriver driver;
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private LogInPageObject loginPage;
    private CustomerPageObject customerInfo;
    private RewardPointObject rewardPoint;
    private String firstName, lastName, email, passWord;

    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(String browserName, String urlValue){
        driver = getBrowserDriver(browserName);
        driver.get(urlValue);
        homePage = PageGeneratorManager.getHomePage(driver);
        firstName = "Automation";
        lastName = "FC";
        email = "test02@gmail.com";
        passWord ="12345r";
    }
    @Test
    public void TC_01_Register(){
        registerPage= homePage.clickToRegisterLink();
        registerPage.registerNewAccount(firstName, lastName, email, passWord);
        //Assert.assertEquals(registerPage.getRegisteredMessage(),"Wrong email");
    }

    @Test
    public void TC_02_Login_Page_SwitchPageByDynamicXpath(){
        loginPage = homePage.clickToLoginLink();
        loginPage.loginToPage(email,passWord);
        loginPage.clickToMyAccount();
        loginPage.openPageAtMyAccountByPageName(driver,"Customer info");
        customerInfo = PageGeneratorManager.getCustomerInfoPage(driver);

        customerInfo.openPageAtMyAccountByPageName(driver, "Reward points");
        rewardPoint =PageGeneratorManager.getRewardPointObject(driver);

        rewardPoint.openPageAtMyAccountByPageName(driver,"Customer info");
        customerInfo = PageGeneratorManager.getCustomerInfoPage(driver);
    }

    @AfterClass
    public void afterClass(){
        //driver.close();
    }
}
