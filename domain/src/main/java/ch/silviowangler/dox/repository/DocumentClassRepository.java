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

package ch.silviowangler.dox.repository;

import ch.silviowangler.dox.domain.DocumentClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public interface DocumentClassRepository extends CrudRepository<DocumentClass, Long> {

    DocumentClass findByShortName(String shortName);

    @Query("select d from DocumentClass d where d.client.shortName in (?1)")
    List<DocumentClass> findAllByClients(List<String> clients);
}
