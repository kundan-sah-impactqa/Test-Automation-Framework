package com.pricer.mobile;

import com.pricer.base.BaseTestMobile;
import com.pricer.page.mobile.pricer.*;
import com.pricer.page.web.LoginPage;
import com.pricer.utilities.ExcelUtil;
import com.pricer.utilities.FrameworkConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Epic("Test Android or iOS")
@Feature("Test Android Execution")
public class Test_Scenarios_For_Pricer_E2E_Functionality extends BaseTestMobile {

    @BeforeClass
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID) {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                FrameworkConfig.getStringConfigProperty("TestDataSheetName_mobile"));

        testDataMap = excel.getRowDataMatchingDataId(dataID);
        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
    }

//    @Test(priority = 1, description = "RN Watchlist E2E flow")
//    @Story("RN Watchlist E2E flow")
//    @Description("Verify Coinbase wallet RN Watchlist E2E flow")
//    public void verify_CoinbaseWallet_RN_MMA_E2E_Flow() throws InterruptedException {
//        String recoveryPhrase = "shield arrive vote thunder belt issue same autumn any endorse output apology";
//        HomePage homePage = new HomePage(driver, platform);
//        // Verify Home Page Elements Displayed
//        homePage.verifyHomePageElementsDisplayed();
//        // Click on I already have a wallet button
//        homePage.clickIAlreadyHaveAWalletButton();
//
//        ImportWalletPage importWalletPage = new ImportWalletPage(driver, platform);
//        // Verify Page Title As Import Wallet
//        importWalletPage.verifyPageTitleAsImportWallet();
//        // Click on Enter Recovery Phrase Button
//        importWalletPage.clickEnterRecoveryPhraseButton();
//
//        SignInWithRecoveryPhrasePage signInWithRecoveryPhrasePage = new SignInWithRecoveryPhrasePage(driver, platform);
//        // Verify Page Title As Sign In With A Recovery Phrase
//        signInWithRecoveryPhrasePage.verifyPageTitleAsSignInWithRecoveryPhrase();
//        // Enter Recovery Phrase
//        signInWithRecoveryPhrasePage.enterRecoveryPhrase(recoveryPhrase);
//        // Click on Continue Button
//        signInWithRecoveryPhrasePage.clickContinueButton();
//
//        BackUpYourWalletPage backUpYourWalletPage = new BackUpYourWalletPage(driver, platform);
//        // Verify Page Title As Back Up Your Wallet
//        backUpYourWalletPage.verifyPageTitleAsBackUpYourWallet();
//        // Click on Skip Button
//        backUpYourWalletPage.clickSkipButton();
//
//        ProtectYourWalletPage protectYourWalletPage = new ProtectYourWalletPage(driver, platform);
//        // Verify Page Title As Protect Your Wallet
//        protectYourWalletPage.verifyPageTitleAsProtectYourWallet();
//        // Accept I Agree Terms Privacy Policy
//        protectYourWalletPage.selectAgreeTermsPrivacyPolicy();
//        // Click on Create Passcode Button
//        protectYourWalletPage.clickCreatePasscodeButton();
//
//        CreatePasscodePage createPasscodePage = new CreatePasscodePage(driver, platform);
//        // Verify Page Title As Create Passcode
//        createPasscodePage.verifyPageTitleAsCreatePasscode();
//        // Enter Passcode
//        createPasscodePage.enterPasscode();
//        // Re-type Passcode
//        createPasscodePage.retypePasscode();
//
//        ClaimUsernamePage claimUsernamePage = new ClaimUsernamePage(driver, platform);
//        // Click on Maybe Later Button
//        claimUsernamePage.clickMaybeLaterButton();
//
//        AssetsPage assetsPage = new AssetsPage(driver, platform);
//        // Verify Page Title As Assets
//        assetsPage.verifyPageTitleAsAssets();
//        // Verify Balances are displayed
//        assetsPage.verifyBalancesAreDisplayed();
//
//        FooterMenu footerMenu = new FooterMenu(driver, platform);
//        // Navigate to Explore
//        footerMenu.navigateToExplore();
//
//        ExplorePage explorePage = new ExplorePage(driver, platform);
//        // Click on Search Bar
//        explorePage.clickOnSearchBar();
//        String ethereum = "Ethereum";
//        // Search Ethereum
//        explorePage.searchElement(ethereum);
//        explorePage.clickOnEthereum();
//        // Verify user is on Ethereum Asset Detail Page
//        explorePage.verifyEthereumAssetDetailPage();
//        explorePage.clickWatchButton();
//        // Verify Watching Button Displayed
//        explorePage.verifyButtonWatchingDisplayed();
//
//        // Navigate to Explore
//        footerMenu.navigateToExplore();
//        explorePage.clickCancelButton();
//        // Verify Ethereum Displayed Under Watchlist
//        explorePage.verifyEthereumDisplayedUnderWatchlist();
//        // Click on Ethereum From Watchlist
//        explorePage.clickEthereumFromWatchlist();
//        // Verify user is on Ethereum Asset Detail Page
//        explorePage.verifyEthereumAssetDetailPage();
//    }

    @Test(priority = 1, description = "Pricer E2E flow")
    @Story("Pricer E2E flow")
    @Description("Verify Pricer E2E flow")
    public void verify_PricerLogin() {
        WelcomePage welcomePage = new WelcomePage(driver, platform);
        // Click on Continue button
        welcomePage.clickOnContinueButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(FrameworkConfig.getStringConfigProperty("Email"));
        loginPage.clickOnNextButton();
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnEnterYourPlaza();
    }
}
