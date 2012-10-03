package ch.silviowangler.dox.security;

import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.security.DoxUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 02.10.12 18:18
 *        </div>
 */
@Service
public class DoxUserDetailService implements UserDetailsService {

    @Autowired
    private DoxUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final DoxUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new  UsernameNotFoundException("No such user " + username);
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
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
