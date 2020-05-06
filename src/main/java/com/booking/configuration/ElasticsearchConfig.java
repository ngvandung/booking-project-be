/**
 * 
 */
package com.booking.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author ddung
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.booking.repository.elasticsearch")
public class ElasticsearchConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(environment.getRequiredProperty("host.elasticsearch"), environment.getRequiredProperty("host.elasticsearch.temp")).build();

		return RestClients.create(clientConfiguration).rest();
	}
}
