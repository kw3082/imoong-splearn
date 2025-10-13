package imoong.splearn.application.member.required;

import imoong.splearn.domain.shared.Email;


public interface EmailSender {

    void send(Email email, String subject, String body);

}
