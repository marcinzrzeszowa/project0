package pl.projectarea.project0;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

//@ConstructorBinding
@Configuration
@ConfigurationProperties(prefix = "sender")
public class ApplicationConfig {

    private String sender;

     public String getSender() {
        return sender;
    }
     public void setSender(String sender) {
        this.sender = sender;
    }
}
