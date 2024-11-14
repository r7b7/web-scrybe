package com.r7b7.webscrybe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.r7b7.webscrybe.model.DriverType;

public class DriverServiceFactoryTest {
    private DriverServiceFactory driverServiceFactory;
    private IBaseService googleService;
    private IBaseService bingService;

    @BeforeEach
    void setUp() {
        googleService = mock(GoogleSearchService.class);
        bingService = mock(BingSearchService.class);
        driverServiceFactory = new DriverServiceFactory(List.of(googleService, bingService));
    }

    @Test
    void getDriverService_GoogleService_ReturnsGoogleService() {
        IBaseService service = driverServiceFactory.getDriverService(DriverType.GOOGLE);
        assertEquals(googleService, service);
    }

    @Test
    void getDriverService_BingService_ReturnsBingService() {
        IBaseService service = driverServiceFactory.getDriverService(DriverType.BING);
        assertEquals(bingService, service);
    }

    @Test
    void getDriverService_UnknownService_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> driverServiceFactory.getDriverService(DriverType.valueOf("UNKNOWN")));
    }
}
