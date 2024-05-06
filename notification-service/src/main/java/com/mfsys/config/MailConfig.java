package com.mfsys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${Mail.UserName}")
    private String UserName;

    @Value("${Mail.UserPassword}")
    private String UserPassword;

//    @Value("${app.client.code}")
//    private String clientCode;

//    @Autowired
//    private IbEmailCredentialRepository emailCredentialRepository;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

//        Optional<IbEmailCredential> emailCredential = getEmailCredentialByOrga(clientCode);

        // Configure the mail sender properties
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

//        if(emailCredential.isEmpty()) {
        mailSender.setUsername(UserName);
        mailSender.setPassword(UserPassword);
//        }
//        else {
//            mailSender.setUsername(emailCredential.get().getEmail());
//            mailSender.setPassword(emailCredential.get().getPassword());
//        }

        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

//    public Optional<IbEmailCredential> getEmailCredentialByOrga(String porOrgacode) {
//        return emailCredentialRepository.findEmailCredentialByOrga(porOrgacode);
//    }
}
