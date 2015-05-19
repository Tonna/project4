package com.yakovchuk.webface;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FrontendFirstTest {

    @Test
    public void dummyTest() throws Exception {
        WebDriver driver = new FirefoxDriver();
        driver.get("http:localhost:8081/sphynx");
        System.out.println("Page title is: " + driver.getTitle());
        driver.quit();
    }
}