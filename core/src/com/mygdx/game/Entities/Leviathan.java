package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Helpers.DecalHelper;
import com.mygdx.game.Screens.FasaarGame;
import com.mygdx.game.Helpers.Utils;

public abstract class Leviathan extends Entity {
    private float detectionThreshold = 12.0f;
    private float avoidanceDistance = 5.0f;
    public Leviathan(String spriteSheetPath, int spriteWidth, int spriteHeight, float velocity) {
        super("leviathans/" + spriteSheetPath, spriteWidth, spriteHeight, velocity);
    }

    public void update() {
        setDecal();
        setPosition(position);
        setDirection(chase(FasaarGame.mainPlayer.getPosition()));

        stateTime += Gdx.graphics.getDeltaTime();
        if (stateTime > animationSpeed) {
            spriteCtr++;
            if (spriteCtr >= FRAME_ROWS) {
                spriteCtr = 0;
            }
            stateTime = 0;
        }

        FasaarGame.decalBatch.add(decal);
        DecalHelper.applyLighting(decal, FasaarGame.scene.cam);
        DecalHelper.faceCameraPerpendicularToGround(decal, FasaarGame.scene.cam);
    }

    public Leviathan(String spriteSheetPath, int spriteWidth, int spriteHeight, float velocity, int FRAME_ROWS, int FRAME_COLS) {
        super("leviathans/" + spriteSheetPath, spriteWidth, spriteHeight, velocity, FRAME_ROWS, FRAME_COLS);
    }

    public Direction chase(Vector3 player) {
        Vector3 currentPosition = new Vector3(getPosition());
        Vector3 direction = new Vector3(player).sub(currentPosition).nor();
        Vector3 potentialPosition = new Vector3(currentPosition).add(direction.scl(getVelocity()));

        if (player.dst(260, Utils.getHeight(260, 230), 230) < 24) {
            return Utils.vectorToDirection(direction.x, direction.z);
        }

        if (position.dst(FasaarGame.mainPlayer.getPosition()) < 24) {
            FasaarGame.mainPlayer.die(this);
        }

        float currentHeight = Utils.getHeight(currentPosition.x, currentPosition.z) + DecalHelper.offset(spriteHeight);
        float newHeight = Utils.getHeight(potentialPosition.x, potentialPosition.z) + DecalHelper.offset(spriteHeight);

        if (newHeight - currentHeight > detectionThreshold) {
            avoidBump(direction);
        } else {
            setPosition(potentialPosition);
        }

        return Utils.vectorToDirection(direction.x, direction.z);
    }

    private void avoidBump(Vector3 direction) {
        Vector3 leftDirection = new Vector3(-direction.z, direction.y + DecalHelper.offset(spriteHeight), direction.x);
        Vector3 leftPosition = new Vector3(getPosition()).add(leftDirection.scl(avoidanceDistance));
        if (isValidPosition(leftPosition)) {
            setPosition(leftPosition);
            return;
        }

        Vector3 rightDirection = new Vector3(direction.z, direction.y + DecalHelper.offset(spriteHeight), -direction.x);
        Vector3 rightPosition = new Vector3(getPosition()).add(rightDirection.scl(avoidanceDistance));
        if (isValidPosition(rightPosition)) {
            setPosition(rightPosition);
        }
    }

    private boolean isValidPosition(Vector3 position) {
        float newHeight = Utils.getHeight(position.x, position.z) + DecalHelper.offset(spriteHeight);
        float currentHeight = Utils.getHeight(getPosition().x, getPosition().z) + DecalHelper.offset(spriteHeight);
        return Math.abs(newHeight - currentHeight) <= detectionThreshold;
    }
}