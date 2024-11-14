package com.r7b7.webscrybe.service;

import java.time.Duration;
import java.util.ArrayList;
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
public class BingSearchService implements IBaseService {
    Logger logger = LoggerFactory.getLogger(BingSearchService.class);

    AppConfigProperties properties;

    @Override
    public List<SearchResponse> getResults(String query, int maxCount) {
        logger.info("Inside Bing Search Function");

        List<SearchResponse> response = new ArrayList<>();
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.createDriver();
            driver.get(properties.getBingUrl());

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(query);
            searchBox.submit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(properties.getSleepTime()));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.b_algo h2 a")));

            List<WebElement> results = driver.findElements(By.cssSelector("li.b_algo")).stream().limit(maxCount)
                    .collect(Collectors.toList());
            response.addAll(results.stream()
                    .map(result -> new SearchResponse(result.findElement(By.tagName("h2")).getText(),
                            result.findElement(By.tagName("h2")).findElement(By.tagName("a")).getAttribute("href")))
                    .toList());
        } catch (Exception e) {
            logger.error("Exception Occurred in Bing Search", e);
        } finally {
            driver.quit();
        }
        return response;
    }

    public BingSearchService(AppConfigProperties properties) {
        this.properties = properties;
    }
}
