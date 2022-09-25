package pl.projectarea.project0.email;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.ApplicationConfig;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;


    private String sender;

    @Autowired
    public MailService(JavaMailSender javaMailSender, String sender) {
        this.javaMailSender = javaMailSender;
        this.sender = sender;
    }

   /* public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }*/
}
