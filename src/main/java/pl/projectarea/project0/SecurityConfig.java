package pl.projectarea.project0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.projectarea.project0.user.UserRepository;
import pl.projectarea.project0.user.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final UserRepository userRepository;

    private static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ADMIN = "ADMIN";
    private static final String MODERATOR = "MODERATOR";

    @Autowired
    public SecurityConfig(UserService userDetailsService, UserRepository userRepository) {
        this.userService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/","/articles").permitAll()
                //.antMatchers(HttpMethod.POST,"/articles").hasAnyRole(MODERATOR, ADMIN)
                //.antMatchers(HttpMethod.DELETE,"/articles").hasRole(ADMIN)
                .and()
                .formLogin().permitAll()
                .and().logout().permitAll()
                .and()
                .csrf().disable();
    }



  /*  @EventListener(ApplicationReadyEvent.class)
    public void initUser(){
        User user1 = new User("Jan",passwordEncoder().encode("123"),ROLE_MODERATOR, "marcinzbrzozowa@gmail.com");
        User user2 = new User("Admin",passwordEncoder().encode("123"),ROLE_ADMIN, "marcinzbrzozowa@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);
    }*/
}
