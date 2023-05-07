package com.ysdeveloper.tgather.modules.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ysdeveloper.tgather.infra.security.Jwt;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.enums.AccountRole;
import com.ysdeveloper.tgather.modules.account.enums.LoginType;
import java.time.LocalDateTime;
import java.util.Set;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude( Include.NON_NULL )
public class AccountDto {

    /** 로그인 아이디 */
    private Long accountId;
    /** 고유 식별자 */
    private String uuid;
    /** 이메일 */
    private String email;
    /** 사용자 이름 */
    private String userName;
    /* 사용자 별명 */
    private String nickname;
    /* 패스워드*/
    private String password;
    /** 가입일자 */
    private LocalDateTime joinedAt;
    /** 권한 */
    @Builder.Default
    private Set<AccountRole> roles = Set.of( AccountRole.USER );
    /** 로그인 횟수 */
    private int loginCount;
    /** 마지막 로그인 일자 */
    private LocalDateTime lastLoginAt;

    private String profileImage;
    private LoginType loginType;
    private String accessToken;
    private String refreshToken;
    /* 나이 */
    private int age;

    private int birth;
    private Set<TravelTheme> travelThemes;

    AccountDto ( Account account ) {
        this.accountId = account.getId();
        this.uuid = account.getUuid();
        this.userName = account.getUsername();
        this.nickname = account.getNickname();
        this.email = account.getEmail();
        this.loginType = account.getLoginType();
        this.roles = account.getRoles();
        this.age = account.getAge();
        this.birth = account.getBirth();
        this.travelThemes = account.getTravelThemes();
        this.profileImage = account.getProfileImage();
        this.joinedAt = account.getJoinedAt();

    }

    public static AccountDto from ( Account account ) {
        return new AccountDto( account );
    }

    public static AccountDto createByAccountAndGenerateAccessToken ( Account account, Jwt jwt ) {
        AccountDto accountDTO = new AccountDto( account );
        accountDTO.generateAccessToken( jwt );
        return accountDTO;
    }

    public void generateAccessToken ( Jwt jwt ) {
        Jwt.Claims claims = Jwt.Claims.of( accountId, email, roles.stream().map( AccountRole::name ).toArray( String[]::new ) );
        this.accessToken = jwt.createAccessToken( claims );
        this.refreshToken = jwt.createRefreshToken( claims );
    }
}
