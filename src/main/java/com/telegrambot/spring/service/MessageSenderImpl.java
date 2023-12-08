package com.telegrambot.spring.service;

import lombok.Data;

import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@Data
public class MessageSenderImpl implements MessageSender{

    private final TelegramBotService telegramBotService;

    public MessageSenderImpl(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }
    @Override
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        try {
            telegramBotService.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
