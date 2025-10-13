package imoong.splearn.application.member.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import imoong.splearn.SplearnTestConfiguration;
import imoong.splearn.domain.member.Member;
import imoong.splearn.domain.member.MemberFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {
    
    @Test
    void find() {

        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = memberFinder.find(member.getId());

        assertThat(found.getId()).isEqualTo(member.getId());


    }

    @Test
    void findFail() {
        assertThatThrownBy(() -> memberFinder.find(999L))
        .isInstanceOf(IllegalArgumentException.class);
    }
}