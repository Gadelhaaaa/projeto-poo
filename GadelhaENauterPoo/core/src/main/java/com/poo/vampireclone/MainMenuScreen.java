package com.poo.vampireclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;

public class MainMenuScreen implements Screen {
    private VampireCloneGame game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private BitmapFont font;

    public MainMenuScreen(VampireCloneGame game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("menu_background.png"); // Certifique-se de ter essa imagem
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 1280, 720);
        font.getData().setScale(2);
        font.draw(batch, "VampireClone", 540, 500);
        font.draw(batch, "Press ENTER to Start", 500, 350);
        batch.end();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        font.dispose();
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
