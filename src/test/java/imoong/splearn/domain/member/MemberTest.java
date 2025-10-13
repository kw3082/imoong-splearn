package imoong.splearn.domain.member;

import static imoong.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static imoong.splearn.domain.member.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() throws Exception {

        this.passwordEncoder = createPasswordEncoder();

        member = Member.register(createMemberRegisterRequest(),
            passwordEncoder);
    }




    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void activate() {
        assertThat(member.getDetail().getActivatedAt()).isNull();

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
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
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
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
        assertThat(member.verifyPassword("longsecret", passwordEncoder)).isTrue();
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
            () -> Member.register(createMemberRegisterRequest("invalid"),
                passwordEncoder)).isInstanceOf(IllegalArgumentException.class);
        Member.register(createMemberRegisterRequest(),
            passwordEncoder);


    }

    @Test
    void updateInfo() {
        member.activate();

        MemberInfoUpdateRequest updateRequest = new MemberInfoUpdateRequest("sangwoong", "kw3082",
            "안녕하세요");

        member.updateInfo(updateRequest);

        assertThat(member.getNickname()).isEqualTo(updateRequest.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateRequest.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(updateRequest.introduction());




    }


}
