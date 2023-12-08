package com.telegrambot.spring.service;

import com.telegrambot.spring.enums.ResponseMessage;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class MessageServiceImpl implements MessageService{
    private final MessageSender messageSender;
    public void handleBeforeTrackingMessage(long chatId){
        String beforeTracking = ResponseMessage.BEFORE_TRACKING.getMessage();
        messageSender.sendMessage(chatId,beforeTracking);
    }
    public void trackingError(long chatId){
        String trackingError = ResponseMessage.TRACKING_ERROR.getMessage();
        messageSender.sendMessage(chatId,trackingError);
    }
    public void handleStartCommand(long chatId, String name) {
        String answer = ResponseMessage.START_MESSAGE.getMessage();
        messageSender.sendMessage(chatId, answer);
    }
    public void handleTrackCommand(long chatId) {
        String reply = ResponseMessage.TRACK_MESSAGE.getMessage();
        messageSender.sendMessage(chatId, reply);
    }
}
