# Jira Integration Environment Variable Setup

To enable Jira integration in your test automation framework, set the following environment variables in your CI/CD pipeline or local environment.

## Required Environment Variables

- `JIRA_URL` — Jira REST API base URL (e.g., `https://your-domain.atlassian.net/rest/api/3/issue/`)
- `JIRA_EMAIL` — Jira user email (with API access)
- `JIRA_TOKEN` — Jira API token (generate from Atlassian account)
- `JIRA_EXECUTION_ID` — Jira Test Execution issue key for this test run (e.g., `TESTEXEC-100`)

---

## Local Setup

### Windows Command Prompt
```cmd
set JIRA_URL=https://your-domain.atlassian.net/rest/api/3/issue/
set JIRA_EMAIL=your-email@example.com
set JIRA_TOKEN=your-api-token
set JIRA_EXECUTION_ID=TESTEXEC-100
```

### PowerShell
```powershell
$env:JIRA_URL="https://your-domain.atlassian.net/rest/api/3/issue/"
$env:JIRA_EMAIL="your-email@example.com"
$env:JIRA_TOKEN="your-api-token"
$env:JIRA_EXECUTION_ID="TESTEXEC-100"
```

---

## Jenkins

- In job configuration, add under **Build Environment** or **Pipeline**:

| Name               | Value                                              |
|--------------------|----------------------------------------------------|
| JIRA_URL           | https://your-domain.atlassian.net/rest/api/3/issue/|
| JIRA_EMAIL         | your-email@example.com                             |
| JIRA_TOKEN         | your-api-token                                     |
| JIRA_EXECUTION_ID  | TESTEXEC-100                                       |

**Jenkinsfile (Pipeline):**
```groovy
environment {
    JIRA_URL = 'https://your-domain.atlassian.net/rest/api/3/issue/'
    JIRA_EMAIL = 'your-email@example.com'
    JIRA_TOKEN = credentials('JIRA_API_TOKEN_ID') // Use Jenkins credentials store!
    JIRA_EXECUTION_ID = 'TESTEXEC-100'
}
```

---

## GitHub Actions

```yaml
env:
  JIRA_URL: https://your-domain.atlassian.net/rest/api/3/issue/
  JIRA_EMAIL: ${{ secrets.JIRA_EMAIL }}
  JIRA_TOKEN: ${{ secrets.JIRA_TOKEN }}
  JIRA_EXECUTION_ID: TESTEXEC-100
```

---

## GitLab CI

```yaml
variables:
  JIRA_URL: "https://your-domain.atlassian.net/rest/api/3/issue/"
  JIRA_EMAIL: "your-email@example.com"
  JIRA_TOKEN: "$JIRA_TOKEN" # Store in GitLab CI/CD secrets
  JIRA_EXECUTION_ID: "TESTEXEC-100"
```

---

## Security Best Practices
- **Never commit secrets** (like `JIRA_TOKEN`) to your repository.
- Use your CI/CD’s **secret management** features for sensitive data.
- Rotate API tokens regularly.

---

## Testing the Setup
After setting the variables, run your tests. If the variables are set correctly, Jira integration will update the test execution in Jira. 