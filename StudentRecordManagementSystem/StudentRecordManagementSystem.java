import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String address;
    private String phoneNumber;

    public Student(String name, int rollNumber, String address, String phoneNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class StudentRecord {
    private List<Student> studentList;

    public StudentRecord() {
        studentList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public void removeStudent(int rollNumber) {
        studentList.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public List<Student> getStudents() {
        return studentList;
    }
}

public class StudentRecordManagementSystem {
    private JFrame frame;
    private JPanel panel;
    private JTextField nameField;
    private JTextField rollNumberField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextArea displayArea;
    private JButton addButton;
    private JButton removeButton;
    private JButton displayButton;
    private JButton saveButton;
    private JButton loadButton;
    private StudentRecord studentRecord;

    public StudentRecordManagementSystem() {
        frame = new JFrame("Student Record Management System");
        panel = new JPanel();
        nameField = new JTextField(20);
        rollNumberField = new JTextField(5);
        addressField = new JTextField(20);
        phoneNumberField = new JTextField(10);
        displayArea = new JTextArea(10, 30);
        addButton = new JButton("Add Student");
        removeButton = new JButton("Remove Student");
        displayButton = new JButton("Display Students");
        saveButton = new JButton("Save Records");
        loadButton = new JButton("Load Records");
        studentRecord = new StudentRecord();

        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Name: "));
        panel.add(nameField);
        panel.add(new JLabel("Roll Number: "));
        panel.add(rollNumberField);
        panel.add(new JLabel("Address: "));
        panel.add(addressField);
        panel.add(new JLabel("Phone Number: "));
        panel.add(phoneNumberField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(displayButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(new JScrollPane(displayArea));

        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStudents();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecords();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRecords();
            }
        });
    }

    private void addStudent() {
        try {
            String name = nameField.getText();
            int rollNumber = Integer.parseInt(rollNumberField.getText());
            String address = addressField.getText();
            String phoneNumber = phoneNumberField.getText();

            Student student = new Student(name, rollNumber, address, phoneNumber);
            studentRecord.addStudent(student);

            nameField.setText("");
            rollNumberField.setText("");
            addressField.setText("");
            phoneNumberField.setText("");
            displayArea.append("Student added: " + name + " (Roll Number: " + rollNumber + ")\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid roll number. Please enter a valid number.");
        }
    }

    private void removeStudent() {
        try {
            int rollNumber = Integer.parseInt(rollNumberField.getText());
            studentRecord.removeStudent(rollNumber);
            displayArea.append("Student with Roll Number " + rollNumber + " removed.\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid roll number. Please enter a valid number.");
        }
    }

    private void displayStudents() {
        displayArea.setText("");
        List<Student> students = studentRecord.getStudents();
        for (Student student : students) {
            displayArea.append("Name: " + student.getName() + "\n");
            displayArea.append("Roll Number: " + student.getRollNumber() + "\n");
            displayArea.append("Address: " + student.getAddress() + "\n");
            displayArea.append("Phone Number: " + student.getPhoneNumber() + "\n\n");
        }
    }

    private void saveRecords() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("student_records.ser"));
            outputStream.writeObject(studentRecord);
            outputStream.close();
            displayArea.append("Student records saved to file.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRecords() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("student_records.ser"));
            studentRecord = (StudentRecord) inputStream.readObject();
            inputStream.close();
            displayArea.append("Student records loaded from file.\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentRecordManagementSystem();
            }
        });
    }
}

