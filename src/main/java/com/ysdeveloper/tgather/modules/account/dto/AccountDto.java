package com.ysdeveloper.tgather.modules.account.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.ysdeveloper.tgather.infra.security.Jwt;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.enums.AccountRole;
import com.ysdeveloper.tgather.modules.account.enums.LoginType;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom Account
 *
 * @author joyeji
 * @since 2023.06.06
 */
@Getter
@Setter
public class AccountDto {

    /** 로그인 아이디 */
    protected Long id;
    /** 고유 식별자 */
    protected String accountId;
    /** 이메일 */
    protected String email;
    /** 사용자 이름 */
    protected String userName;
    /* 사용자 별명 */
    protected String nickname;
    /* 패스워드 */
    protected String password;
    /** 가입일자 */
    protected LocalDateTime joinedAt;
    /** 권한 */
    protected Set<AccountRole> roles = Set.of(AccountRole.USER);
    /** 로그인 횟수 */
    protected int loginCount;
    /** 마지막 로그인 일자 */
    protected LocalDateTime lastLoginAt;
    /** 프로필 이미지 */
    protected String profileImage;
    /** 로그인 타입 */
    protected LoginType loginType;
    /** 접속 토큰 */
    protected String accessToken;
    /** 재발급 토큰 */
    protected String refreshToken;
    /** fcm 토큰 */
    protected String fcmToken;
    /** 나이 */
    protected int age;
    /** 생년월 */
    protected int birth;
    /** 여행 테마 */
    protected Set<TravelTheme> travelThemes;

    private AccountDto(Account account) {
        copyProperties(account, this, "password");
    }

    public static AccountDto from(Account account) {
        return new AccountDto(account);
    }

    public static AccountDto createByAccountAndGenerateAccessToken(Account account, Jwt jwt) {
        AccountDto accountDTO = new AccountDto(account);
        accountDTO.generateAccessToken(jwt);
        return accountDTO;
    }

    public void generateAccessToken(Jwt jwt) {
        Jwt.Claims claims = Jwt.Claims.of(id, accountId, email, nickname,
                roles.stream().map(AccountRole::name).toArray(String[]::new));
        this.accessToken = jwt.createAccessToken(claims);
        this.refreshToken = jwt.createRefreshToken(claims);
    }

}
