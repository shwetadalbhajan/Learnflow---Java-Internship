import javax.swing.*;
import java.awt.*;
import java.util.Map;

class Transcript {
    public static void generateTranscript(Student student) {
        StringBuilder transcript = new StringBuilder("Transcript for " + student.getName() + " (ID: " + student.getStudentId() + ")\n");
        transcript.append("Courses\t\tGrade\n");

        for (Map.Entry<String, Integer> entry : student.getCourses().entrySet()) {
            transcript.append(entry.getKey()).append("\t\t").append(entry.getValue()).append("\n");
        }

        JTextArea transcriptArea = new JTextArea(transcript.toString());
        transcriptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transcriptArea);

        JFrame transcriptFrame = new JFrame("Transcript");
        transcriptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transcriptFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        transcriptFrame.setSize(300, 200);
        transcriptFrame.setLocationRelativeTo(null);
        transcriptFrame.setVisible(true);
    }
}
