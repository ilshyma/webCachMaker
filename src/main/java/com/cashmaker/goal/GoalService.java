package com.cashmaker.goal;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Created by asti on 04.03.2018.
 */
@Service
@CacheConfig(cacheNames = "com.cashmaker.goal.GoalService")
@Slf4j
public class GoalService {
    ;
    CacheLoader<String, GoalKeeper> loader= new CacheLoader<String, GoalKeeper>() {
        @Override
        public GoalKeeper load(String s) throws Exception {
            return getGoalKeeperById(s);
        }
    };
    LoadingCache<String, GoalKeeper> cache = CacheBuilder.newBuilder().build(loader);

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }


    private void lol() {
        LoadingCache<String, GoalKeeper> cache;
        CacheLoader<String, GoalKeeper> loader;
        loader = new CacheLoader<String, GoalKeeper>() {
            @Override
            public GoalKeeper load(String s) throws Exception {
                return getGoalKeeperById(s);
            }
        };
        cache = CacheBuilder.newBuilder().build(loader);
        cache.

    }

    private GoalKeeper getGoalKeeperById(String s) {


    }
}
