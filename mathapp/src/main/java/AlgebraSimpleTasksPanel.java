import javax.swing.*;
import java.awt.*;

public class AlgebraSimpleTasksPanel extends JPanel {
    public AlgebraSimpleTasksPanel() {
        setLayout(new BorderLayout());

        String[] tasks = {"Выберите задачу", "1", "2", "3", "4", "5"};
        JComboBox<String> taskComboBox = new JComboBox<>(tasks);

        JPanel taskContentPanel = new JPanel(new CardLayout());
        taskContentPanel.add(new JPanel(), "Пусто");
        taskContentPanel.add(createTaskPanel("Решите уравнение: 3x + 5 = 11", "2", "3*2 + 5 = 11, следовательно x = 2"), "1");
        taskContentPanel.add(createTaskPanel("Решите уравнение: 2x - 7 = 3", "5", "2*5 - 7 = 3, следовательно x = 5"), "2");
        taskContentPanel.add(createTaskPanel("Решите уравнение: 4x + 2 = 10", "2", "4*2 + 2 = 10, следовательно x = 2"), "3");
        taskContentPanel.add(createTaskPanel("Решите уравнение: 5x - 9 = 16", "5", "5*5 - 9 = 16, следовательно x = 5"), "4");
        taskContentPanel.add(createTaskPanel("Решите уравнение: 7x + 1 = 15", "2", "7*2 + 1 = 15, следовательно x = 2"), "5");

        taskComboBox.addActionListener(e -> {
            String selectedTask = (String) taskComboBox.getSelectedItem();
            CardLayout cl = (CardLayout) (taskContentPanel.getLayout());
            cl.show(taskContentPanel, selectedTask);
        });

        add(taskComboBox, BorderLayout.NORTH);
        add(taskContentPanel, BorderLayout.CENTER);
    }

    private JPanel createTaskPanel(String question, String answer, String explanation) {
        JPanel taskPanel = new JPanel(new BorderLayout());

        JLabel questionLabel = new JLabel(question);
        taskPanel.add(questionLabel, BorderLayout.NORTH);

        JTextField answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(100, 20));
        taskPanel.add(answerField, BorderLayout.CENTER);

        JButton checkButton = new JButton("Проверить");
        JLabel resultLabel = new JLabel();
        checkButton.addActionListener(e -> {
            String userAnswer = answerField.getText().trim();
            if (isNumeric(userAnswer)) {
                if (userAnswer.equals(answer)) {
                    resultLabel.setText("Правильно!");
                    resultLabel.setForeground(Color.GREEN);
                } else {
                    resultLabel.setText("Неправильно. Правильный ответ: " + answer + ". " + explanation);
                    resultLabel.setForeground(Color.RED);
                }
            } else {
                resultLabel.setText("Введите числовое значение.");
                resultLabel.setForeground(Color.RED);
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(checkButton, BorderLayout.WEST);
        bottomPanel.add(resultLabel, BorderLayout.CENTER);

        taskPanel.add(bottomPanel, BorderLayout.SOUTH);

        return taskPanel;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
