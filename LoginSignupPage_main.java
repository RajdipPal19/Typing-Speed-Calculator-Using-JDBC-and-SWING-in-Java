import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginSignupPage_main extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JButton closeButton;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public LoginSignupPage_main() {
        setTitle("Login/Signup Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setUndecorated(true);

        setLayout(new BorderLayout());


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        formPanel.setBackground(new Color(218, 227, 122));


        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 24));
        signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 24));
        closeButton = new JButton("Close");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        formPanel.add(usernameField, gbc);
        gbc.gridy++;
        formPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        formPanel.add(passwordField, gbc);
        GridBagConstraints loginButtonGbc = new GridBagConstraints();
        loginButtonGbc.gridx = 0;
        loginButtonGbc.gridy = 4;
        loginButtonGbc.gridwidth = 1;
        loginButtonGbc.anchor = GridBagConstraints.CENTER;
        loginButtonGbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(loginButton, loginButtonGbc);
        GridBagConstraints signupButtonGbc = new GridBagConstraints();
        signupButtonGbc.gridx = 1;
        signupButtonGbc.gridy = 4;
        signupButtonGbc.gridwidth = 1;
        signupButtonGbc.anchor = GridBagConstraints.CENTER;
        signupButtonGbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(signupButton, signupButtonGbc);


        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setBackground(new Color(246, 167, 38));
        closePanel.add(closeButton);


        add(formPanel, BorderLayout.CENTER);
        add(closePanel, BorderLayout.NORTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "rajdip", "rajdip123");
            statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Could not connect to the database.");
            System.exit(1);
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Perform validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both username and password.");
            return;
        }

        try {
            String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                dispose();
                HomePage homePage = new HomePage(username);
                homePage.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Could not execute SQL query.");
        }
    }

    private void signup() {
        SignupPage signupPage = new SignupPage();
        signupPage.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginSignupPage_main loginSignupPage = new LoginSignupPage_main();
                loginSignupPage.setVisible(true);
            }
        });
    }


    class SignupPage extends JFrame {
        private JTextField signupUsernameField;
        private JPasswordField signupPasswordField;
        private JButton signupConfirmButton;

        public SignupPage() {
            setTitle("Signup Page");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
            setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(15, 15, 15, 15);


            JLabel signupUsernameLabel = new JLabel("New Username:");
            signupUsernameField = new JTextField(25);
            signupUsernameField.setFont(new Font("Arial", Font.PLAIN, 20));
            JLabel signupPasswordLabel = new JLabel("New Password:");
            signupPasswordField = new JPasswordField(25);
            signupPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));
            signupConfirmButton = new JButton("Confirm");


            inputPanel.add(signupUsernameLabel, gbc);
            gbc.gridy++;
            inputPanel.add(signupUsernameField, gbc);
            gbc.gridy++;
            inputPanel.add(signupPasswordLabel, gbc);
            gbc.gridy++;
            inputPanel.add(signupPasswordField, gbc);


            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton redirectToLoginButton = new JButton("Already have an account? Login here");


            buttonPanel.add(signupConfirmButton);
            buttonPanel.add(redirectToLoginButton);


            add(inputPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            signupConfirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    signupConfirm();
                }
            });

            redirectToLoginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new LoginSignupPage_main().setVisible(true);
                }
            });
        }
        private void signupConfirm() {
            String newUsername = signupUsernameField.getText();
            String newPassword = new String(signupPasswordField.getPassword());


            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both username and password.");
                return;
            }


            try {
                String checkQuery = "SELECT * FROM users WHERE username='" + newUsername + "'";
                resultSet = statement.executeQuery(checkQuery);

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.");
                } else {
                    String insertQuery = "INSERT INTO users (username, password) VALUES ('" + newUsername + "', '" + newPassword + "')";
                    statement.executeUpdate(insertQuery);
                    JOptionPane.showMessageDialog(null, "Signup successful!");

                    dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Could not execute SQL query.");
            }
        }
    }
}
