package com.telegrambot.spring.service;

public interface MessageService {
    void handleBeforeTrackingMessage(long chatId);
    void trackingError(long chatId);
    void handleStartCommand(long chatId, String name);
    void handleTrackCommand(long chatId);
}
