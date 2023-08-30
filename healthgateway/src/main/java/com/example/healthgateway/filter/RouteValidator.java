package com.example.healthgateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/authToken/signup",
            "/authToken/authenticate",
            "/authToken/verify",
            "/authToken/sendOTP",
            "/authToken/verifyOTP",
            "/authToken/resetPassword",
            "/authToken/refresh/token",
            "/authToken/logout",
            "/authToken/getUserName",
            "/authToken/checkValidToken",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}