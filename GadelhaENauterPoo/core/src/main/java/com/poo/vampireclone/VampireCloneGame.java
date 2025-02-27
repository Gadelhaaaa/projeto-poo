package com.poo.vampireclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VampireCloneGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
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
