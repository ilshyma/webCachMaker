package com.cashmaker.bot;

import com.cashmaker.SpringBootRunner;
import com.pengrad.telegrambot.TelegramBot;


/**
 * Created by asti on 02.03.2018.
 */
public class SenderApi {

    public static TelegramBot getSender(){
        return getInstance();
    }

    private static volatile TelegramBot instance;

    private static TelegramBot getInstance() {
        TelegramBot localInstance = instance;
        if (localInstance == null) {
            synchronized (TelegramBot.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance =  new TelegramBot(SpringBootRunner.BOT_TOKEN);
                }
            }
        }
        return localInstance;
    }
}
