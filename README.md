# UI Test Automation Framework

## Overview
This project is a Maven-based Selenium/TestNG UI automation framework with Allure reporting, Log4j2 logging, and robust configuration management. It supports parallel execution, retry logic, and is CI/CD ready for Jenkins.

---

## Project Structure
- `src/main/java` - Main framework code (pages, utils, listeners)
- `src/test/java` - Test classes
- `config/` - Properties files for environment/configuration
- `suites/` - TestNG suite XML files (for different test groups)

---

## Running Tests

### 1. **Local Run (Default Suite)**
```bash
mvn test
```
- Runs the default suite: `suites/suite-cart.xml`

### 2. **Run a Specific Suite (Local or Jenkins)**
Specify the suite name (without folder or `.xml`):
```bash
mvn test -DsuiteName=suite-simple-retry
```
- This runs `suites/suite-simple-retry.xml`

### 3. **Override Configuration (Local or Jenkins)**
You can override any config variable at runtime:
```bash
mvn test -DsuiteName=suite-cart -Denv=qa -DBROWSER=firefox -DPASSWORD=jenkinspass -DRETRY_COUNT=3 -DJIRA_TOKEN=xxxx -DJIRA_EXECUTION_ID=yyy -DINFLUX_PASSWORD=zzz
```

### 4. **Jenkins Usage**
- Add Jenkins parameters for any of the following:
  - `suiteName` (e.g., `suite-cart`)
  - `env`, `BROWSER`, `PASSWORD`, `RETRY_COUNT`, `JIRA_TOKEN`, `JIRA_EXECUTION_ID`, `INFLUX_PASSWORD`
- Jenkins build step:
  ```bash
  mvn test -DsuiteName=${suiteName} -Denv=${env} -DBROWSER=${BROWSER} -DPASSWORD=${PASSWORD} -DRETRY_COUNT=${RETRY_COUNT} -DJIRA_TOKEN=${JIRA_TOKEN} -DJIRA_EXECUTION_ID=${JIRA_EXECUTION_ID} -DINFLUX_PASSWORD=${INFLUX_PASSWORD}
  ```
- The framework will pick up these values automatically.

---

## Configuration Priority
1. **Jenkins/CLI system property** (e.g., `-Denv=qa`)
2. **config/local.properties**
3. **config/config.properties**
4. **config/dev.properties** (or other env file)
5. **Default in pom.xml**

---

## Adding/Modifying Suites
- All suite files are in the `suites/` folder.
- To add a new suite, copy an existing XML, change the `<classes>` section, and save as `suites/your-suite.xml`.
- Run with: `mvn test -DsuiteName=your-suite`

---

## Allure Reports
After running tests, generate and view the Allure report:
```bash
allure serve allure-results
```

---

## Notes
- Retry logic, parallel execution, and listeners are all managed globally via suite files and configuration.
- All configuration is centralized and can be overridden at runtime for maximum flexibility.

---

For any questions or contributions, please contact the project maintainer.