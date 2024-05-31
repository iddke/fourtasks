import javax.swing.*;
import java.awt.*;

public class GeometryBasicShapesTasksPanel extends JPanel {
    public GeometryBasicShapesTasksPanel() {
        setLayout(new BorderLayout());

        String[] tasks = {"Выберите задачу", "1", "2", "3", "4", "5"};
        JComboBox<String> taskComboBox = new JComboBox<>(tasks);

        JPanel taskContentPanel = new JPanel(new CardLayout());
        taskContentPanel.add(new JPanel(), "Пусто"); // Пустая панель для начального состояния
        taskContentPanel.add(createTaskPanel("Сколько углов у треугольника?", "3", "Треугольник имеет 3 угла"), "1");
        taskContentPanel.add(createTaskPanel("Сколько сторон у квадрата?", "4", "Квадрат имеет 4 стороны"), "2");
        taskContentPanel.add(createTaskPanel("Сколько вершин у пятиугольника?", "5", "Пятиугольник имеет 5 вершин"), "3");
        taskContentPanel.add(createTaskPanel("Сколько диагоналей у прямоугольника?", "2", "Прямоугольник имеет 2 диагонали"), "4");
        taskContentPanel.add(createTaskPanel("Сколько осей симметрии у круга?", "бесконечно", "Круг имеет бесконечно много осей симметрии"), "5");

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
            if (isNumericOrText(userAnswer)) {
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

    private boolean isNumericOrText(String str) {
        return isNumeric(str) || str.equals("бесконечно");
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
