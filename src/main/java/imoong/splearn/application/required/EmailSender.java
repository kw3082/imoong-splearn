package imoong.splearn.application.required;

import imoong.splearn.domain.Email;


public interface EmailSender {

    void send(Email email, String subject, String body);

}
