package com.glaway.ids.common.helper;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author xst
 * @Date 2019/12/3 15:23
 * @Description
 */
@Controller
@RequestMapping("/dateController")
public class DateController {
    /**
     * 年
     */
    private static final String YEAR = "YEAR";

    /**
     * 周
     */
    private static final String WEEK = "WEEK";

    /**
     * 月
     */
    private static final String MOUTH = "MOUTH";

    /**
     * 时
     */
    private static final String HOUR = "HOUR";

    /**
     * 天
     */
    private static final String DAY = "DAY";

    /**
     * 分钟
     */
    private static final String MINUTES = "MINUTES";

    /**
     * 默认的触发器组
     */
    private static String SCHEDULER_TRIGGER_DEFAULT_GROUP = "trigger_default_group";

    /**
     * 日志
     */
    private OperationLog log = (OperationLog) BaseLogFactory.getOperationLog(DateController.class);

//    /**
//     * 触发器service
//     */
//    @Autowired
//    private TriggerService triggerService;



    /**
     * Description: <br>
     * 1、创建触发器<br>
     *
     * @param cronParam
     * @param request
     * @param response
     * @return
     * @see
     */
//    @RequestMapping(params = "doCreateTrigger")
//    @ResponseBody
//    public AjaxJson doCreateTrigger(FDTrigger trigger, HttpServletRequest request,
//                                    HttpServletResponse response) {
//        AjaxJson j = new AjaxJson();
//        String msg = "";
//
//        List<String> repeatList = new ArrayList<String>();
//
//        Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
//        String year = trigger.getYear();
//        String mouth = trigger.getMouth();
//        String week = trigger.getWeek();
//        String day = trigger.getDay();
//        String hour = trigger.getHour();
//        String minutes = trigger.getMinutes();
//
//        if (StringUtils.isNotEmpty(year) && StringUtils.isNotBlank(year)) {
//            String[] yearArr = year.split(",");
//            List<String> list = Arrays.asList(yearArr);
//            paramMap.put(YEAR, list);
//
//            if (-1 != year.lastIndexOf(",")) {
//                String yearAfter = year.substring(0, year.lastIndexOf(","));
//                trigger.setYear(yearAfter);
//            }
//        }
//
//        if (StringUtils.isNotEmpty(mouth) && StringUtils.isNoneBlank(mouth)) {
//            String[] mouthArr = mouth.split(",");
//            List<String> list = Arrays.asList(mouthArr);
//            paramMap.put(MOUTH, list);
//
//            if (-1 != mouth.lastIndexOf(",")) {
//                String mouthAfter = mouth.substring(0, mouth.lastIndexOf(","));
//                trigger.setMouth(mouthAfter);
//            }
//        }
//
//        if (StringUtils.isNotEmpty(week) && StringUtils.isNoneBlank(week)) {
//            String[] weekArr = week.split(",");
//            List<String> list = Arrays.asList(weekArr);
//            paramMap.put(WEEK, list);
//
//            if (-1 != week.lastIndexOf(",")) {
//                String weekAfter = week.substring(0, week.lastIndexOf(","));
//                trigger.setWeek(weekAfter);
//            }
//        }
//
//        if (StringUtils.isNotEmpty(day) && StringUtils.isNoneBlank(day)) {
//            String[] dayArr = day.split(",");
//            List<String> list = Arrays.asList(dayArr);
//            paramMap.put(DAY, list);
//
//            if (-1 != day.lastIndexOf(",")) {
//                String dayAfter = day.substring(0, day.lastIndexOf(","));
//                trigger.setDay(dayAfter);
//            }
//        }
//
//        if (StringUtils.isNotEmpty(hour) && StringUtils.isNoneBlank(hour)) {
//            String[] hourArr = hour.split(",");
//            List<String> list = Arrays.asList(hourArr);
//            paramMap.put(HOUR, list);
//
//            if (-1 != day.lastIndexOf(",")) {
//                String hourAfter = hour.substring(0, hour.lastIndexOf(","));
//                trigger.setHour(hourAfter);
//            }
//        }
//
//        if (StringUtils.isNotEmpty(minutes) && StringUtils.isNoneBlank(minutes)) {
//            String[] minutesArr = minutes.split(",");
//            List<String> list = Arrays.asList(minutesArr);
//            paramMap.put(MINUTES, list);
//
//            if (-1 != minutes.lastIndexOf(",")) {
//                String minutesAfter = minutes.substring(0, minutes.lastIndexOf(","));
//                trigger.setMinutes(minutesAfter);
//            }
//        }
//
//        String repeat = trigger.getRepeat();
//
//        if (null != repeat) {
//            if (repeat.equals("1")) {
//                repeatList.add(YEAR);
//            }
//            if (repeat.equals("2")) {
//                repeatList.add(MOUTH);
//            }
//            if (repeat.equals("3")) {
//                repeatList.add(WEEK);
//            }
//            if (repeat.equals("4")) {
//                repeatList.add(DAY);
//            }
//            if (repeat.equals("5")) {
//                repeatList.add(HOUR);
//            }
//            if (repeat.equals("6")) {
//                repeatList.add(MINUTES);
//            }
//        }
//
//        String cron = CronExpressionUtil.parse(paramMap, repeatList);
//        if (StringUtils.isEmpty(cron) || StringUtils.isBlank(cron)) {
//            msg = "新增触发器失败";
//            log.info(msg);
//            j.setSuccess(true);
//            j.setMsg(msg);
//            return j;
//        }
//        trigger.setCronExpression(cron);
//        trigger.setTriggerGroup(SCHEDULER_TRIGGER_DEFAULT_GROUP);
//
//        String id = trigger.getId();
//        if (StringUtils.isNotEmpty(id) && StringUtils.isNotBlank(id)) {
//            msg = "修改触发器成功";
//            this.triggerService.updateEntitie(trigger);
//        } else {
//            msg = "新增触发器成功";
//            this.triggerService.save(trigger);
//        }
//
//        log.info(msg);
//        j.setSuccess(true);
//        j.setMsg(msg);
//        return j;
//    }

    /**
     * Description: <br>
     * 1、删除触发器<br>
     *
     * @param cronParam
     * @param request
     * @param response
     * @return
     * @see
     */
//    @RequestMapping(params = "doDeleteTrigger")
//    @ResponseBody
//    public AjaxJson doDeleteTrigger(HttpServletRequest request,
//                                    HttpServletResponse response) {
//        AjaxJson j = new AjaxJson();
//
//        List<FDTrigger> triggerList = new ArrayList<FDTrigger>();
//
//        String ids = request.getParameter("ids");
//        String[] idArr = ids.split(",");
//        for (String item : idArr) {
//            FDTrigger trigger = this.triggerService.get(FDTrigger.class, item);
//            if (null != trigger) {
//                triggerList.add(trigger);
//            }
//        }
//
//        if (CollectionUtils.isNotEmpty(triggerList)) {
//            this.triggerService.deleteAllEntitie(triggerList);
//        }
//
//        j.setSuccess(true);
//        j.setMsg("触发器删除成功");
//        return j;
//    }

    /**
     * Description: <br>
     * 1、更新触发器<br>
     *
     * @param cronParam
     * @param request
     * @param response
     * @return
     * @see
     */
//    @RequestMapping(params = "doUpdateTrigger")
//    @ResponseBody
//    public AjaxJson doUpdateTrigger(HttpServletRequest request,
//                                    HttpServletResponse response) {
//        AjaxJson j = new AjaxJson();
//
//        String id = request.getParameter("id");
//        FDTrigger trigger = this.triggerService.get(FDTrigger.class, id);
//        this.triggerService.updateEntitie(trigger);
//        j.setSuccess(true);
//        j.setMsg("触发器修改成功");
//
//        return j;
//    }

    /**
     * Description: <br>
     * 1、页面校验cron表达式<br>
     *
     * @see
     */
//    @RequestMapping(params = "doJudgeCronExpression")
//    @ResponseBody
//    public AjaxJson doJudgeCronExpression(HttpServletRequest request, HttpServletResponse response) {
//        AjaxJson j = new AjaxJson();
//
//        String cronEx = request.getParameter("cronEx");
//        if (!CronExpressionUtil.judgeCronExpression(cronEx)) {
//            j.setSuccess(false);
//            j.setMsg("cron表达式语法不对");
//            return j;
//        }
//
//        j.setSuccess(true);
//        j.setMsg("cron表达式可用");
//        return j;
//    }

    /**
     * Description: <br>
     * 1、获取定时任务数据<br>
     *
     * @param request
     * @param response
     * @return
     * @see
     */
//    @RequestMapping(params = "doGetTriggerList")
//    public void doGetTriggerList(FDTrigger fdTrigger, HttpServletRequest request,
//                                 HttpServletResponse response) {
//        int curPage = 1;// 查询第几页？
//        try {
//            curPage = Integer.parseInt(request.getParameter("page"));
//        } catch (Exception ex) {
//            curPage = 1;
//        }
//        int maxLine = 10;// 每页多少行
//        try {
//            maxLine = Integer.parseInt(request.getParameter("rows"));
//            if (maxLine > 1000) {
//                maxLine = 1000;
//            }
//        } catch (Exception ex) {
//            maxLine = 1;
//        }
//
//        // 查询
//        String desc = fdTrigger.getTriggerDesc();
//        String name = fdTrigger.getTriggerName();
//
//        CriteriaQuery cq = new CriteriaQuery(FDTrigger.class);
//        cq.setCurPage(curPage);
//        cq.setPageSize(maxLine);
//        cq.like("triggerDesc", desc);
//        cq.like("triggerName", name);
//        cq.add();
//        List<FDTrigger> triggerList = triggerService.getListByCriteriaQuery(cq, true);
//
//        DataGridReturn demo = new DataGridReturn(triggerList.size(), triggerList);
//        String json = com.alibaba.fastjson.JSONObject.toJSONString(demo);
//        TagUtil.ajaxResponse(response, json);
//    }

    /**
     * Description: <br>
     * 1、获取年列表<br>
     *
     * @return
     * @see
     */
    @RequestMapping(params = "doGetYear")
    public void doGetYear(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = DateUtil.getYear(new Date()); i < 2100; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", i + "");
            map.put("text", i + "");
            list.add(map);
        }

        TagUtil.ajaxResponse(response, JSON.toJSONString(list));
    }

    /**
     * Description: <br>
     * 1、获取月列表<br>
     *
     * @return
     * @see
     */
    @RequestMapping(params = "doGetMouth")
    public void doGetMouth(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", i + "");
            map.put("text", i + "");
            list.add(map);
        }

        TagUtil.ajaxResponse(response, JSON.toJSONString(list));
    }

    /**
     * Description: <br>
     * 1、获取周列表<br>
     *
     * @return
     * @see
     */
    @RequestMapping(params = "doGetWeek")
    public void doGetWeek(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 8; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", i + "");
            String str = "";
            if (1 == i) {
                str = "周日";
            } else if (2 == i) {
                str = "周一";
            } else if (3 == i) {
                str = "周二";
            } else if (4 == i) {
                str = "周三";
            } else if (5 == i) {
                str = "周四";
            } else if (6 == i) {
                str = "周五";
            } else if (7 == i) {
                str = "周六";
            }
            map.put("text", str);
            list.add(map);
        }

        TagUtil.ajaxResponse(response, JSON.toJSONString(list));
    }

    /**
     * Description: <br>
     * 1、获取日列表<br>
     *
     * @return
     * @see
     */
    @RequestMapping(params = "doGetDay")
    public void doGetDay(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 32; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", i + "");
            map.put("text", i + "");
            list.add(map);
        }

        TagUtil.ajaxResponse(response, JSON.toJSONString(list));
    }

    /**
     * Description: <br>
     * 1、获取时列表<br>
     *
     * @return
     * @see
     */
    @RequestMapping(params = "doGetHour")
    public void doGetHour(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 25; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", i + "");
            map.put("text", i + "");
            list.add(map);
        }

        TagUtil.ajaxResponse(response, JSON.toJSONString(list));
    }

    /**
     * Description: <br>
     * 1、获取分列表<br>
     *
     * @return
     * @see
     */
    @RequestMapping(params = "doGetMinute")
    public void doGetMinute(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 60; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", "" + i);
            if (i < 10) {
                map.put("text", "0" + i);
            } else {
                map.put("text", i + "");
            }
            list.add(map);
        }

        TagUtil.ajaxResponse(response, JSON.toJSONString(list));
    }
}
