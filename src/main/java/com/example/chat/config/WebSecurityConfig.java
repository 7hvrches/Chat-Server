package com.example.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable()) //기본값이 on 이라 disable 처리했다. on 으로 설정할 시 웹페이지에서 csrf 관련한 추가 처리가 필요하다
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin()
                        ))
//                .cors(cors -> cors .configurationSource(request -> {
//                    CorsConfiguration config = new CorsConfiguration();
//                    config.setAllowedOriginPatterns(Arrays.asList("*")); // 허용하려는 origin 패턴 명시
//                    config.setAllowedOrigins(Arrays.asList("*"));        // 허용하려는 origin 명시
//                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    config.setAllowedHeaders(Arrays.asList("*"));
//                    config.setAllowCredentials(true);
//                    return config;
//                }))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/chat/**").hasRole("USER")
                                .requestMatchers("/logout").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated() //어떠한 요청이라도 인증 필요
                                .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin               //form 방식의 로그인 사용
//                        .loginPage("/login")                  //커스텀할 로그인 페이지
//                        .loginProcessingUrl("/chat/room")	    //submit 받을 url, 로그인 Form Action Url
//                        .usernameParameter("a")	            //submit 할 아이디
//                        .passwordParameter("a")	            //submit 할 비밀번호
                                .defaultSuccessUrl("/chat/room", true) //정상적으로 인증 성공 시 이동하는 페이지
                                .permitAll()
                )
                .logout(withDefaults())                         //로그아웃은 기본설정으로
                .rememberMe(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("a")
                .password("a")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("b")
                .password("b")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
