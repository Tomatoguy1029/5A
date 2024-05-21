package src.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static int PORT = 8080;
    public static int mode = 0;

    public static void main(String[] args)
            throws IOException {
        InetAddress addr = InetAddress.getByName("localhost");// IP アドレスへの変換
        Scanner scanner = new Scanner(System.in); // ユーザー入力を読み取るためのScanner
        System.out.println("selected mode:(1)edit classroom info, (2)post classroom status");

        // ユーザーからのモード選択の取得
        System.out.println("Enter mode (1 or 2):");
        System.out.println("Type 'END' to disconnect:");

        System.out.println("addr = " + addr);
        try (Socket socket = new Socket(addr, PORT)) {
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream())); // データ受信用バッファの設定
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())),
                    true); // 送信バッファ設定

            while (true) {
                String input = scanner.nextLine();
                try {

                    if ("END".equalsIgnoreCase(input)) {
                        out.println("END");
                        break;
                    } else {
                        mode = Integer.parseInt(input);
                        if (mode == 1 || mode == 2) {
                            // モードをサーバーに送信
                            out.println(mode);
                            // サーバーからの応答を受け取る
                            String response = in.readLine();
                            System.out.println("Server response: " + response);
                        } else {
                            System.out.println("Invalid mode. Please enter 1 or 2:");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number (1 or 2):");
                }
            }

        } finally {
            System.out.println("closing...");
            scanner.close();
        }
    }

}
