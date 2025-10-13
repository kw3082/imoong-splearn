package imoong.splearn.adapter.integration;

import imoong.splearn.domain.shared.Email;
import org.junit.jupiter.api.Test;

class DummyEmailSenderTest {

    @Test
    void dummyEmailSender() {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("imoong@gmail.com"), "subject", "body");


    }
}