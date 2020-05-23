package com.nebsting.platformingtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Core extends Game {
    OrthographicCamera camera;
	SpriteBatch batch;

    Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        player = new Player();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.logic();

        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(player.image, player.x, player.y);
		batch.end();

        camera.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
