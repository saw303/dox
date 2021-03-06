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
package ch.silviowangler.dox.document;

import java.io.File;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
public interface DocumentInspector {

    /**
     * Retrieves the number of pages from an input stream
     *
     * @param file document to analyze
     * @return number of pages
     */
    public int retrievePageCount(File file);

}
