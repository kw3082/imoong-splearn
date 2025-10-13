package imoong.splearn.application.member.required;

import imoong.splearn.domain.member.Member;
import imoong.splearn.domain.shared.Email;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);
}
