package src.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    private static final String DB_URL = "jdbc:sqlite:/Users/aipon/Works/5A/classrooms.db";

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening on port 12345");
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");

                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    writer.println("Choose mode: search");
                    String userMode = reader.readLine();

                    if ("search".equals(userMode)) {
                        writer.println("Enter classroom name:");
                        String classroomName = reader.readLine();
                        System.out.println("Client requested classroom: " + classroomName);

                        String classroomInfo = getClassroomInfoFromDatabase(classroomName);
                        writer.println(classroomInfo);
                    } else {
                        writer.println("Invalid mode.");
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String getClassroomInfoFromDatabase(String classroomName) {
        String info = "Classroom not found.";
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            System.out.println("Connected to the database.");

            String sql = "SELECT info FROM classrooms WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, classroomName);
                System.out.println("Executing query: " + sql + " with parameter: " + classroomName);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        info = rs.getString("info");
                        System.out.println("Found classroom info: " + info);
                    } else {
                        System.out.println("Classroom not found in the database.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return info;
    }
}
