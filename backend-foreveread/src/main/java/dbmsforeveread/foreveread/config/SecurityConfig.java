//package dbmsforeveread.foreveread.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests(authorizeRequests ->
////                        authorizeRequests.anyRequest().authenticated()
////                )
////                .formLogin()
////                .and()
////                .httpBasic();
////        return http.build();
////    }
//
////    @Bean
////    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        UserDetails user = User.withUsername("user")
////                .password(passwordEncoder.encode("password"))
////                .roles("USER")
////                .build();
////        manager.createUser(user);
////        return manager;
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}