package com.example.healthgateway.config;

import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.form.FormEncoder;

@Configuration
public class FeignConfiguration {
	
//	@Autowired
//	private ObjectFactory<HttpMessageConverters> messageConverters;
	
//	@Bean
//	public Decoder decoder() {
//		return new JacksonDecoder();
//	}
	
	@Bean
	public Encoder encoder() {
		return new JacksonEncoder();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
