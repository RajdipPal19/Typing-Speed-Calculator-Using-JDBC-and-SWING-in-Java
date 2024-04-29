import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HomePage extends JFrame {
    private JLabel usernameLabel;
    private String username = "";
    public HomePage(String name) {
        username = name;
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setBackground(new Color(197, 250, 64));



        JPanel navbarPanel = new JPanel(new BorderLayout());
        JPanel navbarButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameLabel = new JLabel("Welcome, " + username + "!");
        navbarPanel.add(usernameLabel, BorderLayout.EAST);

        JButton option2Button = new JButton("Training");
        JButton option1Button = new JButton("Guidelines");
        JButton option3Button = new JButton("Tutorial");
        JButton option4Button = new JButton("History");
        JButton logout = new JButton("Log Out");
        option1Button.setBackground(Color.ORANGE);
        option2Button.setBackground(Color.ORANGE);
        option3Button.setBackground(Color.ORANGE);
        option4Button.setBackground(Color.ORANGE);
        logout.setBackground(Color.ORANGE);


        navbarButtonsPanel.add(option1Button);
        navbarButtonsPanel.add(option2Button);
        navbarButtonsPanel.add(option3Button);
        navbarButtonsPanel.add(option4Button);
        navbarButtonsPanel.add(logout);
        navbarPanel.add(navbarButtonsPanel, BorderLayout.CENTER);

        add(navbarPanel, BorderLayout.NORTH);


        Font font = new Font("Arial", Font.BOLD, 26);
        usernameLabel.setFont(font);


        option1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Guidelines guidelines = new Guidelines();
                guidelines.setVisible(true);
            }
        });

        option2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ColorChangeApp option2 = new ColorChangeApp();
                option2.setVisible(true);
            }
        });
        option3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playVideo();

            }
        });
        option4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               TypingHistoryPage t = new TypingHistoryPage(username);
               t.setVisible(true);
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginSignupPage_main l = new LoginSignupPage_main();
                l.setVisible(true);
                dispose();
            }
        });


        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton button1 = new JButton("30 seconds");
        JButton button2 = new JButton("1 Minutes");
        JButton button3 = new JButton("3 Minutes");
        JButton button4 = new JButton("Custom");
        centerPanel.add(button1);
        centerPanel.add(button2);
        centerPanel.add(button3);
        centerPanel.add(button4);
        add(centerPanel, BorderLayout.CENTER);
        button1.setBackground(Color.BLUE);
        button2.setBackground(Color.YELLOW);
        button3.setBackground(Color.red);
        button3.setForeground(Color.white);
        button4.setBackground(Color.GREEN);
        button1.setForeground(Color.WHITE);
        button1.setFont(new Font("Arial", Font.PLAIN, 26));
        button2.setFont(new Font("Arial", Font.PLAIN, 26));
        button3.setFont(new Font("Arial", Font.PLAIN, 26));
        button4.setFont(new Font("Arial", Font.PLAIN, 26));


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypingSpeedCalculator t = new TypingSpeedCalculator(30,username);
                t.setVisible(true);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TypingSpeedCalculator t = new TypingSpeedCalculator(60,username);
                t.setVisible(true);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypingSpeedCalculator t = new TypingSpeedCalculator(180,username);
                t.setVisible(true);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userInput = JOptionPane.showInputDialog(null, "Enter the time in Minutes:");
                if (userInput != null) {
                    try {
                        double number = Double.parseDouble(userInput);
                        TypingSpeedCalculator t = new TypingSpeedCalculator((int)number*60,username);
                        t.setVisible(true);

                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomePage homePage = new HomePage("");
                homePage.setVisible(true);
            }
        });
    }

    public void playVideo() {int space = 0;
        File videoFile = new File("C:\\Users\\Rajdip\\Desktop\\java pro\\How to Type Faster.mp4");
        try {
            Desktop.getDesktop().open(videoFile);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error playing video: " + e.getMessage());
        }
    }
}