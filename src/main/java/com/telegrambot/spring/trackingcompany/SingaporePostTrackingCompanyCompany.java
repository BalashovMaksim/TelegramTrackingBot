package com.telegrambot.spring.trackingcompany;

import com.telegrambot.spring.service.TrackingCompanyService;

public class SingaporePostTrackingCompanyCompany implements TrackingCompanyService {
    @Override
    public String trackCompany(String trackingNumber) {
        return "https://gdeposylka.ru/courier/singapore-post/tracking/" + trackingNumber;
    }
}
