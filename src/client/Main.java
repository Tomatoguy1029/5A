package src.client;

import src.client.pages.ClassroomSearchPage;
import src.client.pages.ClassroomSearchPageVM;

import java.io.*;
import java.net.*;

public class Main {
    public static int PORT = 8080;
    public static PrintWriter out;

    public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);

        try (Socket socket = new Socket(addr, PORT)) {
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            // ClassroomSearchPageを起動し、クエリを作成する
            ClassroomSearchPage searchPage = new ClassroomSearchPage();
            ClassroomSearchPageVM viewModel = ClassroomSearchPageVM.getInstance(searchPage);

            // クエリが生成されるのを待つ
            while (true) {
                String query = viewModel.getGeneratedQuery();
                System.out.println("Generated query: " + query);
                if (query != null) {
                    System.out.println("Sending query: " + query);
                    out.println(query);// サーバーにクエリを送信

                    // サーバーからの応答を受け取る
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Server response: " + response);
                    }
                }

                // メインスレッドの待機を少し入れる
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("closing...");
        }
    }
}
