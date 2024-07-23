package com.vitamojo.page.mobile;

import com.vitamojo.utilities.AppiumUtils;
import com.vitamojo.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;

/**
 *
 * @since   2023-09-12
 * @description All page classes should be extended by this class. It will have handle common elements to all the pages
 */
public class BasePage {

    protected AppiumUtils appiumUtils;

    /**
     * <p>Constructor for BasePage.</p>
     *
     * @param driver a {@link AppiumDriver} object
     * @param pageObjectRepoFileName a {@link String} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public BasePage(AppiumDriver driver, String pageObjectRepoFileName, PageObjectRepoHelper.PLATFORM platform)
    {
        this.appiumUtils = new AppiumUtils(driver, pageObjectRepoFileName, platform);
    }

    /**
     * <p>Getter for the field <code>appiumUtils</code>.</p>
     *
     * @return a {@link AppiumUtils} object
     */
    public AppiumUtils getAppiumUtils(){
        return appiumUtils;
    }

}
