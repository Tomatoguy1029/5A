package src.server.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.model.Classroom;

public class Search {

    public static List<Classroom> searchResult = new ArrayList<>();

    public static void searchClassrooms(Map<String, String> parameters, Connection conn) {// 教室を検索するメソッド
        if (conn == null) {
            System.err.println("Database connection is null.");
            return;
        }

        // System.out.println("Searching classrooms...");

        String sql = "SELECT * FROM Classrooms WHERE 1=1";

        if (parameters.containsKey("DAY")) {
            String day = parameters.get("DAY");
            for (int i = 1; i <= 7; i++) {
                if (parameters.containsKey("TIME" + i)) {
                    sql += " AND json_extract(available, '$." + day + "') LIKE ?";
                }
            }
        }

        if (parameters.containsKey("BUILDING")) {
            sql += " AND building = ?";
        }
        if (parameters.containsKey("OUTLETS")) {
            sql += " AND outlets = 1";
        }
        if (parameters.containsKey("DESK_SIZE")) {
            sql += " AND desk_size = 1";
        }
        // if (parameters.containsKey("CROWDEDNESS")) {
        // sql += " AND crowdedness = 1";
        // }
        if (parameters.containsKey("NETWORK")) {
            sql += " AND network = 1";
        }

        // System.out.println("SQL: " + sql);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int index = 1;

            if (parameters.containsKey("DAY")) {
                for (int i = 1; i <= 7; i++) {
                    if (parameters.containsKey("TIME" + i)) {
                        pstmt.setString(index++, "%" + i + "%");
                    }
                }
            }

            if (parameters.containsKey("BUILDING")) {
                pstmt.setInt(index++, Integer.parseInt(parameters.get("BUILDING")));
            }

            System.out.println(pstmt.toString());
            // System.out.println("Executing query...");
            ResultSet rs = pstmt.executeQuery();
            // System.out.println("Query executed.");
            searchResult.clear();

            while (rs.next()) {
                Classroom classroom = new Classroom();
                classroom.setClassroomId(rs.getString("classroom_id"));
                classroom.setName(rs.getString("name"));
                classroom.setLocation(rs.getString("location"));
                classroom.setBuilding(rs.getInt("building"));
                classroom.setSeats(rs.getInt("seats"));
                classroom.setOutlets(rs.getInt("outlets"));
                classroom.setDeskSize(rs.getInt("desk_size"));
                classroom.setAvailable(rs.getString("available"));
                classroom.setCreatedAt(rs.getString("created_at"));
                classroom.setUpdatedAt(rs.getString("updated_at"));
                searchResult.add(classroom);
            }
            System.out.println("Search result: " + searchResult);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
