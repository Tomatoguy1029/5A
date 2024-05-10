package src.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PostService {
    // 教室の現在の情報を投稿し、データベースを更新するメソッド
    public void insertClassroom(String classroom_id, int crowdedness, int air_conditioning, int internet_quality) {
        String url = "jdbc:sqlite:akikatu.db";
        String sql = "INSERT INTO ClasssroomStatus(status_id, classroom_id, crowdedness, air_conditioning,internet_quality, timestamp) VALUES (?, ?, ?, ?, ?, datetime('now')));";
        // sqlクエリを作成
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String uniqueId = UUID.randomUUID().toString(); // UUIDを生成
            pstmt.setString(1, uniqueId);
            pstmt.setString(2, classroom_id);
            pstmt.setInt(3, crowdedness);
            pstmt.setInt(4, air_conditioning);
            pstmt.setInt(5, internet_quality);

            pstmt.executeUpdate();// SQLクエリを実行。実行するとデータベースにデータが登録される
            System.out.println("Insert completed with UUID: " + uniqueId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
