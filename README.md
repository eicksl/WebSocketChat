# Java Spring-Boot WebSocket Chat
A chat room application implementation using WebSocket.

## Background
WebSocket is a communication protocol that makes it possible to establish a persistent two-way communication channel between a
server and a client.

## Setup
### Installing GeckoDriver
You will need to download the appropriate [GeckoDriver binary](https://github.com/mozilla/geckodriver/releases) for your OS and place it in the project's root directory. If the filename has an OS-specific extension such as .exe, you will also need to edit the `GECKO_DRIVER_FILENAME` constant within the `WebIntegrationTest` class.

### Build and run the application
From the project's root directory, first run `mvn clean install`. This will download dependencies, compile everything, run the Selenium tests, and then package the project. You can then execute the jar with `mvn spring-boot:run` which will start the app.

### Access the app in the browser
Use the following URL: http://localhost:3000
