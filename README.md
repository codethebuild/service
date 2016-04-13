# Service (StickyNote)

This project provides Restful services using Spring Boot.


## Getting Started

To get you started you can simply clone the service repository.

### Prerequisites

To setup a SprintBoot and run the application successfully we need to follow below steps:

* Setup Git
* Setup Gradle
* Setup Java 1.8
* Setup Mongodb


### Run the Application
Steps:

1. Run the mongodb from command prompt. `docker run -d -p 27017:27017 mongo`
2. Add an entry to your hosts file. `<ip-address-docker-engine> mongodb`
3. Build and run the Gradle from command prompt. `stickynote-service $ gradlew bootRun`
4. To test if Spring Boot application  is started, just hit the URL `localhost:8080/notes` which should display list of notes in json format.


## Tasks (example: test)
Please run `gradlew tasks` to check all tasks available.
To execute a task, run command `gradlew <task name>`.
