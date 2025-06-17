package com.here.wikvaya.java.interview;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class HttpServer extends Thread implements Server {

  private class Factory implements ThreadFactory {

    @Override
    public Thread newThread(final Runnable r) {
      final Thread thread = new Thread(r);
      thread.setName("HttpServerThread#" + serverNextThreadId);
      thread.setDaemon(true);
      return thread;
    }
  }

  HttpServer(final String name) throws IOException {
    // TODO: Ensure that this name is returned as server name to the client, just how?
    super(name);
    // TODO: What does this call mean and do we need to do it?
    setDaemon(false);
    serverConfig = config();
    serverExecutor = Executors.newCachedThreadPool(new Factory());
    serverSocket = new ServerSocket(serverConfig.port);
    // TODO: Why do we set this timeout?
    serverSocket.setSoTimeout(1000);
  }

  @Override
  public void run() {
    while (!stopServer.get()) {
      try {
        final Socket socket = serverSocket.accept();
        serverExecutor.submit(new HttpRequest(this, socket));
      } catch (SocketTimeoutException ignore) {
      } catch (Exception e) {
        e.printStackTrace(System.err);
      }
    }
    System.out.println("Goodbye, server down now");
  }

  public void stopServer() {
    stopServer.set(true);
    this.interrupt();
  }

  private final AtomicLong serverNextThreadId = new AtomicLong(1L);
  private final AtomicBoolean stopServer = new AtomicBoolean();
  private final ExecutorService serverExecutor;
  private final Config serverConfig;
  private final ServerSocket serverSocket;

  @Override
  public Config config() {
    // TODO: Parse the resource "service.cfg", return cached value.
    //       Optional: Add code to detect changes in the config, and to update the server.
    return new Config();
  }
}