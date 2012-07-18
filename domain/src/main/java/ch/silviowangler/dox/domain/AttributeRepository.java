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

package ch.silviowangler.dox.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public interface AttributeRepository extends CrudRepository<Attribute, Long> {

    /**
     * Finds all global attribute definitions. This attributes are available on
     * every document class within DOX.
     *
     * @return all global attribute
     * @see DocumentClass
     */
    @Query("from Attribute a where a.documentClasses IS EMPTY")
    List<Attribute> findGlobalAttributes();

    @Query("from Attribute a where a.documentClasses IS EMPTY OR ? in elements(a.documentClasses)")
    List<Attribute> findAttributesForDocumentClass(DocumentClass documentClass);

    Attribute findByShortName(String shortName);
}
