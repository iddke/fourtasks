import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Math Learning App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 960);

        JPanel mainPanel = new JPanel(new CardLayout());

        JPanel startPanel = createStartPanel(mainPanel);
        JPanel studyPanel = createStudyPanel(mainPanel);
        JPanel taskPanel = createTaskPanel(mainPanel);

        mainPanel.add(startPanel, "Начало");
        mainPanel.add(studyPanel, "Изучение тем");
        mainPanel.add(taskPanel, "Решение задач");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createStartPanel(JPanel mainPanel) {
        JPanel startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        String[] options = {"Выберите действие", "Изучение тем", "Решение задач"};
        JComboBox<String> optionsComboBox = new JComboBox<>(options);
        optionsComboBox.setPreferredSize(new Dimension(200, 30));

        optionsComboBox.addActionListener(e -> {
            String selectedOption = (String) optionsComboBox.getSelectedItem();
            if ("Изучение тем".equals(selectedOption)) {
                showPanel(mainPanel, "Изучение тем");
            } else if ("Решение задач".equals(selectedOption)) {
                showPanel(mainPanel, "Решение задач");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        startPanel.add(optionsComboBox, gbc);

        return startPanel;
    }

    private static JPanel createStudyPanel(JPanel mainPanel) {
        JPanel studyPanel = new JPanel(new BorderLayout());

        String[] subjects = {"Выберите предмет", "Алгебра", "Геометрия"};
        JComboBox<String> subjectComboBox = new JComboBox<>(subjects);

        JPanel topicPanel = new JPanel(new BorderLayout());

        subjectComboBox.addActionListener(e -> {
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            if ("Алгебра".equals(selectedSubject)) {
                topicPanel.removeAll();
                JPanel algebraPanel = createAlgebraPanel();
                topicPanel.add(algebraPanel, BorderLayout.CENTER);
                topicPanel.revalidate();
                topicPanel.repaint();
            } else if ("Геометрия".equals(selectedSubject)) {
                topicPanel.removeAll();
                JPanel geometryPanel = createGeometryPanel();
                topicPanel.add(geometryPanel, BorderLayout.CENTER);
                topicPanel.revalidate();
                topicPanel.repaint();
            }
        });

        JButton backButton = new JButton("Назад");
        backButton.addActionListener(e -> showPanel(mainPanel, "Начало"));

        JPanel comboPanel = new JPanel();
        comboPanel.add(subjectComboBox);

        studyPanel.add(comboPanel, BorderLayout.NORTH);
        studyPanel.add(topicPanel, BorderLayout.CENTER);
        studyPanel.add(backButton, BorderLayout.SOUTH);

        return studyPanel;
    }

    private static JPanel createAlgebraPanel() {
        JPanel algebraPanel = new JPanel(new BorderLayout());

        String[] topics = {"Выберите тему", "Простые уравнения", "Квадратные уравнения"};
        JComboBox<String> topicComboBox = new JComboBox<>(topics);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        topicComboBox.addActionListener(e -> {
            String selectedTopic = (String) topicComboBox.getSelectedItem();
            switch (selectedTopic) {
                case "Простые уравнения":
                    descriptionArea.setText("Алгебра: Простые уравнения\n\n" +
                            "Простые уравнения - это уравнения, которые содержат только одну переменную и включают базовые операции, такие как сложение, вычитание, умножение и деление. Изучение простых уравнений в алгебре помогает развить навыки аналитического мышления и решения проблем.\n" +
                            "Решение такого уравнения заключается в нахождении значения x, при котором уравнение становится верным.\n" + "Формула ax+b=c \n" +"Пример такого уравнения: 2x+3=9"
                            );
                    break;
                case "Квадратные уравнения":
                    descriptionArea.setText("Алгебра: Квадратные уравнения\n\n" +
                            "Квадратное уравнение - это уравнение вида ax^2 + bx + c = 0, где a, b и c - числа.\n" +
                            "Решение квадратных уравнений включает нахождение корней x, при которых уравнение верно. Основной метод - использование дискриминанта. Дискриминант находится по формуле: b^2-4ac .\n"+
                            "Квадратное уравнение отличается от линейного тем, что степень переменной x равна 2.\n" +
                            "Пример такого уравнения: 2x^2+3x-2=0");
                    break;
                default:
                    descriptionArea.setText("");
                    break;
            }
        });

        JPanel comboPanel = new JPanel();
        comboPanel.add(topicComboBox);

        algebraPanel.add(comboPanel, BorderLayout.NORTH);
        algebraPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        return algebraPanel;
    }

    private static JPanel createGeometryPanel() {
        JPanel geometryPanel = new JPanel(new BorderLayout());

        String[] topics = {"Выберите тему", "Основные фигуры", "Площади фигур"};
        JComboBox<String> topicComboBox = new JComboBox<>(topics);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        topicComboBox.addActionListener(e -> {
            String selectedTopic = (String) topicComboBox.getSelectedItem();
            switch (selectedTopic) {
                case "Основные фигуры":
                    descriptionArea.setText("Геометрия: Основные фигуры\n\n" +
                            "Определение\n"+
                            "Основные геометрические фигуры включают треугольники, квадраты, прямоугольники и круги. Каждая из этих фигур имеет свои уникальные свойства, такие как длина сторон, углы и симметрия." +
                            "Эти свойства используются для решения различных задач в геометрии и приложениях в других областях науки и техники.\n" +
                            "Треугольники" +
                            "Треугольник – это фигура, состоящая из трех сторон и трех углов. Треугольники классифицируются по длинам сторон и по величинам углов.\n" +
                            "Классификация по сторонам:\n" +
                            "Равносторонний треугольник: все три стороны равны.\n" +
                            "Равнобедренный треугольник: две стороны равны.\n" +
                            "Разносторонний треугольник: все три стороны различны.\n" +
                            "Классификация по углам:\n" +
                            "Остроугольный треугольник: все углы острые (менее 90°).\n" +
                            "Прямоугольный треугольник: один угол прямой (90°).\n" +
                            "Тупоугольный треугольник: один угол тупой (более 90°).\n" +
                            "Свойства треугольников:\n" +
                            "Сумма углов треугольника равна 180°.\n" +
                            "В равнобедренном треугольнике углы при основании равны.\n" +
                            "В прямоугольном треугольнике выполняется теорема Пифагора:a^2+b^2=c^2, где c-гипотенуза\n" +
                            "Квадраты\n" +
                            "Квадрат – это прямоугольник, у которого все стороны равны и все углы прямые.\n" +
                            "Свойства квадрата:\n" +
                            "Все стороны равны.\n" +
                            "Все углы равны 90°.\n" +
                            "Диагонали квадрата равны и пересекаются под прямым углом.\n" +
                            "Диагонали квадрата делят его на четыре равных прямоугольных треугольника.\n" +
                            "Формулы: Периметр: P=4a(a - сторона квадрата); Площадь: S=a^2.\n" +
                            "Прямоугольники\n" +
                            "Прямоугольник – это четырехугольник, у которого все углы прямые.\n" +
                            "Свойства прямоугольника: Противоположные стороны равны. Диагонали равны и пересекаются в точке, делящей каждую диагональ пополам.\n" +
                            "Формулы:\n" +
                            "Периметр:P=2(a+b), где a и b - длины сторон; Площадь:S=ab.");
                    break;
                case "Площади фигур":
                    descriptionArea.setText("Геометрия: Площади фигур\n\n" +
                            "Площадь фигуры - это мера пространства, занимаемого фигурой.\n" +
                            "Формулы для площади включают: S=а² для квадрата, S=πr² для круга, S=1/2*b*h для треугольника.\n" +
                            "Эти формулы являются основными инструментами для расчета площади различных геометрических фигур.");
                    break;
                default:
                    descriptionArea.setText("");
                    break;
            }
        });

        JPanel comboPanel = new JPanel();
        comboPanel.add(topicComboBox);

        geometryPanel.add(comboPanel, BorderLayout.NORTH);
        geometryPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        return geometryPanel;
    }

    private static JPanel createTaskPanel(JPanel mainPanel) {
        JPanel taskPanel = new JPanel(new BorderLayout());

        String[] subjects = {"Выберите предмет", "Задачи по алгебре", "Задачи по геометрии"};
        JComboBox<String> subjectComboBox = new JComboBox<>(subjects);

        JPanel taskTypePanel = new JPanel(new BorderLayout());

        subjectComboBox.addActionListener(e -> {
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            if ("Задачи по алгебре".equals(selectedSubject)) {
                taskTypePanel.removeAll();
                JPanel algebraTaskPanel = createAlgebraTaskPanel();
                taskTypePanel.add(algebraTaskPanel, BorderLayout.CENTER);
                taskTypePanel.revalidate();
                taskTypePanel.repaint();
            } else if ("Задачи по геометрии".equals(selectedSubject)) {
                taskTypePanel.removeAll();
                JPanel geometryTaskPanel = createGeometryTaskPanel();
                taskTypePanel.add(geometryTaskPanel, BorderLayout.CENTER);
                taskTypePanel.revalidate();
                taskTypePanel.repaint();
            }
        });

        JButton backButton = new JButton("Назад");
        backButton.addActionListener(e -> showPanel(mainPanel, "Начало"));

        JPanel comboPanel = new JPanel();
        comboPanel.add(subjectComboBox);

        taskPanel.add(comboPanel, BorderLayout.NORTH);
        taskPanel.add(taskTypePanel, BorderLayout.CENTER);
        taskPanel.add(backButton, BorderLayout.SOUTH);

        return taskPanel;
    }

    private static JPanel createAlgebraTaskPanel() {
        JPanel algebraTaskPanel = new JPanel(new BorderLayout());

        String[] topics = {"Выберите тему", "Простые уравнения", "Квадратные уравнения"};
        JComboBox<String> topicComboBox = new JComboBox<>(topics);

        JPanel taskContentPanel = new JPanel(new CardLayout());
        taskContentPanel.add(new JPanel(), "Пусто"); // Пустая панель для начального состояния
        taskContentPanel.add(createTasksPanel(new AlgebraSimpleTasksPanel()), "Простые уравнения");
        taskContentPanel.add(createTasksPanel(new AlgebraQuadraticTasksPanel()), "Квадратные уравнения");

        topicComboBox.addActionListener(e -> {
            String selectedTopic = (String) topicComboBox.getSelectedItem();
            CardLayout cl = (CardLayout) (taskContentPanel.getLayout());
            cl.show(taskContentPanel, selectedTopic);
        });

        JPanel comboPanel = new JPanel();
        comboPanel.add(topicComboBox);

        algebraTaskPanel.add(comboPanel, BorderLayout.NORTH);
        algebraTaskPanel.add(taskContentPanel, BorderLayout.CENTER);

        return algebraTaskPanel;
    }

    private static JPanel createGeometryTaskPanel() {
        JPanel geometryTaskPanel = new JPanel(new BorderLayout());

        String[] topics = {"Выберите тему", "Основные фигуры", "Площади фигур"};
        JComboBox<String> topicComboBox = new JComboBox<>(topics);

        JPanel taskContentPanel = new JPanel(new CardLayout());
        taskContentPanel.add(new JPanel(), "Пусто"); // Пустая панель для начального состояния
        taskContentPanel.add(createTasksPanel(new GeometryBasicShapesTasksPanel()), "Основные фигуры");
        taskContentPanel.add(createTasksPanel(new GeometryAreaTasksPanel()), "Площади фигур");

        topicComboBox.addActionListener(e -> {
            String selectedTopic = (String) topicComboBox.getSelectedItem();
            CardLayout cl = (CardLayout) (taskContentPanel.getLayout());
            cl.show(taskContentPanel, selectedTopic);
        });

        JPanel comboPanel = new JPanel();
        comboPanel.add(topicComboBox);

        geometryTaskPanel.add(comboPanel, BorderLayout.NORTH);
        geometryTaskPanel.add(taskContentPanel, BorderLayout.CENTER);

        return geometryTaskPanel;
    }

    private static JPanel createTasksPanel(JPanel tasksPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tasksPanel, BorderLayout.CENTER);
        return panel;
    }

    private static void showPanel(JPanel mainPanel, String panelName) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, panelName);
    }
}
