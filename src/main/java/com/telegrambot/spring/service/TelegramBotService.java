package com.telegrambot.spring.service;

import com.telegrambot.spring.config.TelegramBotConfig;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@Data
public class TelegramBotService extends TelegramLongPollingBot {
    private final TelegramBotConfig config;
    private final PackageTrackingService packageTrackingService;

    public TelegramBotService(TelegramBotConfig config, PackageTrackingService packageTrackingService) {
        this.config = config;
        this.packageTrackingService = packageTrackingService;
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        packageTrackingService.startProgram(update);
    }

}
