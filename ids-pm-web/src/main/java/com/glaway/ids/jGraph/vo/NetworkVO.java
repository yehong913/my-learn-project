package com.glaway.ids.jGraph.vo;


import java.util.List;


public class NetworkVO {

    private List<PlanVO> planVOs;

    private List<SwimLaneVO> swimLaneVOs;

    private List<PlanLinkVO> planLinkVOs;

    public NetworkVO() {}

    public NetworkVO(List<PlanVO> planVOs, List<SwimLaneVO> swimLaneVOs,
                     List<PlanLinkVO> planLinkVOs) {
        super();
        this.planVOs = planVOs;
        this.swimLaneVOs = swimLaneVOs;
        this.planLinkVOs = planLinkVOs;
    }

    public List<PlanVO> getPlanVOs() {
        return planVOs;
    }

    public void setPlanVOs(List<PlanVO> planVOs) {
        this.planVOs = planVOs;
    }

    public List<SwimLaneVO> getSwimLaneVOs() {
        return swimLaneVOs;
    }

    public void setSwimLaneVOs(List<SwimLaneVO> swimLaneVOs) {
        this.swimLaneVOs = swimLaneVOs;
    }

    public List<PlanLinkVO> getPlanLinkVOs() {
        return planLinkVOs;
    }

    public void setPlanLinkVOs(List<PlanLinkVO> planLinkVOs) {
        this.planLinkVOs = planLinkVOs;
    }

}
