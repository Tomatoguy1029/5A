package src.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClassroomService {
    private static final String DATABASE_URL = "jdbc:sqlite:akikatu.db";

    // 教室情報をデータベースに登録するメソッド
    public void insertClassroom(String name, int seats, int outlets, int deskSize, String available) {
        String sql = "INSERT INTO Classrooms (classroom_id, name, seats, outlets, desk_size, available, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, datetime('now'), datetime('now'));";
        // sqlクエリを作成
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String uniqueId = UUID.randomUUID().toString(); // UUIDを生成
            pstmt.setString(1, uniqueId);
            pstmt.setString(2, name);
            pstmt.setInt(3, seats);
            pstmt.setInt(4, outlets);
            pstmt.setInt(5, deskSize);
            pstmt.setString(6, available);

            pstmt.executeUpdate();// SQLクエリを実行。実行するとデータベースにデータが登録される
            System.out.println("Insert completed with UUID: " + uniqueId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 教室情報をデータベースから削除するメソッド
    public void deleteClassroom(String classroom_id) {
        String url = "jdbc:sqlite:akikatu.db";
        String sql = "DELETE FROM Classrooms WHERE classroom_id = ?;";
        // sqlクエリを作成
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, classroom_id);

            pstmt.executeUpdate();
            System.out.println("Delete completed with UUID: " + classroom_id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 教室情報をデータベースから読み込むメソッド
    public void loadClassroom(String classroom_id) {
        String sql = "SELECT * FROM Classrooms WHERE classroom_id = ?;";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, classroom_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Classroom ID: " + rs.getString("classroom_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Seats: " + rs.getInt("seats"));
                System.out.println("Outlets: " + rs.getInt("outlets"));
                System.out.println("Desk Size: " + rs.getInt("desk_size"));
                System.out.println("Available: " + rs.getString("available"));
                System.out.println("Created At: " + rs.getString("created_at"));
                System.out.println("Updated At: " + rs.getString("updated_at"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
