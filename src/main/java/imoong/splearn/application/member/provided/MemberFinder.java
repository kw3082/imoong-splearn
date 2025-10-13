package imoong.splearn.application.member.provided;

import imoong.splearn.domain.member.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
