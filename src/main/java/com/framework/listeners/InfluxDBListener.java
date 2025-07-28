package com.framework.listeners;

import org.testng.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.framework.utils.ConfigBaseClass;
import com.framework.utils.HttpUtil;
import java.util.HashMap;
import java.util.Map;

public class InfluxDBListener implements ITestListener, ISuiteListener {

    private int passedCount = 0;
    private int failedCount = 0;
    private int skippedCount = 0;

    private static final String INFLUX_URL = ConfigBaseClass.INFLUX_URL;
    private static final String USERNAME = ConfigBaseClass.INFLUX_USERNAME;
    private static final String PASSWORD = ConfigBaseClass.INFLUX_PASSWORD;
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(InfluxDBListener.class);

    private static void sendToInflux(String body) {
        // Skip if InfluxDB is not configured
        if (INFLUX_URL == null || INFLUX_URL.isEmpty() || INFLUX_URL.contains("localhost")) {
            logger.info("InfluxDB not configured or running locally, skipping metrics upload");
            return;
        }
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            boolean success = HttpUtil.sendAuthenticatedRequest(
                INFLUX_URL,
                "POST",
                body,
                headers,
                USERNAME,
                PASSWORD
            );
            if (success) {
                logger.info("Successfully sent metrics to InfluxDB");
            } else {
                logger.warn("Failed to send metrics to InfluxDB");
            }
        } catch (Exception e) {
            logger.warn("Failed to send data to InfluxDB (non-blocking): " + e.getMessage());
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
        String buildId = ConfigBaseClass.getEnv("BUILD_ID") != null ? ConfigBaseClass.getEnv("BUILD_ID") : "local";
        String env = ConfigBaseClass.getConfig("env") != null ? ConfigBaseClass.getConfig("env") : "qa";

        String data = String.format(
            "suite_summary,suite=%s,env=%s,build=%s total=%d,passed=%d,failed=%d,skipped=%d",
            suite.getName(), env, buildId, total, passedCount, failedCount, skippedCount
        );

        sendToInflux(data);
    }
} 