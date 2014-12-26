package ch.silviowangler.dox.api.security;

import java.util.List;

/**
 * Created by saw on 26.12.14.
 */
public interface UserService {

    List<String> getCurrentUserClients();

    boolean isLoggedIn();
}
