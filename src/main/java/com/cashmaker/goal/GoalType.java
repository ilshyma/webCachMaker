package com.cashmaker.goal;

/**
 * Created by asti on 04.03.2018.
 */
public enum GoalType {

    LEAD("leads")
    ;

    GoalType(String dtoFieldProperty) {
        this.dtoFieldProperty = dtoFieldProperty;
    }

    private String dtoFieldProperty;
}
