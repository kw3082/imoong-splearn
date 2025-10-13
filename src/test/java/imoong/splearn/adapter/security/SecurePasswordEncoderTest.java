package imoong.splearn.adapter.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SecurePasswordEncoderTest {

    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String password = securePasswordEncoder.encode("secret");

        assertThat(securePasswordEncoder.matches("secret", password)).isTrue();
        assertThat(securePasswordEncoder.matches("false", password)).isFalse();
    }
}