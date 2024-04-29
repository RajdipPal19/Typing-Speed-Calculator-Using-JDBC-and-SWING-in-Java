import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class TypingHistoryPage extends JFrame {
    private String username = "rajdip";
    private JTable historyTable;

    public TypingHistoryPage(String username) {
        this.username = username;

        historyTable = new JTable();

        fetchTypingHistory();

        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane);

        setTitle("Typing History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchTypingHistory() {
        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "rajdip", "rajdip123");

            String sql = "SELECT date, speed, accuracy FROM history WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);


            ResultSet resultSet = statement.executeQuery();


            Vector<String> columns = new Vector<>();
            columns.add("Date");
            columns.add("Speed (wpm)");
            columns.add("Accuracy (%)");
            Vector<Vector<Object>> data = new Vector<>();


            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("date"));
                row.add(resultSet.getInt("speed"));
                row.add(resultSet.getInt("accuracy"));
                data.add(row);
            }


            resultSet.close();
            statement.close();
            conn.close();


            if (data.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "No typing history found for this user.");
                    dispose();
                });
                return;
            }


            DefaultTableModel model = new DefaultTableModel(data, columns);
            historyTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to fetch typing history: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TypingHistoryPage("rajdip"));
    }
}
