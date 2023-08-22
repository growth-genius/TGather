package com.ysdeveloper.tgather.modules.travelgroup.dto;

import com.ysdeveloper.tgather.modules.common.EnumMapperValue;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelGroupRegisterInitDto {

    List<EnumMapperValue> travelThemes;

}
