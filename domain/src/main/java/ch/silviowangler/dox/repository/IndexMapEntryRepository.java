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

import ch.silviowangler.dox.domain.Document;
import ch.silviowangler.dox.domain.IndexMapEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.1
 * <div>
 * Date: 15.07.12 19:16
 * </div>
 */
public interface IndexMapEntryRepository extends CrudRepository<IndexMapEntry, Long> {

    @Query("select distinct d from IndexMapEntry i, Document d where i.document = d and d.client.shortName in (:clientShortNames) and (i.stringRepresentation = :upperCaseValue or i.document.originalFilename = :originalValue) order by d.creationDate desc")
    List<Document> findByValue(@Param("upperCaseValue") String upperCaseValue, @Param("originalValue") String originalValue, @Param("clientShortNames") List<String> clientShortNames);

    @Query("select distinct d from IndexMapEntry i, Document d where i.document = d and d.client.shortName in (?3) and (i.stringRepresentation like %?1 or i.document.originalFilename like %?2) order by d.creationDate desc")
    List<Document> findByValueLike(String upperCaseValue, String originalValue, List<String> clientShortNames);

    @Query("select distinct d from IndexMapEntry i, Document d where i.document = d and d.client.shortName in (:clientShortNames) and (i.stringRepresentation = :upperCaseValue or i.document.originalFilename = :originalValue) and i.document.userReference = :userReference  order by d.creationDate desc")
    List<Document> findByValueAndUserReference(@Param("upperCaseValue") String upperCaseValue, @Param("originalValue") String originalValue, @Param("userReference") String userReference, @Param("clientShortNames") List<String> clientShortNames);

    @Query("select distinct d from IndexMapEntry i, Document d where i.document = d and d.client.shortName in (?4) and (i.stringRepresentation like %?1 or i.document.originalFilename like %?2) and i.document.userReference = ?3  order by d.creationDate desc")
    List<Document> findByValueLikeAndUserReference(String upperCaseValue, String originalValue, String userReference, List<String> clientShortNames);

    List<IndexMapEntry> findByDocument(Document document);
}
