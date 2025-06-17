package com.here.wikvaya.java.interview;

import java.io.IOException;

public class App {

  public static void main(String[] args) throws IOException {
    // TODO: Run the server and then try this URL in your browser:
    //       http://localhost:8080/info
    //       Is there a way to stop the server via browser?
    //       Find out (and implement) the two tasks
    final HttpServer server = new HttpServer("Java-Interview/1.0");
    server.start();
    System.out.println("Server up and running!");
  }

}