package com.mygdx.game.Entities.Monsters;

import com.mygdx.game.Entities.Leviathan;

public class ForestLurker extends Leviathan {
    public ForestLurker() {
        super("forest_lurker.png", 100, 100, 2f, 4, 8);
        setPosition(1500, getY(1500, 2400) , 2400);
    }
}
