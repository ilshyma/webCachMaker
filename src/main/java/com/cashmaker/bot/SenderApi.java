package com.cashmaker.bot;

import com.cashmaker.config.SpringBootRunner;
import com.google.common.collect.Lists;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by asti on 02.03.2018.
 */
@Component
@Slf4j
public class SenderApi {

    public static List<Long> CHAT_IDS = Lists.newArrayList(247731410L, 339483084L);


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

    /**
     * Отправляет сообщение списку получателей
     * @param chatIds ид чатов получаетелей
     * @param messageText текст сообщения
     */
    public void sendSyncMessageForGroupRecipients(List<Long> chatIds, String messageText){
        log.info("sendSyncMessageForGroupRecipients() start work! текст [{}] чаты-получатели [{}]", messageText, chatIds);
        for (Long chatId : chatIds) {
            sendSyncMessageForOneRecipient(chatId, messageText);
        }
        log.info("sendSyncMessageForGroupRecipients() end work!", messageText, chatIds);

    }

    /**
     * Отправляет сообщение конкретному получателю. Синхронный метод
     * @param chatId ид чата получателя
     * @param messageText текст сообщения
     * @return true - отправка прошла успешно
     */
    public boolean sendSyncMessageForOneRecipient(long chatId, String messageText){
      log.info("sendSyncMessageForOneRecipient() текст [{}] чат-получатель {}", messageText, chatId);
        SendMessage request = new SendMessage(chatId, messageText)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
//                .replyToMessageId(1)
//                .replyMarkup(new ForceReply())
                ;

        // sync
        SendResponse sendResponse = getInstance().execute(request);
        boolean ok = sendResponse.isOk();
        log.info("Статус отправки: {}", ok ? "успешно":"не успешно");
        Message message = sendResponse.message();
        log.info("message after sending: {}", message);
        return ok;
    }

}
