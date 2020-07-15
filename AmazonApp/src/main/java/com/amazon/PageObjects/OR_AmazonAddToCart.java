package com.amazon.PageObjects;

import java.util.HashMap;

import com.amazon.BaseDriver.BaseDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

@SuppressWarnings("rawtypes")
public class OR_AmazonAddToCart extends BaseDriver{
	
	@SuppressWarnings({"unchecked" })
	protected OR_AmazonAddToCart(AppiumDriver driver, HashMap data) {
		super(driver, data);
	}
	
	@AndroidFindBy(xpath="//android.widget.GridLayout/descendant::android.widget.ImageView[1]")
	protected MobileElement chooseLang;
	
	@AndroidFindBy(xpath="//android.widget.Button[contains(@text,'Skip sign in')]")
	protected MobileElement btn_SkipSignIn;
	
	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/sign_in_button']")
	protected MobileElement btn_SignIn;
	
	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/rs_search_src_text' and (contains(@text,'Search'))]")
	protected MobileElement input_SearchBox;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='search']/descendant::android.view.View[contains(@content-desc,'F Gear Standard Size Mask for Adult Color Black F95 Safeguard 7 layer')]")
	protected MobileElement selectMask;

	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/loc_ux_gps_auto_detect']")
	protected MobileElement btn_CurrentLoc;
	
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'Allow access')]")
	protected MobileElement btn_UseCurrentLoc;
	
	@AndroidFindBy(xpath="//android.widget.Button[contains(@text,'ALLOW ONLY WHILE USING THE APP')]")
	protected MobileElement btn_AllowOnce;
	
	@AndroidFindBy(xpath="//android.widget.Button[@resource-id='add-to-cart-button']")
	protected MobileElement btn_AddToCart;
	
	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/action_bar_cart']")
	protected MobileElement btn_CartIcon;
	
	@AndroidFindBy(xpath="//android.view.View[contains(@content-desc,'F Gear Standard Size Mask for Adult Color Black F95')]")
	protected MobileElement validateMask;
}
