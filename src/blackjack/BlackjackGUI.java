package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.net.URL;

public class BlackjackGUI extends JFrame {
    private final GameManager gameManager = new GameManager();

    private final JPanel playerPanel = new JPanel();
    private final JPanel dealerPanel = new JPanel();
    private final JLabel statusLabel = new JLabel("Welcome to Blackjack!");
    private final JLabel scoreLabel = new JLabel("Wins: 0  Losses: 0");

    public BlackjackGUI() {
        setTitle("Blackjack 21 點遊戲");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer"));
        add(dealerPanel, BorderLayout.NORTH);

        playerPanel.setBorder(BorderFactory.createTitledBorder("You"));
        add(playerPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");
        JButton newGameButton = new JButton("New Game");

        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        controlPanel.add(newGameButton);
        controlPanel.add(scoreLabel);

        add(statusLabel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.PAGE_END);

        hitButton.addActionListener(e -> {
            gameManager.playerHits();
            updateUI();
        });

        standButton.addActionListener(e -> {
            gameManager.playerStands();
            updateUI();
        });

        newGameButton.addActionListener(e -> {
            gameManager.startNewGame();
            updateUI();
        });

        gameManager.startNewGame();
        updateUI();
    }

    private void updateUI() {
        dealerPanel.removeAll();
        playerPanel.removeAll();

        addCardsToPanel(dealerPanel, gameManager.getDealerHand(), gameManager.isDealerRevealed());
        addCardsToPanel(playerPanel, gameManager.getPlayerHand(), true);

        statusLabel.setText(gameManager.getStatusMessage());
        scoreLabel.setText("Wins: " + gameManager.getWins() + "  Losses: " + gameManager.getLosses());

        revalidate();
        repaint();
    }

    private void addCardsToPanel(JPanel panel, List<Card> hand, boolean revealAll) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            String path = revealAll ? card.getImagePath() : "/cards/back.png";
            
            URL imgURL = getClass().getResource(path);
            if (imgURL == null) {
                System.err.println("⚠️ 無法找到圖片：" + path);
                continue; // 或用一個預設空白圖片代替
            }
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
            Image scaledImage = originalIcon.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH); // ← 設定你想要的寬高
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel cardLabel = new JLabel(scaledIcon);
            panel.add(cardLabel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackjackGUI().setVisible(true));
    }
}