package ch.silviowangler.dox.web.rest.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * @author Silvio Wangler
 * @since 0.3
 *
 * @link http://stackoverflow.com/questions/13700853/jackson2-json-iso-8601-date-from-jodatime-in-spring-3-2rc1
 */
public class DoxObjectMapper extends ObjectMapper {

    public DoxObjectMapper() {
        super();
        registerModule(new JodaModule());
    }
}
