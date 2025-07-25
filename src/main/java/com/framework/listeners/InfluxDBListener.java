package com.framework.listeners;

import org.testng.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class InfluxDBListener implements ITestListener, ISuiteListener {

    private int passedCount = 0;
    private int failedCount = 0;
    private int skippedCount = 0;

    private static final String INFLUX_URL = "http://localhost:8086/write?db=testmetrics";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpass";

    private static void sendToInflux(String body) {
        try {
            String encodedAuth = Base64.getEncoder()
                    .encodeToString((USERNAME + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(INFLUX_URL))
                    .header("Authorization", "Basic " + encodedAuth)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedCount++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedCount++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedCount++;
    }

    @Override
    public void onFinish(ISuite suite) {
        int total = passedCount + failedCount + skippedCount;

        // Read Jenkins build & environment info
        String buildId = System.getenv("BUILD_ID") != null ? System.getenv("BUILD_ID") : "local";
        String env = System.getProperty("env", "qa");

        String data = String.format(
            "suite_summary,suite=%s,env=%s,build=%s total=%d,passed=%d,failed=%d,skipped=%d",
            suite.getName(), env, buildId, total, passedCount, failedCount, skippedCount
        );

        sendToInflux(data);
    }
} 