package com.mavenit.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class SmokeTest extends Hooks {

    @Test
    public void searchTest() {
        doSearch("puma");

        String url = driver.getCurrentUrl();
        assertThat(url, endsWith("puma"));


        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));

        for (WebElement indProduct : productWebElements) {
            String actual = indProduct.getText();
            assertThat(actual, containsString("puma"));
        }

        String actualTitle = driver.findElement(By.className("search-title__term")).getText();
        assertThat(actualTitle, is(equalToIgnoringCase("puma")));

        driver.findElement(By.id("searchTerm")).sendKeys("mobile");
        driver.findElement(By.id("searchTerm"))
                .sendKeys(Keys.ENTER);

    }

    @Test
    public void basketTest() {
        doSearch("nike");

        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));
        if (productWebElements.size() == 0) {
            fail("No Products found with: " + "nike");
        }

        // TODO: 2020-02-08 this will be converted in future
        Random random = new Random();
        int randomNumber = random.nextInt(productWebElements.size() - 1);

        WebElement selectedElement = productWebElements.get(randomNumber);
        String selectedProductName = selectedElement.getText();
        selectedElement.click();

        addToBasket();
        goToBasket();
        String actual = getProductInBasket();
        assertThat(actual, is(equalToIgnoringCase(selectedProductName)));
    }


    public void doSearch(String searchTerm) {
        driver.findElement(By.id("searchTerm"))
                .sendKeys(searchTerm);

        driver.findElement(By.id("searchTerm"))
                .sendKeys(Keys.ENTER);

    }


    public void addToBasket() {
        driver.findElement(By.cssSelector("button[data-test='component-att-button']")).click();
    }

    public void goToBasket() {
        driver.findElement(By.cssSelector(".xs-row a[data-test='component-att-button-basket']")).click();
    }
    public String getProductInBasket(){
       return driver.findElement(By.cssSelector(".ProductCard__content__9U9b1.xsHidden.lgFlex")).getText();
    }
}
