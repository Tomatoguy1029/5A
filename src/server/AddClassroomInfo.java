//classroom infoをデータベースに登録する処理を行うクラス
package src.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;

import src.service.ClassroomService;

public class AddClassroomInfo {
    private static ClassroomService classroomService = new ClassroomService();

    public void addInfoAsync(BufferedReader in, PrintWriter out) {
        CompletableFuture.runAsync(() -> {
            try {
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
                out.println("Classroom info added: " + name + ", " + location + ", " + seats + " seats, " + outlets
                        + " outlets, desk size: " + deskSize);
            } catch (IOException e) {
                out.println("Error adding classroom info: " + e.getMessage());
            }
        });
    }
}
