import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StudentInformationSystemGUI {
    private static JFrame frame;
    private static DefaultListModel<String> studentListModel;
    private static DefaultListModel<String> courseListModel;
    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Course> courses = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Student Information System");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            studentListModel = new DefaultListModel<>();
            courseListModel = new DefaultListModel<>();


            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));

            JButton addStudentButton = new JButton("Add Student");
            addStudentButton.addActionListener(e -> addStudent());

            JButton addCourseButton = new JButton("Add Course");
            addCourseButton.addActionListener(e -> addCourse());

            JButton registerButton = new JButton("Register for Course");
            registerButton.addActionListener(e -> registerStudentForCourse());

            JButton generateTranscriptButton = new JButton("Generate Transcript");
            generateTranscriptButton.addActionListener(e -> generateTranscript());

            panel.add(addStudentButton);
            panel.add(addCourseButton);
            panel.add(registerButton);
            panel.add(generateTranscriptButton);
            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.setVisible(true);
        });
    }

    private static void addStudent() {
        String studentId = JOptionPane.showInputDialog("Enter student ID:");
        String name = JOptionPane.showInputDialog("Enter student name:");

        if (studentId != null && name != null) {
            Student student = new Student(studentId, name);
            students.put(studentId, student);
            studentListModel.addElement(student.getName());
        }
    }

    private static void addCourse() {
        String courseId = JOptionPane.showInputDialog("Enter course ID:");
        String courseName = JOptionPane.showInputDialog("Enter course name:");

        if (courseId != null && courseName != null) {
            Course course = new Course(courseId, courseName);
            courses.put(courseId, course);
            courseListModel.addElement(course.getCourseName());
        }
    }

    private static void registerStudentForCourse() {
        String studentId = JOptionPane.showInputDialog("Enter student ID:");
        String courseId = JOptionPane.showInputDialog("Enter course ID:");
        String gradeString = JOptionPane.showInputDialog("Enter grade:");

        if (studentId != null && courseId != null && gradeString != null) {
            int grade = Integer.parseInt(gradeString);

            Student student = students.get(studentId);
            Course course = courses.get(courseId);

            if (student != null && course != null) {
                student.registerCourse(courseId, grade);
                JOptionPane.showMessageDialog(frame, "Registration successful.");
            } else {
                JOptionPane.showMessageDialog(frame, "Student or course not found.");
            }
        }
    }

    private static void generateTranscript() {
        String studentId = JOptionPane.showInputDialog("Enter student ID:");

        if (studentId != null) {
            Student student = students.get(studentId);

            if (student != null) {
                Transcript.generateTranscript(student);
            } else {
                JOptionPane.showMessageDialog(frame, "Student not found.");
            }
        }
    }
}
