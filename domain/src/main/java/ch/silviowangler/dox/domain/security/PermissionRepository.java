package ch.silviowangler.dox.domain.security;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {
}
