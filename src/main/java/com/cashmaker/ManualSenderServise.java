package com.cashmaker;

import com.cashmaker.bot.SenderApi;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by asti on 02.03.2018.
 */
@RestController
public class ManualSenderServise {

    private SenderApi senderApi;

    @Autowired
    public void setSenderApi(SenderApi senderApi) {
        this.senderApi = senderApi;
    }

    @GetMapping("/send")
    public String sendMes(@RequestParam("to") long id,
                          @RequestParam("text") String text) {
        System.out.println(String.format("Получил задачу отправить [%s] в чат {%s]", text, id));
        senderApi.sendSyncMessageForOneRecipient(id, text);
        return "ok!";
    }
}
