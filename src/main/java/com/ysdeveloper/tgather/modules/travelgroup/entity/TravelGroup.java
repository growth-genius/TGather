package com.ysdeveloper.tgather.modules.travelgroup.entity;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.UpdatedEntity;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@NamedEntityGraph(name = "TravelGroup.withTravelThemes", attributeNodes = {@NamedAttributeNode("travelThemes")})
public class TravelGroup extends UpdatedEntity {

    private static final int MAX_PARTICIPANT_COUNT = 999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_group_id")
    private Long id;

    /* 여행 만남 이름 */
    private String groupName;

    /* 여행 테마 (복수 선택 가능 )*/
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "travel_themes", joinColumns = @JoinColumn(name = "travel_group_id"))
    private Set<TravelTheme> travelThemes;

    /** 만남 참여자 수 */
    private long participantCount = 1;

    /** 공개 여부 */
    private boolean open = true;

    /** 참여자 수 제한 **/
    private long limitParticipantCount;

    /** 여행 시작일 */
    private String startDate;

    /* 권한 그룹 소속 유저들 */
    @OneToMany(mappedBy = "travelGroup", fetch = FetchType.LAZY)
    private final List<TravelGroupMember> travelGroupMemberList = new ArrayList<>();

    public TravelGroup(TravelGroupForm travelGroupForm) {
        this.groupName = travelGroupForm.getGroupName();
        this.travelThemes = travelGroupForm.getTravelThemes();
        this.startDate = travelGroupForm.getStartDate();
        this.open = travelGroupForm.isOpen();
        this.limitParticipantCount = travelGroupForm.isLimitedParticipant() ? travelGroupForm.getLimitParticipantCount() : MAX_PARTICIPANT_COUNT;
    }

    public static TravelGroup from(TravelGroupForm travelGroupForm) {
        return new TravelGroup(travelGroupForm);
    }

    public void plusParticipant() {
        this.participantCount++;
    }

    public void minusParticipant() {
        this.participantCount--;
    }

}
