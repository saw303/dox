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
package ch.silviowangler.dox.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class DoxDocumentFileFilter implements FileFilter {

    private Logger logger = LoggerFactory.getLogger(DoxDocumentFileFilter.class);

    public boolean accept(File file) {
        final boolean result = file.getName().length() == 64;
        logger.trace("Accepting file {}? {}", file.getAbsolutePath(), result);
        return result;
    }
}
