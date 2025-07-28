package com.framework.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class HttpUtil {
    private static final Logger logger = LogManager.getLogger(HttpUtil.class);

    /**
     * Send an authenticated HTTP request (POST or PUT) with custom headers and body.
     * @param url The endpoint URL
     * @param method "POST" or "PUT"
     * @param body The request body (JSON or plain text)
     * @param headers Map of header key-value pairs
     * @param username Optional username for Basic Auth (null if not needed)
     * @param password Optional password for Basic Auth (null if not needed)
     * @return true if request succeeded, false otherwise
     */
    public static boolean sendAuthenticatedRequest(String url, String method, String body, Map<String, String> headers, String username, String password) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url));

            // Add method and body
            if ("POST".equalsIgnoreCase(method)) {
                builder.POST(HttpRequest.BodyPublishers.ofString(body));
            } else if ("PUT".equalsIgnoreCase(method)) {
                builder.PUT(HttpRequest.BodyPublishers.ofString(body));
            } else {
                logger.warn("Unsupported HTTP method: " + method);
                return false;
            }

            // Add headers
            if (headers != null) {
                headers.forEach(builder::header);
            }

            // Add Basic Auth if provided
            if (username != null && password != null) {
                String encodedAuth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                builder.header("Authorization", "Basic " + encodedAuth);
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                logger.info("HTTP request to " + url + " succeeded with status " + response.statusCode());
                return true;
            } else {
                logger.warn("HTTP request to " + url + " failed with status " + response.statusCode() + ": " + response.body());
                return false;
            }
        } catch (Exception e) {
            logger.warn("HTTP request to " + url + " failed: " + e.getMessage(), e);
            return false;
        }
    }
} 