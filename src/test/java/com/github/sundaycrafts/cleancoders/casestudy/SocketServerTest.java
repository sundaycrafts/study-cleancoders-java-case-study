package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class SocketServerTest {
  private FakeSocketService service;
  private SocketServer server;
  private int port;

  @BeforeEach
  void setUp() throws Exception {
    port = 8042;
    service = new FakeSocketService();
    server = new SocketServer(port, service);
  }

  @AfterEach
  void tearDown() throws IOException, InterruptedException {
    server.stop();
  }

  @Test
  void instantiate() {
    assertEquals(port, server.getPort());
    assertEquals(service, server.getService());
  }

  @Test
  void canStartAndStopServer() throws IOException, InterruptedException {
    server.start();
    assertTrue(server.isRunning());
    server.stop();
    assertFalse(server.isRunning());
  }

  @Test
  void acceptsInComingConnection() throws IOException, InterruptedException {
    server.start();
    new Socket("localhost", port);
    server.stop();

    assertEquals(1, service.connections);
  }

  public static class FakeSocketService implements SocketService {
    public int connections;

    public void serve(Socket s) {
      connections++;
    }
  }
}

