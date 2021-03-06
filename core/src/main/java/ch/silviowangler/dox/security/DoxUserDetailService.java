/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox.security;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.security.Role;
import ch.silviowangler.dox.repository.security.DoxUserRepository;

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
    @Transactional(readOnly = true)
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
