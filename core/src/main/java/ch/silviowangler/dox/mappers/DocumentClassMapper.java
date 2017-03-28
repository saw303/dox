package ch.silviowangler.dox.mappers;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import ch.silviowangler.dox.api.Attribute;
import ch.silviowangler.dox.api.AttributeDataType;
import ch.silviowangler.dox.api.DescriptiveIndex;
import ch.silviowangler.dox.api.Domain;
import ch.silviowangler.dox.api.TranslatableKey;
import ch.silviowangler.dox.api.rest.DocumentClass;


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

  default Object mapToObject(DescriptiveIndex descriptiveIndex) { return descriptiveIndex.getValue(); }

  @MapMapping(keyTargetType = String.class, valueTargetType = Object.class)
  Map<String, Object> toEntityMap(Map<TranslatableKey, DescriptiveIndex> indices);
}
