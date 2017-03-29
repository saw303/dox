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
package ch.silviowangler.dox;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class HashGenerator {

    private static Logger logger = LoggerFactory.getLogger(HashGenerator.class);

    public static String sha256Hex(File file) throws IOException {

        Assert.notNull(file, "file must not be null");

        logger.debug("About to generate SHA-256 hash for file '{}'. Does it exist? {}", file.getAbsolutePath(), file.exists());

        InputStream inputStream = new FileInputStream(file);
        return DigestUtils.sha256Hex(inputStream);
    }
}
