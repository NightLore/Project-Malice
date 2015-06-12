package com.mygdx.game;

import java.util.EnumMap;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.player.Character.*;

/**
 *
 *  @author  Nathan Lui
 *  @version May 26, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice
 */
public class Options
{
    /**
     * This array stores Key input values
     * 0    , 1   , 2    , 3   , 4
     * NORTH, EAST, SOUTH, WEST, Attack
     */
    public static int[] CONTROLS = new int[]{ Input.Keys.W,
                                                             Input.Keys.D,
                                               Input.Keys.S,
                                  Input.Keys.A,
                Input.Buttons.LEFT
             };

//    public static final String[] spriteNames = { "BlackMage", "Monk", "RedMage", "Thief",
//        "Warrior", "WhiteMage" };
    public enum Names {
        BlackMage( "Dark Wizard", "DarkFire" ), 
        Monk( "Brawler", "Boomerang" ), 
        RedMage( "Crimson Wizard", "Fireball" ), 
        Thief( "Bandit", "PoisonShot" ), 
        Warrior( "Warrior", "Sword1" ), 
        WhiteMage( "Mage of Justice", "HolyCross" );
        
        private String button, projectile;
        
        Names( String button, String projectile ) {
            this.button = button;
            this.projectile = projectile;
        }
        
        public String getButtonName()
        {
            return button;
        }
        
        public String getProjectileName()
        {
            return projectile;
        }
    }
    
//    public static final String[] projectileNames = { "DarkFire", "Boomerang", "Fireball",
//        "PoisonShot", "Sword1", "HolyCross" };
    public static final Skin buttonSkin = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
    public static final BitmapFont FONT = new BitmapFont();
    public static final Names[] NAMES = Names.values();
    public static final EnumMap<Names, TextureAtlas> playerAtlas = new EnumMap<Names, TextureAtlas>(Names.class);
    public static final HashMap<String, TextureAtlas> atlas = new HashMap<String, TextureAtlas>();
    public static final int NUMENEMIES = 7;
    
    public static void initialize()
    {
        Audio.initializeAudio();
        loadAtlas();
        createSkin();
    }
    
    private static void loadAtlas()
    {
        String s;
        for ( Names n : NAMES )
        {
            playerAtlas.put( n, new TextureAtlas( "img/sprites/Players/" + n + "/" + n + ".atlas" ) );
            s = n.getProjectileName();
            atlas.put( s, new TextureAtlas( "img/sprites/Projectiles/" + s 
                + "/" + s + ".atlas" ) );
        }
        for ( int i = 1; i <= NUMENEMIES; i++ )
        {
            s = "Enemy" + i;
            atlas.put( s, new TextureAtlas( "img/sprites/Enemies/" + s + "/" 
                                + s + ".atlas" ) );
        }
        atlas.put( "EnemyBullet", new TextureAtlas( "img/sprites/Projectiles/EnemyBullet/EnemyBullet.atlas" ) );
    }

    /**
     * Creates a skin and the text button style that will be displayed in the
     * main menu.
     * 
     * The skin should be the default LibGDX skin and the text button style
     * should also be the default style.
     */
    private static void createSkin()
    {
        buttonSkin.add( "default", FONT );

        // Create a texture
        Pixmap pixmap = new Pixmap( (int) Gdx.graphics.getWidth() / 4,
                (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888 );
        pixmap.setColor( Color.WHITE );
        pixmap.fill();
        buttonSkin.add( "background", new Texture( pixmap ) );
        pixmap.dispose();

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable( "background", Color.GRAY );
        textButtonStyle.down = buttonSkin.newDrawable( "background", Color.DARK_GRAY );
        textButtonStyle.checked = buttonSkin.newDrawable( "background", Color.DARK_GRAY );
        textButtonStyle.over = buttonSkin.newDrawable( "background", Color.LIGHT_GRAY );
        textButtonStyle.font = buttonSkin.getFont( "default" );
        buttonSkin.add( "default", textButtonStyle );
    }
    
    // -------------------------- Player Controls --------------------- //
    
    /**
     * Returns direction to go based on key input and an array that stores
     * input values
     * (Used by Player class)
     * @return direction or -1 if no direction
     */
    public static int getInputDirection() {
        int dirY = -1;
        if ( Gdx.input.isKeyPressed( CONTROLS[0] ) )
            dirY = NORTH;
        if ( Gdx.input.isKeyPressed( CONTROLS[2] ) )
            dirY = ( dirY == NORTH ) ? -1 : SOUTH;
        
        int dirX = -1;
        if ( Gdx.input.isKeyPressed( CONTROLS[1] ) )
            dirX = EAST;
        if ( Gdx.input.isKeyPressed( CONTROLS[3] ) )
            dirX = ( dirX == EAST ) ? -1 : WEST;
        
        if ( dirY == -1 )
            return dirX;
        if ( dirX == -1 )
            return dirY;
        if ( dirY == NORTH && dirX == WEST )
            return NORTHWEST;
        return ( dirY + dirX ) / 2;
    }
    
    /*** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * note:ALL METHODS BELOW ARE FOR TESTING AND ISOLATION OF THE GDX CLASS *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // -------------------------- Music and Audio --------------------- //
    public static class Audio {
        public static boolean SOUND_MUTED = false; // not implemented into the game, provides ability to mute once all audio is isolated
        public static boolean MUSIC_MUTED = false;

        public static Music mainTheme;
        public static HashMap<String,Sound> SOUNDS;

        /**
         * Initializes all the audio
         */
        private static void initializeAudio()
        {
            mainTheme = Gdx.audio.newMusic( Gdx.files.internal( "audio/music/revivedpower.mp3" ) );
            SOUNDS = new HashMap<String, Sound>();
            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
            for ( Names n : NAMES )
                SOUNDS.put( n.getProjectileName(), Gdx.audio.newSound( Gdx.files.internal( "audio/sound/" + n.getProjectileName().toLowerCase() + ".wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
        }
        
        /**
         * Stops the theme music from playing
         * @param type method of stopping:
         *          0 - pauses playing
         *          1 - stops playing (returns theme to the beginning)
         */
        public static void stopTheme( int type )
        {
            if ( mainTheme.isPlaying() ) // may be able to remove
                switch ( type )
                {
                    case 0:
                        mainTheme.pause();
                        break;
                    case 1:
                        mainTheme.stop();
                        break;
                }
        }
        
        /**
         * Plays the theme music
         * @param volume volume to set the theme music to
         */
        public static void playTheme( float volume )
        {
            if ( mainTheme == null )
                initializeAudio();
            mainTheme.setLooping( true );
            mainTheme.setVolume( volume );
            if ( !MUSIC_MUTED )
                mainTheme.play();
            else
                stopTheme( 1 );
        }

        /**
         * Plays a sound based on a given string (relates to the file name)
         * @param s string related to the file name of the sound to play
         */
        public static void playAudio( String s )
        {
            if ( !SOUND_MUTED && SOUNDS.containsKey( s ) )
                SOUNDS.get( s ).play();
        }
    }
    
    // -------------------------- File Input -------------------------- //
    // note: File Input may have to be separated somehow through constructors, 
    //       this class may not be able to do that
    
}
