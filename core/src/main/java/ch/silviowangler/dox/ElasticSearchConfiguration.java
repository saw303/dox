package ch.silviowangler.dox;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 02.08.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
@Configuration
public class ElasticSearchConfiguration {

    @Bean(destroyMethod = "close")
    public Client elasticSearchClient() {
        Node node = NodeBuilder.nodeBuilder()
                .settings(ImmutableSettings.settingsBuilder().put("http.enabled", false))
                .client(true)
                .node();

        return node.client();
    }
}
