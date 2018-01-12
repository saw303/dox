/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.sql.DataSource;
import java.io.File;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DoxApplication {

    private final static Logger log = LoggerFactory.getLogger(DoxApplication.class);

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(false)
                .addScripts("ddl_h2_create.sql", "install.sql")
                .build();
    }

    public static void main(String[] args) {

        String doxStore = System.getenv("DOX_STORE");

        hasText(doxStore, "Archive directory must not be null. Please make sure you have properly set environment variable DOX_STORE");
        File doxStoreFile = new File(doxStore);

        isTrue(doxStoreFile.exists(), String.format("DOX_STORE folder %s does not exist", doxStore));

        File thumbnailsFolder = new File(doxStoreFile, "thumbnails");

        if (!thumbnailsFolder.exists() && doxStoreFile.canWrite() && System.getProperty("spring.profiles.active").equals("dev")) {
            log.info("You are running in development mode. Creating missing thumnail folder {}", thumbnailsFolder.getAbsolutePath());
            thumbnailsFolder.mkdir();
        }

        isTrue(thumbnailsFolder.exists(), String.format("Thumbails folder %s does not exist.", thumbnailsFolder.getAbsolutePath()));

        SpringApplication springApplication = new SpringApplication(DoxApplication.class);
        springApplication.run(args);
    }
}
