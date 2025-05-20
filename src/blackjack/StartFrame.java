package blackjack;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    public StartFrame() {
        setTitle("21點遊戲 - 開始介面");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            // 按開始，開啟遊戲視窗，關閉起始視窗
            BlackjackGUI game = new BlackjackGUI();
            game.setVisible(true);
            this.dispose();
        });

        setLayout(new GridBagLayout());
        add(startButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame start = new StartFrame();
            start.setVisible(true);
        });
    }
}
