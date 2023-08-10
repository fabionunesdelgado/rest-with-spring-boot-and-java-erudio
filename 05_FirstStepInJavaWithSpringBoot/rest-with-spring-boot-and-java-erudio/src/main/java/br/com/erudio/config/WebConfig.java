package br.com.erudio.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erudio.serialization.converter.YamlJackson2HttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

	// Habilita CORS de maneira global, buscando os dados no application.yaml
	@Value("${cors.originPatterns:http://localhost:8080}")
	private String corsOriginPatterns = "";
	
	// Altera o MessageConverter para o padrão yaml 
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}

	// Habilita CORS de maneira global, buscando os dados do application.yaml
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**") // Todas as rotas da API
			//.allowedMethods("GET", "POST", "PUT") // Caso queira segregar por verbs
			.allowedMethods("*") // Todos os métodos
			.allowedOrigins(allowedOrigins) // Todas as origens que poderão consumir as APIs
			.allowCredentials(true); //Possibilita autenticação
	}

	// Configuração do Content Negotiation
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		// Query Param
		/*configurer.favorParameter(true)
			.parameterName("mediaType")
			.ignoreAcceptHeader(true)
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);*/
		
		// Header Param
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
	}	
	
}
