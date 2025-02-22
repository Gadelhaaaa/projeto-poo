package com.poo.vampireclone.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.poo.vampireclone.VampireCloneGame;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    private static void createApplication() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Vampire Clone");
        config.setWindowedMode(1280, 720);
        config.setResizable(false); // Impede o redimensionamento da tela
        new Lwjgl3Application(new VampireCloneGame(), config);
    }
}
