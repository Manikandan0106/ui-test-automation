package com.framework.utils;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JiraUtil {
    private static final String JIRA_URL = System.getenv("JIRA_URL");
    private static final String JIRA_EMAIL = System.getenv("JIRA_EMAIL");
    private static final String JIRA_TOKEN = System.getenv("JIRA_TOKEN");

    public static void updateTestExecutionSet(String testExecutionId, String testCaseKey, String status, String comment) {
        if (JIRA_URL == null || JIRA_EMAIL == null || JIRA_TOKEN == null) {
            System.err.println("Jira credentials or URL not set in environment variables.");
            return;
        }
        try {
            JSONObject body = new JSONObject();
            body.put("status", status);
            body.put("comment", comment);

            String auth = Base64.getEncoder().encodeToString((JIRA_EMAIL + ":" + JIRA_TOKEN).getBytes(StandardCharsets.UTF_8));
            String url = JIRA_URL + testExecutionId + "/test/" + testCaseKey;

            Request.put(url)
                    .addHeader("Authorization", "Basic " + auth)
                    .addHeader("Content-Type", "application/json")
                    .bodyString(body.toString(), ContentType.APPLICATION_JSON)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 