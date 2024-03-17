package org.tiostitch.tictactoe;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

public final class Desktop
extends JFrame {

    @Getter @Setter public boolean botRound = false;
    @Getter @Setter public boolean endedGame = false;

    @Getter public final List<JButton> gameButtons = new ArrayList<>();
    @Getter final JLabel display = new JLabel("O - 0 : X - 0");
    @Getter public final WeakHashMap<String, Short> winnerMap = new WeakHashMap<>();

    private Desktop() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 500);
        setLayout(null);

        display.setFont(new Font("BOLD", Font.BOLD, 15));
        display.setBounds(160, 0, 200, 100);
        add(display);

        start();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void start() {
        for (int filler = 1; filler <= 3; filler++) {
            for (int tree = 1; tree <= 3; tree++) {
                JButton gmButton = new JButton("");
                gmButton.setBounds(tree * 100 - 50, filler * 100, 100, 100);
                gmButton.setVisible(true);
                gameButtons.add(gmButton);
            }
        }
        gameButtons.forEach(button -> button.addActionListener(new PuzzleEvents(this)));
        gameButtons.forEach(this::add);
    }

    public static void main(String[] args) {
        new Desktop();
    }
}
