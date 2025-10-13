package imoong.splearn.application.member.provided;

import static imoong.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import imoong.splearn.SplearnTestConfiguration;
import imoong.splearn.domain.member.DuplicateEmailException;
import imoong.splearn.domain.member.Member;
import imoong.splearn.domain.member.MemberFixture;
import imoong.splearn.domain.member.MemberInfoUpdateRequest;
import imoong.splearn.domain.member.MemberRegisterRequest;
import imoong.splearn.domain.member.MemberStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {


    @Test
    void register() {
        Member member = memberRegister.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(createMemberRegisterRequest());

        assertThatThrownBy(
            () -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
            .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);


    }

    @Test
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = registerMember();

        member.activate();
        entityManager.flush();
        entityManager.clear();

        var updateRequest = new MemberInfoUpdateRequest("sangwoong", "kw3082", "안녕하세요");
        member = memberRegister.updateInfo(member.getId(), updateRequest);

        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateRequest.profileAddress());
    }


    @Test
    void memberRegisterRequestFail() {

        checkValidation(new MemberRegisterRequest("imoong@splear.com", "imo", "longsecret"));
        checkValidation(new MemberRegisterRequest("imoong@splear.com",
            "imoong____________________________________", "longsecret"));
        checkValidation(new MemberRegisterRequest("imoongsplear.com", "imoong", "longsecret"));

    }


    private void checkValidation(MemberRegisterRequest request) {
        assertThatThrownBy(() -> memberRegister.register(request))
            .isInstanceOf(ConstraintViolationException.class);
    }

    private Member registerMember() {
        Member member = memberRegister.register(createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

}
