package com.glaway.ids.rule;

import org.jeasy.rules.support.UnitRuleGroup;

public class GroupRule extends UnitRuleGroup {
    public GroupRule(Object... rules){
        for(Object rule : rules){
            addRule(rule);
        }
    }

    @Override
    public int getPriority(){
        return 0;
    }
}
