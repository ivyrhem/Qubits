package planner;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {

    private Graph graph;
    private Dijkstra dijkstra;

    private JComboBox<String> startBox;
    private JComboBox<String> destinationBox;

    private JTextArea outputArea;

    private JButton findButton;
    private JButton favoriteButton;
    private JButton bookmarkButton;
    private JButton historyButton;

    private Route currentRoute;

    // Graph panel
    private GraphPanel graphPanel;

    public GUI() {

        graph = new Graph();
        initializeGraph();

        dijkstra = new Dijkstra(graph);

        setTitle("Student Commute Route Planner");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new FlowLayout());

        startBox = new JComboBox<>();
        destinationBox = new JComboBox<>();

        for (String location : graph.getLocationNames()) {
            startBox.addItem(location);
            destinationBox.addItem(location);
        }

        findButton = new JButton("Find Route");
        favoriteButton = new JButton("Save Favorite");
        bookmarkButton = new JButton("Bookmark");
        historyButton = new JButton("History");

        topPanel.add(new JLabel("From:"));
        topPanel.add(startBox);

        topPanel.add(new JLabel("To:"));
        topPanel.add(destinationBox);

        topPanel.add(findButton);
        topPanel.add(favoriteButton);
        topPanel.add(bookmarkButton);
        topPanel.add(historyButton);

        add(topPanel, BorderLayout.NORTH);

        // ---------- GRAPH PANEL ----------
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // ---------- OUTPUT PANEL ----------
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(280, 600));

        add(scrollPane, BorderLayout.EAST);

        addButtonActions();
    }

    private void initializeGraph() {

        graph.addLocation("Home");
        graph.addLocation("Terminal");
        graph.addLocation("Market");
        graph.addLocation("Hospital");
        graph.addLocation("MarSU");

        graph.addRoad("Home", "Terminal", 2, 5, 13);
        graph.addRoad("Home", "Market", 3, 8, 13);
        graph.addRoad("Terminal", "MarSU", 4, 10, 15);
        graph.addRoad("Market", "Hospital", 1, 3, 13);
        graph.addRoad("Hospital", "MarSU", 2, 5, 13);
        graph.addRoad("Market", "MarSU", 2, 6, 13);
    }

    // ============================
    // Button Actions
    // ============================

    private void addButtonActions() {

        findButton.addActionListener(e -> findRoute());

        favoriteButton.addActionListener(e -> saveFavorite());

        bookmarkButton.addActionListener(e -> bookmarkLocation());

        historyButton.addActionListener(e -> showHistory());

    }

    // ============================
    // Find Shortest Route
    // ============================

    private void findRoute() {

        String start = (String) startBox.getSelectedItem();
        String end = (String) destinationBox.getSelectedItem();

        if (start.equals(end)) {
            JOptionPane.showMessageDialog(this,
                    "Please select different locations.");
            return;
        }

        List<String> path = dijkstra.shortestPath(start, end);

        double distance = dijkstra.getTotalDistance(path);
        double time = dijkstra.getTotalTime(path);
        double fare = dijkstra.getTotalFare(path);

        currentRoute = new Route(path, distance, time, fare);

        outputArea.setText(
                "===== SHORTEST ROUTE =====\n\n" +
                        "Route:\n" +
                        String.join(" -> ", path) +
                        "\n\nDistance : " + distance + " km" +
                        "\nTravel Time : " + time + " minutes" +
                        "\nEstimated Fare : ₱" + fare
        );

        RouteHistory history = new RouteHistory();
        history.saveHistory(currentRoute);

        // Highlight the shortest path
        graphPanel.setShortestPath(path);

    }

    // ============================
    // Save Favorite
    // ============================

    private void saveFavorite() {

        if (currentRoute == null) {

            JOptionPane.showMessageDialog(this,
                    "Find a route first.");

            return;
        }

        Favorites favorites = new Favorites();
        favorites.addFavorite(currentRoute);

        JOptionPane.showMessageDialog(this,
                "Route saved to Favorites!");

    }

    // ============================
    // Bookmark Location
    // ============================

    private void bookmarkLocation() {

        String location = (String) startBox.getSelectedItem();

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(location);

        JOptionPane.showMessageDialog(this,
                location + " has been bookmarked.");

    }

    // ============================
    // Show History
    // ============================

    private void showHistory() {

        RouteHistory history = new RouteHistory();

        StringBuilder builder = new StringBuilder();

        builder.append("===== ROUTE HISTORY =====\n\n");

        for (String route : history.getHistory()) {

            builder.append(route).append("\n");

        }

        outputArea.setText(builder.toString());

    }

    // ============================
    // Graph Panel
    // ============================

    class GraphPanel extends JPanel {

        private List<String> shortestPath;

        public void setShortestPath(List<String> path) {
            shortestPath = path;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            Point home = new Point(100, 250);
            Point terminal = new Point(250, 120);
            Point market = new Point(250, 380);
            Point hospital = new Point(450, 380);
            Point marsu = new Point(650, 250);

            // Draw all roads
            g2.drawLine(home.x, home.y, terminal.x, terminal.y);
            g2.drawLine(home.x, home.y, market.x, market.y);
            g2.drawLine(terminal.x, terminal.y, marsu.x, marsu.y);
            g2.drawLine(market.x, market.y, marsu.x, marsu.y);
            g2.drawLine(market.x, market.y, hospital.x, hospital.y);
            g2.drawLine(hospital.x, hospital.y, marsu.x, marsu.y);

            // Highlight shortest path
            if (shortestPath != null) {

                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(4));

                for (int i = 0; i < shortestPath.size() - 1; i++) {

                    Point p1 = getPoint(shortestPath.get(i));
                    Point p2 = getPoint(shortestPath.get(i + 1));

                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }

                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
            }

            drawVertex(g2, home, "Home");
            drawVertex(g2, terminal, "Terminal");
            drawVertex(g2, market, "Market");
            drawVertex(g2, hospital, "Hospital");
            drawVertex(g2, marsu, "MarSU");
        }

        private void drawVertex(Graphics2D g2, Point p, String name) {

            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.drawString(name, p.x - 20, p.y - 20);

        }

        private Point getPoint(String location) {

            switch (location) {

                case "Home":
                    return new Point(100, 250);

                case "Terminal":
                    return new Point(250, 120);

                case "Market":
                    return new Point(250, 380);

                case "Hospital":
                    return new Point(450, 380);

                case "MarSU":
                    return new Point(650, 250);

                default:
                    return new Point(0, 0);
            }
        }
    }
}

