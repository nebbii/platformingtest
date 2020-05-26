package com.nebsting.platformingtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle {

    public Texture image;

    public int vx;
    public int vy;
    public boolean onGround;

    public int walkAcceleration;
    public int walkMaxSpeed;

    public int jumpSpeed;
    public int fallSpeed;

    public Player() {
        image = new Texture(Gdx.files.internal("block.png"));
        width = 24;
        height = 24;
        x = 100;
        y = 184;
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
        gravity();

        /*
        Gdx.app.log("On Ground", Boolean.toString(onGround));
        Gdx.app.log("Speed", Float.toString(vy - jumpSpeed) + " of " + Float.toString(jumpSpeed));
        Gdx.app.log("X: ", Float.toString(x));
        Gdx.app.log("Y: ", Float.toString(y));
        Gdx.app.log("VY: ", Float.toString(vy));
        */
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
            vx = Math.min(vx + walkAcceleration, walkMaxSpeed);
        } 
        else if(vx > 0){
            vx = Math.max(vx - (walkAcceleration * 2), 0);
        }

        x += vx * Gdx.graphics.getDeltaTime();
    }

    public void jumpPlayer() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && onGround) {
            vy = jumpSpeed;
            this.setOnGround(false);
        }
    }

    public void gravity() {
        if(!onGround) {
            vy = vy - fallSpeed;
        }

        y += vy * Gdx.graphics.getDeltaTime();
    }

    public void collideTop(float pos) {
        this.vy = 0;
        this.y = pos;
    }

    public void collideBottom(float pos) {
        this.onGround = true;
        this.vy = 0;
        this.y = pos;
    }

    public void collideLeft(float pos) {
        this.onGround = true;
        this.vx = 0;
        this.x = pos;
    }

    public void collideRight(float pos) {
        this.onGround = true;
        this.vx = 0;
        this.x = pos;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVx() {
        return vx;
    }

    public void setVy(int vy) {
        this.vy = vy;

        if(vy != 0) {
            this.onGround = false;
        }
    }

    public int getVy() {
        return vy;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean getOnGround() {
        return onGround;
    }

}
