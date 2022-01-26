package ru.kata.spring.boot_security.demo.configs;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import ru.kata.spring.boot_security.demo.models.Role;
//
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {}
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    private final SuccessUserHandler successUserHandler;
//
//    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
//        this.successUserHandler = successUserHandler;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
////        UserDetails user =
////                User.builder()
////                        .username("user")
////                        .password(passwordEncoder().encode("user"))
////                        .roles("USER")
////                        .username("admin")
////                        .password(passwordEncoder().encode("admin"))
////                        .roles("ADMIN", "USER")
////                        .build();
//
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles(Role.USER.name())
//                        .build(),
//                User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles(Role.ADMIN.name(), Role.USER.name())
//                .build());
//    }
//
//    @Bean
//    protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//}