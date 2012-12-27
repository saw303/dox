package ch.silviowangler.dox.security;

import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.security.DoxUserRepository;
import ch.silviowangler.dox.domain.security.Role;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            throw new UsernameNotFoundException("No such user " + username);
        }
        Collection<SimpleGrantedAuthority> authorities = Sets.newHashSet();

        for (Role role : user.getRoles()) {
            final String roleName = "ROLE_" + role.getName();
            logger.debug("Adding role {}", roleName);
            authorities.add(new SimpleGrantedAuthority(roleName));
            for (ch.silviowangler.dox.domain.security.GrantedAuthority grantedAuthority : role.getGrantedAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(grantedAuthority.getName()));
            }
        }
        User springSecurityUser = new User(user.getUsername(), user.getPassword(), authorities);

        logger.trace("User '{}' has these granted authorities '{}'", springSecurityUser.getUsername(), springSecurityUser.getAuthorities());
        return springSecurityUser;
    }
}
