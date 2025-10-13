package imoong.splearn.application.member.provided;

import imoong.splearn.domain.member.Member;
import imoong.splearn.domain.member.MemberInfoUpdateRequest;
import imoong.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
