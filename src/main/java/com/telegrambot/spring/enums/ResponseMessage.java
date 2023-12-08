package com.telegrambot.spring.enums;

import lombok.Data;

public enum ResponseMessage  {
    UNKNOWN_COMMAND("Неизвестная команда"),
    BEFORE_TRACKING("Актуальное отслеживание получено. Если вы хотите получить информацию по другому трек-номеру, то введите команду /track <трек-номер>"),
    TRACKING_ERROR("Произошла ошибка отслеживания. Пожалуйста, попробуйте позже"),
    TRACKING_INFO_ERROR("Ошибка получении информации по трек-номеру, попробуйте позже."),
    START_MESSAGE("Приветствую. Если ты хочешь получить актуальный статус своей посылки, то введи команду /track <трек-номер>"),
    TRACKING_INFO("Происходит поиск отслеживания по трек-номеру"),
    TRACK_MESSAGE("Пожалуйста, отправьте трек-номер командой /track <трек-номер>");


    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
