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
    MapLayer[] layers;

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
        layers = new MapLayer[2];
        layers[0] = map.getLayers().get(1);
        layers[1] = map.getLayers().get(2);
        MapObjects polyLayer = layers[0].getObjects();
        MapObjects recLayer = layers[1].getObjects();

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // Get collision objects
        polygonObjects = new Polygon[polyLayer.getCount()];
        rectangleObjects = new Rectangle[recLayer.getCount()];

        for(int i=0; i<polygonObjects.length; i++) {
            if(polyLayer.get(i) instanceof PolygonMapObject) {
                PolygonMapObject cast = (PolygonMapObject) polyLayer.get(i);
                Polygon result = cast.getPolygon();

                polygonObjects[i] = result;
                Gdx.app.log("ObjLayers", result.toString());
            }
        }
        for(int i=0; i<rectangleObjects.length; i++) {
            if(recLayer.get(i) instanceof RectangleMapObject) {
                RectangleMapObject cast = (RectangleMapObject) recLayer.get(i);
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

        // collision check for player
        player.setOnGround(checkRectangleCollision());

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

    public boolean checkRectangleCollision() {
        boolean landed = false;
        for(int i = 0; i<rectangleObjects.length; i++) {
            if(rectangleObjects[i] instanceof Rectangle) {
                // top
                if(rectangleObjects[i].contains(player.x, player.y)) {
                    player.collideTop(player.y);
                    Gdx.app.log("Collision", "Top: "+Float.toString(player.y));
                }

                // bottom
                if(rectangleObjects[i].contains(player.x, player.y-player.height)) {
                    player.collideBottom(player.y);
                    landed = true;
                    Gdx.app.log("Collision", "Bottom: "+Float.toString(player.y));
                }
                
                // left
                if(rectangleObjects[i].contains(player.x, player.y)) {
                    player.collideLeft(player.x);
                    Gdx.app.log("Collision", "Left: "+Float.toString(player.x));
                }
                
                // right
                if(rectangleObjects[i].contains(player.x+player.width, player.y)) {
                    player.collideRight(player.x);
                    Gdx.app.log("Collision", "Right: "+Float.toString(player.x));
                }
            }

        }
        return landed;
    }
}
