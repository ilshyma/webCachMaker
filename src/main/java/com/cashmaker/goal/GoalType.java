package com.cashmaker.goal;

/**
 * Created by asti on 04.03.2018.
 */
public enum GoalType {

    LEAD("leads", 10)
    ;

    GoalType(String dtoFieldProperty, int defaultGoalCount) {
        this.dtoFieldProperty = dtoFieldProperty;
        this.defaultGoalCount = defaultGoalCount;
    }

    private String dtoFieldProperty;
    private int defaultGoalCount;

    public String getDtoFieldProperty() {
        return dtoFieldProperty;
    }

    public int getDefaultGoalCount() {
        return defaultGoalCount;
    }
}
