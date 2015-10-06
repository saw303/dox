package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.VersionService;
import ch.silviowangler.dox.web.filters.DoxInterceptor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Silvio Wangler on 06/10/15.
 */
@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private VersionService versionService;
    @Autowired
    private DocumentService documentService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");

        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(new DeviceResolverHandlerInterceptor());

        DoxInterceptor doxInterceptor = new DoxInterceptor();
        doxInterceptor.setDocumentService(documentService);
        doxInterceptor.setVersionService(versionService);
        registry.addInterceptor(doxInterceptor);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        Map<String, MediaType> mediaTypes = new HashMap<>();

        mediaTypes.put("html", MediaType.TEXT_HTML);
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);

        configurer.favorParameter(true)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.TEXT_HTML)
                .useJaf(false)
                .mediaTypes(mediaTypes);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.definition");
    }

    @Bean
    public MarshallingHttpMessageConverter marshallingHttpMessageConverter(XStreamMarshaller marshaller) {
        MarshallingHttpMessageConverter messageConverter = new MarshallingHttpMessageConverter();
        messageConverter.setMarshaller(marshaller);
        messageConverter.setUnmarshaller(marshaller);
        return messageConverter;
    }

    @Bean
    public XStreamMarshaller xstreamMarshaller() {
        return new XStreamMarshaller();
    }

    @Bean
    public Jackson2ObjectMapperFactoryBean objectMapper() {
        Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();

        bean.setSimpleDateFormat("yyyy-MM-dd");
        bean.setIndentOutput(true);

        // http://stackoverflow.com/questions/13700853/jackson2-json-iso-8601-date-from-jodatime-in-spring-3-2rc1
        bean.setModules(Arrays.<Module>asList(new JodaModule()));

        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        //registry.enableContentNegotiation(new TilesView()/*, new JstlView()*/);

        registry.tiles().cache(false);
        registry.jsp("/WEB-INF/partials/", ".jsp");
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        return tilesConfigurer;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
