package ch.silviowangler.dox;

import ch.silviowangler.dox.repository.DocumentRepositoryCustomImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Silvio Wangler on 04/10/15.
 */
@Configuration
@EnableJpaRepositories("ch.silviowangler.dox.repository")
public class DomainConfiguration {

    @Bean
    public DocumentRepositoryCustomImpl documentRepositoryImpl() {
        return new DocumentRepositoryCustomImpl();
    }
}
