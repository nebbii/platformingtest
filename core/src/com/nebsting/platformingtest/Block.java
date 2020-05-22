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
        this.image = new Texture(Gdx.files.internal("block.png"));
        this.width = 24;
        this.height = 24;
        this.x = 100;
        this.y = 24;
        this.vx = 0;
        this.vy = 0;

        this.walkAcceleration = 20;
        this.walkMaxSpeed = 400;

        this.jumpSpeed = 400;
        this.fallSpeed = 40;
    }

    public void logic() {
        this.movePlayer();
        //this.jumpPlayer();
    }

    public void movePlayer() {
        // move left
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.vx = Math.max(this.vx - this.walkAcceleration, (this.walkMaxSpeed * -1));
        } 
        else if(this.vx < 0){
            this.vx = Math.min(this.vx + (this.walkAcceleration * 2), 0);
        } 
        // move right
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.vx = Math.min(this.vx + this.walkAcceleration, this.walkMaxSpeed);
        } 
        else if(this.vx > 0){
            this.vx = Math.max(this.vx - (this.walkAcceleration * 2), 0);
        }
        this.x += this.vx * Gdx.graphics.getDeltaTime();

        Gdx.app.log("Speed", Float.toString(this.vx - this.walkAcceleration) + " of " + Float.toString(this.walkMaxSpeed));
    }

    public void jumpPlayer() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            this.vy = this.jumpSpeed;
            this.y += this.vy * Gdx.graphics.getDeltaTime();
        }
        if(this.vy > 0) {
            this.vy = Math.min(this.vy, (this.jumpSpeed * 2));
        }
    }
}

