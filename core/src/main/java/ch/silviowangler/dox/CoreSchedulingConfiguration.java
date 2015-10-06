package ch.silviowangler.dox;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Silvio Wangler on 06/10/15.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class CoreSchedulingConfiguration {


    @Bean
    @Scope("prototype")
    public Executor executor() {
        return new DefaultExecutor();
    }
}
