package com.github.sundaycrafts.cleancoders.casestudy;

public class SocketServer {
  private final int port;
  private final SocketService service;

  public SocketServer(int port, SocketService service) {
    this.port = port;
    this.service = service;
  }

  public int getPort() {
    return port;
  }

  public SocketService getService() {
    return service;
  }
}
