package com.telegrambot.spring.service;

import com.telegrambot.spring.config.TelegramBotConfig;
import com.telegrambot.spring.enums.ResponseMessage;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Data
public class PackageTrackingServiceImpl implements PackageTrackingService{
    private final MessageSender messageSender;
    private final MessageService messageService;
    private final RestTemplate restTemplate;
    private final TrackingCompanyServiceFactory trackingCompanyServiceFactory;
    private final TelegramBotConfig config;

    public PackageTrackingServiceImpl(@Lazy MessageSender messageSender, @Lazy MessageService messageService, RestTemplateBuilder restTemplate, TrackingCompanyServiceFactory trackingCompanyServiceFactory, TelegramBotConfig config) {
        this.messageSender = messageSender;
        this.messageService = messageService;
        this.restTemplate = restTemplate.build();
        this.trackingCompanyServiceFactory = trackingCompanyServiceFactory;
        this.config = config;
    }

    public void startProgram(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if(messageText != null){
                switch (messageText) {
                    case "/start":
                        messageService.handleStartCommand(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/track":
                        messageService.handleTrackCommand(chatId);
                        break;
                    default:
                        handleMessageDefault(chatId, messageText);
                        break;
                }
            }
        }
    }
    public void handleMessageDefault (long chatId, String messageText) {
        if (messageText.startsWith("/track ")) {
            String trackingNumber = messageText.replace("/track ", "");
            executeTracking(chatId, trackingNumber);
        } else {
            messageSender.sendMessage(chatId, ResponseMessage.UNKNOWN_COMMAND.getMessage());
        }
    }
    public void executeTracking(long chatId, String trackingNumber){
        TrackingCompanyService trackingCompanyService = trackingCompanyServiceFactory.getTrackingService(trackingNumber);
        String url = trackingCompanyService.trackCompany(trackingNumber);

        warmUpTrackingNumber(url);
        trackPackage(chatId,url);
    }
    public void warmUpTrackingNumber(String url){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", getConfig().getAuthCookie());
        headers.set("User-Agent", getConfig().getAuthUserAgent());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            getRestTemplate().exchange(url, HttpMethod.GET,entity,String.class);
        } catch (RestClientException e){
            e.printStackTrace();
        }
    }
    public void trackPackage(long chatId, String url){
        try{
            ResponseEntity<String> response = getRestTemplate().getForEntity(url,String.class);
            if(response.getStatusCode() == HttpStatus.OK){
                String trackingInfo = parseTrackingInfo(chatId, response.getBody());
                messageSender.sendMessage(chatId,trackingInfo);
                messageService.handleBeforeTrackingMessage(chatId);
            } else{
                messageService.trackingError(chatId);
            }
        } catch (RestClientException e){
            e.printStackTrace();
            messageService.trackingError(chatId);
        }
    }
    public String parseTrackingInfo(long chatId, String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            return ResponseMessage.TRACKING_ERROR.getMessage();
        }
        try {
            Document document = Jsoup.parse(responseBody);

            Element statusElement = document.selectFirst(".checkpoints > :first-child");
            if (statusElement != null) {
                return statusElement.text();
            } else {
                messageSender.sendMessage(chatId, ResponseMessage.TRACKING_INFO_ERROR.getMessage());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
