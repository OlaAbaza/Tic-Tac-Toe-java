/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author tasne
 */
public class Score {

        SimpleStringProperty opponent;
        SimpleStringProperty PlayerOneScore;
        SimpleStringProperty PlayerTwoScore;

        public Score(String opponent, String PlayerOneScore, String PlayerTwoScore) {
            this.opponent = new SimpleStringProperty(opponent);
            this.PlayerOneScore = new SimpleStringProperty(PlayerOneScore);
            this.PlayerTwoScore = new SimpleStringProperty(PlayerTwoScore);
        }

        public String getOpponent() {
            return opponent.get();
        }

        public String getPlayerOneScore() {
            return PlayerOneScore.get();
        }

        public String getPlayerTwoScore() {
            return PlayerTwoScore.get();
        }
    }
