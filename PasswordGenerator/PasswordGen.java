import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class PasswordGeneratorUI extends JFrame {
    private JCheckBox lowercaseCheckBox;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox numericCheckBox;
    private JCheckBox specialCheckBox;
    private JTextField lengthField;
    private JTextArea passwordArea;

    public PasswordGeneratorUI() {
        setTitle("Password Generator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel optionsPanel = new JPanel(new GridLayout(4, 2));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));

        lowercaseCheckBox = new JCheckBox("Include Lowercase Letters");
        uppercaseCheckBox = new JCheckBox("Include Uppercase Letters");
        numericCheckBox = new JCheckBox("Include Numeric Characters");
        specialCheckBox = new JCheckBox("Include Special Characters");

        optionsPanel.add(lowercaseCheckBox);
        optionsPanel.add(uppercaseCheckBox);
        optionsPanel.add(numericCheckBox);
        optionsPanel.add(specialCheckBox);

        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lengthLabel = new JLabel("Password Length:");
        lengthField = new JTextField(10);
        lengthPanel.add(lengthLabel);
        lengthPanel.add(lengthField);

        JPanel generatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Generate Password");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });
        generatePanel.add(generateButton);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Generated Password"));

        passwordArea = new JTextArea(5, 20);
        passwordArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(passwordArea);
        passwordPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(optionsPanel);
        mainPanel.add(lengthPanel);
        mainPanel.add(generatePanel);
        mainPanel.add(passwordPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void generatePassword() {
        int length;
        try {
            length = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid password length.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (length <= 0) {
            JOptionPane.showMessageDialog(this, "Password length must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean includeLowercase = lowercaseCheckBox.isSelected();
        boolean includeUppercase = uppercaseCheckBox.isSelected();
        boolean includeNumeric = numericCheckBox.isSelected();
        boolean includeSpecial = specialCheckBox.isSelected();

        String password = generatePassword(length, includeLowercase, includeUppercase, includeNumeric, includeSpecial);
        passwordArea.setText(password);
    }

    private String generatePassword(int length, boolean includeLowercase, boolean includeUppercase,
                                    boolean includeNumeric, boolean includeSpecial) {
        StringBuilder characters = new StringBuilder();
        if (includeLowercase) characters.append("abcdefghijklmnopqrstuvwxyz");
        if (includeUppercase) characters.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (includeNumeric) characters.append("0123456789");
        if (includeSpecial) characters.append("!@#$%^&*()-_+=<>?");

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PasswordGeneratorUI();
            }
        });
    }
}
