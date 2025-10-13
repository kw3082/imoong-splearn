package imoong.splearn;

import imoong.splearn.application.member.required.EmailSender;
import imoong.splearn.domain.member.MemberFixture;
import imoong.splearn.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SplearnTestConfiguration {

    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println(
            "Sending Email: " + email + ", Subject: " + subject + ", Body: " + body);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }

}
