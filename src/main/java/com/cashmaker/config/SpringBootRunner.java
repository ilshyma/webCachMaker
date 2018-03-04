package com.cashmaker.config;

import com.cashmaker.pojo.example.Quote;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * Created by asti on 02.03.2018.
 */
@SpringBootApplication
@RestController
@EnableScheduling
@EnableAsync
@EnableCaching
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

        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder().build(loader);


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

