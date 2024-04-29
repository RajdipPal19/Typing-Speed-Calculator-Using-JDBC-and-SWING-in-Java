import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class ColorChangeApp extends JFrame {
    private JLabel wordLabel;
    private int currentIndex = 0;
    private String[] words = {"persistent", "resilience", "determination", "ambition", "iloveprogramming"};
    private String[] sentences = {"Type the correct letters to change the color!", "Test your typing skills!", "Enjoy learning Java!"};
    int min = 0;
    int max = 5;
    Random random = new Random();
    int randomNumber = random.nextInt(max - min + 1) + min;
    private String word = words[randomNumber];
    private Clip correctClip;
    private Clip wrongClip;

    private JLabel sentenceLabel;



    public ColorChangeApp() {
        setTitle("Color Change App");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.YELLOW);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel js = new JLabel("Stay focused and aim for precision! Every correct keystroke brings you closer to mastering the word");
        js.setHorizontalAlignment(SwingConstants.CENTER);
        js.setFont(new Font("Arial", Font.PLAIN, 30));
        add(js, BorderLayout.NORTH);

        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Arial", Font.BOLD, 72));
        updateWordLabel();
        centerPanel.add(wordLabel, gbc);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton homeButton = new JButton("Home");
        JButton practiceAgainButton = new JButton("Practice Again");

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the program
                dispose();
            }
        });

        practiceAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                main(new String[0]);
            }
        });

        buttonPanel.add(homeButton);
        buttonPanel.add(practiceAgainButton);
        add(buttonPanel, BorderLayout.SOUTH);


        try {
            AudioInputStream correctStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Rajdip\\Desktop\\java pro\\cor.wav"
            ));
            AudioInputStream wrongStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Rajdip\\Desktop\\java pro\\wor.wav"
            ));
            correctClip = AudioSystem.getClip();
            wrongClip = AudioSystem.getClip();
            correctClip.open(correctStream);
            wrongClip.open(wrongStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char pressedChar = Character.toLowerCase(e.getKeyChar());
                char expectedChar = Character.toLowerCase(word.charAt(currentIndex));
                if (pressedChar == expectedChar) {
                    currentIndex++;
                    updateWordLabel();
                    wordLabel.setForeground(Color.GREEN);
                    correctClip.setFramePosition(0);
                    correctClip.start();
                } else {
                    wordLabel.setForeground(Color.RED);
                    wrongClip.setFramePosition(0);
                    wrongClip.start();
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    private void updateWordLabel() {
        StringBuilder sb = new StringBuilder();
        int i=0;
        while(i<word.length()) {
            if (i < currentIndex) {
                sb.append("<font color='green'>").append(word.charAt(i)).append("</font>");

            } else {
                sb.append("<font color='black'>" + word.charAt(i) + "</font>");
            }
            i++;
        }
        wordLabel.setText("<html><div style='text-align: center;'>" + sb.toString() + "</div></html>");
    }

    public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                ColorChangeApp app = new ColorChangeApp();
                app.setVisible(true);
            });

    }
}
