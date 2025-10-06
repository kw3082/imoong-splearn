package imoong.splearn.application.required;

import imoong.splearn.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

}
