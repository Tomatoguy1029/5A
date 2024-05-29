package src.server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import src.server.service.Search;

public class Main {
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
        public static PrintWriter out;

        // クライアントとの通信を担当するスレッド
        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
                ClientHandler.out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); // 送信バッファ設定
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
                            conductQuery(str);
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

        // private String handleAddClassroomInfo() {
        // CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
        // addCInfo.addInfoAsync(in, out);
        // });
        // try {
        // future.get(); // 非同期処理が完了するまで待つ
        // return "Classroom info added.";
        // } catch (Exception e) {
        // return "Error completing the operation: " + e.getMessage();
        // }
        // }

        private static Map<String, String> parseQuery(String query) {// クエリを解析してパラメータを取得
            Map<String, String> parameters = new HashMap<>();
            if (query.contains("?")) {
                String[] parts = query.split("\\?");// parts[0]にはクエリの種類が入る
                if (parts.length > 1) {
                    String[] pairs = parts[1].split("&");// pairsにはクエリの中身が入る
                    for (String pair : pairs) {
                        String[] keyValue = pair.split("=");// keyValue[]にはkeyとvalueが入る
                        if (keyValue.length > 1) {
                            parameters.put(keyValue[0], keyValue[1]);// keyvalue[0]はkey, keyvalue[1]はvalueを持つ
                        }
                    }
                }
                parameters.put("MODE", parts[0]); // クエリの種類を取得
            }
            return parameters;
        }

        public void conductQuery(String query) {
            Map<String, String> parameters = parseQuery(query);
            String mode = parameters.get("MODE");

            if (mode == null) {
                out.println("No mode specified");
                return;
            }

            switch (mode) {
                case "SEARCH":
                    Search.searchClassrooms(parameters);
                    out.println("Search completed.");
                    out.println(Search.searchResult);
                    break;
                case "ADD_CLASSROOM_INFO":
                    // AddClassroomInfo(parameters);
                    out.println("Add classroom info completed.");
                    break;
                case "POST_CLASSROOM_STATUS":
                    // PostClassroomStatus(parameters);
                    out.println("Post classroom status completed.");
                    break;
                default:
                    out.println("Unknown mode: " + mode);
            }
        }
    }
}
