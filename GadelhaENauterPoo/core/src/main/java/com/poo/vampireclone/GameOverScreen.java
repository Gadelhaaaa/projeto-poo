package com.poo.vampireclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreen implements Screen {
    private VampireCloneGame game;
    private boolean victory;
    private float gameTime;  // Recebendo o tempo de jogo
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture background;

    public GameOverScreen(VampireCloneGame game, boolean victory, float gameTime) {
        this.game = game;
        this.victory = victory;
        this.gameTime = gameTime;  // Atribuindo o tempo de jogo
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.background = new Texture("gameover_background.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        font.getData().setScale(3);
        String message = victory ? "Você Matou Todos os Inimigos!" : "Você Morreu!";
        font.draw(batch, message, Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2);

        // Exibindo o tempo de jogo na tela de Game Over
        font.getData().setScale(2);
        font.draw(batch, "Tempo de Jogo: " + String.format("%.2f", gameTime), Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 100);
        
        font.draw(batch, "Pressione ENTER para voltar ao menu", Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 50);

        batch.end();
        
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game)); // Volta para o menu principal
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        background.dispose();
    }

    @Override
    public void show() {}
    public void resize(int width, int height) {}
    public void pause() {}
    public void resume() {}
    public void hide() {}
}
