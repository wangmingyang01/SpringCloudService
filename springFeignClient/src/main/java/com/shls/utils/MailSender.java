package com.shls.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class MailSender
{
    private Logger logger = LoggerFactory.getLogger(MailSender.class);

    @Value("${email.sender.account}")
    private String account;

    @Value("${email.sender.password}")
    private String password;

    @Value("${email.SMTP.host}")
    private String smtpHost;

    @Value("${email.SMTP.port}")
    private String smtpPort;

    private Properties props;

    private Session getSession()
    {
        if (props == null)
        {
            props = new Properties();                    // 参数配置
            props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", smtpHost);   // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");            //  需要请求认证
            props.setProperty("mail.smtp.port", smtpPort);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        }

        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        return session;
    }


    /**
     * 发送邮件
     *
     * @param toList      邮件接收者
     * @param subject     主题
     * @param body        正文
     * @throws MessagingException
     * @throws IOException
     */
    public void send(List<String> toList, String subject, String body)
            throws IOException, MessagingException
    {
        this.send(toList, null, null, subject, body);
    }



    /**
     * 发送邮件
     *
     * @param toList      邮件接收者
     * @param subject     主题
     * @param body        正文
     * @param attachments 附件
     * @throws MessagingException
     * @throws IOException
     */
    public void send(List<String> toList, String subject, String body, File... attachments)
            throws IOException, MessagingException
    {
        this.send(toList, null, null, subject, body, attachments);
    }

    /**
     * 发送邮件
     *
     * @param toList      邮件接收者
     * @param ccList      抄送者
     * @param bccList     密送者
     * @param subject     主题
     * @param body        正文
     * @param attachments 附件
     * @throws MessagingException
     * @throws IOException
     */
    public void send(List<String> toList, List<String> ccList, List<String> bccList,
                     String subject, String body, File... attachments)
            throws MessagingException, IOException
    {
        Session session = getSession();
        MimeMessage msg = new MimeMessage(session);
        MimeMultipart contentList = new MimeMultipart(); // 邮件内容，包括正文和附件
        MimeBodyPart bodyPart = new MimeBodyPart(); // 邮件正文
        contentList.addBodyPart(bodyPart);
        msg.setContent(contentList);
        session.setDebug(false);

        // 设置此次发送的发件人
        InternetAddress fromAddr = new InternetAddress(account);
        msg.setFrom(fromAddr); // 设置发件人


        if (body == null)
            body = "";

        if (subject == null)
            subject = "";

        // 设置邮件接收者
        if (toList != null && toList.size() > 0)
        {
            for (String to : toList)
            {
                msg.addRecipients(RecipientType.TO, to);
            }
        }

        // 设置邮件抄送者
        if (ccList != null && ccList.size() > 0)
        {
            for (String to : ccList)
            {
                msg.addRecipients(RecipientType.CC, to);
            }
        }

        // 设置邮件密送者
        if (bccList != null && bccList.size() > 0)
        {
            for (String to : bccList)
            {
                msg.addRecipients(RecipientType.BCC, to);
            }
        }

        // 设置邮件主题
        msg.setSubject(subject);

        // 设置邮件正文
        bodyPart.setContent(body, "text/html;charset=utf-8");

        // 添加附件
        if (attachments != null)
        {
            for (File file : attachments)
            {
                addAttachment(contentList, file);
            }
        }

        Transport transport = session.getTransport();
        transport.connect(account, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    /**
     * add attachments.
     *
     * @param contentList
     * @param file
     * @throws IOException
     * @throws MessagingException
     */
    private void addAttachment(MimeMultipart contentList, File file) throws IOException, MessagingException
    {
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(file);
        attachment.setFileName(MimeUtility.encodeText(file.getName())); // 设置显示的文件名

        contentList.addBodyPart(attachment);
    }
}