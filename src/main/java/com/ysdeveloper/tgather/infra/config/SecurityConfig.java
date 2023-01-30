package com.ysdeveloper.tgather.infra.config;

import com.ysdeveloper.tgather.infra.properties.AppProperties;
import com.ysdeveloper.tgather.modules.account.enums.AccountRole;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
        http.httpBasic().disable().csrf().disable();

        http.cors()
            .configurationSource( corsConfigurationSource() )
            .and()
            .authorizeHttpRequests()
            .requestMatchers( "/api/**" )
            .hasRole( AccountRole.ADMIN.getCode() )
            .anyRequest()
            .authenticated();

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
}
