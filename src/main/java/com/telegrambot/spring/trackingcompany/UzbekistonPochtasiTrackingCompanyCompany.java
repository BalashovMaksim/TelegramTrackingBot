package com.telegrambot.spring.trackingcompany;

import com.telegrambot.spring.service.TrackingCompanyService;
import org.springframework.stereotype.Service;

@Service
public class UzbekistonPochtasiTrackingCompanyCompany implements TrackingCompanyService {
    @Override
    public String trackCompany(String trackingNumber) {
        return "https://gdeposylka.ru/courier/ozbekiston-pochtasi/tracking/" + trackingNumber;
    }
}
