package src.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class ClassroomService {
    // 教室情報をデータベースに登録するメソッド
    public void insertClassroom(String name, String location, int seats, int outlets, int deskSize) {
        String url = "jdbc:sqlite:akikatu.db";
        String sql = "INSERT INTO Classrooms (classroom_id, name, location, seats, outlets, desk_size, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, datetime('now'), datetime('now'));";
        // sqlクエリを作成
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String uniqueId = UUID.randomUUID().toString(); // UUIDを生成
            pstmt.setString(1, uniqueId);
            pstmt.setString(2, name);
            pstmt.setString(3, location);
            pstmt.setInt(4, seats);
            pstmt.setInt(5, outlets);
            pstmt.setInt(6, deskSize);

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
}
