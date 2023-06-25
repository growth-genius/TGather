package com.ysdeveloper.tgather.infra.security;

public record JwtAuthentication(Long id, String accountId, String email, String nickname) {}

