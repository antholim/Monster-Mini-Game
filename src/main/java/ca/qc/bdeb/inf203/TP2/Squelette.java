package ca.qc.bdeb.inf203.TP2;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.util.ArrayList;

public class Squelette extends EntiteQuiBouge {
    private final double w = 48;
    private final double h = 96;
    private final Image[] listeImagesFrames = new Image[3];
    private Image imageSquelette = new Image("stable.png");
    private double x, y;
    private final double saut = 600;
    private double accelerationX;
    private double accelerationY = 1200;
    private boolean dansLesAirs = true;
    private final double vitesseMax = 300;
    private int nbVies;

    public Squelette() {
        listeFrames();
        this.x = MainJavaFX.WIDTH/2;
        this.y = MainJavaFX.HEIGHT/2;
        this.vx = 0;
        this.nbVies = 3;
        this.accelerationX = 0;
    }

    public void update(double deltaTemps) {
        updatePhysique();
        vy += deltaTemps * accelerationY;
        vx += deltaTemps * accelerationX;
        y += deltaTemps * vy;
        x += deltaTemps * vx;
    }

    /**
     * Cette méthode s'occupe de la physique du squelette. Elle s'assure d'abord que le squelette ne puisse pas
     * sauter 2 fois, que le squelette ne sort pas de l'écran, le pouvoir de le contrôler avec les flèches, etc.
     */
    public void updatePhysique() {
        if (y + h >= MainJavaFX.HEIGHT) {
            y = MainJavaFX.HEIGHT - h;
            vy = 0;
            dansLesAirs = false;
        } else {
            dansLesAirs = true;
        }
        if (!dansLesAirs)
            accelerationY = 0;
        else {
            accelerationY = 1200;
            accelerationX = 0;
        }

        if (x < 0) {
            x = 0;
            vx = -vx;
        } else if (x + w > MainJavaFX.WIDTH) {
            x = MainJavaFX.WIDTH - w;
            vx = -vx;
        }

        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean jump = Input.isKeyPressed(KeyCode.UP);

        if (left) {
            accelerationX = -1000;
        } else if (right) {
            accelerationX = 1000;
        } else {
            //Rebondir sur le côté de l'écran
            if (vx > 4)
                accelerationX = -200;
            else if (vx < -4)
                accelerationX = 200;
            else if (Math.abs(vx) <= 4) {
                vx = 0;
                accelerationX = 0;
            }
        }
        if (jump && !dansLesAirs) {
            vy = -saut;
        }
        //Vitesse max
        if (vx >= vitesseMax)
            vx = vitesseMax;
        if (vx <= -vitesseMax)
            vx = -vitesseMax;
    }

    public void draw(GraphicsContext context, int frame) {
        if (vx != 0) {
            imageSquelette = listeImagesFrames[frame % listeImagesFrames.length];
        }
        imageSquelette = verifierDirection(context, imageSquelette);
        context.drawImage(imageSquelette, x, y, w, h);
    }

    /**
     * Cette méthode contient les frames du squelette
     */
    public void listeFrames() {
        listeImagesFrames[0] = new Image("stable.png");
        listeImagesFrames[1] = new Image("marche1.png");
        listeImagesFrames[2] = new Image("marche2.png");
    }
    public void updateNbVies(Monstres monstres){
        if (monstres.aSurvecu()) {
            nbVies--;
        }
    }
    public int getNbVies() {
        return nbVies;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setNbVies(int nbVies) {
        this.nbVies = nbVies;
    }
}