package com.poo.vampireclone;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private VampireCloneGame game;
    private Player player;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private float shootCooldown = 1.2f;
    private float shootTimer = 0;
    private List<Enemy> toRemoveEnemies;
    private List<Projectile> toRemoveProjectiles;
    private int enemiesKilled = 0;
    private int level = 1;
    private final int MAX_LEVEL = 300;
    private final int BASE_ENEMIES = 3;
    private BitmapFont font;
    private SpriteBatch batch;
    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 720;
    private float enemyDamageCooldown = 1.0f;
    private float enemyDamageTimer = 0;

    public GameScreen(VampireCloneGame game) {
        this.game = game;
        projectiles = new ArrayList<>();
        toRemoveEnemies = new ArrayList<>();
        toRemoveProjectiles = new ArrayList<>();
        font = new BitmapFont();
        batch = new SpriteBatch();
        player = new Player(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f);
        enemies = new ArrayList<>();
        spawnEnemies(BASE_ENEMIES);
    }

    private void spawnEnemies(int count) {
        for (int i = 0; i < count; i++) {
            float enemyX, enemyY;
            do {
                enemyX = (float) Math.random() * SCREEN_WIDTH;
                enemyY = (float) Math.random() * SCREEN_HEIGHT;
            } while (Math.abs(enemyX - player.getX()) < 50 || Math.abs(enemyY - player.getY()) < 50);
            enemies.add(new Enemy(enemyX, enemyY));
        }
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        enemyDamageTimer += delta;

        for (Enemy enemy : enemies) {
            enemy.update(player.getX(), player.getY(), delta);
            if (enemy.collidesWith(player.getX(), player.getY()) && enemyDamageTimer >= enemyDamageCooldown) {
                player.takeDamage(1);
                enemyDamageTimer = 0;
            }
        }

        for (Enemy enemy : enemies) {
            float direction = player.getX() - enemy.getX();
            boolean facingLeft = enemy.isFlipped();
            
            if (direction > 0 && facingLeft) { 
                enemy.flip(false);
            } else if (direction < 0 && !facingLeft) {
                enemy.flip(true);
            }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        player.render(batch);
        
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        shootCooldown = Math.max(0.2f, 1.2f - (level * 0.005f));
        shootTimer += delta;
        float targetX = Gdx.input.getX();
        float targetY = SCREEN_HEIGHT - Gdx.input.getY();

        if (shootTimer >= shootCooldown) {
            shootTimer = 0;
            projectiles.add(new Projectile(player.getX(), player.getY(), targetX, targetY, 250 + (level * 8), 10));
        }

        for (Projectile projectile : projectiles) {
            for (Enemy enemy : enemies) {
                if (enemy.collidesWith(projectile.getX(), projectile.getY())) {
                    toRemoveEnemies.add(enemy);
                    toRemoveProjectiles.add(projectile);
                    enemiesKilled++;
                }
            }
        }
        enemies.removeAll(toRemoveEnemies);
        projectiles.removeAll(toRemoveProjectiles);

        if (enemiesKilled % 10 == 0 && enemiesKilled > 0) {
            player.restoreHealth();
        }

        if (enemiesKilled >= level * 3 && level < MAX_LEVEL) {
            level++;
            spawnEnemies(BASE_ENEMIES + Math.min(level, 5));
        }

        for (Projectile projectile : projectiles) {
            projectile.update(delta, toRemoveProjectiles);
        }
        projectiles.removeAll(toRemoveProjectiles);

        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }

        font.getData().setScale(2);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "Kills: " + enemiesKilled, 20, SCREEN_HEIGHT - 20);
        font.draw(batch, "Level: " + level, 20, SCREEN_HEIGHT - 50);
        font.draw(batch, "HP: " + player.getHealth(), 20, SCREEN_HEIGHT - 80);

        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        player.dispose();
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
