package com.tapcus.portfoliowebsitejava.service.impl;

import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.util.Result;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MemberDao memberDao;

    @Value("${spring.mail.username}")
    private String SEND_EMAIL;

    public Result<Object> sendEmail(String toEmail) {


        Member member = memberDao.getMemberByEmail(toEmail);
        Result<Object> r = new Result<>();
        if(member == null) {
            r.setCode(404);
            r.setMessage("此帳號不存在");
            return r;
        }
        // 隨機生成 16 碼數字
        String newPassword = this.createRandomCharData(16);
        log.info("新產生的密碼 --- {}", newPassword);

        // 雜湊密碼
        PasswordEncoder pe = new BCryptPasswordEncoder();
        memberDao.changePasswordByMemberId(member.getMemberId(), pe.encode(newPassword));

        // 寄信
        SimpleMailMessage message = new SimpleMailMessage();
        // 主機信箱
        message.setFrom(SEND_EMAIL);
        // 發送對象
        message.setTo(toEmail);
        // Email 內容主體
        String body = "以下這是您的新密碼：" + newPassword +
                "\n登錄完成後，可以至會員中心更改密碼！";
        message.setText(body);
        // Email 標題
        String subject = "密碼重設";
        message.setSubject(subject);

        mailSender.send(message);

        r.setCode(200);
        r.setMessage("寄送成功");
        return r;
    }

    private String createRandomCharData(int length)
    {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//隨機用以下三個隨機生成器
        Random randdata = new Random();
        int data = 0;
        for( int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //目的是隨機選擇生成數字，大小寫字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//僅僅會生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保證只會產生65~90之間的整數
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保證只會產生97~122之間的整數
                    sb.append((char)data);
                    break;
            }
        }
        return sb.toString();
    }
}
