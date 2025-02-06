import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private int num1 = 0, num2 = 0, result = 0;
    private String operator = "";
    private boolean startNewNumber = true; // Allows continuous calculations

    public Calculator() {
        // Set up JFrame properties
        setTitle("Simple Calculator");
        setSize(375, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(227, 227, 227));

        // Display field (TextField)
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Helvetica", Font.BOLD, 36));
        display.setBackground(new Color(255, 255, 255));
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(display, BorderLayout.NORTH);

        // Make sure the display field always has focus
        display.setFocusable(true);
        display.requestFocusInWindow();

        // Keyboard Support
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                if (Character.isDigit(key)) {
                    handleButtonClick(String.valueOf(key));
                } else if ("+-*/=".indexOf(key) != -1) {
                    handleButtonClick(String.valueOf(key));
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    handleButtonClick("C");
                }
            }
        });

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBackground(new Color(255, 255, 255, 203));

        // Button labels (macOS like layout, no decimal button)
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        // Create buttons and add action listeners
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Helvetica", Font.BOLD, 24));
            button.setBackground(new Color(230, 230, 230)); // Light grey for number buttons
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Border style
            button.addActionListener(this);
            panel.add(button);
        }

        // Adding the panel to the JFrame
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleButtonClick(e.getActionCommand());
        display.requestFocusInWindow(); // Keep keyboard input active
    }

    private void handleButtonClick(String command) {
        try {
            if (command.matches("[0-9]")) { // If number is clicked
                if (startNewNumber) {
                    display.setText(command);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + command);
                }
            } else if (command.matches("[/*\\-+]")) { // If operator is clicked
                num1 = Integer.parseInt(display.getText());
                operator = command;
                startNewNumber = true;
            } else if (command.equals("=")) { // If equals button is clicked
                num2 = Integer.parseInt(display.getText());
                result = calculate(num1, num2, operator);
                display.setText(String.valueOf(result));
                num1 = result; // Allow continuous calculations
                startNewNumber = true;
            } else if (command.equals("C")) { // Clear button
                display.setText("");
                num1 = num2 = result = 0;
                operator = "";
                startNewNumber = true;
            }
        } catch (Exception ex) {
            display.setText("Error");
            startNewNumber = true;
        }
    }

    private int calculate(int num1, int num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": return (num2 == 0) ? 0 : num1 / num2; // Handle division by zero
            default: return num2;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
