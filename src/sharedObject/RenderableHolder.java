package sharedObject;
 
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
import javax.sound.sampled.AudioSystem;
 
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import logic.Bullet;
import logic.Enemy;
import logic.Ship;
//import logic.;
 
public class RenderableHolder {
    private static final RenderableHolder instance = new RenderableHolder();
 
    private List<IRenderable> entities;
    private Comparator<IRenderable> comparator;
    public static AudioClip damageSound;
    public static AudioClip deathSound;
    public static AudioClip shieldSound;
    public static AudioClip gameSong;
    public static AudioClip themeSong;
    public static AudioClip enemyShoot;
    public static AudioClip enemydamageSound;
    public static AudioClip levelupSound;
    public static AudioClip shootsound;
    public static AudioClip winSound;
    public static Font font;
    public static Font helpfont;
    public static Image a;
    public static Image f;
    public static Image k;
    public static Image u;
    public static Image ship;
    public static Image backGround;
    public static Image menuBG;
    public static Image levelUpImg;
    public static Image alien1;
    public static Image	alien2;
    public static Image	alien3;
    public static Image	alienBoss;
    static {
        loadResource();
    }
 
    public static void loadResource() {
        try {
            enemydamageSound = new AudioClip(ClassLoader.getSystemResource("sounds/hitmarkerSound.wav").toString());
            damageSound = new AudioClip(ClassLoader.getSystemResource("sounds/damageSound.wav").toString());
            deathSound = new AudioClip(ClassLoader.getSystemResource("sounds/deathSound.wav").toString());
            shieldSound = new AudioClip(ClassLoader.getSystemResource("sounds/shieldSound.wav").toString());
            themeSong = new AudioClip(ClassLoader.getSystemResource("sounds/Ost2.wav").toString());
            gameSong = new AudioClip(ClassLoader.getSystemResource("sounds/gameSong2.wav").toString());
            enemyShoot = new AudioClip(ClassLoader.getSystemResource("sounds/alienBeam.wav").toString());
            shootsound = new AudioClip(ClassLoader.getSystemResource("sounds/bulletSound.wav").toString());
            levelupSound = new AudioClip(ClassLoader.getSystemResource("sounds/levelUpSound.wav").toString());
            winSound = new AudioClip(ClassLoader.getSystemResource("sounds/smb_world_clear.wav").toString());
        } catch (Exception e) {
            System.out.println("Cant load sound in RenderHolder");
        }
        try {
            a = new Image(ClassLoader.getSystemResource("letters/a.png").toString());
            f = new Image(ClassLoader.getSystemResource("letters/f.png").toString());
            k = new Image(ClassLoader.getSystemResource("letters/k.png").toString());
            u = new Image(ClassLoader.getSystemResource("letters/u.png").toString());
        } catch (Exception e) {
            System.out.println("Cant load letters in RenderHolder");
        }
        try {
        	levelUpImg = new Image(ClassLoader.getSystemResource("images/levelup2.png").toString());
        	menuBG = new Image(ClassLoader.getSystemResource("images/CyberPunk2.jpg").toString());
            ship = new Image(ClassLoader.getSystemResource("images/shipSkin.gif").toString());
            backGround = new Image(ClassLoader.getSystemResource("images/star3.jpg").toString());
            alien1 = new Image(ClassLoader.getSystemResource("images/alien1Skin.gif").toString());
			alien2 = new Image(ClassLoader.getSystemResource("images/alien2Skin.gif").toString());
			alien3 = new Image(ClassLoader.getSystemResource("images/alien3Skin.gif").toString());
			alienBoss = new Image(ClassLoader.getSystemResource("images/boss1.gif").toString());
        } catch (Exception e) {
            System.out.println("Cant load ship and bg in RenderHolder");
        }
        try {
            InputStream fontStream = ClassLoader.getSystemResourceAsStream("font/BLADRMF_.TTF");
            font = Font.loadFont(fontStream, 56);
            InputStream fontStream2 = ClassLoader.getSystemResourceAsStream("font/BebasNeue Bold.otf");
			helpfont = Font.loadFont(fontStream2,30);
        } catch (Exception e) {
            System.out.println("Cant load font in RenderHolder");
        }
       
    }
    public RenderableHolder() {
        entities = new ArrayList<IRenderable>();
        comparator = (IRenderable o1, IRenderable o2) -> {
            if (o1.getZ() > o2.getZ())
                return 1;
            return -1;
        };
    }
 
    public static RenderableHolder getInstance() {
        return instance;
    }
 
   
 
    public void add(IRenderable entity) {
        //System.out.println("add");
        entities.add(entity);
        Collections.sort(entities, comparator);
        /*for(IRenderable x: entities){
            if(x instanceof Ship); //System.out.println("Ship");
            if(x instanceof Enemy); //System.out.println("Enemy");
            if(x instanceof Bullet); //System.out.println("Bullet");
        }*/
    }
 
    public void update() {
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i).isDestroyed())
                entities.remove(i);
        }
    }
 
    public List<IRenderable> getEntities() {
        return entities;
    }
}