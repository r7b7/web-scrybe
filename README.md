![GitHub stars](https://img.shields.io/github/stars/r7b7/web-scrybe?style=social)
[![GitHub Forks](https://img.shields.io/github/forks/r7b7/web-scrybe.svg)]()
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)
[![Last Commit](https://img.shields.io/github/last-commit/r7b7/web-scrybe.svg)](https://github.com/r7b7/web-scrybe/commits/main)
[![Powered by Spring Boot](https://img.shields.io/badge/Powered%20by-Spring%20Boot-green)]()
[![Runs on Docker](https://img.shields.io/badge/Runs%20on-Docker-blue)]()
[![Wiki](https://img.shields.io/badge/Documentation-Wiki-blue)](https://github.com/r7b7/web-scrybe/wiki/Web%E2%80%90Scrybe-Wiki)



# web-scrybe

web-scrybe is an open-source SERP (Search Engine Results Page) scraping and social media web scraping tool built using Spring Boot. It provides a simple and efficient way to extract data from websites and integrate it into your applications.

## Features

- **Available SERP scraping Integrations**: Currently, Google, Bing and DuckDuckGo are supported. More features will be added subsequently.
- **Available Social Media Scraping Integrations**: Reddit Hot Topic.  More features will be added subsequently.
- **Easy-to-use API**: Developers can quickly integrate web scraping functionality into their applications using the provided API.
- **Scalable and Distributed**: The application is designed to be highly scalable and can be deployed in a distributed environment using Docker.
- **Unlimited Scraping**: The automation-based approach doesn't have the same rate limits or charges as Paid APIs, allowing users to scrape data at scale without facing throttling or downtime.
- **Docker and Docker Compose Support**: web-scrybe is designed to be easily deployable and scalable using Docker and Docker Compose. This makes it an ideal choice for quick prototyping, development, and production deployments.

## Getting Started

### Prerequisites

- Java 21 or higher
- Docker (optional, for running the application in a container)
- Reddit Account (optional)

### Installation

1. Clone the repository:

   ```bash
   [git clone https://github.com/your-username/web-scrybe.git](https://github.com/r7b7/web-scrybe.git)

2. Navigate to the project directory

3. Add Following Environment Variables (Not required if not planning to use Reddit API)
    REDDIT_CLIENT_ID, REDDIT_CLIENT_SECRET, USER_AGENT

   Environment variables can be set in runtime configurations, yaml or property files. 

   In Visual Studio Code, environment variables can be set inside launch.json of .vscode folder as shown below,
    ```bash
    [{
        "type": "java",
        "name": "Launch Java Program",
        "request": "launch",
        "mainClass": "com.r7b7.webscrybe.WebSearchApplication",
        "env": {
            "REDDIT_CLIENT_ID": "<YOUR_CLIENT_ID>",
            "REDDIT_CLIENT_SECRET": "<YOUR_CLIENT_SECRET>",
            "USER_AGENT": "<YOUR_USER_AGENT>"
        }
    }]

  Alternatively, Keys can be set during runtime as well - See Step 5, #2 for details.

  Reddit API keys can be generated from https://www.reddit.com/prefs/apps -> Create Another app Button -> Fill Details -> Copy generated key and secret.
  User-Agent Header can be set to a unique string similar to "redditdev scraper by x/MyRedditId"

4. Build App Using Maven:
    ```bash
    mvn clean package

5. Run App (Application can be run using any of the following options)
   1. In IDE - Use Run Option
   2. From Terminal
      - export Environment variables if not done already
         ```bash
         export REDDIT_CLIENT_ID=<CLIENT_ID>
         export REDDIT_CLIENT_SECRET=<YOUR_CLIENT_SECRET>
         export USER_AGENT="<YOUR_USER_AGENT>"
      - Run app using mvn
         ```bash
         mvn spring-boot:run
       
      - Alternatively, Run app as a jar . From the target directory within the project folder, run the following command
          ```bash
          target % java -jar web-scrybe-0.0.1-SNAPSHOT.jar

   3. In Docker
      - Run the following commands in terminal
          ```bash
          docker build -t web-scrybe .
          docker-compose up -d
      - Check logs
          ```bash
          docker-compose logs -f
          
6. Available APIs
   1. Reddit Search API
       ```bash
       http://localhost:8080/api/v1/reddit/hot?subreddit=ClaudeAI&limit=2
   2. Google Search API
       ```bash
       http://localhost:8080/api/v1/search?query=what%20is%20latest%20in%20AI&driver=GOOGLE
   3. Bing Search API
       ```bash
       http://localhost:8080/api/v1/search?query=what%20is%20latest%20in%20AI&driver=BING


7. Swagger Documentation
    ```bash
    http://localhost:8080/v3/api-docs

8. Stop Application
   1. Terminal
      Use Ctrl + C to stop a running application
      
   2. Docker
      1. To stop container, use the following command
         ```bash
         docker-compose down

      2. To remove container and image
          ```bash
          docker rm -f <container-name>
          docker rmi <image-name>

      3. If you don't know the container ID or name, list all running containers using:
          ```bash
          docker ps

# Contributing Guidelines
Contributions are welcome from the open-source community! Please read the contributing guidelines to get started.

To maintain code quality and ensure stability, please follow these guidelines before submitting a Pull Request (PR).

1. Fork and Clone the Repository
   Fork the repository on GitHub.
   Clone your fork locally
      ```bash
      git clone https://github.com/yourusername/yourproject.git
   
2. Code Formatting
   Ensure your code follows the standard Java code style. Use the built-in code formatting feature of your IDE (IntelliJ IDEA, Eclipse, or VS Code).

3. Run Tests Locally
   Make sure that all existing tests pass before submitting your PR:
      ```bash
      ./mvnw test

4. Run SonarQube Analysis (To run Sonarqube in local, please read the section - "Run Sonarqube Locally")
   Run SonarQube locally to check for code quality issues and ensure the code meets the standards.
      ```bash
          mvn clean verify sonar:sonar -Dsonar.login=<YOUR_TOKEN>
   
5. Ensure the build is stable

6. Commit Message Guidelines
   Use meaningful and concise commit messages. Follow this format:
      ```bash
      [Type]: Brief description
      
      Types can include:
      
      feat: New feature
      fix: Bug fix
      docs: Documentation changes
      test: Adding or updating tests
      refactor: Code refactoring
      
7. Creating a Pull Request
   Before creating a Pull Request, ensure that your branch is up to date with the main branch:


8. Code Review Process
   The PR will be reviewed by maintainers and other contributors. Please be patient and respond to any requested changes.
   
### Run Sonarqube Locally
1. Install Docker
2. Run the following command
   ```bash
   docker run -d --name sonarqube -p 9000:9000 sonarqube
3. Sonarqube server should be up and running at - http://localhost:9000
4. To Stop server, use this command
   ```bash
   docker stop sonarqube
5. To run the sonarqube locally, you would need to pass a login token for authentication. Follow these steps to generate the token from your local sonarqube server
   ```bash
   1. Go to your SonarQube instance at http://localhost:9000.
   2. Log in with your admin credentials (admin/admin by default).
   3. Navigate to My Account > Security > Generate Tokens.
   4. Enter a token name (e.g., my-project-token) and click Generate.

# Issues
If you encounter any issues, please open a [discussion](https://github.com/r7b7/r7b7.github.io/issues) or create an issue. We're here to help!

