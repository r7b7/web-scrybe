package com.r7b7.webscrybe.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.r7b7.webscrybe.model.DriverType;

@Service
public class DriverServiceFactory {
    private final Map<DriverType, IBaseService> driverServices;

    public DriverServiceFactory(List<IBaseService> driverServices) {
        this.driverServices = driverServices.stream()
                .collect(Collectors.toMap(
                        service -> getDriverType(service.getClass()),
                        service -> service));
    }

    public IBaseService getDriverService(DriverType type) {
        return driverServices.get(type);
    }

    private DriverType getDriverType(Class<?> serviceClass) {
        if (serviceClass == GoogleSearchService.class) {
            return DriverType.GOOGLE;
        } else if (serviceClass == BingSearchService.class) {
            return DriverType.BING;
        }
        throw new IllegalArgumentException("Unknown driver service type");
    }
}
