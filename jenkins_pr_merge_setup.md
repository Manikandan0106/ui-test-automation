# Jenkins Setup for GitHub PR Merge Automation and Allure Reporting

This setup guide outlines the steps to configure Jenkins to trigger a job when a pull request is merged into the `main` branch on GitHub, along with recommended plugins, job parameters, credentials, and Allure reporting integration.

---

## 1. Prerequisites

- Jenkins installed and accessible (tested on Jenkins 2.440+)
- Jenkins user with admin privileges
- GitHub repository access
- GitHub personal access token (with `repo`, `admin:repo_hook` scopes)

---

## 2. Required Jenkins Plugins

Install these plugins from **Manage Jenkins > Plugins**:

- GitHub Integration Plugin
- Git Plugin
- Pipeline
- Allure Jenkins Plugin
- Credentials Binding Plugin
- Parameterized Trigger Plugin

---

## 3. GitHub Webhook Configuration

### Step-by-step:

1. Navigate to your GitHub repository.
2. Go to **Settings > Webhooks > Add webhook**:
   - Payload URL: `http://<jenkins-host>/github-webhook/`
   - Content type: `application/json`
   - Events: **Just the push event** (or choose "Let me select individual events" > `Pull requests` and `Pushes`)
3. Save webhook.

Ensure Jenkins global security allows "GitHub hook trigger for GITScm polling".

---

## 4. Jenkins Credentials Setup

1. Go to **Manage Jenkins > Credentials > (Global)**.
2. Add credentials:
   - **Kind**: Username with password
   - **ID**: `git-credentials`
   - **Username**: GitHub username
   - **Password**: GitHub personal access token

---

## 5. Jenkins Pipeline Job Configuration

### Create a new pipeline job:

1. Go to **New Item > Pipeline**
2. Name: `build-on-pr-merge`
3. Enable: **GitHub hook trigger for GITScm polling**
4. Add parameters:
   - `Choice Parameter`: `ENVIRONMENT` with values: `dev`, `qa`, `prod`
   - `String Parameter`: `BUILD_LABEL` (Optional label or tag)

### Pipeline Script (Declarative Example):

```groovy
pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'qa', 'prod'], description: 'Select Environment')
        string(name: 'BUILD_LABEL', defaultValue: '', description: 'Build label or tag')
    }

    environment {
        GIT_CREDENTIALS_ID = 'git-credentials'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: env.GIT_CREDENTIALS_ID, url: 'https://github.com/your-org/your-repo.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                bat 'build.bat %ENVIRONMENT%'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'run-tests.bat'
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
            }
        }
    }

    post {
        always {
            script {
                currentBuild.displayName = "#${BUILD_NUMBER} - ${params.ENVIRONMENT}"
            }
        }
    }
}
```

---

## 6. Windows Batch Command Examples

- `build.bat`:

```bat
@echo off
echo Building project for environment: %1
:: your build commands here
```

- `run-tests.bat`:

```bat
@echo off
echo Running tests...
:: your test commands here
```

---

## 7. Allure Report Configuration

1. Ensure test framework (e.g., TestNG, JUnit) generates `allure-results` folder.
2. In **Manage Jenkins > Configure System**, find **Allure Report** section:
   - Add path: `allure-results`
   - Optionally add build-specific metadata

---

## 8. Enhancements (Optional)

- Add Slack/email notifications
- Archive artifacts
- Tag Git commits with build label
- Trigger downstream deployments

---

## Notes

- Use Jenkins Blue Ocean for better UI
- Use `Pipeline: Multibranch` for more advanced workflows
- Rotate old builds or configure retention policy to manage storage

---

This setup enables CI automation via GitHub PR merges, parameterized jobs, and real-time test reporting using Allure. Add this file to your repository for reference.

