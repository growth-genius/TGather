package com.ysdeveloper.tgather.modules.travelgroup.entity;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.UpdatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Entity
@Getter
public class TravelGroup extends UpdatedEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "travel_group_id" )
    private Long id;

    /* 여행 만남 이름 */
    private String groupName;

    /* 여행 테마 (복수 선택 가능 )*/
    private Set<TravelTheme> travelThemes;

    /** 만남 참여자 수 */
    private long participantCount = 1;

    /** 공개 여부 */
    private boolean open;

    /** 여행 시작일 */
    private String startDate;

    /* 권한 그룹 소속 유저들 */
    @OneToMany( mappedBy = "travelGroup", fetch = FetchType.LAZY )
    private List<TravelGroupMember> travelGroupMemberList = new ArrayList<>();

    public void plusParticipant () {
        this.participantCount++;
    }

    public void minusParticipant () {
        this.participantCount--;
    }

}
