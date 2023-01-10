package com.ling1.springmvc.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()	
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/").permitAll()		
				.antMatchers("/users/register","/lobbies/oca","/lobbies/parchis").permitAll()
				.antMatchers("/friends/*", "/friends/delete/*","/lobbies/createOca", "/lobbies/createParchis","users/edit/*").authenticated()
		        .antMatchers("/*/create","/*/delete/*", "/friends","/users","/lobbies").hasAuthority("admin")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.failureUrl("/login-error")
				.and()
				.logout()
				.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select login,password,true "
	        + "from user "
	        + "where login = ?")
	      .authoritiesByUsernameQuery(
	       "select login, role "
	        + "from user "
	        + "where login = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
}
}


