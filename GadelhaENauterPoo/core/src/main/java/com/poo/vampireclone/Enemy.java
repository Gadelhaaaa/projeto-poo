package com.poo.vampireclone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    private float x, y;
    private float speed = 30;
    private Texture texture;
    private TextureRegion textureRegion;
    private boolean flipped;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("enemy.png");
        textureRegion = new TextureRegion(texture);
        flipped = false;
    }

    public void update(float playerX, float playerY, float delta) {
        float dx = playerX - x;
        float dy = playerY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 15) {
            x += (dx / distance) * speed * delta;
            y += (dy / distance) * speed * delta;
        }
    }

    public void flip(boolean flipX) {
        if (flipped != flipX) { 
            textureRegion.flip(true, false);
            flipped = flipX;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, x, y);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isFlipped() {
        return flipped;
    }

    // --- Adicionando o método collidesWith ---
    public boolean collidesWith(float playerX, float playerY) {
        float distance = (float) Math.sqrt(Math.pow(this.x - playerX, 2) + Math.pow(this.y - playerY, 2));
        return distance < 20; // Ajuste o valor se necessário
    }
}
