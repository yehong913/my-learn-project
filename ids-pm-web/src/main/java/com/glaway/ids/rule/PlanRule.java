package com.glaway.ids.rule;

import com.glaway.ids.rdtask.task.vo.TaskInfoReq;
import org.jeasy.rules.annotation.*;

import java.util.Map;

@Rule
public class PlanRule {

    @Condition
    public boolean searchByRuleCondition(@Fact("map") Map map,@Fact("taskInfo") TaskInfoReq taskInfo){
        if("taskReference".equals(map.get("taskReference"))){
            map.put("flag",true);
            return true;
        }else{

            map.put("flag",false);
            return false;
        }
    }

    @Action
    public void printPlanName(){
//        System.out.println("planName");
    }
    @Priority
    public int getPriority(){
        return 1;
    }
}
