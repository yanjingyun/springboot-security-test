package com.yjy.security.verifyCode.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yjy.security.verifyCode.utils.VerifyCodeUtil;

@Controller
@RequestMapping("/verify/code")
public class VerifyCodeController {

	@RequestMapping("/get")
    @ResponseBody
    public void getVerifyCodeImg(HttpServletResponse response, HttpSession session) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = VerifyCodeUtil.drawImage(output);
        //将验证码文本直接存放到session中
        session.setAttribute(VerifyCodeUtil.VERIFY_CODE_SESSION_PRE_KEY, code);
        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
