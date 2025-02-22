package com.poo.vampireclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private float x, y;
    private int health;
    private Texture texture;
    private TextureRegion region;
    private float speed = 150; // Velocidade do jogador

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.health = 20;
        this.texture = new Texture("player.png");
        this.region = new TextureRegion(texture);
    }

    public void update(float delta) {
        float moveSpeed = speed * delta;

        boolean movingLeft = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean movingRight = Gdx.input.isKeyPressed(Input.Keys.D);

        if (movingLeft) {
            x -= moveSpeed;
            if (!region.isFlipX()) {
                region.flip(true, false);
            }
        }
        if (movingRight) {
            x += moveSpeed;
            if (region.isFlipX()) {
                region.flip(true, false);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += moveSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= moveSpeed;
        }

        // Limites da tela
        x = Math.max(0, Math.min(1280 - texture.getWidth(), x));
        y = Math.max(0, Math.min(720 - texture.getHeight(), y));
    }

    public void render(SpriteBatch batch) {
        batch.draw(region, x, y);
    }

    public void restoreHealth() {
        this.health = 20;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
    }

    public int getHealth() { // Método adicionado
        return health;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        texture.dispose();
    }
}
