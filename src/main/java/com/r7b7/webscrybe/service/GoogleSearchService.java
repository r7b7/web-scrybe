package com.r7b7.webscrybe.service;

import static com.r7b7.webscrybe.config.Constants.HREF;
import static com.r7b7.webscrybe.config.Constants.QUERY_ELEMENT;
import static com.r7b7.webscrybe.config.Constants.SUCCESS;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.SearchResponse;

@Service
public class GoogleSearchService implements IBaseService {
    Logger logger = LoggerFactory.getLogger(GoogleSearchService.class);

    private static final String H3_TAG = "h3.LC20lb.MBeuO.DKV0Md";
    private static final String ANCHOR_TAG = "a[jsname='UWckNb']";

    AppConfigProperties properties;

    @Override
    public ApiResponse<List<SearchResponse>> getResults(String query, int maxCount) {
        logger.info("Inside Google Search Function");
        List<SearchResponse> response = null;
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.createDriver();
            driver.get(properties.getGoogleUrl());

            WebElement searchBox = driver.findElement(By.name(QUERY_ELEMENT));
            searchBox.sendKeys(query);
            searchBox.submit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(properties.getSleepTime()));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(ANCHOR_TAG)));

            List<WebElement> results = driver.findElements(By.cssSelector(ANCHOR_TAG)).stream()
                    .collect(Collectors.toList());
            response = results.stream().filter(result -> StringUtils.isNotBlank(result.findElement(By.cssSelector(H3_TAG)).getText())).limit(maxCount).map(result -> new SearchResponse(result.findElement(By.cssSelector(H3_TAG)).getText(),
                    result.getAttribute(HREF))).toList();
        } catch (Exception e) {
            logger.error("Exception Occured in Google Search", e);
            return ApiResponse.error(e.getMessage());
        } finally {
            driver.quit();
        }
        return ApiResponse.success(SUCCESS, response);
    }

    public GoogleSearchService(AppConfigProperties properties) {
        this.properties = properties;
    }

}
