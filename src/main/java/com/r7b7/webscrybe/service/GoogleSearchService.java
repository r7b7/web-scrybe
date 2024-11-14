package com.r7b7.webscrybe.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.r7b7.webscrybe.config.AppConfigProperties;
import com.r7b7.webscrybe.config.WebDriverFactory;
import com.r7b7.webscrybe.model.SearchResponse;

@Service
public class GoogleSearchService implements IBaseService {
    Logger logger = LoggerFactory.getLogger(GoogleSearchService.class);

    AppConfigProperties properties;

    @Override
    public List<SearchResponse> getResults(String query, int maxCount) {
        logger.info("Inside Google Search Function");

        List<SearchResponse> response = null;
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.createDriver();
            driver.get(properties.getGoogleUrl());

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(query);
            searchBox.submit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(properties.getSleepTime()));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3")));

            List<WebElement> results = driver.findElements(By.cssSelector("h3")).stream().limit(maxCount)
                    .collect(Collectors.toList());
            response = results.stream().map(result -> new SearchResponse(result.getText(),
                    result.findElement(By.xpath("..")).getAttribute("href"))).toList();
        } catch (Exception e) {
            logger.error("Exception Occured in Google Search", e);
        } finally {
            driver.quit();
        }
        return response;
    }

    public GoogleSearchService(AppConfigProperties properties) {
        this.properties = properties;
    }

}
