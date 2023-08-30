package com.example.healthgateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.healthgateway.commonservice.ViewAuth;
import com.example.healthgateway.exception.CustomException;
import com.example.healthgateway.modeldto.CheckValidTokenDto;
import com.example.healthgateway.modeldto.RefreshTokenRequest;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;


    @Autowired
    ViewAuth viewAuth;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
        	
        	
        	
            if (validator.isSecured.test(exchange.getRequest())) {
            	
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                	
                String email = null;
        		String jwtToken = null;
        		boolean flag=false;
        		
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                	jwtToken = authHeader.substring(7);
                	
                	try {
                		RefreshTokenRequest refreshTokenRequest=new RefreshTokenRequest();
                		refreshTokenRequest.setRefreshToken(jwtToken);
                		System.out.println("kl");
                		
                		email = restTemplate.postForObject("http://localhost:8005//authToken//getUserName",refreshTokenRequest,String.class);
                		
//                		email = webClientBuilder.build()
//                			.post()
//                			.uri("http://authservice//getUserName",refreshTokenRequest)
//                			.retrieve()
//                			.bodyToMono(String.class)
//                			.block();
                		
                		
//                		email = viewAuth.getUserName(refreshTokenRequest);
                		System.out.println("bye");
                		if (email != null ) {
                        	
                        	CheckValidTokenDto checkValidTokenDto=new CheckValidTokenDto();
                            checkValidTokenDto.setEmail(email);
                            checkValidTokenDto.setJwtToken(jwtToken);
                            
                            flag = restTemplate.postForObject("http://localhost:8005//authToken//checkValidToken",checkValidTokenDto,Boolean.class);
                            
//                            flag = webClientBuilder.build()
//	                			.post()
//	                			.uri("http://authservice//checkValidToken",checkValidTokenDto)
//	                			.retrieve()
//	                			.bodyToMono(Boolean.class)
//	                			.block();
                            
//                            flag= viewAuth.checkValidToken(checkValidTokenDto);
                            System.out.println("JWT token");
                        	
                        	if (!flag) {
                        		throw new RuntimeException("Not Authorized");
                        	}
                        	
                        } 
                		
        			} catch (IllegalArgumentException e) {
        				System.out.println("Unable to get JWT Token");
        			} catch (Exception e) {
        				e.printStackTrace();
        				System.out.println("JWT Token has expired");
        				throw new CustomException("unauhorized");
        			}
                	
                }else {
        			System.out.println("JWT token does not start with Bearer");
        		}
                
                
                
                
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
