/*
 * 文件名：TimeConditionUtil.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：shitian
 * 修改时间：2018年5月31日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.planview.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.query.qom.And;

import com.glaway.ids.project.planview.dto.PlanViewSetConditionDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.StringUtil;


public class TimeConditionUtil {

    private static List<Date> current = new ArrayList<Date>();

    /**
     * Description:构造时间筛选 <br>
     * 1、年<br>
     * 2、季度<br>
     * 3、月<br>
     * 
     * @param maps
     * @return
     * @see
     */
    public static String createYSM(Map<String, String> maps) {
        JSONArray ysm = new JSONArray();
        JSONObject o = null;
        if (!CommonUtil.isEmpty(maps)) {
            Set<Entry<String, String>> entrySet = maps.entrySet();
            for (Iterator<Entry<String, String>> iterator = entrySet.iterator(); iterator.hasNext();) {
                o = new JSONObject();
                Entry<String, String> entry = iterator.next();
                o.put("id", entry.getKey());
                o.put("name", entry.getValue());
                ysm.add(o);
            }
        }
        else {
            o = new JSONObject();
            o.put("id", "year");
            o.put("name", "年");
            ysm.add(o);
            o = new JSONObject();
            o.put("id", "season");
            o.put("name", "季度");
            ysm.add(o);
            o = new JSONObject();
            o.put("id", "month");
            o.put("name", "月度");
            ysm.add(o);
        }
        return ysm.toString();
    }

    /**
     * Description: 构造年选择范围<br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param date
     *            默认年， null 为系统当前时间
     * @param length
     *            范围长度
     * @return
     * @see
     */
    public static String createYearRange(Date date, int length) {
        JSONArray years = new JSONArray();
        JSONObject o1 = null;
        int year = 0;
        Date d = date;
        if (null == d) {
            d = new Date();
        }
        year = DateUtil.getYear(d);
        current.add(d);
        int scope = 0;
        if (length > 0) {
            scope = length;
        }
        else {
            scope = 50;
        }
        for (int i = -scope; i <= scope; i++ ) {
            o1 = new JSONObject();
            o1.put("id", year + i);
            o1.put("name", year + i);
            years.add(o1);
        }
        return years.toString();
    }

    /**
     * Description:构造季度范围 <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param date
     * @return
     * @see
     */
    public static String createSeaonRange(Date date) {
        JSONArray seaons = new JSONArray();
        JSONObject o = null;
        int curMonth = 0;
        if (null != date) {
            curMonth = DateUtil.getMonth(date);
        }
        else {
            curMonth = DateUtil.getMonth(new Date());
        }
        {
            o = new JSONObject();
            o.put("id", 0);
            o.put("name", "当前季度");
            seaons.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 1);
            o.put("name", "第一季度");
            seaons.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 2);
            o.put("name", "第二季度");
            seaons.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 3);
            o.put("name", "第三季度");
            seaons.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 4);
            o.put("name", "第四季度");
            seaons.add(o);
        }

        return seaons.toString();
    }

    /**
     * Description: 构造月份范围<br>
     * 
     * @param date
     * @return
     * @see
     */
    public static String createMonthRange(Date date) {
        JSONArray months = new JSONArray();
        JSONObject o = null;
        int curMonth = 0;
        if (null != date) {
            curMonth = DateUtil.getMonth(date);
        }
        else {
            curMonth = DateUtil.getMonth(new Date());
        }
        {
            o = new JSONObject();
            o.put("id", 0);
            o.put("name", "当前月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 1);
            o.put("name", "一月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 2);
            o.put("name", "二月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 3);
            o.put("name", "三月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 4);
            o.put("name", "四月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 5);
            o.put("name", "五月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 6);
            o.put("name", "六月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 7);
            o.put("name", "七月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 8);
            o.put("name", "八月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 9);
            o.put("name", "九月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 10);
            o.put("name", "十月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 11);
            o.put("name", "十一月份");
            months.add(o);
        }
        {
            o = new JSONObject();
            o.put("id", 12);
            o.put("name", "十二月份");
            months.add(o);
        }
        return months.toString();

    }

    /**
     * Description:把选择的季度，月份转成对应的数字 <br>
     * 
     * @param text
     * @return
     * @see
     */
    public static String convertText2Value(String text) {
        String texts[] = text.trim().split(",");
        String rs = "";
        for (int i = 0, len = texts.length; i < len; i++ ) {
            if (i == len - 1) {
                rs += convert(texts[i]);
            }
            else {
                rs += convert(texts[i]) + ",";
            }
        }
        return rs;
    }

    private static String convert(String t) {
        String s = "";
        switch (t) {
            case "当前季度":
               // s += ((DateUtil.getMonth(current.get(0)) - 1) / 3 + 1);
                s += 0;
                break;
            case "第一季度":
                s += 1;
                break;
            case "第二季度":
                s += 2;
                break;
            case "第三季度":
                s += 3;
                break;
            case "第四季度":
                s += 4;
                break;
            case "当前月份":
                //s += DateUtil.getMonth(current.get(0));
                s += 0;
                break;
            case "一月份":
                s += 1;
                break;
            case "二月份":
                s += 2;
                break;
            case "三月份":
                s += 3;
                break;
            case "四月份":
                s += 4;
                break;
            case "五月份":
                s += 5;
                break;
            case "六月份":
                s += 6;
                break;
            case "七月份":
                s += 7;
                break;
            case "八月份":
                s += 8;
                break;
            case "九月份":
                s += 9;
                break;
            case "十月份":
                s += 10;
                break;
            case "十一月份":
                s += 11;
                break;
            case "十二月份":
                s += 12;
                break;
            default:
                break;
        }
        return s;
    }

    public static String toText(String timeRange, String sperator) {
        StringBuilder sb = new StringBuilder();
        String[] ace = timeRange.split(":");
        String sp=",";
        if(null!=sperator)
        {
            sp=sperator;
        }
        if (timeRange.startsWith("year")) {
            String[] split = ace[1].split(",");
            for (String s : split) {
                if (!CommonUtil.isBlank(s)) {
                    sb.append(s).append("年").append(sp);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        else {
            String[] split = ace[2].split(",");
            String type = "";
            if (timeRange.startsWith("month")) {
                type="月份";
            }else if(timeRange.startsWith("season"))
            {
                type="季度";
            }
//            sb.append(ace[1]).append("年");
            for (String s : split) {
                if (!CommonUtil.isBlank(s)) {
                    sb.append(ace[1]+ "年").append(toUpperNum(s)).append(type).append(sp);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static String toUpperNum(String small) {
        String s = "";
        switch (small) {
            case "0":
                s ="当前";
                break;
            case "1":
                s = "一";
                break;
            case "2":
                s = "二";
                break;
            case "3":
                s = "三";
                break;
            case "4":
                s = "四";
                break;
            case "5":
                s = "五";
                break;

            case "6":
                s = "六";
                break;
            case "7":
                s = "七";
                break;
            case "8":
                s = "八";
                break;

            case "9":
                s = "九";
                break;
            case "10":
                s = "十";
                break;
            case "11":
                s = "十一";
                break;

            case "12":
                s = "十二";
                break;
            default:
                break;
        }
        return s;
    }

    /**
     * PlanViewSetCondition中（timeRange, showRange）转换成HQL<br>
     * 
     * @return
     * @see
     */
    public static String toHql(PlanViewSetConditionDto timeCondition, String shortName) {
        StringBuilder sb = new StringBuilder();
        List<Pair<String, String>> timeScope = new ArrayList<Pair<String, String>>();
        if (StringUtil.isNotEmpty(timeCondition.getTimeRange())
            && StringUtil.isNotEmpty(timeCondition.getShowRange())) {
            String[] times = timeCondition.getTimeRange().split(":");
            if (times[0].startsWith("year")) {
                // year
                String[] years = times[1].split(",");
                for (int i = 0; i < years.length; i++ ) {
                    if (!CommonUtil.isEmpty(years[i])) {
                        Pair<String, String> pair = new Pair<String, String>(years[i] + "-01-01",
                            (Integer.valueOf(years[i]) + 1) + "-01-01");
                        timeScope.add(pair);
                    }
                    /*
                     * if (i == times.length - 1) {
                     * sb.append("to_date('").append(times[i]).append("-01-01','yyyy')");
                     * }
                     * else {
                     * sb.append("to_date('").append(times[i]).append("','yyyy'),");
                     * }
                     */
                }
            }
            else if (times[0].startsWith("season")) {
                // season
                String[] seasons = times[2].split(",");
                for (int i = 0; i < seasons.length; i++ ) {
                   /* if (!CommonUtil.isEmpty(seasons[i])) {*/
                        if("0".equals(seasons[i]))
                        {
                            int curSeason=(DateUtil.getMonth(new Date())-1)/3+1;
                            Pair<String, String> pair = new Pair<String, String>(
                                times[1] + "-" + (3 * (curSeason - 1) + 1) + "-01",
                                (Integer.valueOf(times[1]) + (3 * (curSeason - 1) + 4) / 12)
                                + "-" + (3 * (Integer.valueOf(seasons[i]) - 1) + 4) % 12 + "-01");
                            timeScope.add(pair);
                        }
                        else
                        {
                            Pair<String, String> pair = new Pair<String, String>(
                                times[1] + "-" + (3 * (Integer.valueOf(seasons[i]) - 1) + 1) + "-01",
                                (Integer.valueOf(times[1]) + (3 * (Integer.valueOf(seasons[i]) - 1) + 4) / 12)
                                + "-" + (3 * (Integer.valueOf(seasons[i]) - 1) + 4) % 12 + "-01");
                            timeScope.add(pair);
                        }
                    /*}*/
                }
            }
            else if (times[0].startsWith("month")) { // month
                String[] months = times[2].split(",");
                for (int i = 0; i < months.length; i++ ) {
                  /*  if (!CommonUtil.isEmpty(times[i])) {*/
                        if("0".equals(months[i]))
                        {
                            int curMonth=DateUtil.getMonth(new Date());
                            Pair<String, String> pair = new Pair<String, String>(times[1] + "-"
                                + curMonth + "-01",
                                (Integer.valueOf(times[1]) + (curMonth + 1) / 12)
                                + "-" + (curMonth + 1) % 12 + "-01");
                            timeScope.add(pair);
                        }else
                        {
                            Pair<String, String> pair = new Pair<String, String>(times[1] + "-"
                                + months[i] + "-01",
                                (Integer.valueOf(times[1]) + (Integer.valueOf(months[i]) + 1) / 12)
                                + "-" + (Integer.valueOf(months[i]) + 1) % 12 + "-01");
                            timeScope.add(pair);
                        }
                    /*}*/
                }
            }
            // 需开始的计划
            if (timeCondition.getShowRange().equalsIgnoreCase("toStart")) {
                sb.append(" and ( '1' != '1' ");
                for (Pair<String, String> p : timeScope) {
                    sb.append(" or ( ").append(shortName).append(".").append("planStartTime").append(
                        " <= ").append("to_date('").append(p.getL()).append("','yyyy-mm-dd')").append(
                        " and ").append(shortName).append(".").append("planStartTime").append(
                        " >= ").append("to_date('").append(p.getT()).append("','yyyy-mm-dd') )");
                }
                sb.append(" ) ");
            }
            // 执行的计划 execPlan
            else if (timeCondition.getShowRange().equalsIgnoreCase("execPlan")) {
                sb.append(" and ( '1' != '1' ");
                for (Pair<String, String> p : timeScope) {
                    sb.append(" or ").append("(").append(shortName).append(".").append(
                        "planStartTime").append(" <= ").append("to_date('").append(p.getT()).append(
                        "','yyyy-mm-dd')").append(" and ").append(shortName).append(".").append(
                        "planEndTime").append(" >= ").append("to_date('").append(p.getL()).append(
                        "','yyyy-mm-dd')").append(" or ").append(shortName).append(".").append(
                        "planStartTime").append(" >= ").append("to_date('").append(p.getT()).append(
                        "','yyyy-mm-dd')").append(" and ").append(shortName).append(".").append(
                        "planEndTime").append(" <= ").append("to_date('").append(p.getL()).append(
                        "','yyyy-mm-dd')").append("  ) ");
                }
                sb.append(" ) ");
            }
            // 需完成的计划 toComplete
            else if (timeCondition.getShowRange().equalsIgnoreCase("toComplete")) {
                sb.append(" and ( '1' != '1' ");
                for (Pair<String, String> p : timeScope) {
                    sb.append(" or ( ").append(shortName).append(".").append("planEndTime").append(
                        " >= ").append("to_date('").append(p.getT()).append("','yyyy-mm-dd')").append(
                        " and ").append(shortName).append(".").append("planEndTime").append(" <= ").append(
                        "to_date('").append(p.getL()).append("','yyyy-mm-dd') )");
                }
                sb.append(" ) ");
            }

        }
        return sb.toString();
    }

    static class Pair<T, L> {
        private T t;

        private L l;

        public Pair(T tt, L ll) {
            t = tt;
            l = ll;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public L getL() {
            return l;
        }

        public void setL(L l) {
            this.l = l;
        }

    }

}
