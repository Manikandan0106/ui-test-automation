package com.framework.utils;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.framework.utils.HttpUtil;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.UUID;

public class JiraUtil {
    private static final Logger logger = LogManager.getLogger(JiraUtil.class);
    private static final String JIRA_URL = ConfigBaseClass.JIRA_URL;
    private static final String JIRA_EMAIL = ConfigBaseClass.JIRA_EMAIL;
    private static final String JIRA_TOKEN = ConfigBaseClass.JIRA_TOKEN;

    public static void updateTestExecutionSet(String testExecutionId, String testCaseKey, String status, String comment) {
        if (JIRA_URL == null || JIRA_EMAIL == null || JIRA_TOKEN == null) {
            logger.info("Jira credentials or URL not set in environment variables. Skipping Jira update.");
            return;
        }
        try {
            JSONObject body = new JSONObject();
            body.put("status", status);
            body.put("comment", comment);

            String url = JIRA_URL + testExecutionId + "/test/" + testCaseKey;
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            boolean success = HttpUtil.sendAuthenticatedRequest(
                url,
                "PUT",
                body.toString(),
                headers,
                JIRA_EMAIL,
                JIRA_TOKEN
            );
            if (!success) {
                logger.warn("Failed to update Jira test execution for " + testCaseKey);
            }
        } catch (Exception e) {
            logger.warn("Failed to update Jira test execution: " + e.getMessage(), e);
        }
    }

    /**
     * Attach a screenshot to a Jira issue or test execution.
     * @param issueKeyOrId The Jira issue key or test execution id
     * @param screenshotBytes The screenshot as a byte array
     * @param filename The filename for the attachment
     * @return true if upload succeeded, false otherwise
     */
    public static boolean attachScreenshotToJira(String issueKeyOrId, byte[] screenshotBytes, String filename) {
        if (JIRA_URL == null || JIRA_EMAIL == null || JIRA_TOKEN == null) {
            logger.info("Jira credentials or URL not set in environment variables. Skipping Jira attachment upload.");
            return false;
        }
        try {
            String boundary = "----WebKitFormBoundary" + UUID.randomUUID();
            String url = JIRA_URL + issueKeyOrId + "/attachments";
            String encodedAuth = Base64.getEncoder().encodeToString((JIRA_EMAIL + ":" + JIRA_TOKEN).getBytes(StandardCharsets.UTF_8));

            // Build multipart body
            String partHeader = "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "\"\r\n" +
                    "Content-Type: image/png\r\n\r\n";
            String partFooter = "\r\n--" + boundary + "--\r\n";
            byte[] headerBytes = partHeader.getBytes(StandardCharsets.UTF_8);
            byte[] footerBytes = partFooter.getBytes(StandardCharsets.UTF_8);
            byte[] multipartBody = new byte[headerBytes.length + screenshotBytes.length + footerBytes.length];
            System.arraycopy(headerBytes, 0, multipartBody, 0, headerBytes.length);
            System.arraycopy(screenshotBytes, 0, multipartBody, headerBytes.length, screenshotBytes.length);
            System.arraycopy(footerBytes, 0, multipartBody, headerBytes.length + screenshotBytes.length, footerBytes.length);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("X-Atlassian-Token", "no-check")
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                logger.info("Screenshot attached to Jira issue " + issueKeyOrId);
                return true;
            } else {
                logger.warn("Failed to attach screenshot to Jira: " + response.statusCode() + ": " + response.body());
                return false;
            }
        } catch (Exception e) {
            logger.warn("Failed to attach screenshot to Jira: " + e.getMessage(), e);
            return false;
        }
    }
} 