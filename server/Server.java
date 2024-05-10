//サーバーのメインクラス
//package server;

import java.io.*;
import java.net.*;

public class Server {
    public static int PORT = 8080;
    public static int mode = 0;

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
            Socket socket = s.accept(); // コネクション設定要求を待つ
            try {
                System.out.println(
                        "Connection accepted: " + socket);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream())); // データ受信用バッファの設定
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream())),
                        true); // 送信バッファ設定

                while (true) {
                    String str = in.readLine(); // データの受信
                    if (str.equals("END")) {
                        break;
                    } else {
                        mode = Integer.parseInt(str);
                    }
                    str = handleMode(socket, mode);
                    out.println(str); // データの送信
                }
            } finally {
                System.out.println("closing...");
                socket.close();
            }
        } finally {
            s.close();
        }
    }

    private static String handleMode(Socket socket, int mode) throws IOException {
        if (mode == 1) {
            // addClassroomInfo(socket);
            return "Classroom info added.";
        } else if (mode == 2) {
            // postClassroomStatus(socket);
            return "Classroom status posted.";
        } else {
            System.out.println("Invalid mode.");
            return "Invalid mode.";
        }
    }
}
// private static void addClassroomInfo(){}