package src.server;

import java.io.*;
import java.net.*;

import src.service.ClassroomService;

public class Server {
    public static int PORT = 8080;
    public static int mode = 0;
    private static ClassroomService classroomService = new ClassroomService();

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
                    str = handleMode(socket, mode, in, out);
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

    private static String handleMode(Socket socket, int mode, BufferedReader in, PrintWriter out) throws IOException {
        if (mode == 1) {
            addClassroomInfo(in, out);
            return "Classroom info added.";
        } else if (mode == 2) {
            // postClassroomStatus(socket);
            return "Classroom status posted.";
        } else {
            System.out.println("Invalid mode.");
            return "Invalid mode.";
        }
    }

    private static String addClassroomInfo(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Enter classroom name:");
        String name = in.readLine();

        out.println("Enter classroom location:");
        String location = in.readLine();

        out.println("Enter number of seats:");
        int seats = Integer.parseInt(in.readLine());

        out.println("Enter number of outlets:");
        int outlets = Integer.parseInt(in.readLine());

        out.println("Enter desk size:");
        int deskSize = Integer.parseInt(in.readLine());

        classroomService.insertClassroom(name, location, seats, outlets, deskSize);
        return "Classroom info added: " + name + ", " + location + ", " + seats + " seats, " + outlets
                + " outlets, desk size: " + deskSize;
    }
}