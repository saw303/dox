/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 08.08.12 07:49
 *        </div>
 */
public class ValueNotInDomainException extends ValidationException {

    private String value;
    private List<String> validValues = new ArrayList<>();

    public ValueNotInDomainException(String message, String value, List<String> validValues) {
        super(message);
        this.value = value;
        this.validValues = validValues;
    }

    public List<String> getValidValues() {
        return validValues;
    }

    public String getValue() {
        return value;
    }
}
