import javax.swing.*;
import java.awt.*;

public class AlgebraQuadraticTasksPanel extends JPanel {
    public AlgebraQuadraticTasksPanel() {
        setLayout(new BorderLayout());

        String[] tasks = {"Выберите задачу", "1", "2", "3"};
        JComboBox<String> taskComboBox = new JComboBox<>(tasks);

        JPanel taskContentPanel = new JPanel(new CardLayout());
        taskContentPanel.add(new JPanel(), "Пусто");
        taskContentPanel.add(createTaskPanel("Решите уравнение: x^2 - 5x + 6 = 0", "x1 = 2, x2 = 3", "Дискриминант D = 1, корни уравнения: x1 = 2, x2 = 3"), "1");
        taskContentPanel.add(createTaskPanel("Решите уравнение: x^2 + 4x + 4 = 0", "x = -2", "Уравнение можно представить как (x + 2)^2 = 0, следовательно x = -2"), "2");
        taskContentPanel.add(createTaskPanel("Решите уравнение: x^2 - 4x + 4 = 0", "x = 2", "Уравнение можно представить как (x - 2)^2 = 0, следовательно x = 2"), "3");
        taskContentPanel.add(createTaskPanel("Решите уравнение: x^2 + 2x - 3 = 0", "x1 = 1, x2 = -3", "Дискриминант D = 16, корни уравнения: x1 = 1, x2 = -3"), "4");
        taskContentPanel.add(createTaskPanel("Решите уравнение: x^2 - 6x + 9 = 0", "x = 3", "Уравнение можно представить как (x - 3)^2 = 0, следовательно x = 3"), "5");

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
            if (isNumericList(userAnswer)) {
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

    private boolean isNumericList(String str) {
        String[] parts = str.split(",");
        for (String part : parts) {
            if (!isNumeric(part.trim())) {
                return false;
            }
        }
        return true;
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
