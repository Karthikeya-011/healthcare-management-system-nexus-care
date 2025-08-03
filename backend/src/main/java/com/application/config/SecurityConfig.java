package com.application.config;

import com.application.filter.JwtFilter;
import com.application.service.UserRegistrationService;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRegistrationService registrationService;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(registrationService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Not recommended for production
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(
                    "/authenticate",
                    "/", "/loginuser", "/logindoctor", "/loginadmin", // allow admin login here
                    "/registeruser", "/registerdoctor",
                    "/addDoctor", "/gettotalusers", "/doctorlist", "/gettotaldoctors",
                    "/gettotalslots", "/acceptstatus/**", "/rejectstatus/**",
                    "/acceptpatient/**", "/rejectpatient/**", "/addBookingSlots",
                    "/doctorlistbyemail/**", "/slotDetails/**", "/slotDetailsWithUniqueDoctors",
                    "/slotDetailsWithUniqueSpecializations", "/patientlistbydoctoremail/**",
                    "/addPrescription", "/doctorProfileDetails/**", "/updatedoctor",
                    "/patientlistbydoctoremailanddate/**", "/userlist", "/getprescriptionbyname/**",
                    "/patientlistbyemail/**", "/patientlist", "/gettotalpatients",
                    "/gettotalappointments", "/gettotalprescriptions", "/profileDetails/**",
                    "/updateuser", "/bookNewAppointment"
                ).permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }
}
