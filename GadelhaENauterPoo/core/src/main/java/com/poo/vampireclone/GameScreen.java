package com.poo.vampireclone;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private VampireCloneGame game;
    private Texture playerTexture;
    private float playerX, playerY;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private float shootCooldown = 0.9f;
    private float shootTimer = 0;
    private List<Enemy> toRemoveEnemies;
    private List<Projectile> toRemoveProjectiles;
    private int enemiesKilled = 0;
    private int level = 1;
    private final int MAX_LEVEL = 20;
    private final int BASE_ENEMIES = 5;
    private BitmapFont font;
    private SpriteBatch batch;

    public GameScreen(VampireCloneGame game) {
        this.game = game;
        projectiles = new ArrayList<>();
        toRemoveEnemies = new ArrayList<>();
        toRemoveProjectiles = new ArrayList<>();
        playerTexture = new Texture("player.png");
        font = new BitmapFont(); // Inicializando a fonte
        font.getData().setScale(2); // Aumentando o tamanho da fonte para ser mais legível
        font.setColor(1, 1, 0, 1); // Definindo a cor da fonte para amarelo
        batch = new SpriteBatch();
        playerX = 400;
        playerY = 300;
        enemies = new ArrayList<>();
        spawnEnemies(BASE_ENEMIES);
    }

    private void updatePlayer(float delta) {
        float speed = 200 * delta;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) playerY += speed;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) playerY -= speed;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) playerX -= speed;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) playerX += speed;
    }

    private void spawnEnemies(int count) {
        for (int i = 0; i < count; i++) {
            float enemyX, enemyY;
            do {
                enemyX = (float) Math.random() * 800;
                enemyY = (float) Math.random() * 600;
            } while (Math.abs(enemyX - playerX) < 50 || Math.abs(enemyY - playerY) < 50);
            enemies.add(new Enemy(enemyX, enemyY));
        }
    }

    @Override
    public void render(float delta) {
        updatePlayer(delta);

        // Atualiza inimigos
        for (Enemy enemy : enemies) {
            enemy.update(playerX, playerY, delta);
        }

        // Disparo de projéteis
        shootTimer += delta;
        float targetX = Gdx.input.getX();
        float targetY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (shootTimer >= shootCooldown) {
            shootTimer = 0;
            projectiles.add(new Projectile(playerX, playerY, targetX, targetY));
        }

        // Verifica colisões
        for (Projectile projectile : projectiles) {
            for (Enemy enemy : enemies) {
                float distance = (float) Math.sqrt(Math.pow(projectile.getX() - enemy.getX(), 2) + Math.pow(projectile.getY() - enemy.getY(), 2));
                if (distance < 20) {
                    toRemoveEnemies.add(enemy);
                    toRemoveProjectiles.add(projectile);
                    enemiesKilled++;
                    break;
                }
            }
        }

        // Remove inimigos e projéteis
        enemies.removeAll(toRemoveEnemies);
        projectiles.removeAll(toRemoveProjectiles);

        // Verifica nível
        if (enemiesKilled >= level * 5 && level < MAX_LEVEL) {
            level++;
            spawnEnemies(BASE_ENEMIES + level);
        }

        // Atualiza projéteis
        for (Projectile projectile : projectiles) {
            projectile.update(delta, toRemoveProjectiles);
        }
        projectiles.removeAll(toRemoveProjectiles);

        // Renderiza o jogo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Desenha projéteis, jogador e inimigos
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }
        batch.draw(playerTexture, playerX, playerY);
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        batch.end();

        // Renderiza o HUD (kills e level)
        batch.begin();

        // Exibe kills e level no canto superior esquerdo
        font.draw(batch, "Kills: " + enemiesKilled, 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Level: " + level, 20, Gdx.graphics.getHeight() - 50);

        batch.end();
    }

    @Override
    public void dispose() {
        playerTexture.dispose();
        font.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }

    public void show() {}
    public void resize(int width, int height) {}
    public void pause() {}
    public void resume() {}
    public void hide() {}
}
