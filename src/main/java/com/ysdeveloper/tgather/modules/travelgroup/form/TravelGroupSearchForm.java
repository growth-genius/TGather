package com.ysdeveloper.tgather.modules.travelgroup.form;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TravelGroupSearchForm {

    private Set<TravelTheme> travelThemes;

}
