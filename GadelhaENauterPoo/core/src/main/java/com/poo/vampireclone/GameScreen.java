package com.poo.vampireclone;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture; // Importando a classe Texture

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
    private boolean gameOver = false;
    private boolean gameWon = false;
    private float gameTime = 0;  // Adicionando o contador de tempo

    // Variável para armazenar a imagem de fundo
    private Texture background;

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

        // Carregar a imagem de fundo
        background = new Texture("background.png"); // Certifique-se de que a imagem esteja no diretório assets
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
        if (gameOver || gameWon) {
            renderEndScreen();
            return;
        }

        player.update(delta);
        enemyDamageTimer += delta;

        // Variável para armazenar o dano total de todos os inimigos colidindo com o jogador
        int damageFromEnemies = 0;

        for (Enemy enemy : enemies) {
            enemy.update(player.getX(), player.getY(), delta);

            // Verificando colisão e acumulando o dano
            if (enemy.collidesWith(player.getX(), player.getY())) {
                damageFromEnemies++;
            }
        }

        // Aplica o dano acumulado no jogador
        if (damageFromEnemies > 0 && enemyDamageTimer >= enemyDamageCooldown) {
            player.takeDamage(damageFromEnemies); // Dano é proporcional ao número de inimigos
            enemyDamageTimer = 0;
        }

        if (player.getHealth() <= 0) {
            gameOver = true;
            return;
        }

        if (enemies.isEmpty()) {
            gameWon = true;
            return;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Desenhando a imagem de fundo
        batch.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        player.render(batch);

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        shootCooldown = Math.max(0.2f, 1.2f - (level * 0.005f));
        shootTimer += delta;

        float targetX = Gdx.input.getX();
        float targetY = SCREEN_HEIGHT - Gdx.input.getY();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (shootTimer >= shootCooldown) {
                shootTimer = 0;
                projectiles.add(new Projectile(player.getX() + player.getTextureWidth() / 2 - 5,
                                               player.getY() + player.getTextureHeight() / 2 - 5,
                                               targetX, targetY, 250 + (level * 8), 30, level, "projectile2.png"));
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            if (shootTimer >= shootCooldown) {
                shootTimer = 0;
                // Criar um círculo de projéteis
                int numProjectiles = 8; // Número de projéteis na explosão
                for (int i = 0; i < numProjectiles; i++) {
                    double angle = 2 * Math.PI * i / numProjectiles;
                    float dirX = (float) Math.cos(angle);
                    float dirY = (float) Math.sin(angle);
                    projectiles.add(new Projectile(player.getX() + player.getTextureWidth() / 2 - 5,
                                                   player.getY() + player.getTextureHeight() / 2 - 5,
                                                   player.getX() + dirX * 50, player.getY() + dirY * 50,
                                                   300 + (level * 10), 30, level, "projectile3.png"));
                }
            }
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

        // Exibindo o tempo de jogo no centro superior
        font.draw(batch, "Tempo: " + String.format("%.1f", gameTime), SCREEN_WIDTH / 2f - 50, SCREEN_HEIGHT - 20);

        batch.end();

        // Atualiza o tempo de jogo
        gameTime += delta;
    }

    private void renderEndScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        font.getData().setScale(3);
        font.setColor(1, 0, 0, 1);

        String message = gameWon ? "Você Matou Todos os Inimigos!" : "Você Morreu!";
        font.draw(batch, message, SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2);
        
        // Exibindo o tempo de jogo na tela de Game Over
        font.getData().setScale(2);
        font.draw(batch, "Tempo de Jogo: " + String.format("%.2f", gameTime), SCREEN_WIDTH / 2 - 150, SCREEN_HEIGHT / 2 - 100);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "Pressione ENTER para voltar ao menu", SCREEN_WIDTH / 2 - 250, SCREEN_HEIGHT / 2 - 50);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Voltando para o Menu...");
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        player.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        background.dispose(); // Dispose da imagem de fundo
    }

    public void show() {}
    public void resize(int width, int height) {}
    public void pause() {}
    public void resume() {}
    public void hide() {}
}
