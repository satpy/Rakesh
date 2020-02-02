package com.mavenit.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SmokeTestSuite extends Hooks {

    @Test
    public void searchTest() {
        //search
        driver.findElement(By.id("searchTerm"))
                .sendKeys("puma");

        driver.findElement(By.id("searchTerm"))
                .sendKeys(Keys.ENTER);

        //Assert -1
        String url = driver.getCurrentUrl();
        assertThat("Not got results of search term. ", url, endsWith("puma"));

        //Assert -2
        //Collect a item to list
        //loop and verify
        //product contains string

        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));

        for (WebElement indProduct : productWebElements) {
            String actual = indProduct.getText();
            assertThat(actual, containsString("puma"));
        }

//        Assert -3
        String actualTitle = driver.findElement(By.className("search-title__term")).getText();
        assertThat(actualTitle, is(equalToIgnoringCase("puma")));

    }
}
