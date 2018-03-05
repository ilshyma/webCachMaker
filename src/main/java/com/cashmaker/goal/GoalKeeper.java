package com.cashmaker.goal;

import lombok.*;

import java.util.Objects;

/**
 * Created by asti on 04.03.2018.
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalKeeper)) return false;
        GoalKeeper that = (GoalKeeper) o;
        return Objects.equals(campaignId, that.campaignId) &&
                goalType == that.goalType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(campaignId, goalType);
    }
}
