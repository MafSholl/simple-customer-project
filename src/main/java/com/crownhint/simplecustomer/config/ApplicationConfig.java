package com.crownhint.simplecustomer.config;

import com.crownhint.simplecustomer.security.SpringSecurityAuditorAware;
import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository
//                .findByEmail(username).orElseThrow(()-> new SimpleCustomerException("User does not exist!")
//                );
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(username -> this.userRepository.findByEmail(username).orElseThrow());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


}
