package imoong.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("imoong@gmail.com");
        var email2 = new Email("imoong@gmail.com");

        assertThat(email1).isEqualTo(email2);
    }

}