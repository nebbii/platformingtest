package com.nebsting.platformingtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Core extends Game {
    OrthographicCamera camera;
	SpriteBatch batch;

    TiledMap map;
    MapLayer layer;
    Polygon[] polygonObjects;
    Rectangle[] rectangleObjects;

    MapRenderer mapRenderer;
    Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        map = new TmxMapLoader().load("level/testlevel.tmx");

        // Map stuff
        layer = map.getLayers().get(1);
        MapObjects layerObjects = layer.getObjects();

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // Get collision objects
        polygonObjects = new Polygon[layerObjects.getCount()];
        rectangleObjects = new Rectangle[layerObjects.getCount()];

        for(int i=0; i<layerObjects.getCount(); i++) {
            if(layerObjects.get(i) instanceof PolygonMapObject) {
                PolygonMapObject cast = (PolygonMapObject) layerObjects.get(i);
                Polygon result = cast.getPolygon();

                polygonObjects[i] = result;
                Gdx.app.log("ObjLayers", result.toString());
            }
            if(layerObjects.get(i) instanceof RectangleMapObject) {
                RectangleMapObject cast = (RectangleMapObject) layerObjects.get(i);
                Rectangle result = cast.getRectangle();

                rectangleObjects[i] = result;
                Gdx.app.log("ObjLayers", result.toString());
            }
        }

        player = new Player();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.logic();

        mapRenderer.setView(camera);
        mapRenderer.render();

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
