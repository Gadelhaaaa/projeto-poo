package com.poo.vampireclone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    private float x, y;
    private float speed = 100;
    private Texture texture;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("enemy.png");
    }

    // Corrigindo o método update
    public void update(float playerX, float playerY, float delta) {
        // Movimentação simples em direção ao player
        float dx = playerX - x;
        float dy = playerY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 10) {
            x += (dx / distance) * speed * delta;
            y += (dy / distance) * speed * delta;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }

    // Getters para obter a posição
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
