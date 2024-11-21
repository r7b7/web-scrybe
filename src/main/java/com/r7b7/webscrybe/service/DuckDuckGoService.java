package com.r7b7.webscrybe.service;

import static com.r7b7.webscrybe.config.Constants.HREF;
import static com.r7b7.webscrybe.config.Constants.QUERY_ELEMENT;
import static com.r7b7.webscrybe.config.Constants.SUCCESS;

import java.time.Duration;
import java.util.List;

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
public class DuckDuckGoService implements IBaseService {
    Logger logger = LoggerFactory.getLogger(DuckDuckGoService.class);

    private static final String SPAN = "span";
    private static final String RESULT_TITLE_ID = "a[data-testid=\"result-title-a\"]";

    AppConfigProperties properties;

    @Override
    public ApiResponse<List<SearchResponse>> getResults(String query, int maxCount) {
        logger.info("Inside DuckDuckGo Search Function");

        List<SearchResponse> response = null;
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.createDriver();
            driver.get(properties.getDuckDuckGoUrl());

            WebElement searchBox = driver.findElement(By.name(QUERY_ELEMENT));
            searchBox.sendKeys(query);
            searchBox.submit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(properties.getSleepTime()));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(RESULT_TITLE_ID)));

            List<WebElement> results = driver.findElements(By.cssSelector(RESULT_TITLE_ID));
            response = results.stream().map(result -> new SearchResponse(result.findElement(By.tagName(SPAN)).getText(),
                    result.getAttribute(HREF))).toList();
        } catch (Exception e) {
            logger.error("Exception Occured in DuckDuckGo Search", e);
            return ApiResponse.error(e.getMessage());
        } finally {
            driver.quit();
        }
        return ApiResponse.success(SUCCESS, response);
    }

    public DuckDuckGoService(AppConfigProperties properties) {
        this.properties = properties;
    }
}
