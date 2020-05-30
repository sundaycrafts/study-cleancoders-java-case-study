package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

  @Test
  void canSendAndReceive() throws IOException, InterruptedException {
    server.start();
    var s = new Socket("localhost", port);
    var os = s.getOutputStream();
    os.write("hello\n".getBytes());
    server.stop();

    assertEquals("hello", service.message);
  }

  public static class FakeSocketService implements SocketService {
    public int connections;
    public String message;

    public void serve(Socket s) {
      connections++;

      try {
        var is = s.getInputStream();
        var isr = new InputStreamReader(is);
        var br = new BufferedReader(isr);

        message = br.readLine();

        s.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

