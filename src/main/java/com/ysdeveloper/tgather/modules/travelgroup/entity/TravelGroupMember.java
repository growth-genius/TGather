package com.ysdeveloper.tgather.modules.travelgroup.entity;

import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.travelgroup.enums.TravelGroupRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class TravelGroupMember {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "travel_group_member_id" )
    private Long id;
    /* 여행 그룹 */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "travel_group_id" )
    private TravelGroup travelGroup;
    /* 사용자 */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "account_id" )
    private Account account;
    /* 여행 그룹 권한 */
    @Enumerated( EnumType.STRING )
    private TravelGroupRole travelGroupRole;

    private TravelGroupMember ( TravelGroup travelGroup, Account account, TravelGroupRole travelGroupRole ) {
        this.travelGroup = travelGroup;
        this.account = account;
        this.travelGroupRole = travelGroupRole;
    }

    private static TravelGroupMember of ( TravelGroup travelGroup, Account account, TravelGroupRole travelGroupRole ) {
        return new TravelGroupMember( travelGroup, account, travelGroupRole );
    }

    public void addMember () {
        this.travelGroup.getTravelGroupMemberList().add( this );
        this.travelGroup.plusParticipant();
    }

    public void removeMember () {
        this.travelGroup.getTravelGroupMemberList().remove( this );
        this.travelGroup.minusParticipant();
    }

}
