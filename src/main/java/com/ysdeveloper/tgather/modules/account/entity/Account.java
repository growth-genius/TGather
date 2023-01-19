package com.ysdeveloper.tgather.modules.account.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.ysdeveloper.tgather.modules.account.enums.AccountRole;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.UpdatedEntity;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupMember;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private String uuid;
    /* 사용자 이름 */
    private String userName;
    /* 이메일 */
    private String email;
    /* 비밀번호 */
    private String password;
    /* 권한 */
    @ElementCollection( fetch = LAZY )
    @Enumerated( EnumType.STRING )
    @CollectionTable( name = "account_roles", joinColumns = @JoinColumn( name = "account_id" ) )
    private Set<AccountRole> roles;
    /* 나이 */
    private int age;
    /* 여행 테마 */
    @ElementCollection( fetch = LAZY )
    @Enumerated( EnumType.STRING )
    @CollectionTable( name = "travel_themes", joinColumns = @JoinColumn( name = "account_id" ) )
    private Set<TravelTheme> travelThemes;

    @OneToMany( mappedBy = "account", fetch = LAZY )
    private List<TravelGroupMember> travelGroupMemberList;

    public Account ( String userName, String email, String password, Set<AccountRole> roles, int age, Set<TravelTheme> travelThemes ) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.age = age;
        this.travelThemes = travelThemes;
        this.uuid = UUID.randomUUID().toString();
    }

    public static Account of ( String userName, String email, String password, Set<AccountRole> roles, int age, Set<TravelTheme> travelThemes ) {
        return new Account( userName, email, password, roles, age, travelThemes );
    }

}
