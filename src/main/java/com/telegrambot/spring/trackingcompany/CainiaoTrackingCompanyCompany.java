package com.telegrambot.spring.trackingcompany;

import com.telegrambot.spring.service.TrackingCompanyService;

public class CainiaoTrackingCompanyCompany implements TrackingCompanyService {
    @Override
    public String trackCompany(String trackingNumber) {
        return "https://gdeposylka.ru/courier/cainiao/tracking/" + trackingNumber;
    }
}
