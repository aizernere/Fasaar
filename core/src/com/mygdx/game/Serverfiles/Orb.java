package com.mygdx.game.Serverfiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Helpers.DecalHelper;
import com.mygdx.game.Screens.FasaarGame;
import com.mygdx.game.Helpers.Utils;

public class Orb {
    public Vector3 position;
    public Decal decal;
    boolean isCollected;

    public Orb(float x, float z) {
        position = new Vector3();
        position.set(x, Utils.getHeight(x, z), z);
        decal = Decal.newDecal( 40, 40, new TextureRegion(new Texture("orb.png")), true);
        decal.setPosition(position);
        isCollected = false;
    }

    public void pickup() {
        isCollected = true;
    }

    public void update() {
        if (!isCollected) {
            FasaarGame.decalBatch.add(decal);
            DecalHelper.faceCameraPerpendicularToGround(decal, FasaarGame.scene.cam);
        }
    }
}
