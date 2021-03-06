/*
 * Copyright 2017 Yi-Kun Yang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.allnix.boot.web;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Yi-Kun Yang &gt;ykyang@gmail.com&lt;
 */
public class WebSocketClientHandler extends TextWebSocketHandler {

  static private final Logger logger = LoggerFactory.getLogger(
    WebSocketClientHandler.class);

//  private WebSocketSession session;
  private BlockingQueue<WebSocketSession> sessions;

  private BlockingQueue<TextMessage> queue;
//  private AtomicBoolean ready;

  public WebSocketClientHandler() {
    queue = new LinkedBlockingQueue<>();
//    ready = new AtomicBoolean(false);
    sessions = new LinkedBlockingQueue<>();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws
    Exception {
    logger.info("Session established: {}", session.getUri().toString());
    logger.info("Session ID: {}", session.getId());
//    this.session = session;
    sessions.put(session);
//    ready.set(true);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
    throws Exception {
    logger.info("Remove session: {}", session.getId());
    sessions.remove(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message)
    throws Exception {
    logger.info("Message: {}", message.toString());
  }

  /**
   * Blocking send
   *
   * The call is blocked until the connection is done
   *
   * @param message
   * @throws IOException
   */
  public void sendMessage(TextMessage message) throws IOException {
//    while (!ready.get()) {
//      try {
//        TimeUnit.MILLISECONDS.sleep(1);
//      } catch (InterruptedException ex) {
//        return;
//      }
//    }
    for (WebSocketSession session : sessions) {
      session.sendMessage(message);
    }

  }
}
