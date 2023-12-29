import java.util.HashMap;
import java.util.Map;

class Student {
    private String studentId;
    private String name;
    private Map<String, Integer> courses = new HashMap<>();

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void registerCourse(String courseId, int grade) {
        courses.put(courseId, grade);
    }

    public Map<String, Integer> getCourses() {
        return courses;
    }
}