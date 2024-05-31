import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumApplication {
    private static final String DB_URL = "jdbc:mysql://it.vshp.online:3306/db_2bc5d9";
    private static final String DB_USER = "st_2bc5d9";
    private static final String DB_PASSWORD = "bec8ff935f6c";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Forum Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new MainPanel());
            frame.setVisible(true);
        });
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    static class MainPanel extends JPanel {
        private CardLayout cardLayout = new CardLayout();
        private JPanel mainPanel = this;
        private User loggedInUser;

        public MainPanel() {
            setLayout(cardLayout);

            JPanel loginPanel = new LoginPanel(this);
            JPanel registerPanel = new RegisterPanel(this);
            JPanel forumPanel = new ForumPanel(this);

            add(loginPanel, "login");
            add(registerPanel, "register");
            add(forumPanel, "forum");

            cardLayout.show(this, "login");
        }

        public void switchTo(String panelName) {
            cardLayout.show(this, panelName);
        }

        public void setLoggedInUser(User user) {
            this.loggedInUser = user;
        }

        public User getLoggedInUser() {
            return loggedInUser;
        }
    }

    static class LoginPanel extends JPanel {
        private JTextField usernameField = new JTextField(20);
        private JPasswordField passwordField = new JPasswordField(20);
        private JButton loginButton = new JButton("Login");
        private JButton registerButton = new JButton("Register");

        public LoginPanel(MainPanel mainPanel) {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;

            add(new JLabel("Username:"), gbc);
            gbc.gridx++;
            add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Password:"), gbc);
            gbc.gridx++;
            add(passwordField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(loginButton, gbc);

            gbc.gridy++;
            add(registerButton, gbc);

            loginButton.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                UserController userController = new UserController();

                User user = userController.loginUser(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(this, "Login successful");
                    mainPanel.setLoggedInUser(user);
                    mainPanel.switchTo("forum");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials");
                }
            });

            registerButton.addActionListener(e -> mainPanel.switchTo("register"));
        }
    }

    static class RegisterPanel extends JPanel {
        private JTextField usernameField = new JTextField(20);
        private JPasswordField passwordField = new JPasswordField(20);
        private JButton registerButton = new JButton("Register");
        private JButton backButton = new JButton("Back");

        public RegisterPanel(MainPanel mainPanel) {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;

            add(new JLabel("Username:"), gbc);
            gbc.gridx++;
            add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Password:"), gbc);
            gbc.gridx++;
            add(passwordField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(registerButton, gbc);

            gbc.gridy++;
            add(backButton, gbc);

            registerButton.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = new User(0, username, password);
                UserController userController = new UserController();

                if (userController.registerUser(user)) {
                    JOptionPane.showMessageDialog(this, "Registration successful");
                    mainPanel.switchTo("login");
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed");
                }
            });

            backButton.addActionListener(e -> mainPanel.switchTo("login"));
        }
    }

    static class ForumPanel extends JPanel {
        private MainPanel mainPanel;
        private DefaultListModel<Thread> threadListModel;
        private JList<Thread> threadList;
        private JTextField threadTitleField = new JTextField(20);
        private JButton createThreadButton = new JButton("Create Thread");
        private JButton deleteThreadButton = new JButton("Delete Thread");

        public ForumPanel(MainPanel mainPanel) {
            this.mainPanel = mainPanel;
            setLayout(new BorderLayout());

            threadListModel = new DefaultListModel<>();
            threadList = new JList<>(threadListModel);
            threadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            add(new JScrollPane(threadList), BorderLayout.CENTER);

            JPanel createThreadPanel = new JPanel();
            createThreadPanel.add(new JLabel("Thread Title:"));
            createThreadPanel.add(threadTitleField);
            createThreadPanel.add(createThreadButton);
            add(createThreadPanel, BorderLayout.NORTH);

            JPanel manageThreadPanel = new JPanel();
            manageThreadPanel.add(deleteThreadButton);
            add(manageThreadPanel, BorderLayout.SOUTH);

            createThreadButton.addActionListener(e -> {
                String title = threadTitleField.getText().trim();
                if (!title.isEmpty()) {
                    Thread thread = new Thread(0, title, mainPanel.getLoggedInUser().getId());
                    ThreadController threadController = new ThreadController();
                    thread = threadController.createThread(thread);
                    if (thread != null) {
                        threadListModel.addElement(thread);
                        threadTitleField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to create thread");
                    }
                }
            });

            deleteThreadButton.addActionListener(e -> {
                Thread selectedThread = threadList.getSelectedValue();
                if (selectedThread != null) {
                    ThreadController threadController = new ThreadController();
                    if (threadController.deleteThread(selectedThread.getId())) {
                        threadListModel.removeElement(selectedThread);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete thread");
                    }
                }
            });

            threadList.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        Thread selectedThread = threadList.getSelectedValue();
                        if (selectedThread != null) {
                            mainPanel.add(new ThreadPanel(mainPanel, selectedThread, mainPanel.getLoggedInUser()), "thread");
                            mainPanel.switchTo("thread");
                        }
                    }
                }
            });

            loadThreads();
        }

        private void loadThreads() {
            ThreadController threadController = new ThreadController();
            List<Thread> threads = threadController.getThreads();
            for (Thread thread : threads) {
                threadListModel.addElement(thread);
            }
        }
    }

    static class ThreadPanel extends JPanel {
        private MainPanel mainPanel;
        private Thread thread;
        private User user;
        private JTextArea postContentArea = new JTextArea(5, 30);
        private JButton postButton = new JButton("Post");
        private JButton deleteButton = new JButton("Delete Post");
        private JButton backButton = new JButton("Back");
        private DefaultListModel<Post> postListModel;
        private JList<Post> postList;

        public ThreadPanel(MainPanel mainPanel, Thread thread, User user) {
            this.mainPanel = mainPanel;
            this.thread = thread;
            this.user = user;
            setLayout(new BorderLayout());

            postListModel = new DefaultListModel<>();
            postList = new JList<>(postListModel);
            postList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            add(new JScrollPane(postList), BorderLayout.CENTER);

            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.add(new JScrollPane(postContentArea), BorderLayout.CENTER);
            postPanel.add(postButton, BorderLayout.EAST);
            add(postPanel, BorderLayout.NORTH);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            bottomPanel.add(deleteButton);
            bottomPanel.add(backButton);
            add(bottomPanel, BorderLayout.SOUTH);

            deleteButton.setEnabled(false);
            postList.addListSelectionListener(e -> {
                Post selectedPost = postList.getSelectedValue();
                deleteButton.setEnabled(selectedPost != null && selectedPost.getUserId() == user.getId());
            });

            postButton.addActionListener(e -> {
                String content = postContentArea.getText().trim();
                if (!content.isEmpty()) {
                    Post post = new Post(0, thread.getId(), user.getId(), user.getUsername(), content);
                    PostController postController = new PostController();
                    post = postController.createPost(post);
                    if (post != null) {
                        postListModel.addElement(post);
                        postContentArea.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to create post");
                    }
                }
            });

            deleteButton.addActionListener(e -> {
                Post selectedPost = postList.getSelectedValue();
                if (selectedPost != null) {
                    PostController postController = new PostController();
                    if (postController.deletePost(selectedPost.getId())) {
                        postListModel.removeElement(selectedPost);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete post");
                    }
                }
            });

            backButton.addActionListener(e -> mainPanel.switchTo("forum"));

            loadPosts();
        }

        private void loadPosts() {
            PostController postController = new PostController();
            List<Post> posts = postController.getPostsByThreadId(thread.getId());
            for (Post post : posts) {
                postListModel.addElement(post);
            }
        }
    }

    static class User {
        private int id;
        private String username;
        private String password;

        public User(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class Thread {
        private int id;
        private String title;
        private int userId;

        public Thread(int id, String title, int userId) {
            this.id = id;
            this.title = title;
            this.userId = userId;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getUserId() {
            return userId;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    static class Post {
        private int id;
        private int threadId;
        private int userId;
        private String username;
        private String content;

        public Post(int id, int threadId, int userId, String username, String content) {
            this.id = id;
            this.threadId = threadId;
            this.userId = userId;
            this.username = username;
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public int getThreadId() {
            return threadId;
        }

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return username + ": " + content;
        }
    }

    static class UserController {
        public boolean registerUser(User user) {
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        user.id = generatedKeys.getInt(1);
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public User loginUser(String username, String password) {
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class ThreadController {
        public Thread createThread(Thread thread) {
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "INSERT INTO threads (title, user_id) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, thread.getTitle());
                statement.setInt(2, thread.getUserId());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        thread.id = generatedKeys.getInt(1);
                        return thread;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public boolean deleteThread(int threadId) {
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "DELETE FROM threads WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, threadId);
                int affectedRows = statement.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public List<Thread> getThreads() {
            List<Thread> threads = new ArrayList<>();
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "SELECT * FROM threads";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Thread thread = new Thread(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getInt("user_id"));
                    threads.add(thread);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return threads;
        }
    }

    static class PostController {
        public Post createPost(Post post) {
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "INSERT INTO posts (thread_id, user_id, content) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, post.getThreadId());
                statement.setInt(2, post.getUserId());
                statement.setString(3, post.getContent());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        post.id = generatedKeys.getInt(1);
                        return post;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public boolean deletePost(int postId) {
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "DELETE FROM posts WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, postId);
                int affectedRows = statement.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public List<Post> getPostsByThreadId(int threadId) {
            List<Post> posts = new ArrayList<>();
            try (Connection connection = ForumApplication.getConnection()) {
                String query = "SELECT posts.*, users.username FROM posts JOIN users ON posts.user_id = users.id WHERE thread_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, threadId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Post post = new Post(resultSet.getInt("id"), resultSet.getInt("thread_id"),
                            resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("content"));
                    posts.add(post);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return posts;
        }
    }
}