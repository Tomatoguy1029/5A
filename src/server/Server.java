package src.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.CompletableFuture;

public class Server {
    public static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0] != null) {
            int num = Integer.parseInt(args[0]);
            if (num > 0 && num < 65536) {
                PORT = num;
            } else {
                System.out.println("Invalid port number.");
            }
        }
        ServerSocket s = new ServerSocket(PORT); // ソケットを作成する
        System.out.println("Started: " + s);
        try {
            while (true) {
                Socket socket = s.accept(); // コネクション設定要求を待つ
                new Thread(new ClientHandler(socket)).start();
            }
        } finally {
            s.close();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        public BufferedReader in;
        public PrintWriter out;
        private AddClassroomInfo addCInfo = new AddClassroomInfo();

        // クライアントとの通信を担当するスレッド
        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
                this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); // 送信バッファ設定
            } catch (IOException e) {
                System.err.println("Error setting up streams: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                System.out.println("Connection accepted: " + socket);
                while (true) {
                    String str = in.readLine(); // データの受信
                    if (str.equals("END")) {
                        break;
                    } else {
                        try {
                            int mode = Integer.parseInt(str);
                            handleMode(mode);
                        } catch (NumberFormatException e) {
                            out.println("Invalid input. Please enter a number (1 or 2):");
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Connection error: " + e.getMessage());
            } finally {
                try {
                    System.out.println("closing...");
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        private String handleMode(int mode) throws IOException {
            switch (mode) {
                case 1:
                    return handleAddClassroomInfo();
                case 2:
                    return handlePostClassroomStatus();
                default:
                    System.out.println("Invalid mode.");
                    return "Invalid mode.";
            }
        }

        private String handleAddClassroomInfo() {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                addCInfo.addInfoAsync(in, out);
            });
            try {
                future.get(); // 非同期処理が完了するまで待つ
                return "Classroom info added.";
            } catch (Exception e) {
                return "Error completing the operation: " + e.getMessage();
            }
        }

        private String handlePostClassroomStatus() throws IOException {
            out.println("Classroom status posted.");
            return "Classroom status posted.";
        }
    }
}
