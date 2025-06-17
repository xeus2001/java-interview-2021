package com.here.wikvaya.java.interview;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest implements Runnable, Tasks {

  HttpRequest(final HttpServer server, final Socket socket) {
    if (server == null || socket == null) {
      throw new NullPointerException();
    }
    this.server = server;
    this.socket = socket;
  }

  private final HttpServer server;
  private final Socket socket;
  InputStream in;
  OutputStream out;
  String method;
  String uri;
  String protocol;
  HashMap<String, String> header;
  byte[] bytes;
  int readIndex;
  int writeIndex;
  boolean done;
  boolean responseSent;

  private String readLine() {
    for (int i = readIndex; i < writeIndex; i++) {
      if (bytes[i] == '\n') {
        int len = i - readIndex;
        if (bytes[i - 1] == '\r') {
          len--;
        }
        final String text = new String(bytes, readIndex, len, StandardCharsets.UTF_8);
        readIndex = i + 1; // TODO: Why +1?
        return text;
      }
    }
    return null;
  }

  @Override
  public int[] generateRandomData(int min, int max, int size, boolean ascending) {
    // TODO: Implement me so that it always return the same instance
    //       BONUS: It should be cached thread safe.
    //       BONUS: Use a lock-free algorithm to ensure that all threads see the same instance.
    return new int[]{1, 5, 7, 9, 11, 15, 200, 500, 1239};
  }

  @Override
  public List<Map<Integer, Integer>> count(String s) {
    // TODO: Implement me as defined in the documentation.
    return null;
  }

  @Override
  public int indexOf(int[] haystack, int needle) {
    // TODO: Implement me as defined in the documentation.
    return 0;
  }

  private void processRequest() throws IOException {
    if ("GET".equals(method) && uri.equals("/info")) {
      sendResponse(200, "OK", "Hello World!");
    } else if ("GET".equals(method) && uri.equals("/quit")) {
      sendResponse(200, "OK", "Good bye!");
      server.stopServer();
    } else if ("GET".equals(method) && uri.startsWith("/count")) {
      // TODO: Implement count
      sendResponse(501, "Not Implemented", "We need to implement count");
    } else if ("GET".equals(method) && uri.startsWith("/find")) {
      // TODO: Implement find (indexOf)
      sendResponse(501, "Not Implemented", "We need to implement find using indexOf method");
    }
    if ("GET".equals(method) && uri.startsWith("/status")) {
      // TODO: Implement some overview of sever statistics, so:
      //      - number of currently idle threads
      //      - number of running threads
      //      - memory consumption, free memory
      //      - whatever you find useful!
      sendResponse(501, "Not Implemented", "No statistics");
    } else {
      sendResponse(404, "Not found", "Invalid resource requested");
    }
  }

  private void processBytes() throws IOException {
    // Hint: https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
    String line = readLine();
    if (line != null) {
      if (method == null) {
        // TODO: What is done here?
        final String[] requestParts = line.split(" ");
        method = requestParts[0].toUpperCase();
        uri = requestParts[1];
        protocol = requestParts[2].toUpperCase();
        line = readLine();
      }
      while (line != null && line.length() > 0) {
        if (header == null) {
          header = new HashMap<>();
        }
        // TODO: What is wrong with this naive implementation?
        final String[] headerParts = line.split(":");
        header.put(headerParts[0].trim(), headerParts[1].trim());
        line = readLine();
      }
      if (line != null) {
        processRequest();
      }
    }
  }

  @Override
  public void run() {
    try {
      out = socket.getOutputStream();
      in = socket.getInputStream();
      bytes = new byte[16384];
      int read;
      while (!done) {
        read = in.read(bytes, writeIndex, bytes.length - writeIndex);
        if (read > 0) {
          writeIndex += read;
        }
        processBytes();
        // This design has a flaw that any line being longer than the buffer leads to an endless loop!
        if (readIndex >= 1024) {
          System.arraycopy(bytes, readIndex, bytes, 0, readIndex);
          writeIndex -= readIndex;
          readIndex = 0;
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.err);
      if (!responseSent) {
        try {
          sendResponse(504, "Internal Server Error", e.getMessage());
        } catch (IOException ioException) {
          ioException.printStackTrace(System.err);
        }
      }
    } finally {
      try {
        out.flush();
        out.close();
        socket.close();
      } catch (IOException e) {
        e.printStackTrace(System.err);
      }
    }
  }

  private void sendResponse(final int code, final String reasonPhrase, final String body) throws IOException {
    final StringBuilder sb = new StringBuilder();
    sb.append(protocol);
    sb.append(" ");
    sb.append(code);
    sb.append(" ");
    sb.append(reasonPhrase);
    sb.append("\r\n");
    sb.append("Server: Experimental/1.0\r\n");
    sb.append("Content-type: text/plain\r\n");
    sb.append("Connection: close\r\n");
    sb.append("\r\n");
    sb.append(body);
    out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
    responseSent = true;
    done = true;
  }
}