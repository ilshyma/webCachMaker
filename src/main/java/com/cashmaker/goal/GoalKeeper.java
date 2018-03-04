package com.cashmaker.goal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by asti on 04.03.2018.
 */
@Data
@ToString
@NoArgsConstructor
public class GoalKeeper {

    /**
     * идентификатор кампании
     */
    private String campaignId;

    /**
     * тип цели
     */
    private GoalType goalType;
    /**
     * Настройки цели
     */
    private GoalsProperties goalsProperties;
}
