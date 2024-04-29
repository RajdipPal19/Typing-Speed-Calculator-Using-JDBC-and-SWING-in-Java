import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
;

class Guidelines extends JFrame {
    public Guidelines() {
        setTitle("Typing Speed Test Guidelines");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(240, 240, 240));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Typing Speed Test Guidelines");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(30, 144, 255));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JTextArea guidelinesTextArea = new JTextArea();
        guidelinesTextArea.setEditable(false);
        guidelinesTextArea.setLineWrap(true);
        guidelinesTextArea.setWrapStyleWord(true);
        guidelinesTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        guidelinesTextArea.setText(
                "Welcome to the Typing Speed Test!\n\n" +
                        "Instructions:\n" +
                        "- Type the given text into the text area as quickly and accurately as possible.\n" +
                        "- Press the 'Start' button to begin the test.\n" +
                        "- Once the test starts, the timer will begin counting.\n" +
                        "- Press the 'Submit' button after you've finished typing the text.\n\n" +
                        "Cautions:\n" +
                        "- Ensure that you are comfortable and relaxed before starting the test.\n" +
                        "- Avoid distractions and interruptions during the test.\n" +
                        "- Focus on accuracy as well as speed.\n" +
                        "- Take breaks if needed to prevent fatigue.\n\n" +
                        "Enjoy the typing speed test!"
        );
        guidelinesTextArea.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(guidelinesTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);


        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.setBackground(new Color(30, 144, 255));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(backButton, BorderLayout.SOUTH);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Guidelines guidelines = new Guidelines();
            guidelines.setVisible(true);
        });
    }
}
