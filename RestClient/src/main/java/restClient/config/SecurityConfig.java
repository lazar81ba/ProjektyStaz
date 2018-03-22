package restClient.config;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan("restClient")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.eraseCredentials(false)
		 .jdbcAuthentication()
		 .dataSource(dataSource)
		 .usersByUsernameQuery(
				"select username, password,1 from users where username=?")
		.authoritiesByUsernameQuery(
				"select users.username,role from users " + 
				"inner join user_role_connection on users.username=user_role_connection.USERNAME " + 
				"inner join user_roles on user_roles.user_role_id = user_role_connection.USER_ROLE_ID " + 
				"where users.username = ?" )
		.passwordEncoder(new StandardPasswordEncoder());
		 
	 }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	 http
	 .csrf().disable()
	 .authorizeRequests()
	 .antMatchers("/add").hasAuthority("ROLE_ADMIN")
	 .antMatchers("/addJSP").hasAuthority("ROLE_ADMIN")
	 .antMatchers("/update").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
	 .antMatchers("/updateJSP").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
	 .antMatchers("/delete").hasAuthority("ROLE_ADMIN")
	 .antMatchers("/deleteJSP").hasAuthority("ROLE_ADMIN")
	 .antMatchers("/home").authenticated()
	 .and()
	 .formLogin()
	 .loginPage("/login")
	 .defaultSuccessUrl("/home")
	 .usernameParameter("USERNAME").passwordParameter("PASSWORD")
	 .and()
	 .logout()
	 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	 .logoutSuccessUrl("/login?logout");

	 
	}
}