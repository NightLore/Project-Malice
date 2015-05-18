package com.mygdx.game.Map;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen extends ScreenAdapter
{
    private Map map = new Map( 25, 25 ); // TODO change to be dynamic
    private OrthographicCamera cam = new OrthographicCamera(320,480);
    private Sprite player = new Sprite( new Texture( "map/Mine.png" ) );
    
    
    @Override
    public void render( float delta )
    {
        cam.position.x = player.getX();
        cam.position.y = player.getY();
        cam.update();
    }
    
    @Override
    public void dispose()
    {
        map.dispose();
    }
}
