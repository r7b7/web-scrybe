package com.r7b7.webscrybe.service;

import static com.r7b7.webscrybe.config.Constants.SUCCESS;
import static com.r7b7.webscrybe.config.Constants.QUERY_ELEMENT;
import static com.r7b7.webscrybe.config.Constants.HREF;

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
import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.SearchResponse;

@Service
public class BingSearchService implements IBaseService {
    Logger logger = LoggerFactory.getLogger(BingSearchService.class);

    private static final String H2_TAG = "h2";
    private static final String ANCHOR = "a";
    private static final String LINK_CLASS = "li.b_algo";
    private static final String RESULT_LINK = "li.b_algo h2 a";

    AppConfigProperties properties;

    @Override
    public ApiResponse<List<SearchResponse>> getResults(String query, int maxCount) {
        logger.info("Inside Bing Search Function");

        List<SearchResponse> response = new ArrayList<>();
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.createDriver();
            driver.get(properties.getBingUrl());

            WebElement searchBox = driver.findElement(By.name(QUERY_ELEMENT));
            searchBox.sendKeys(query);
            searchBox.submit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(properties.getSleepTime()));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(RESULT_LINK)));

            List<WebElement> results = driver.findElements(By.cssSelector(LINK_CLASS)).stream().limit(maxCount)
                    .collect(Collectors.toList());
            response.addAll(results.stream()
                    .map(result -> new SearchResponse(result.findElement(By.tagName(H2_TAG)).getText(),
                            result.findElement(By.tagName(H2_TAG)).findElement(By.tagName(ANCHOR)).getAttribute(HREF)))
                    .toList());
        } catch (Exception e) {
            logger.error("Exception Occurred in Bing Search", e);
            return ApiResponse.error(e.getMessage());
        } finally {
            driver.quit();
        }
        return ApiResponse.success(SUCCESS, response);
    }

    public BingSearchService(AppConfigProperties properties) {
        this.properties = properties;
    }
}
