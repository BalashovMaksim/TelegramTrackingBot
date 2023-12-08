package com.telegrambot.spring.service;

import com.telegrambot.spring.trackingcompany.*;
import org.springframework.stereotype.Service;

@Service
public class TrackingCompanyServiceFactory {
    public TrackingCompanyService getTrackingService(String trackingNumber) {
        if(trackingNumber.startsWith("UX")) {
            return new UzbekistonPochtasiTrackingCompanyCompany();
        } else if (trackingNumber.startsWith("67")) {
            return new SdekTrackingCompanyCompany();
        } else if (trackingNumber.startsWith("LD") || trackingNumber.startsWith("LQ")) {
            return new ChinaEMSTrackingCompanyCompany();
        } else if (trackingNumber.startsWith("RB")) {
            return new SingaporePostTrackingCompanyCompany();
        } else {
            return new CainiaoTrackingCompanyCompany();
        }
    }
}
