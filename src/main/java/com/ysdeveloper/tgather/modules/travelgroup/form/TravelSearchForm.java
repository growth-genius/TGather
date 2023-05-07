package com.ysdeveloper.tgather.modules.travelgroup.form;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import java.util.Set;
import lombok.Data;

@Data
public class TravelSearchForm {

    private Set<TravelTheme> travelThemes;

}
