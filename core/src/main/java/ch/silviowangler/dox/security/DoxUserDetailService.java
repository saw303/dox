package ch.silviowangler.dox.security;

import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.security.DoxUserRepository;
import ch.silviowangler.dox.domain.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 02.10.12 18:18
 *        </div>
 */
@Service
public class DoxUserDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DoxUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.trace("Trying create user details for user '{}'", username);

        final DoxUser user = userRepository.findByUsername(username);

        if (user == null) {
            logger.info("No such user with name '{}'", username);
            throw new  UsernameNotFoundException("No such user " + username);
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {

                Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    for (ch.silviowangler.dox.domain.security.GrantedAuthority grantedAuthority : role.getGrantedAuthorities()) {
                        grantedAuthorities.add(new SimpleGrantedAuthority(grantedAuthority.getName()));
                    }
                }

                logger.trace("User '{}' has these granted authorities '{}'", user.getUsername(), grantedAuthorities);

                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return new String(user.getPassword());
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
