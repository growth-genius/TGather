package com.ysdeveloper.tgather.modules.travelgroup.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ysdeveloper.tgather.infra.annotation.ServiceTest;
import com.ysdeveloper.tgather.infra.container.AbstractContainerBaseTest;
import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.infra.security.JwtAuthenticationToken;
import com.ysdeveloper.tgather.infra.security.WithMockJwtAuthentication;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import com.ysdeveloper.tgather.modules.travelgroup.enums.TravelGroupRole;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupRepository;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@ServiceTest
class TravelGroupMemberTest extends AbstractContainerBaseTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    private TravelGroupForm travelGroupForm;

    @BeforeEach
    void before() {
        travelGroupForm = new TravelGroupForm();
        travelGroupForm.setGroupName("미국여행");
        travelGroupForm.setTravelThemes(Set.of(TravelTheme.ACTIVITY));
        travelGroupForm.setStartDate("20230510");

        TravelGroupForm travelGroupFormByOpen = new TravelGroupForm();
        travelGroupFormByOpen.setGroupName("제주도여행");
        travelGroupFormByOpen.setTravelThemes(Set.of(TravelTheme.ACTIVITY));
        travelGroupFormByOpen.setOpen(false);
        travelGroupFormByOpen.setStartDate("20230510");

        travelGroupRepository.save(TravelGroup.from(travelGroupFormByOpen));

        JwtAuthentication jwtAuthentication = getJwtAuthentication();
        accountRepository.save(Account.createForTest(jwtAuthentication.email()));
    }

    @Test
    @WithMockJwtAuthentication
    @DisplayName("travelGroupRole이 리더면 approved true")
    void test_case_1() {
        Account account = accountRepository.findByEmail(getEmail()).orElseThrow(() -> new RuntimeException("계정을 찾을 수 없습니다."));

        TravelGroup travelGroup = TravelGroup.from(travelGroupForm);

        TravelGroupMember travelGroupMember = TravelGroupMember.of(travelGroup, account, TravelGroupRole.LEADER);

        assertEquals(TravelGroupRole.LEADER, travelGroupMember.getTravelGroupRole());
        assertTrue(travelGroupMember.isApproved());
    }

    @Test
    @WithMockJwtAuthentication
    @DisplayName("travelGroup이 비공개면 일반 사용자는 approved false")
    void test_case_2() {
        Account account = accountRepository.findByEmail(getEmail()).orElseThrow(() -> new RuntimeException("계정을 찾을 수 없습니다."));

        TravelGroup travelGroup = travelGroupRepository.findByGroupName("제주도여행").orElseThrow(() -> new RuntimeException("등록된 여행그룹을 찾을 수 없습니다."));

        TravelGroupMember travelGroupMember = TravelGroupMember.of(travelGroup, account, TravelGroupRole.USER);

        assertEquals(TravelGroupRole.USER, travelGroupMember.getTravelGroupRole());
        assertFalse(travelGroupMember.isApproved());
    }

    private String getEmail() {
        return getJwtAuthentication().email();
    }

    private static JwtAuthentication getJwtAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return (JwtAuthentication) authenticationToken.getPrincipal();
    }
}