package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class SocketServerTest {
  @Nested
  class WithClosingSocketService {
    private ClosingSocketService service;
    private SocketServer server;
    private int port;

    @BeforeEach
    public void setUp() throws Exception {
      port = 8042;
      service = new ClosingSocketService();
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

  }

  @Nested
  class WithReadingSocketService {
    private SocketServer server;
    private int port;

    @BeforeEach
    public void setUp() throws Exception {
      port = 8042;
      var service = new ReadingSocketService();
      server = new SocketServer(port, service);
    }

    @Test
    void canSendAndReceive() throws IOException, InterruptedException {
      var readingSocketService = new ReadingSocketService();
      server.setService(readingSocketService);
      server.start();
      new Socket("localhost", port)
        .getOutputStream()
        .write("hello\n".getBytes());
      server.stop();

      assertEquals("hello", readingSocketService.message);
    }
  }

  public static class ReadingSocketService implements SocketService {
    public String message;

    public void serve(Socket s) {
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

  public static class ClosingSocketService implements SocketService {
    public int connections;

    public void serve(Socket s) {
      connections++;
      try {
        s.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}

