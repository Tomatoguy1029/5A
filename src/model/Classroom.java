package src.model;

public class Classroom {
    private String classroomId;
    private String name;
    private String location;
    private int building;
    private int seats;
    private int outlets;
    private int deskSize;
    private String available;
    private String createdAt;
    private String updatedAt;

    // Getters and setters for all fields

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getOutlets() {
        return outlets;
    }

    public void setOutlets(int outlets) {
        this.outlets = outlets;
    }

    public int getDeskSize() {
        return deskSize;
    }

    public void setDeskSize(int deskSize) {
        this.deskSize = deskSize;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "classroomId=" + classroomId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", building=" + building +
                ", seats=" + seats +
                ", outlets=" + outlets +
                ", deskSize=" + deskSize +
                ", available='" + available + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

}