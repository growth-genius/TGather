package com.ysdeveloper.tgather.modules.account.entity;

import static jakarta.persistence.FetchType.LAZY;
import static org.springframework.beans.BeanUtils.copyProperties;

import com.ysdeveloper.tgather.infra.converter.StringEncryptConverter;
import com.ysdeveloper.tgather.modules.account.enums.AccountRole;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.common.UpdatedEntity;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupMember;
import com.ysdeveloper.tgather.modules.utils.TimeUtil;
import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class Account extends UpdatedEntity {

    /* 아이디 */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "account_id" )
    private Long id;
    /* 고유 식별자 */
    @Column( unique = true )
    private String uuid;
    /* 사용자 이름 */
    private String username;
    /* 사용자 별명 */
    private String nickname;
    /* 이메일 */
    @Convert( converter = StringEncryptConverter.class )
    private String email;
    /* 비밀번호 */
    private String password;
    /* 권한 */
    @ElementCollection( fetch = LAZY )
    @Enumerated( EnumType.STRING )
    @CollectionTable( name = "account_roles", joinColumns = @JoinColumn( name = "account_id" ) )
    private Set<AccountRole> roles = Set.of( AccountRole.USER );
    /* 나이 */
    private int age;

    private int birth;

    /* 여행 테마 */
    @ElementCollection( fetch = LAZY )
    @Enumerated( EnumType.STRING )
    @CollectionTable( name = "travel_themes", joinColumns = @JoinColumn( name = "account_id" ) )
    private Set<TravelTheme> travelThemes;

    /** 프로필 이미지 */
    @Lob
    @Basic
    private String profileImage;

    /** 가입일자 */
    private LocalDateTime joinedAt;

    /** 로그인 횟수 */
    private int loginCount;

    /** 로그인 실패 회수 */
    private int loginFailCount;

    /** 마지막 로그인 일자 */
    private LocalDateTime lastLoginAt;

    /** 인증용 otp 코드 */
    private String otpCode;

    private LocalDateTime otpCodeModifiedAt;
    
    @OneToMany( mappedBy = "account", fetch = LAZY )
    private List<TravelGroupMember> travelGroupMemberList = new ArrayList<>();

    /** 로그인 후 세팅 */
    public void afterLoginSuccess () {
        this.loginFailCount = 0;
        this.loginCount++;
        this.lastLoginAt = LocalDateTime.now();
    }

    /** 비밀번호 변경 */
    public void changePassword ( String password ) {
        this.password = password;
    }

    /** 프로필 사진 변경 */
    public void changeProfileImage ( String profileImage ) {
        this.profileImage = profileImage;
    }

    /** 이름 변경 */
    public void changeNickname ( String nickname ) {
        this.nickname = nickname;
    }

    private Account ( AccountSaveForm accountSaveForm ) {
        copyProperties( accountSaveForm, this );
        TimeUtil.getThisYear();
        this.joinedAt = LocalDateTime.now();
    }

    public static Account from ( AccountSaveForm accountSaveForm ) {
        return new Account( accountSaveForm );
    }

}
