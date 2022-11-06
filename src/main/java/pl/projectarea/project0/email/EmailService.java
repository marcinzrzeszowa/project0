package pl.projectarea.project0.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.pricealert.PriceAlert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final static Logger LOGGER= LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Async
    public void send(PriceAlert priceAlert, BigDecimal currentPrice){
        long id = priceAlert.getId();
        String userName = priceAlert.getUser().getUsername();
        String email = priceAlert.getUser().getEmail();
        String description = priceAlert.getDescription();
        String ticker = priceAlert.getTicker();
        double price = currentPrice.doubleValue();
        double maxPrice = priceAlert.getMaxPrice().doubleValue();
        double minPrice = priceAlert.getMinPrice().doubleValue();

        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = LocalTime.now().format(simpleDateFormat);

        StringBuilder subjectMsg= new StringBuilder();
        subjectMsg.append("Czas: ").append(time).append(" | Kurs: ").append(ticker).append(" ,wynosi: ").append(price).append("\n");
        CharSequence charSequence = (maxPrice != 0 && price > maxPrice) ? subjectMsg.append("Kurs powyżej wartość: ").append(maxPrice).append("\n") : "";
        CharSequence charSequence2 = (minPrice != 0 && price < minPrice) ? subjectMsg.append("Kurs poniżej wartość: ").append(minPrice).append("\n") : "";

        String message ="<div>" +
                        "<h1>Alert cenowy Nr: "+ id +" wysłany z strony projectarea.pl</h1>" +
                        "<h2> Cześć "+ userName + "</h2>" +
                        "<p> Wiadomość: "+ subjectMsg.toString() +"</p>" +
                        "<p>Twoja notatka: "+ description +"</p>" +
                        "</div>";

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message,true);
            helper.setTo(email);
            helper.setSubject(subjectMsg.toString());
            helper.setFrom("projectarea@onet.pl");
            helper.setReplyTo("projectarea@onet.pl");
            //mailSender.send(mimeMessage); //TODO Aktywowac wysyłanie emaili
        }catch(MessagingException e){
            LOGGER.info("Nie można wysłać wiadomości pod adress: " + email + " uzytkownika: "+ userName, e);
            e.printStackTrace();
        }
    }
}
