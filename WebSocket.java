package test;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/webSocketTest/{userId}")
public class WebSocket {
    // 这个处理 WebSocket 的类需要实现一些特定的方法

    // 客户端建立连接的时候, 就会由服务器自动调用 onOpen
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        System.out.println("connection success! userId = " + userId);

        // 只要连接建立成功, 服务器就每隔一秒钟就给客户端
        // 发送一个 hello 字符串~
        // 此处最好创建一个新的线程完成这个工作.
        // onOpen 这个方法是 Tomcat 内部有一个线程来进行调用的.
        // 如果直接把 耗时操作 就放到 onOpen 里面, 可能就导致
        // Tomcat 原有的这个线程就不能正常工作了~~
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        session.getBasicRemote().sendText("hello");
                        Thread.sleep(1000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 真正的在操作系统中创建线程执行了.
        t.start();
    }

    // 客户端断开连接的时候, 就会由服务器自动调用 onClose
    @OnClose
    public void onClose() {
        System.out.println("connection failed!");
    }

    // 连接出现异常的时候(断网了), 就会由服务器自动调用 onError
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("connection error!");
        error.printStackTrace();
    }

    // 服务器收到客户端发来的消息的时候会自动调用
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("received news! message = " + message);
        // 服务器也给客户端回复一个消息
        // 这个代码实现了一个简单的 "回显" 服务器
        // 一旦连接建立完毕, 就可以在代码中通过这一行逻辑给客户端发送消息.
        // 前提是连接正常. 如果连接已经断开, 这样的发送消息操作就会触发
        // IOException 异常
        session.getBasicRemote().sendText(message);
    }
}
