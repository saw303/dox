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
package ch.silviowangler.dox;

import ch.silviowangler.dox.document.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 02.08.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:application-dox.properties")
})
public class CoreConfiguration {


    @Bean
    public DocumentInspectorFactory documentInspectorFactory(MicrosoftWordDocumentInspector wordDocumentInspector,
                                                             PdfDocumentInspector pdfDocumentInspector,
                                                             TiffDocumentInspector tiffDocumentInspector) {

        DocumentInspectorFactoryImpl factory = new DocumentInspectorFactoryImpl();

        Map<String, DocumentInspector> map = new HashMap<>();

        map.put("application/pdf", pdfDocumentInspector);
        map.put("image/tiff", tiffDocumentInspector);
        map.put("application/msword", wordDocumentInspector);
        map.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", wordDocumentInspector);

        factory.setDocumentInspectorMap(map);
        factory.setFallbackDocumentInspector(new DummyDocumentInspector());
        return factory;
    }

    @Bean
    public DoxVersion doxVersion(@Value("${app.version}") String version) {
        return new DoxVersion(version);
    }
}
