package imoong.splearn.application.member;

import imoong.splearn.application.member.provided.MemberFinder;
import imoong.splearn.application.member.provided.MemberRegister;
import imoong.splearn.application.member.required.EmailSender;
import imoong.splearn.application.member.required.MemberRepository;
import imoong.splearn.domain.member.DuplicateEmailException;
import imoong.splearn.domain.member.Member;
import imoong.splearn.domain.member.MemberInfoUpdateRequest;
import imoong.splearn.domain.member.MemberRegisterRequest;
import imoong.splearn.domain.member.PasswordEncoder;
import imoong.splearn.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;


    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.deactivate();

        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUpdateRequest) {
        Member member = memberFinder.find(memberId);

        member.updateInfo(memberInfoUpdateRequest);

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
        }
    }


}
