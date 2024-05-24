package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);

            // モード選択
            String userMode = consoleReader.readLine();
            writer.println(userMode);

            // サーバーからの最終応答
            serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);

            if ("search".equals(userMode)) {
                // 教室名を入力
                String classroomName = consoleReader.readLine();
                writer.println(classroomName);

                // サーバーからの最終応答
                serverMessage = reader.readLine();
                System.out.println("Server: " + serverMessage);
            }

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
