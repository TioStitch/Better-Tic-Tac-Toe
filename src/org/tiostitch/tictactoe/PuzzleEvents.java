package org.tiostitch.tictactoe;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@AllArgsConstructor
public final class PuzzleEvents
implements ActionListener {

    private Desktop desktop;

    //Linha - Coluna
    private final int[][] victoryCombos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!(actionEvent.getSource() instanceof JButton)) return;
        if (desktop.isEndedGame()) return;

        val button = (JButton) actionEvent.getSource();
        if (!button.getText().isEmpty()) return;
        if (checkEmpate()) gameResetUpdate("N", true);

        desktop.setBotRound(!desktop.isBotRound());
        button.setText(desktop.isBotRound() ? "O" : "X");

        if (checkWinner("X")) {
            System.out.println("O X ganhou a rodada!");
            gameResetUpdate("X", false);
        } else if (checkWinner("O")) {
            System.out.println("O O ganhou a rodada!");
            gameResetUpdate("O", false);
        }
    }

    private void gameResetUpdate(final String str, boolean empate) {
        if (!empate) {
            desktop.getWinnerMap().put(str, (short) (1 + desktop.getWinnerMap().getOrDefault(str, (short) 0)));

            val xWins = desktop.getWinnerMap().getOrDefault("X", (short) 0);
            val oWins = desktop.getWinnerMap().getOrDefault("O", (short) 0);

            desktop.getDisplay().setText("O - " + oWins + " : X - " + xWins);
        }

        desktop.endedGame = true;

        var renewButton = new JButton("Recomeçar!");
        renewButton.setBounds(145, 420, 120, 50);
        renewButton.addActionListener(this::renewGame);

        desktop.add(renewButton);
        desktop.update(desktop.getGraphics());
    }


    private void renewGame(ActionEvent action) {
        if (action.getSource() != (JButton) action.getSource()) return;

        desktop.remove((JButton) action.getSource());
        desktop.gameButtons.forEach(desktop::remove);

        desktop.gameButtons.clear();
        desktop.setEndedGame(false);
        desktop.start();

        desktop.update(desktop.getGraphics());
        System.out.println("Game reiniciado!");
    }

    private boolean checkWinner(final String str) {

        val gameButtons = desktop.getGameButtons();

        for (int[] combos : victoryCombos) {
            if (checkCombo(str, combos[0], gameButtons)
                    && checkCombo(str, combos[1], gameButtons)
                    && checkCombo(str, combos[2], gameButtons)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkCombo(final String str, final int index, final List<JButton> botao) {
        return botao.get(index).getText().equals(str);
    }

    private boolean checkEmpate() {
        val gameButtons = desktop.getGameButtons();
        int usedIndex = 0;

        for (JButton button : gameButtons) {
            if (!button.getText().isEmpty()) {
                usedIndex++;
            }
        }
        return usedIndex >= 8;
    }

}
