package ch.silviowangler.dox;

import ch.silviowangler.dox.document.*;
import ch.silviowangler.dox.hibernate.Mysql5InnoDBBitBooleanDialect;
import com.googlecode.flyway.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import static ch.silviowangler.dox.DocumentServiceImpl.CACHE_DOCUMENT_COUNT;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * Created on 02.08.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
@Configuration
@EnableTransactionManagement
@EnableCaching
@ComponentScan({"ch.silviowangler.dox"})
@EnableAspectJAutoProxy
public class CoreConfiguration {

    @Bean(name = "dataSource")
    @Profile("prod")
    public JndiObjectFactoryBean dataSourceProd() {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/dox");
        return bean;
    }

    @Bean(name = "dataSource")
    @Profile("local")
    public DataSource dataSourceLocal() {

        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(false)
                .addScript("ddl_h2_create.sql")
                .addScripts("install.sql")
                .build();
    }

    @Bean(name = "entityManagerFactory")
    @Profile("dev")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryTest(DataSource dataSource) {

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("HibernateJpaVendorAdapter", "validate");

        return createEntityManager(jpaProperties, dataSource);
    }

    @Bean(name = "entityManagerFactory")
    @Profile("prod")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryProd(DataSource dataSource) {

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("HibernateJpaVendorAdapter", "validate");
        jpaProperties.put("hibernate.dialect", Mysql5InnoDBBitBooleanDialect.class.getName());

        return createEntityManager(jpaProperties, dataSource);
    }

    private LocalContainerEntityManagerFactoryBean createEntityManager(Map<String, Object> jpaProperties, DataSource dataSource) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setShowSql(false);

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

        bean.setDataSource(dataSource);
        bean.setPackagesToScan("ch.silviowangler.dox.domain");
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setJpaPropertyMap(jpaProperties);
        return bean;
    }

    @Bean
    @Profile("prod")
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
        return flyway;
    }

    @Bean
    public DocumentInspectorFactory documentInspectorFactory(DummyDocumentInspector dummyDocumentInspector,
                                                             PdfDocumentInspector pdfDocumentInspector,
                                                             TiffDocumentInspector tiffDocumentInspector,
                                                             MicrosoftWordDocumentInspector wordDocumentInspector) {

        DocumentInspectorFactoryImpl factory = new DocumentInspectorFactoryImpl();

        Map<String, DocumentInspector> map = new HashMap<>();

        map.put("application/pdf", pdfDocumentInspector);
        map.put("image/tiff", tiffDocumentInspector);
        map.put("application/msword", wordDocumentInspector);
        map.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", wordDocumentInspector);

        factory.setDocumentInspectorMap(map);
        factory.setFallbackDocumentInspector(dummyDocumentInspector);
        return factory;
    }

    @Bean
    public CacheManager cacheManager(ConcurrentMapCacheFactoryBean bookCache) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        HashSet<Cache> caches = new HashSet<>();
        caches.add(bookCache.getObject());

        simpleCacheManager.setCaches(caches);

        return simpleCacheManager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean bookCache() {
        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CACHE_DOCUMENT_COUNT);
        return cacheFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public Properties mimeTypes() throws IOException {
        Properties properties = new Properties();

        ClassPathResource classPathResource = new ClassPathResource("mimeTypes.properties");
        properties.load(classPathResource.getInputStream());

        return properties;
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();

        configurer.setLocation(new ClassPathResource("application.properties"));
        configurer.setIgnoreResourceNotFound(false);
        configurer.setIgnoreUnresolvablePlaceholders(false);
        configurer.setOrder(1);

        return configurer;
    }

    @Bean
    public DoxVersion doxVersion(@Value("${app.version}") String version) {
        return new DoxVersion(version);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
}
