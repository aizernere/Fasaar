package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;

public class Fasaar extends Game {

    @Override
    public void create() {
        setScreen(new LoginApp(this));
    }
    public void render(){
        super.render();
    }
    public void dispose(){
        super.dispose();
    }
}
