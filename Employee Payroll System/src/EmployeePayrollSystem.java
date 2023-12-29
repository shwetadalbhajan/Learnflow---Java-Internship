import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollSystem extends JFrame {
    private PayrollSystem payrollSystem;

    private JTextField idField;
    private JTextField nameField;
    private JTextField hourlyRateField;
    private JTextField halfDayLeaveField;
    private JTextField fullDayLeaveField;

    public EmployeePayrollSystem() {
        this.payrollSystem = new PayrollSystem();

        setTitle("Employee Payroll System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        idField = new JTextField();
        nameField = new JTextField();
        hourlyRateField = new JTextField();
        halfDayLeaveField = new JTextField();
        fullDayLeaveField = new JTextField();

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        JButton viewButton = new JButton("View Employee Details");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewEmployeeDetails();
            }
        });

        JButton processButton = new JButton("Generate Payslips");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayroll();
            }
        });

        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Employee Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Hourly Rate:"));
        panel.add(hourlyRateField);
        panel.add(new JLabel("Half-Day Leave:"));
        panel.add(halfDayLeaveField);
        panel.add(new JLabel("Full-Day Leave:"));
        panel.add(fullDayLeaveField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(processButton);

        add(panel);
    }

    private void addEmployee() {
        String id = idField.getText();
        String name = nameField.getText();
        String hourlyRateText = hourlyRateField.getText();
        String halfDayLeaveText = halfDayLeaveField.getText();
        String fullDayLeaveText = fullDayLeaveField.getText();

        if (id.isEmpty() || name.isEmpty() || hourlyRateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double hourlyRate = Double.parseDouble(hourlyRateText);
            int halfDayLeave = Integer.parseInt(halfDayLeaveText);
            int fullDayLeave = Integer.parseInt(fullDayLeaveText);

            Employee employee = new Employee(id, name, hourlyRate);
            employee.addHoursWorked(200 - halfDayLeave - (2 * fullDayLeave)); // Adjust hours based on leaves
            payrollSystem.addEmployee(employee);

            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewEmployeeDetails() {
        if (payrollSystem.getEmployees().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees added. Add employees before viewing details.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            StringBuilder details = new StringBuilder("Employee Details:\n\n");
            for (Employee employee : payrollSystem.getEmployees()) {
                details.append("ID: ").append(employee.getId()).append("\n")
                        .append("Name: ").append(employee.getName()).append("\n")
                        .append("Hourly Rate: $").append(employee.getHourlyRate()).append("\n")
                        .append("Hours Worked: ").append(employee.getHoursWorked()).append("\n")
                        .append("--------------------------").append("\n");
            }

            JOptionPane.showMessageDialog(this, details.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void processPayroll() {
        if (payrollSystem.getEmployees().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees added. Add employees before processing payroll.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            payrollSystem.processPayroll();
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        hourlyRateField.setText("");
        halfDayLeaveField.setText("");
        fullDayLeaveField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeePayrollSystem().setVisible(true);
            }
        });
    }
}

class PayrollSystem {
    private List<Employee> employees;

    public PayrollSystem() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void processPayroll() {
        // Perform payroll processing logic, such as tax calculations, deductions, etc.
        // For simplicity, we'll just print payslips in this example.
        for (Employee employee : employees) {
            double salary = employee.calculateSalary();
            double tax = 0.1 * salary; // 10% tax for demonstration purposes
            double netSalary = salary - tax;
            // Print payslip
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String payslip = "Payslip for Employee " + employee.getId() + "\n" +
                    "Name: " + employee.getName() + "\n" +
                    "Hours Worked: " + employee.getHoursWorked() + "\n" +
                    "Hourly Rate: $" + decimalFormat.format(employee.getHourlyRate()) + "\n" +
                    "Gross Salary: $" + decimalFormat.format(salary) + "\n" +
                    "Tax Deduction: $" + decimalFormat.format(tax) + "\n" +
                    "Net Salary: $" + decimalFormat.format(netSalary) + "\n" +
                    "----------------------------";
            JOptionPane.showMessageDialog(null, payslip, "Payslip", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

class Employee {
    private String id;
    private String name;
    private double hourlyRate;
    private int hoursWorked;

    public Employee(String id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void addHoursWorked(int hours) {
        this.hoursWorked += hours;
    }

    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}
