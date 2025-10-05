package imoong.splearn.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() throws Exception {

        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        member = Member.create(new MemberCreateRequest("imoong@splearn.com", "imoong", "secret"),
            passwordEncoder);
    }


    @Test
    void createMember() {

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {

        member.activate();

        assertThatThrownBy(() -> member.activate())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {

        member.activate();
        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {

        assertThatThrownBy(() -> member.deactivate())
            .isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("false", passwordEncoder)).isFalse();

    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("imoong");

        member.changeNickname("kw3082");

        assertThat(member.getNickname()).isEqualTo("kw3082");
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret", passwordEncoder);

        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat((member.isActive())).isFalse();

        member.activate();

        assertThat((member.isActive())).isTrue();

        member.deactivate();

        assertThat((member.isActive())).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(
            () -> Member.create(new MemberCreateRequest("invalid", "imoong", "secret"),
                passwordEncoder)).isInstanceOf(IllegalArgumentException.class);
        Member.create(new MemberCreateRequest("kw3082@naver.com", "imoong", "secret"),
            passwordEncoder);


    }
}
