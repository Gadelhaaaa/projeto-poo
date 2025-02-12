package com.poo.vampireclone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public class Projectile {
    public float x, y, speed = 200;
    private float directionX, directionY;
    private float distanceTraveled = 0;
    private final float MAX_DISTANCE = 300;
    private Texture texture;

    public Projectile(float x, float y, float targetX, float targetY) {
        this.x = x;
        this.y = y;
        float dx = targetX - x;
        float dy = targetY - y;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        this.directionX = dx / length;
        this.directionY = dy / length;
        texture = new Texture("projectile.png"); // Certifique-se de que a imagem exista!
    }

    // Atualiza o projétil
    public void update(float delta, List<Projectile> toRemoveProjectiles) {
        x += directionX * speed * delta;
        y += directionY * speed * delta;
        distanceTraveled += speed * delta;

        if (distanceTraveled >= MAX_DISTANCE) {
            toRemoveProjectiles.add(this); // Marca o projétil para ser removido
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }

    // Métodos getX() e getY() adicionados
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
