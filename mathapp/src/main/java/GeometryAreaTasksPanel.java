import javax.swing.*;
import java.awt.*;

public class GeometryAreaTasksPanel extends JPanel {
    public GeometryAreaTasksPanel() {
        setLayout(new BorderLayout());

        String[] tasks = {"Выберите задачу", "1", "2", "3", "4", "5"};
        JComboBox<String> taskComboBox = new JComboBox<>(tasks);

        JPanel taskContentPanel = new JPanel(new CardLayout());
        taskContentPanel.add(new JPanel(), "Пусто"); // Пустая панель для начального состояния
        taskContentPanel.add(createTaskPanel("Найдите площадь квадрата со стороной 5 см", "25 см²", "Площадь квадрата S = a^2, где a = 5 см. Следовательно, S = 25 см²"), "1");
        taskContentPanel.add(createTaskPanel("Найдите площадь круга с радиусом 3 см", "28.27 см²", "Площадь круга S = πr^2, где r = 3 см. Следовательно, S ≈ 28.27 см²"), "2");
        taskContentPanel.add(createTaskPanel("Найдите площадь треугольника с основанием 6 см и высотой 4 см", "12 см²", "Площадь треугольника S = 1/2 * b * h, где b = 6 см, h = 4 см. Следовательно, S = 12 см²"), "3");
        taskContentPanel.add(createTaskPanel("Найдите площадь прямоугольника со сторонами 8 см и 3 см", "24 см²", "Площадь прямоугольника S = a * b, где a = 8 см, b = 3 см. Следовательно, S = 24 см²"), "4");
        taskContentPanel.add(createTaskPanel("Найдите площадь параллелограмма с основанием 7 см и высотой 5 см", "35 см²", "Площадь параллелограмма S = a * h, где a = 7 см, h = 5 см. Следовательно, S = 35 см²"), "5");

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
