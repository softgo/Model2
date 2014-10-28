package com.bruce.gogo.image.util;

import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 生成验证码图片
 * 
 * @version 1.0
 *
 */
public class ImageCaptchaServlet extends HttpServlet {

    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

    }
    
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

       byte[] captchaChallengeAsJpeg = null;
        // 使Captcha图片为JPEG格式流输出
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
        // 获得会话ID，来产生验证码，会话ID是一个很好的生成验证码方式，有很好的防止非同一会话错误。
        String captchaId = httpServletRequest.getSession().getId();
        // 调用ImageCaptchaService getImageChallengeForID方法
        BufferedImage challenge =CaptchaService.getInstance().getImageChallengeForID(captchaId,
                    httpServletRequest.getLocale());

            // JPEG编码器
            JPEGImageEncoder jpegEncoder =
                    JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        // 转换成字节数组以流方式输出
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // 在Response刷新输出流
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}