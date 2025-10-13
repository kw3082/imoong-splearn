package imoong.splearn.adapter.integration;

import imoong.splearn.application.member.required.EmailSender;
import imoong.splearn.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("Dummy Sending Email: " + email);
    }
}
