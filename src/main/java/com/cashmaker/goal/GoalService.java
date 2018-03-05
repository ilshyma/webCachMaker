package com.cashmaker.goal;

import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by asti on 04.03.2018.
 */
@Service
@Slf4j
public class GoalService {

    /**
     * максимальное кол-во элементов в кеше
     */
    private static final int CACHE_MAX_ELEM_SIZE = 5000;

    /**
     * максимальное кол-во потоков одновременно обращающихся к кешу
     */
    private static final int CONCURRENCY_LEVEL = 10;


    private Cache<GoalKeeper, GoalKeeper> cacheeGoal;

    @PostConstruct
    public void init() {
        cacheeGoal = CacheBuilder
                .newBuilder()
                .maximumSize(CACHE_MAX_ELEM_SIZE)
                .concurrencyLevel(CONCURRENCY_LEVEL)
                .build();
    }

    private Set<GoalKeeper> getAllGoals(){
        final Set<GoalKeeper> allGoals = cacheeGoal.asMap().keySet();
        log.info("getAllGoals(): {}", allGoals);
        return allGoals;

    }

    public Set<GoalKeeper> getGoalsForCampaign(String campaignId){
        final Collection<GoalKeeper> filteredGoals = Collections2.filter(getAllGoals(), new Predicate<GoalKeeper>() {
            @Override
            public boolean apply(GoalKeeper goalKeeper) {
                return goalKeeper.getCampaignId().equals(campaignId);
            }
        });
        log.info("getGoalsForCampaign() компания {}, цели: {}", campaignId, filteredGoals);
        return Sets.newHashSet(filteredGoals);
    }

    public void addGoal(GoalKeeper itemToAdd){
        cacheeGoal.put(itemToAdd, itemToAdd);
        log.info("Добавил новую цель: {}", itemToAdd);
    }

    public void updateGoal(GoalKeeper itemToupdate){
        final GoalKeeper goalIfPresent = cacheeGoal.getIfPresent(itemToupdate);
        log.info("Нужно обновить цель. Текущее значение: {}", goalIfPresent);
        cacheeGoal.invalidate(itemToupdate);
        cacheeGoal.put(itemToupdate, itemToupdate);
        log.info("Успешно обновил цель. Новое значение: {}", itemToupdate);
    }

    public void markGoalAsDone(GoalKeeper itemToMarkDone){
        log.info("markGoalAsDone() start for {}", itemToMarkDone);
        itemToMarkDone.getGoalsProperties().setGoalFulfill(true);
        updateGoal(itemToMarkDone);
        log.info("markGoalAsDone() done");
    }



}
