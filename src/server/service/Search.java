package src.server.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.model.Classroom;

public class Search {

    private static final String URL = "jdbc:sqlite:akikatu.db";
    public static List<Classroom> searchResult = new ArrayList<>();

    public static void searchClassrooms(Map<String, String> parameters) {// 教室を検索するメソッド
        String sql = "SELECT * FROM Classrooms WHERE 1=1";

        for (int i = 1; i <= 7; i++) {
            if (parameters.containsKey("TIME" + i)) {
                sql += " AND json_extract(available, '$." + parameters.get("DAY") + "') LIKE '%" + i + "%'";
            }
        }
        if (parameters.containsKey("BUILDING")) {
            sql += " AND building = " + parameters.get("BUILDING");
        }
        if (parameters.containsKey("OUTLETS")) {
            sql += " AND outlets >= 10";
        }
        if (parameters.containsKey("DESK_SIZE")) {
            sql += " AND desk_size = 1";
        }
        if (parameters.containsKey("CROWDEDNESS")) {
            sql += " AND crowdedness = 1";
        }
        if (parameters.containsKey("NETWORK")) {
            sql += " AND network = 1";
        }

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            searchResult.clear();

            while (rs.next()) {
                Classroom classroom = new Classroom();
                classroom.setClassroomId(rs.getLong("classroom_id"));
                classroom.setName(rs.getString("name"));
                classroom.setLocation(rs.getString("location"));
                classroom.setSeats(rs.getInt("seats"));
                classroom.setOutlets(rs.getInt("outlets"));
                classroom.setDeskSize(rs.getInt("desk_size"));
                classroom.setAvailable(rs.getString("available"));
                classroom.setCreatedAt(rs.getString("created_at"));
                classroom.setUpdatedAt(rs.getString("updated_at"));
                searchResult.add(classroom);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}