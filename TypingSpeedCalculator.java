import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TypingSpeedCalculator extends JFrame {
    private JLabel wordLabel;
    private JLabel timerLabel;
    private String username = "";

    private double time = 0;
    private int currentIndex = 0;
    private double timeLeft;
    private String paragraph = ("Once upon a time in a small village nestled between towering mountains and lush forests, there lived a young boy named Kavi. Kavi was known for his unwavering determination and boundless enthusiasm, but he faced a challenge that seemed insurmountable - he wanted to climb the tallest peak in the entire region, Mount Serenity. Mount Serenity was legendary among the villagers, its summit hidden amidst swirling mists and treacherous terrain. Many had attempted the climb, but few had succeeded. Despite the warnings and doubts from others, Kavi remained undeterred. He believed that with enough persistence and courage, he could conquer the mountain. Armed with little more than a backpack filled with supplies, Kavi embarked on his journey. Along the way, he encountered various obstacles - steep cliffs, raging rivers, and dense forests. Each challenge tested his resolve, but he refused to give up. With each step, he grew stronger, more determined to reach the summit.As Kavi climbed higher and higher");

    private Clip correctClip;
    private Clip wrongClip;

    private StringBuilder userInput = new StringBuilder();
    private int correctKeystrokes = 0;
    private int totalKeystrokes = 0;
    private Timer timer;
    private int space = 0;
    public TypingSpeedCalculator(double timeInSeconds, String name){
        username = name;
        time = timeInSeconds;
        this.timeLeft = timeInSeconds;
        setTitle("Typing Speed Calculator");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(241, 131, 120));

        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        wordLabel.setVerticalAlignment(SwingConstants.CENTER);
        updateWordLabel();
        add(wordLabel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time Left: " + timeLeft + " sec");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(timerLabel, BorderLayout.NORTH);

        try {
            AudioInputStream correctStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Rajdip\\Desktop\\java pro\\cor.wav"));
            AudioInputStream wrongStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Rajdip\\Desktop\\java pro\\wor.wav"));
            correctClip = AudioSystem.getClip();
            wrongClip = AudioSystem.getClip();
            correctClip.open(correctStream);
            wrongClip.open(wrongStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }


        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLeft--;
                if (timeLeft >= 0) {
                    timerLabel.setText("Time Left: " + timeLeft + " sec");
                } else {
                    timer.cancel();
                    showResultPopup();
                    timerLabel.setText("Time's up!");
                }
            }
        }, 1000, 1000);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char pressedChar = Character.toLowerCase(e.getKeyChar());
                char expectedChar = Character.toLowerCase(paragraph.charAt(currentIndex));
                if (pressedChar == expectedChar) {
                    if(expectedChar==' ')space++;
                    currentIndex++;
                    correctKeystrokes++;
                    userInput.append(pressedChar);
                    updateWordLabel();
                    wordLabel.setForeground(Color.GREEN);
                    playSound(correctClip);
                } else {
                    userInput.append(pressedChar);
                    wordLabel.setForeground(Color.RED);
                    playSound(wrongClip);
                }
                totalKeystrokes++;
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    private void updateWordLabel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paragraph.length(); i++) {
            char currentChar = paragraph.charAt(i);
            if (i == currentIndex) {
                sb.append("<font color='blue'>").append(currentChar).append("</font>");
            } else if (i < currentIndex) {
                sb.append("<font color='yellow'>").append(currentChar).append("</font>");
            } else {
                sb.append("<font color='black'>").append(currentChar).append("</font>");
            }
        }
        wordLabel.setText("<html><div style='text-align: center;'>" + sb.toString() + "</div></html>");
    }

    private void playSound(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private void showResultPopup() {
        double accuracy = (double) correctKeystrokes / totalKeystrokes * 100;
        double wpm;
        if(time==30)wpm = (space)*2;
        else{
            wpm = (space+1)/(time/60);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel resultLabel = new JLabel("<html><div style='text-align: center;'>" +
                String.format("<span style='font-size: 16px;'>%.2f</span><br>", wpm) +
                "Words Per Minute<br>" +
                String.format("<span style='font-size: 14px;'>Accuracy: %.2f%%</span></div></html>", accuracy));
                resultLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        panel.add(resultLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton startAgainButton = new JButton("Start Again");
        JButton homeScreenButton = new JButton("Back to Home Screen");

        startAgainButton.addActionListener(e -> {
            dispose();
            TypingSpeedCalculator typingSpeedCalculator = new TypingSpeedCalculator(time,username);
            typingSpeedCalculator.setVisible(true);
        });

        homeScreenButton.addActionListener(e -> {
            dispose();
            HomePage h = new HomePage(username);
            h.setVisible(true);
        });

        buttonPanel.add(startAgainButton);
        buttonPanel.add(homeScreenButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        storeResultInDatabase(username, (int)wpm, (int)accuracy);



        JOptionPane.showOptionDialog(this, panel, "Typing Result", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
    }


    public static void main(String[] args) {
        int initialTime = Integer.parseInt(args[0]);
        SwingUtilities.invokeLater(() -> {
            TypingSpeedCalculator app = new TypingSpeedCalculator(initialTime,"rajdip");
            app.setVisible(true);
        });
    }
    private void storeResultInDatabase(String username, int wpm, int accuracy) {
        String url = "jdbc:mysql://localhost:3306/login";
        String user = "rajdip";
        String password = "rajdip123";

        // SQL query to insert data into the database
        String sql = "INSERT INTO history (username, date, speed, accuracy) VALUES (?, CURDATE(), ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, username);
            statement.setInt(2, wpm);
            statement.setInt(3, accuracy);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
