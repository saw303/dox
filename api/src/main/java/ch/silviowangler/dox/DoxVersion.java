/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DoxVersion implements Serializable {

    private String version;

    public DoxVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String formatVersion() {

        if (this.version.contains("-")) {

            String[] tokens = this.version.split("-");

            if (tokens.length == 3) {

                DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

                DateTime dateTime = dateTimeFormatter.parseDateTime(tokens[tokens.length - 1]);

                StringBuilder sb = new StringBuilder(tokens[0]);
                sb.append("-").append(tokens[1]).append(" (").append(dateTime.toString("dd.MM.yyyy HH:mm:ss")).append(")");

                return sb.toString();
            }
        }

        if ("@dox.app.version@".equals(this.version)) {
            return "<development mode>";
        }

        return "invalid";
    }
}
