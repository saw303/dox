/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            if (tokens.length == 3 && this.version.matches("(\\d\\.?)+-[A-Za-z0-9]+-\\d{14}")) {

                LocalDateTime dateTime = LocalDateTime.parse(tokens[tokens.length - 1], formatter);

                StringBuilder sb = new StringBuilder(tokens[0]);
                sb.append("-").append(tokens[1]).append(" (").append(dateTime.format(formatter2)).append(")");

                return sb.toString();

            } else if (tokens.length == 2 && tokens[1].matches("\\d{14}")) {

                LocalDateTime dateTime = LocalDateTime.parse(tokens[tokens.length - 1], formatter);

                StringBuilder sb = new StringBuilder(tokens[0]);
                sb.append(" (").append(dateTime.format(formatter2)).append(")");

                return sb.toString();
            }
        }

        if ("@dox.app.version@".equals(this.version)) {
            return "<development mode>";
        }

        return "invalid";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoxVersion{");
        sb.append("version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
