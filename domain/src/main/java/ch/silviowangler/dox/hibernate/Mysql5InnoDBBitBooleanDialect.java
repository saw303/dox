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

package ch.silviowangler.dox.hibernate;

import org.hibernate.dialect.MySQL5InnoDBDialect;

import java.sql.Types;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <p/>
 *        Workaround due to an issue https://hibernate.onjira.com/browse/HHH-6935
 */
public class Mysql5InnoDBBitBooleanDialect extends MySQL5InnoDBDialect {

    public Mysql5InnoDBBitBooleanDialect() {
        super();
        registerColumnType(Types.BOOLEAN, "bit");
    }
}
