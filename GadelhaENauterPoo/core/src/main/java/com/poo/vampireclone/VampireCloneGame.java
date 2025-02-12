package com.poo.vampireclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VampireCloneGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));  // Passando corretamente o GameScreen
    }

    @Override
    public void render() {
        super.render();  // Chamando o método render da classe Game
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
