package carswell.tristan.hive.hiveapi.auth.config;

import carswell.tristan.hive.hiveapi.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String ASTERISK = "*";
    private static final String ALL_BASE_PATH = "/**";

    @Override
    protected final void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("%s cannot be found", username)))
                )
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected final void configure(final HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/auth-api/authenticate").permitAll()
                .antMatchers("/auth-api/register/**").permitAll()
                .antMatchers("/auth-api/v2/api-docs").permitAll()
                .antMatchers("/auth-api/swagger-ui").permitAll()
                .antMatchers(ALL_BASE_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        jwtTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        final var config = new CorsConfiguration();
        config.addAllowedOrigin(ASTERISK);
        config.addAllowedHeader(ASTERISK);
        config.addAllowedMethod(ASTERISK);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(ALL_BASE_PATH, config);

        return new CorsFilter(source);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
