package imoong.splearn.application.provided;

import imoong.splearn.domain.Member;
import imoong.splearn.domain.MemberRegisterRequest;

public interface MemberRegister {

    Member register(MemberRegisterRequest registerRequest);
}
