package src.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import src.client.pages.ClassroomSearchPage;
import src.client.pages.ClassroomSearchPageVM;
import src.model.Classroom;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Main {
    public static int PORT = 8080;
    public static PrintWriter out;
    public static final Object lock = new Object();

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
                String query;
                synchronized (lock) {
                    query = viewModel.getGeneratedQuery();
                    while (query == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        query = viewModel.getGeneratedQuery();
                    }
                }

                // System.out.println("Sending query: " + query);
                out.println(query); // サーバーにクエリを送信

                // サーバーからの応答を受け取る
                StringBuilder responseBuilder = new StringBuilder();
                String response;
                while ((response = in.readLine()) != null) {
                    if (response.equals("END_OF_RESPONSE")) {
                        break;
                    }
                    responseBuilder.append(response);
                }

                String jsonResponse = responseBuilder.toString();
                // System.out.println("Server response: " + jsonResponse);

                Gson gson = new Gson();
                try {
                    List<Classroom> classrooms = gson.fromJson(jsonResponse, new TypeToken<List<Classroom>>() {
                    }.getType());
                    System.out.println("Received classrooms: " + classrooms);
                    showClassroomsDialog(classrooms);
                } catch (com.google.gson.JsonSyntaxException e) {
                    System.err.println("Failed to parse JSON response: " + e.getMessage());
                    System.err.println("Response: " + jsonResponse);
                }

                // 次のアクションを待つ
                synchronized (lock) {
                    viewModel.clearGeneratedQuery();
                    viewModel.clearQueryParameters();
                }

            }
        } finally {
            System.out.println("closing...");
        }
    }

    private static void showClassroomsDialog(List<Classroom> classrooms) {
        if (classrooms == null || classrooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No classrooms found.", "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = { "建物", "階数", "名前",
        };
        Object[][] data = new Object[classrooms.size()][columnNames.length];

        for (int i = 0; i < classrooms.size(); i++) {
            Classroom classroom = classrooms.get(i);
            data[i][0] = classroom.getBuilding();
            data[i][1] = classroom.getLocation();
            data[i][2] = classroom.getName();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
