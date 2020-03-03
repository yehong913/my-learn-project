package com.didispace.chapter25.weChat.utils;


import com.alibaba.fastjson.JSONObject;
import com.didispace.chapter25.weChat.pojo.Buuton;
import com.didispace.chapter25.weChat.pojo.ClickBuuton;
import com.didispace.chapter25.weChat.pojo.Menu;
import com.didispace.chapter25.weChat.pojo.ViewBuuton;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



import java.io.IOException;
@Slf4j
public class WeixinUtil {
    private static  final  String  appId="wxe62c65dd20482b41";
    private static  final  String  secret="0a081dd31556e7e0e9d133e690cca917";
    private static  final  String  GET_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
    private  static  final String  MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String access_token="29_vVEAJjhKLituSgoQlY6RL7wCir3Kih-QD_z3gSChceWUxzDeqO4DaciRmFxNm0Os8ZlTnM7HjQH39BLCFQMOmSo_7MUqK8006erMULCmjAufTBIrAhhH4UpZKG_6utOXswRGK9mZ3WuVXLOZJXLfAIACQE";

     public  static JSONObject doGetStr(String url){
         JSONObject json= null;
         DefaultHttpClient httpClient=new DefaultHttpClient();
         HttpGet  httpGet= new HttpGet(url);
         try {

             HttpResponse response= httpClient.execute(httpGet);
             HttpEntity  httpEntity=response.getEntity();
             if(httpEntity !=null){
                 String  result= EntityUtils.toString(httpEntity);
                 json=JSONObject.parseObject(result);
             }

         } catch (IOException e) {
             e.printStackTrace();
         }
         return json;
     }


    public  static JSONObject doPostStr(String url,String params){
        JSONObject json= null;
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost= new HttpPost(url);
        try {

            httpPost.setEntity(new StringEntity(params,"utf-8"));
            HttpResponse response= httpClient.execute(httpPost);
            HttpEntity  httpEntity=response.getEntity();
            if(httpEntity !=null){
                String  result= EntityUtils.toString(httpEntity,"utf-8");
                    json=JSONObject.parseObject(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    public  static Menu initMenu(){
        Menu menu= new Menu();
        ClickBuuton  buuton1= new ClickBuuton();
        buuton1.setName("点击显示中国");
        buuton1.setKey("11");
        buuton1.setType("click");


        ViewBuuton buuton2= new ViewBuuton();
        buuton2.setName("view菜单");
        buuton2.setUrl("http://www.baidu.com");
        buuton2.setType("view");



        ClickBuuton  buuton3= new ClickBuuton();
        buuton3.setName("地理位置");
        buuton3.setKey("3");
        buuton3.setType("location_select");
        ClickBuuton  buuton31= new ClickBuuton();
        buuton31.setName("地理位置");
        buuton31.setKey("31");
        buuton31.setType("location_select");

        Buuton buuton= new Buuton();
        buuton.setName("地理菜单一");
        buuton.setSub_button(new Buuton[]{buuton3,buuton31});
        menu.setButton(new Buuton[]{buuton1,buuton2,buuton});
        return menu;

    }



    public  static  JSONObject createMenu(String url,String menu){
    String postUrl=url.replace("ACCESS_TOKEN",WeixinUtil.access_token);
        JSONObject  json=WeixinUtil.doPostStr(postUrl,menu);
        log.info(json.toString());
        return json;

    }

    public static void main(String[] args) {
        JSONObject json=WeixinUtil.doGetStr(WeixinUtil.GET_URL);
        Menu menu=WeixinUtil.initMenu();
        String textMenu=JSONObject.toJSONString(menu);
        JSONObject json=WeixinUtil.createMenu(WeixinUtil.MENU_URL,textMenu);
        log.info(json.toString());
    }

}
