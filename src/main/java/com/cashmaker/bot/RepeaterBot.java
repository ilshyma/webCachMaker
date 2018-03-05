package com.cashmaker.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class RepeaterBot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            System.out.println(String.format("chatId [%s], message was [%s]", chat_id, message_text));

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText("Старое сообщение: \n" + message_text + ".\n ChatID = ["+ chat_id+"]");
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        // Return bot username
        // If bot username is @RepeaterBot, it must return 'RepeaterBot'
        return "wcmr_bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "564225171:AAHajRSJNkgcI-eWKP7azxz_a6zSTrVe5yM";
    }
}
