package com.cashmaker;

import com.cashmaker.bot.MyAmazingBot;
import com.cashmaker.bot.SenderApi;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by asti on 02.03.2018.
 */
@RestController
@EnableAutoConfiguration
public class SpringBootRunner {

    public static final String BOT_TOKEN = "564225171:AAHajRSJNkgcI-eWKP7azxz_a6zSTrVe5yM";

    private static long CHAT_ID_MY = 247731410;
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/send")
    public String sendMes(@RequestParam("to") long id,
                          @RequestParam("text") String text) {
        System.out.println(String.format("Получил задачу отправить [%s] в чат {%s]", text, id));
        SendMessage request = new SendMessage(id, text)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
//                .disableNotification(true)
//                .replyToMessageId(1)
//                .replyMarkup(new ForceReply())
                ;

// sync
        SendResponse sendResponse = SenderApi.getSender().execute(request);
        boolean ok = sendResponse.isOk();
        Message message = sendResponse.message();
        return "ok!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootRunner.class, args);

        // бот
        TelegramBot bot = new TelegramBot("564225171:AAHajRSJNkgcI-eWKP7azxz_a6zSTrVe5yM");

//
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {

            botsApi.registerBot(new MyAmazingBot());
            System.out.println("bot reg success");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
