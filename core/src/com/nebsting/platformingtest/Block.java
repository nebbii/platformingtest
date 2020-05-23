package com.nebsting.platformingtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle {

    public Texture image;

    public int vx;
    public int vy;
    public boolean onGround;

    public int walkAcceleration;
    public int walkMaxSpeed;

    public int jumpSpeed;
    public int fallSpeed;

    public Block() {
        image = new Texture(Gdx.files.internal("block.png"));
        width = 24;
        height = 24;
        x = 100;
        y = 84;
        vx = 0;
        vy = 0;

        walkAcceleration = 20;
        walkMaxSpeed = 400;

        jumpSpeed = 600;
        fallSpeed = 20;

        onGround = false;
    }

    public void logic() {
        movePlayer();
        jumpPlayer();

        Gdx.app.log("On Ground", Boolean.toString(onGround));
        Gdx.app.log("Speed", Float.toString(vy - jumpSpeed) + " of " + Float.toString(jumpSpeed));
        Gdx.app.log("X: ", Float.toString(x));
        Gdx.app.log("Y: ", Float.toString(y));
        Gdx.app.log("VY: ", Float.toString(vy));
    }

    public void movePlayer() {
        // left
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vx = Math.max(vx - walkAcceleration, (walkMaxSpeed * -1));
        } 
        else if(vx < 0){
            vx = Math.min(vx + (walkAcceleration * 2), 0);
        } 

        // right
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if((x + vx * Gdx.graphics.getDeltaTime()) > 400 && y < 90) {
                vx = 0;
            } else {
                vx = Math.min(vx + walkAcceleration, walkMaxSpeed);
            }
        } 
        else if(vx > 0){
            vx = Math.max(vx - (walkAcceleration * 2), 0);
        }

        x += vx * Gdx.graphics.getDeltaTime();
    }

    public void jumpPlayer() {
        if(y < 20 ||
        (x > 400 && y < 100)) {
            onGround = true;
            vy = 0;
        } else {
            onGround = false;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && onGround) {
            vy = jumpSpeed;
            Gdx.app.log("Pressed", Float.toString(vy));
        }

        // test collision
        if(!onGround) {
            vy = vy - fallSpeed;
            Gdx.app.log("unpressed", Float.toString(vy));
        }

        y += vy * Gdx.graphics.getDeltaTime();
    }
}

