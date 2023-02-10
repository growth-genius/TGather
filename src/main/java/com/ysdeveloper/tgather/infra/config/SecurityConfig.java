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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter () {
        return new JwtAuthenticationTokenFilter( jwtProperties.getHeader(), jwt );
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider ( AccountService accountService ) {
        return new JwtAuthenticationProvider( accountService );
    }

    @Bean
    public SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
        http.httpBasic()
            .disable()                                              // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
            .csrf()
            .disable()                                              // rest api 이므로 csrf 보안이 필요없으므로 disable 처리.
            .exceptionHandling()
            .accessDeniedHandler( jwtAccessDeniedHandler )
            .authenticationEntryPoint( unAuthorizedHandler )
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy( SessionCreationPolicy.STATELESS ) // jwt token 으로 인증하므로 세션은 필요없으므로 생성안함.
            .and()
            .cors()
            // .configurationSource(request -> new CorsConfiguration(setCorsConfig()).applyPermitDefaultValues())
            .configurationSource( corsConfigurationSource() )
            .and()
            .authorizeRequests()
            .requestMatchers( "/*",
                              "/api/sign-in",
                              "/api/account/sign-up",
                              "/api/account/check-email",
                              "/api/account/check-email-token/**",
                              "/api/email-login",
                              "/api/check-email-login",
                              "/api/account/login",
                              "/h2-console/**",
                              "/api/sign/refresh-token/**",
                              "/api/employee/positions" )
            .permitAll()
            .requestMatchers( HttpMethod.GET, "/api/profile/*" )
            .permitAll()
            .requestMatchers( "/api/**" )
            .hasRole( "USER" )
            .and()
            .addFilterBefore( jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager ( AuthenticationConfiguration authenticationConfiguration ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        var manager = new InMemoryUserDetailsManager();
        var user1 = User.withUsername( "admin" ).password( "admin" ).roles( "ADMIN" ).build();
        var user2 = User.withUsername( "yeji" ).password( "yeji" ).roles( "USER" ).build();

        manager.createUser( user1 );
        manager.createUser( user2 );
        return manager;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource () {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( appProperties.getHosts() );
        config.setAllowedMethods( Arrays.stream( HttpMethod.values() ).map( HttpMethod::name ).toList() );
        config.setExposedHeaders( List.of( "Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "strict-origin-when-cross-origin" ) );
        config.setAllowedHeaders( List.of( "*" ) );
        config.setAllowCredentials( true );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer () {
        return ( web ) -> web.ignoring().requestMatchers( "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**" );
    }

}
