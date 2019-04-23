package com.loet.mine.util;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URL;
import java.util.Properties;

/**
 * 邮件工具类
 */
public class EmailUtils {
    private static MimeMessage message;
    private static Session session;
    private static Transport transport;
    private static String mailHost;
    private static String senderUsername;
    private static String senderPassword;

    private EmailUtils() {
    }

    /**
     * 发送Html文本邮件
     *
     * @param subject      邮件主题
     * @param sendHtml     邮件内容
     * @param receiveEmail 收件人地址
     */
    public static void doSendHtmlEmail(String subject, String sendHtml, String receiveEmail) throws Exception {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(MimeUtility.encodeWord("财鱼管家") + " <" + senderUsername + ">");
            message.setFrom(from);

            // 收件人
            InternetAddress receiveUser = new InternetAddress(receiveEmail);
            message.setRecipient(Message.RecipientType.TO, receiveUser);

            // 邮件主题
            message.setSubject(subject);

            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(sendHtml, "text/html;charset=UTF-8");

            // 保存邮件
            message.saveChanges();

            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport = session.getTransport("smtp");
            transport.connect(mailHost, senderUsername, senderPassword);

            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    /**
     * 发送带附件邮件
     *
     * @param subject      邮件主题
     * @param sendHtml     邮件内容
     * @param receiveEmail 收件人地址
     * @param fileUrl      附件url地址
     */
    public static void doSendAttachmentEmail(String subject, String sendHtml, String receiveEmail, URL fileUrl)
            throws Exception {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(MimeUtility.encodeWord("财鱼管家") + " <" + senderUsername + ">");
            message.setFrom(from);

            // 收件人
            InternetAddress receiveUser = new InternetAddress(receiveEmail);
            message.setRecipient(Message.RecipientType.TO, receiveUser);

            // 邮件主题
            message.setSubject(subject);

            // 向Multipart对象中添加文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件，附件从url中获取
            if (fileUrl != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(fileUrl));

                // MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility.encodeWord("财鱼电签合同.pdf"));
                multipart.addBodyPart(attachmentBodyPart);
            }

            // 保存邮件
            message.setContent(multipart);
            message.saveChanges();

            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport = session.getTransport("smtp");
            transport.connect(mailHost, senderUsername, senderPassword);

            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    /*
      初始化
     */
    static {
        session = Session.getInstance(new Properties());
        message = new MimeMessage(session);

        mailHost = PropertiesUtils.getString("mail.smtp.host");
        senderUsername = PropertiesUtils.getString("mail.sender.username");
        senderPassword = PropertiesUtils.getString("mail.sender.password");
    }
}
