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
public class GoalsProperties {

    private int goalCount;
    private boolean isGoalFulfill;

}
