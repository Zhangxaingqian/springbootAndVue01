package cn.myweb01.money01.utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.InputStream;
import java.util.Properties;

/**
 * 发送邮件工具类
 */
public final class MailUtil {

	private MailUtil(){}
	/**
	 * 发送邮件
	 * 参数一:发送邮件给谁
	 * 参数二:发送邮件的内容
	 */
	public static void sendMail(String toEmail, String emailMsg) throws Exception {

		//163邮箱的第二种设置
		Properties prop = new Properties();
		//InputStream inputStream = MailUtil.class.getClassLoader().getResourceAsStream("mail.properties");
		//prop.load(inputStream);
		prop.setProperty("mail.host", "smtp.163.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
		prop.setProperty("mail.smtp.socketFactory.fallback", "false");
		prop.setProperty("mail.smtp.ssl.enable", "true");//非常重要
		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.user", "13248134961@163.com");
		prop.setProperty("mail.password", "ZXQzxq1992");
		prop.setProperty("mail.subject", "激活验证-weekdayJob");

		Session session = Session.getInstance(prop);
		//开启debug模式，方便看程序发送Email的运行状态
		session.setDebug(true);
		Transport ts = session.getTransport();
		ts.connect(prop.getProperty("mail.host"), prop.getProperty("mail.user"), prop.getProperty("mail.password"));
		Message message = simpleMail(prop.getProperty("mail.user"),session, prop.getProperty("mail.subject"), emailMsg,toEmail);
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();

	}

	/**
	 * 一封简单的只包含文本的邮件
	 */
	public static MimeMessage simpleMail(String user,Session session, String subject, String content,String toEmail) throws Exception {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		message.setSubject(subject);
		message.setContent(content, "text/html;charset=UTF-8");
		return message;
	}

	/**
	 * 测试类
	 */
	public static void main(String[] args) throws Exception{
		String toEmail = "1439167393@qq.com";
		String emailMsg = "测试一下";
		sendMail(toEmail,emailMsg);
		System.out.println("发送成功。。。");
	}
}









