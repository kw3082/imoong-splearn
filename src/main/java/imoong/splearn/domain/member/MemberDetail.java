package imoong.splearn.domain.member;


import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.isTrue;

import imoong.splearn.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {

    private Profile profile;

    private String introduction;

    private LocalDateTime registeredAt;


    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;


    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    void activate() {
        isTrue(activatedAt == null, "이미 activated가 설정이 되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        isTrue(deactivatedAt == null, "이미 deactivated가 설정이 되었습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.introduction = requireNonNull(updateRequest.introduction());

        this.profile = new Profile(updateRequest.profileAddress());
    }
}
