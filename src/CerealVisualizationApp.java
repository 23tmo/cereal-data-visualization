import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CerealVisualizationApp extends JPanel {
    private static final int WINDOW_WIDTH = 1440;
    private static final int WINDOW_HEIGHT = 872;
    private static final Color BACKGROUND = new Color(237, 196, 100);
    private static final Color ACCENT = new Color(37, 174, 219);
    private static final Color TITLE_PINK = new Color(255, 0, 225);
    private static final Rectangle SORT_BUTTON = new Rectangle(1250, 10, 150, 25);
    private static final Rectangle BEST_RATED_BUTTON = new Rectangle(1250, 45, 150, 25);
    private static final int[] RATING_OPTIONS = {0, 20, 40, 60, 80};
    private static final Rectangle[] RATING_BUTTONS = {
            new Rectangle(1365, 105, 35, 25),
            new Rectangle(1365, 145, 35, 25),
            new Rectangle(1365, 185, 35, 25),
            new Rectangle(1365, 225, 35, 25),
            new Rectangle(1365, 265, 35, 25)
    };

    private final Dataset dataset;
    private final Timer blinkTimer;
    private boolean highlightVisible = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CerealVisualizationApp::showWindow);
    }

    public CerealVisualizationApp() {
        dataset = new Dataset();
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(BACKGROUND);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClicked(e.getX(), e.getY());
            }
        });

        blinkTimer = new Timer(350, e -> updateHighlightState());
        blinkTimer.start();
    }

    private static void showWindow() {
        JFrame frame = new JFrame("Cereals!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new CerealVisualizationApp());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateHighlightState() {
        if (dataset.getFoundAt() == -1) {
            if (!highlightVisible) {
                highlightVisible = true;
                repaint();
            }
            return;
        }

        highlightVisible = !highlightVisible;
        repaint();
    }

    private void handleMouseClicked(int x, int y) {
        if (SORT_BUTTON.contains(x, y)) {
            dataset.sort();
            repaint();
            return;
        }

        if (BEST_RATED_BUTTON.contains(x, y)) {
            dataset.findBestRated();
            repaint();
            return;
        }

        for (int i = 0; i < RATING_BUTTONS.length; i++) {
            if (RATING_BUTTONS[i].contains(x, y)) {
                dataset.findClosestTo(RATING_OPTIONS[i]);
                repaint();
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            paintRecords(g2);
            paintTitle(g2);
            paintControls(g2);
        } finally {
            g2.dispose();
        }
    }

    private void paintRecords(Graphics2D g2) {
        int x = 150;
        int y = 120;
        Record[] records = dataset.getRecords();
        int foundAt = dataset.getFoundAt();

        for (int i = 0; i < records.length; i++) {
            BufferedImage image = records[i].getImage();
            if (foundAt != -1 && i != foundAt) {
                drawDimmedImage(g2, image, x, y);
            } else {
                g2.drawImage(image, x, y, null);
            }

            if (i == foundAt && highlightVisible) {
                g2.setColor(TITLE_PINK);
                g2.setStroke(new BasicStroke(4f));
                g2.drawRoundRect(x - 4, y - 4, image.getWidth() + 8, image.getHeight() + 8, 14, 14);
            }

            y += 250;
            if (y > 700) {
                y = 120;
                x += 150;
            }
        }
    }

    private void drawDimmedImage(Graphics2D g2, BufferedImage image, int x, int y) {
        AlphaComposite originalComposite = (AlphaComposite) g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.28f));
        g2.drawImage(image, x, y, null);
        g2.setComposite(originalComposite);
    }

    private void paintTitle(Graphics2D g2) {
        String[] titleLetters = {"C", "e", "r", "e", "a", "l", "s", "!"};
        Color[] titleColors = {
                Color.RED,
                new Color(255, 145, 0),
                new Color(255, 242, 0),
                new Color(145, 255, 0),
                new Color(0, 238, 255),
                new Color(0, 72, 255),
                new Color(187, 0, 255),
                TITLE_PINK
        };

        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        int x = WINDOW_WIDTH / 2 - 120;
        for (int i = 0; i < titleLetters.length; i++) {
            g2.setColor(titleColors[i]);
            g2.drawString(titleLetters[i], x, 60);
            x += 40;
        }

        g2.setColor(Color.BLACK);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
        g2.drawString("sort by sugar (least to most from left to right)", WINDOW_WIDTH / 2 - 165, 83);
        g2.drawString("or search for the best rated cereal (high rating = more nutritious)", WINDOW_WIDTH / 2 - 250, 103);
    }

    private void paintControls(Graphics2D g2) {
        paintButton(g2, SORT_BUTTON, "sort by sugar");
        paintButton(g2, BEST_RATED_BUTTON, "find best rated");

        g2.setColor(new Color(14, 43, 204));
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        g2.drawString("find rating closest to:", 1250, 93);

        for (int i = 0; i < RATING_BUTTONS.length; i++) {
            paintButton(g2, RATING_BUTTONS[i], Integer.toString(RATING_OPTIONS[i]));
        }
    }

    private void paintButton(Graphics2D g2, Rectangle bounds, String label) {
        g2.setColor(Color.WHITE);
        g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g2.setColor(ACCENT);
        g2.setStroke(new BasicStroke(5f));
        g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g2.drawString(label, bounds.x + 8, bounds.y + 18);
    }
}
