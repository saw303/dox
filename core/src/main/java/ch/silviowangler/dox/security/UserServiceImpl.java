package ch.silviowangler.dox.security;

import ch.silviowangler.dox.api.security.UserService;
import ch.silviowangler.dox.domain.Client;
import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.repository.security.DoxUserRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 26.12.14.
 *
 * @author Silvio Wangler
 * @since 0.4
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private DoxUserRepository doxUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(readOnly = true)
    public List<String> getCurrentUserClients() {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DoxUser user = doxUserRepository.findByUsername(principal.getUsername());

        List<String> clientList = Lists.newArrayListWithCapacity(user.getClients().size());

        for (Client client : user.getClients()) {
            logger.debug("User {} hold client {}", user.getUsername(), client.getShortName());
            clientList.add(client.getShortName());
        }
        return clientList;
    }
}
