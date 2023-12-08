package com.telegrambot.spring.service;

import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface PackageTrackingService {
    void warmUpTrackingNumber(String url);
    void trackPackage(long chatId, String url);
    String parseTrackingInfo(long chatId, String responseBody);
    void startProgram(Update update);
    void executeTracking(long chatId, String trackingNumber);
}
