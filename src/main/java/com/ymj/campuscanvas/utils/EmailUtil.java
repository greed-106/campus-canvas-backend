package com.ymj.campuscanvas.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class EmailUtil {
    private static final String HTML_TEMPLATE =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <style>\n" +
                    "        body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; line-height: 1.6; color: #333; }\n" +
                    "        .container { max-width: 600px; margin: 20px auto; padding: 40px 20px; }\n" +
                    "        .header { text-align: center; margin-bottom: 30px; }\n" +
                    "        .brand { font-size: 24px; font-weight: 300; color: #444; }\n" +
                    "        .code-box { \n" +
                    "            text-align: center;\n" +
                    "            margin: 40px 0;\n" +
                    "            padding: 30px 0;\n" +
                    "            background: #f8f9fa;\n" +
                    "            border-radius: 8px;\n" +
                    "            box-shadow: 0 2px 4px rgba(0,0,0,0.05);\n" +
                    "        }\n" +
                    "        .verification-code {\n" +
                    "            font-size: 36px;\n" +
                    "            font-weight: 700;\n" +
                    "            color: #212529;\n" +
                    "            letter-spacing: 2px;\n" +
                    "            margin: 10px 0;\n" +
                    "        }\n" +
                    "        .notice { \n" +
                    "            text-align: center;\n" +
                    "            color: #666;\n" +
                    "            font-size: 14px;\n" +
                    "            margin: 20px 0;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            border-top: 1px solid #eee;\n" +
                    "            padding-top: 20px;\n" +
                    "            margin-top: 40px;\n" +
                    "            color: #999;\n" +
                    "            font-size: 13px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <div class=\"brand\">CampusCanvas</div>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div class=\"code-box\">\n" +
                    "            <p style=\"color: #666; margin-bottom: 15px;\">您的验证码是</p>\n" +
                    "            <div class=\"verification-code\">%s</div>\n" +
                    "            <p style=\"color: #888; font-size: 13px; margin-top: 10px;\">有效期 %d 分钟</p>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"notice\">\n" +
                    "            <p>请勿将此验证码分享给他人</p>\n" +
                    "            <p>如非本人操作，请忽略此邮件</p>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>北京邮电大学 杨明佳</p>\n" +
                    "            <p>© 2025 CampusCanvas</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationCodeAsync(String email, String code, int liveMinutes) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 带名称的发件人
            helper.setFrom("CampusCanvas <1062415694@qq.com>");
            helper.setTo(email);
            helper.setSubject("[CampusCanvas] 邮箱验证码");

            // 构建纯文本内容
            String textContent = String.format(
                    "您好！\n" +
                            "感谢使用CampusCanvas服务，您的验证码是：【%s】\n" +
                            "验证码将在 %d 分钟后失效\n\n" +
                            "北京邮电大学 杨明佳\n",
                    code, liveMinutes
            );

            // 格式化HTML内容
            String htmlContent = String.format(HTML_TEMPLATE, code, liveMinutes);

            // 设置多版本内容
            helper.setText(textContent, htmlContent);
        } catch (MessagingException e) {
            log.error("Failed to send email verification code to {}", email, e);
        }
        mailSender.send(message);
    }
}