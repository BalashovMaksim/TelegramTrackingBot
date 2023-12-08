package com.telegrambot.spring.trackingcompany;

import com.telegrambot.spring.service.TrackingCompanyService;
import org.springframework.stereotype.Service;

@Service
public class SdekTrackingCompanyCompany implements TrackingCompanyService {
    @Override
    public String trackCompany(String trackingNumber) {
        return "https://gdeposylka.ru/courier/sdek/tracking/" + trackingNumber;
    }
}
