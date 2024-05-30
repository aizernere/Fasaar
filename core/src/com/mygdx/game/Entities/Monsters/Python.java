package com.mygdx.game.Entities.Monsters;

import com.mygdx.game.Entities.Leviathan;

public class Python extends Leviathan {
    public Python() {
        super("python_head.png", 200, 200, 2f, 3, 8);
        setPosition(860, getY(860, 440) , 440);
    }
}
