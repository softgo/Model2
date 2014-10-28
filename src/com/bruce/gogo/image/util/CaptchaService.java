package com.bruce.gogo.image.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bruce.gogo.image.util.ImageCaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 实例一个验证码CaptchaService
 * 注意:必须是单例模式的
 * 
 * @version 1.0
 *
 */
public class CaptchaService {

    private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();


    static
    {
    	instance = new DefaultManageableImageCaptchaService(   
    	   new FastHashMapCaptchaStore(),
    	   new ImageCaptchaEngine(),
    	   180,
    	   100000,   
    	   75000);
    }

    public static ImageCaptchaService getInstance()
    {
        return instance;
    }
    
    /**
     * 检查验证码是否正确
     * @param request
     * @param response
     * @return 正确返回:true 不正确返回:false
     */
    public static boolean isOk(HttpServletRequest request,HttpServletResponse response)
    {
		// 验证码是否正确flag
		Boolean isResponseCorrect = Boolean.FALSE;
		// 取session用来验证是否在同一session中
		String captchaId = request.getSession().getId();
		// 取前台输入的证码
		String checkcode = request.getParameter("checkcode");
		
		try{
		// 取得验证对象，并检验session和输入验证码是否正确
		isResponseCorrect = CaptchaService.getInstance().validateResponseForID(captchaId, checkcode);
		} catch (CaptchaServiceException e) {
			e.getStackTrace();
		}
		return isResponseCorrect;
    }

}