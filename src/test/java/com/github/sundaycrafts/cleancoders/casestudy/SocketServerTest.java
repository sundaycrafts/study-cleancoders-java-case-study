package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SocketServerTest {
  @Test
  void instantiate() {
    int port = 42;
    var service = new FakeSocketService();
    var socketServer = new SocketServer(port, service);

    assertEquals(port, socketServer.getPort());
    assertEquals(service, socketServer.getService());
  }
}

