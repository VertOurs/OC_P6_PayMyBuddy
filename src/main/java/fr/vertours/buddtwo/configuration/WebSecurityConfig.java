package fr.vertours.buddtwo.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MyUserDetailsService detailsService;

    @Autowired
    MyAuthentificationProvider myAuthentificationProvider;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] resources = new String[] {
                "/","index", "/css/**", "/images/**", "/registration/**", "/h2-console/**"};

//******************ACCEES CONSOLE H2**********************************************

//        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();

//******************ACCEES SECURITY VOULU******************************************

        http
                .authorizeRequests()
                .antMatchers(resources).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthProvider());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider getAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
