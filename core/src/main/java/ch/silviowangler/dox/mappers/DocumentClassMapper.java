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
package ch.silviowangler.dox.mappers;

import ch.silviowangler.dox.api.*;
import ch.silviowangler.dox.api.rest.DocumentClass;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;


/**
 * @author Silvio Wangler
 */
@Mapper(config = MappingConfig.class)
public interface DocumentClassMapper {

    @Mappings({
            @Mapping(target = "client", source = "client.shortName"),
            @Mapping(target = "attributes", ignore = true),
            @Mapping(target = "translation", ignore = true)
    })
    DocumentClass toDocumentClassRestApi(ch.silviowangler.dox.domain.DocumentClass domain);

    @Mappings({
            @Mapping(target = "client", source = "client.shortName"),
            @Mapping(target = "translation", ignore = true)
    })
    ch.silviowangler.dox.api.DocumentClass toDocumentClassApi(ch.silviowangler.dox.domain.DocumentClass documentClass);

    @Mappings({
            @Mapping(target = "modifiable", source = "updateable"),
            @Mapping(target = "translation", ignore = true)
    })
    Attribute toAttributeApi(ch.silviowangler.dox.domain.Attribute domain);

    SortedSet<Attribute> toAttributeApi(List<ch.silviowangler.dox.domain.Attribute> attributes);

    @Mappings({
            @Mapping(target = "translation", ignore = true)
    })
    Domain toDomainApi(ch.silviowangler.dox.domain.Domain domain);

    AttributeDataType toAttributeDataTypeApi(ch.silviowangler.dox.domain.AttributeDataType domain);

    default String mapToString(TranslatableKey translatableKey) {
        return translatableKey.getKey();
    }

    default Object mapToObject(DescriptiveIndex descriptiveIndex) {
        return descriptiveIndex.getValue();
    }

    @MapMapping(keyTargetType = String.class, valueTargetType = Object.class)
    Map<String, Object> toEntityMap(Map<TranslatableKey, DescriptiveIndex> indices);
}
