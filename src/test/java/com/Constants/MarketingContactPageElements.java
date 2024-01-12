package com.Constants;

public class MarketingContactPageElements {
    public String marketingContact ="//div[contains(text(),'Marketing')]/following-sibling::div/button[text()='Contact']";
    public String marketingContactForm ="//form[contains(@id,'contactMarketing')]";
    public String marketingContactFN =marketingContactForm + "//input[@id='form-input-fullName']";
    public String marketingContactOrg =marketingContactForm + "//input[@id='form-input-organisationName']";
    public String marketingContactPh =marketingContactForm + "//input[@id='form-input-cellPhone']";
    public String marketingContactEmail =marketingContactForm + "//input[@id='form-input-email']";
    public String marketingContactRole =marketingContactForm + "//select[@id='form-input-jobRole']";
    public String marketingContactAgree =marketingContactForm + "//input[@id='form-input-consentAgreed-0']";
    public String marketingContactSubmit =marketingContactForm + "//button[@name='form_page_submit']";
    public String marketingContactError ="//div[@class='ff-form-errors']/p";
}
