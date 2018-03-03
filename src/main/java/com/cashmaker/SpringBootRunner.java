package com.cashmaker;

import com.cashmaker.bot.MyAmazingBot;
import com.cashmaker.bot.SenderApi;
import com.cashmaker.pojo.example.Quote;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * Created by asti on 02.03.2018.
 */
@SpringBootApplication
@RestController
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = "com.cashmaker")
public class SpringBootRunner {

    public static final String BOT_TOKEN = "564225171:AAHajRSJNkgcI-eWKP7azxz_a6zSTrVe5yM";

    private static long CHAT_ID_MY = 247731410;
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public TaskExecutor taskScheduler() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

//    @Bean
//    public ScheduleTask scheduleTask() {
//        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
//        return scheduledThreadPoolExecutor;
//    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(SpringBootRunner.class, args);

        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);

        System.out.println(quote);
//        // бот
//        TelegramBot bot = new TelegramBot("564225171:AAHajRSJNkgcI-eWKP7azxz_a6zSTrVe5yM");
//
////
//        ApiContextInitializer.init();
//
//        // Instantiate Telegram Bots API
//        TelegramBotsApi botsApi = new TelegramBotsApi();
//
//        // Register our bot
//        try {
//
//            botsApi.registerBot(new MyAmazingBot());
//            System.out.println("bot reg success");
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }
}

