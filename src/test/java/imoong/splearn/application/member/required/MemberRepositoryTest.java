package imoong.splearn.application.member.required;

import static imoong.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static imoong.splearn.domain.member.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import imoong.splearn.domain.member.Member;
import imoong.splearn.domain.member.MemberStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        Member found = memberRepository.findById(member.getId()).orElseThrow();

        assertThat(found.getDetail().getRegisteredAt()).isNotNull();
        assertThat(found.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        memberRepository.save(member);

        Member duplicate = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        assertThatThrownBy(() -> memberRepository.save(duplicate))
        .isInstanceOf(DataIntegrityViolationException.class);
    }
}