package com.glaway.foundation.fdk.rt.sso;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.cache.constant.CacheConstant;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.log.constants.LoggerLevel;
import com.glaway.foundation.common.log.constants.LoggerType;
import com.glaway.foundation.common.log.entity.FoundationLogEntity;
import com.glaway.foundation.common.service.CommonService;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.core.common.constant.Globals;
import com.glaway.foundation.fdk.rt.handler.DefaultHandler;
import com.glaway.foundation.web.system.manager.ClientManager;
import com.glaway.license.Application;
import com.glaway.license.client.LicenseClient;
import com.glaway.license.util.IpAddress;
import org.jasig.cas.client.util.AssertionHolder;
import org.jivesoftware.smack.XMPPConnection;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class LogoutFilter implements Filter {

    /**
     * appKey被过滤后，跳转的页面。
     */
	private String casServerLogoutUrl;
	
    private RedisService redisService;

    
    @Override
    public void destroy() {
    }

    /**
     * 过滤逻辑
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	
    	HttpServletRequest req = (HttpServletRequest)request;
    	new DefaultHandler().handleLogout(req, (HttpServletResponse)response);
    	
    	HttpServletResponse res = (HttpServletResponse)response;
    	// 用于left_shortcut_menu.js判断非Iframe时，登出页面
    	Cookie fdUserLoginCookie = new Cookie("fdUserLoginCookie", "");
		fdUserLoginCookie.setMaxAge(0);
		fdUserLoginCookie.setPath(req.getContextPath());
		res.addCookie(fdUserLoginCookie);	
    	
    	HttpSession session = req.getSession();
    	String path = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath();
		String url= path + "/index.jsp";
		if (ClientManager.getInstance().getClient(session.getId()) == null) {
			if(session != null){
				session.invalidate();
			}
			res.sendRedirect(this.casServerLogoutUrl+"?ssologin="+url);
	   	}else {
	   		TSUserDto user = ClientManager.getInstance().getClient(session.getId()).getUser();
			String mesg = "用户" + user.getUserName() + "已退出";
			
	        String userId = "";
	        String securityLog2Db = SystemConfigUtil.getValue("securityLog2Db");
	        if ("true".equals(securityLog2Db)){
	            FoundationLogEntity entity = new FoundationLogEntity();
	            entity.setBroswer(BrowserUtils.checkBrowse(req));
	            entity.setContent(mesg);
	            entity.setLogtype(LoggerType.SECURITY_LOG);
	            entity.setLoglevel(LoggerLevel.INFO_LEVEL);
	            entity.setCreatetime(DateUtil.getTimeStamp());
	            if ("".equals(userId)){
	                entity.setUserid(userId);
	            }
	            else {
	                entity.setUserid(ResourceUtil.getSessionUserName().getId());
	            }
	            entity.setIpaddr(ObjectConvertUtils.getIpAddrByRequest(req));
	            entity.setLogAction(Globals.Log_Type_EXIT);
//	            ApplicationContext ctx = OnlineListener.getCtx();
//	            if (null != ctx) {
	            	CommonService service = (CommonService)ApplicationContextUtil.getBean("commonServiceImpl");
	                service.save(entity);
//	            }
	        }
	        
	        XMPPConnection connection = ClientManager.getInstance().getClient(session.getId()).getConnection();
	        if (null != connection && connection.isConnected()) {
	            connection.disconnect();
	        }
	        ClientManager.getInstance().removeClinet(session.getId());
	        ClientManager.getInstance().removeHttpSession(session.getId());
	    	
	    	session = req.getSession(false);
	    	if(session != null){
	    		session.invalidate();
	    	}
	    	
	    	//检查redis中是否有相同用户的登录信息存在，如果有，则将用户登录信息移除
	        if(StringUtil.isNotEmpty(user.getUserName())) {
	        	List<JSONObject> userLoginClientList = (List<JSONObject>) redisService
	        			.getFromRedis(CacheConstant.BASE_CACHE_MODULE, "userLoginClientList");        
	        	if(userLoginClientList != null && userLoginClientList.size() > 0) {
	        		boolean hasBeenTick = true;
	        		//检查用户在redis中的登录信息是否被清除，如果被清除，表示该用户已经被强制下线了，则将请求重定向到退出的页面
	        		for (int i = userLoginClientList.size() - 1; i >= 0; i--) {
	        			JSONObject userLoginInfo = userLoginClientList.get(i);
	        			if(user.getUserName().equals(userLoginInfo.get("username"))) {
	        				userLoginClientList.remove(i);
						}
					}
	        		redisService.setToRedis(CacheConstant.BASE_CACHE_MODULE, "userLoginClientList", userLoginClientList);
	        	}
	        }
			if(!demoProject()) {
				/**
				 * License start
				 * 向License注销浮点数
				 */
				logoutForUserFloat(req,user.getUserName());
			}
	    	((HttpServletResponse)response).sendRedirect(this.casServerLogoutUrl+"?ssologin="+url);
	    	
//			response.getWriter().println("<script type='text/javascript'>");
//	      response.getWriter().println("top.location.href='" + this.casServerLogoutUrl + "'");
//	      response.getWriter().println("</script>");
//			response.getWriter().flush();
	   	}
    	
    }
    
    /**
     * 获取Filter配置，读取redirectPath参数值。
     */
    @Override
    public void init(FilterConfig config)
        throws ServletException {
		String casServerLogoutUrl = config.getInitParameter("casServerLogoutUrl");
		if(casServerLogoutUrl != null){
			this.casServerLogoutUrl = casServerLogoutUrl;
		}
        this.redisService = ApplicationContextUtil.getBean(RedisService.class);

    }

	/**
	 * Description: <br>
	 * 是否是演示项目
	 *
	 * @return
	 * @see
	 */
	private boolean demoProject() {
		boolean demoProjectFlag = false;
		try {
			// PropertiesUtil util = new PropertiesUtil("sysConfig.properties");
			// demoProjectFlag = Boolean.parseBoolean(util.readProperty("demoProject"));
			Map<String, Object> loginBasicInfoMap  = (Map<String, Object>) redisService.getFromRedis(CacheConstant.BASE_CACHE_MODULE, "loginBasicInfoList");
			if(!CommonUtil.isEmpty(loginBasicInfoMap)) {
				demoProjectFlag =(boolean) loginBasicInfoMap.get("demoProjectFlag");
			}
			System.out.println("是否是演示项目"+demoProjectFlag);
		}
		catch(Exception ex) {
			demoProjectFlag = false;
		}
		finally {
			return demoProjectFlag;
		}
	}

	/**
	 *
	 * <功能描述>
	 *
	 * @param system
	 *            system
	 * @return boolean
	 *
	 */
	public boolean logoutForUserFloat(HttpServletRequest req,String userName) {
		boolean result = false;
		LicenseClient licenseClient = new LicenseClient();
		try {
			String licenseType = Application.project.getFeatureName();
			String liceseSessionId="";
//			替换从reids中获取sessionid 改成 从cas 中获取
//			RedisService redisService = (RedisService) ServiceDelegate.getService("redisServiceImpl");
//			Map<String, String> liceseSessionIdMap =  (Map<String, String>) redisService.getFromRedis(CacheConstant.BASE_CACHE_MODULE, "liceseSessionId");
//			if (liceseSessionIdMap != null ) {
//				liceseSessionId =liceseSessionIdMap.get(userName+"_liceseSessionId");
//			}
//
//			licenseClient.logoutSynchronize(userName, IpAddress.getIpAddress(req),
//					liceseSessionId, licenseType);
//			liceseSessionIdMap.remove(userName+"_liceseSessionId");
//			redisService.setToRedis(CacheConstant.BASE_CACHE_MODULE, "liceseSessionId", liceseSessionIdMap);

			String username = AssertionHolder.getAssertion().getPrincipal().getName();
			//cas用户名格式 (用户名 ### 组织id ### cas-Sessionid)
			String[] casUserInfo = username.split("###");

			if (casUserInfo != null) {
				//cas Sessionid
				liceseSessionId = casUserInfo.length>=3 ? casUserInfo[2] : "";
			}
			licenseClient.logoutSynchronize(userName, IpAddress.getIpAddress(req),
					liceseSessionId, licenseType);
			result = true;

			RedisService redisService = (RedisService) ServiceDelegate.getService("redisServiceImpl");
			Map<String, String> liceseSessionIdMap =  (Map<String, String>) redisService.getFromRedis(CacheConstant.BASE_CACHE_MODULE, "liceseSessionId");
			liceseSessionIdMap.remove(userName+"_liceseSessionId_"+liceseSessionId);
			redisService.setToRedis(CacheConstant.BASE_CACHE_MODULE, "liceseSessionId", liceseSessionIdMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
