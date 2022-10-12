package CLI.cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.lang.Thread;

public class JavaWebSocketClient {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        WebSocket ws = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8999"),
                        new WebSocketClient(latch))
                .join();

        boolean token = false;
        Scanner scanner = new Scanner(System.in);
        while (!token) {
            String message = scanner.nextLine();
            ws.sendText(message, true);
        }
        latch.await();
    }

    private static class WebSocketClient implements WebSocket.Listener {
        private final CountDownLatch latch;

        public WebSocketClient(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            // System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println(data);
            latch.countDown();
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.out.println("Bad day! " + webSocket.toString());
            WebSocket.Listener.super.onError(webSocket, error);
        }
    }
}