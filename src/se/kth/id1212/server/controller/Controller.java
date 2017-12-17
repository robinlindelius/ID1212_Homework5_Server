package se.kth.id1212.server.controller;

import se.kth.id1212.server.model.Game;

/**
 * Created by Robin on 2017-11-16.
 */
public class Controller {
    private Game game = new Game();

    public String[] getRules() {
        return game.getRules().split("\\n");
    }

    public String[] startGame() {
        return game.startGame().split("\\n");
    }

    public String[] guess(String guess) {
        return game.guess(guess).split("\\n");
    }

}
