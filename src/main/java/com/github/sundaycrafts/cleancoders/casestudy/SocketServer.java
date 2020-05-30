package com.github.sundaycrafts.cleancoders.casestudy;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketServer {
  private int port;
  private SocketService service;
  private boolean running;
  private ServerSocket serverSocket;
  private ExecutorService executor;

  public SocketServer(int port, SocketService service) throws Exception {
    this.port = port;
    this.service = service;
    this.serverSocket = new ServerSocket(port);
    this.executor = Executors.newFixedThreadPool(4);
  }

  public int getPort() {
    return port;
  }

  public SocketService getService() {
    return service;
  }

  public void start() {
    var connectionHandler = new Runnable() {
      public void run() {
        try {
          var serviceSocket = serverSocket.accept();
          service.serve(serviceSocket);
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

  public void stop() throws IOException, InterruptedException {
    executor.awaitTermination(500, TimeUnit.MICROSECONDS);
    serverSocket.close();
    running = false;
  }

  public void setService(SocketService socketService) {
    service = socketService;
  }
}
