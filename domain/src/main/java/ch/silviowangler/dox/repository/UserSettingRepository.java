package ch.silviowangler.dox.repository;

import ch.silviowangler.dox.domain.UserSetting;
import ch.silviowangler.dox.domain.security.DoxUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public interface UserSettingRepository extends CrudRepository<UserSetting, Long> {

    UserSetting findByKeyAndUser(String key, DoxUser user);

    List<UserSetting> findByUser(DoxUser user);
}
