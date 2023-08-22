package com.ysdeveloper.tgather.infra.config;

import com.ysdeveloper.tgather.infra.properties.AppProperties;
import com.ysdeveloper.tgather.infra.properties.JwtProperties;
import com.ysdeveloper.tgather.infra.security.EntryPointHandler;
import com.ysdeveloper.tgather.infra.security.Jwt;
import com.ysdeveloper.tgather.infra.security.JwtAccessDeniedHandler;
import com.ysdeveloper.tgather.infra.security.JwtAuthenticationProvider;
import com.ysdeveloper.tgather.infra.security.JwtAuthenticationTokenFilter;
import com.ysdeveloper.tgather.modules.account.AccountService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Jwt jwt;

    private final JwtProperties jwtProperties;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final EntryPointHandler unAuthorizedHandler;

    private final AppProperties appProperties;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtProperties.getHeader(), jwt);
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(AccountService accountService) {
        return new JwtAuthenticationProvider(accountService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
            .authenticationEntryPoint(unAuthorizedHandler).and().headers().frameOptions().sameOrigin().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt
            .and().cors().configurationSource(corsConfigurationSource()).and().authorizeHttpRequests()
            .requestMatchers("/*", "/api/sign-in", "/api/account/sign-up", "/api/account/check-email", "/api/account/check-nickname/**", "/api/account/login",
                "/api/account/auth/**", "/api/sign/refresh-token/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/profile/*").permitAll().and()
            .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(appProperties.getHosts());
        config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toList());
        config.setExposedHeaders(List.of("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "strict-origin-when-cross-origin"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
