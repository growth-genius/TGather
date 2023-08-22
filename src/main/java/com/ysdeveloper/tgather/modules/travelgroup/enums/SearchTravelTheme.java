package com.ysdeveloper.tgather.modules.travelgroup.enums;

import com.ysdeveloper.tgather.modules.common.EnumMapperType;

public enum SearchTravelTheme implements EnumMapperType {
    ALL("전체", true),
    FOOD("먹방", false),
    HEALING("힐링", false),
    ACTIVITY("액티비티", false);

    private final String theme;
    private final boolean active;

    SearchTravelTheme(String theme, boolean active) {
        this.theme = theme;
        this.active = active;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getTitle() {
        return this.theme;
    }

    @Override
    public boolean isDefault() {
        return this.active;
    }
}
