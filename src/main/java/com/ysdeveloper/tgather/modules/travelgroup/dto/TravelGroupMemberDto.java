package com.ysdeveloper.tgather.modules.travelgroup.dto;

import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TravelGroupMemberDto {

    /** 여행그룹 가입자 아이디 */
    private String travelGroupMemberId;
    /** 계정정보 */
    private String accountId;
    /** 여행그룹 승인 여부 */
    private boolean approved;

    public TravelGroupMemberDto(TravelGroupMember travelGroupMember) {
        this.travelGroupMemberId = travelGroupMember.getTravelGroupMemberId();
        this.accountId = travelGroupMember.getAccountId();
        this.approved = travelGroupMember.isApproved();
    }

    public static TravelGroupMemberDto from(TravelGroupMember travelGroupMember) {
        return new TravelGroupMemberDto(travelGroupMember);
    }
}
