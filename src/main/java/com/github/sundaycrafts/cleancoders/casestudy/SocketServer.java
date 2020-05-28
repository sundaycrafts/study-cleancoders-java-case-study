package com.github.sundaycrafts.cleancoders.casestudy;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class SocketServer {
  private int port;
  private SocketService service;
  private boolean running;
  private ServerSocket serverSocket;

  public SocketServer(int port, SocketService service) throws Exception {
    this.port = port;
    this.service = service;
    this.serverSocket = new ServerSocket(port);
  }

  public int getPort() {
    return port;
  }

  public SocketService getService() {
    return service;
  }

  public void start() throws IOException  {
    var executor = Executors.newFixedThreadPool(4);
    var connectionHandler = new Runnable() {
      public void run() {
        try {
          var serviceSocket = serverSocket.accept();
        } catch (IOException e) {
          if (running)
            e.printStackTrace();
        }
      }
    };
    executor.execute(connectionHandler);

    running = true;
  }

  public boolean isRunning() {
    return running;
  }

  public void stop() throws IOException {
    serverSocket.close();
    running = false;
  }
}
