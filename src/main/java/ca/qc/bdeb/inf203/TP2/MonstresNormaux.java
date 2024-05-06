package ca.qc.bdeb.inf203.TP2;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MonstresNormaux extends Monstres {
    protected ArrayList<Color> couleurPossible = new ArrayList();
    protected Color couleur;
    protected double accelerationY = 100;

    public MonstresNormaux(int niveau) {
        possibilitesDesMonstres();
        choisirMonstre();
        positionInitial();
        //Vitesse à changer pour les monstres différents
        vy = (rd.nextInt(200 - 100) + 100) * -1;
        vx = 100 * Math.pow(niveau, 0.33) + 200;

        directionInitial();
        //Couleurs
        int couleurIndex = rd.nextInt(couleurPossible.size());
        couleur = couleurPossible.get(couleurIndex);
        imageEntite = ImageHelpers.colorize(imageEntite, couleur);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
        updatePhysique(deltaTemps);
    }

    @Override
    public void updatePhysique(double deltaTemps) {
        vy += deltaTemps * accelerationY;
        y += deltaTemps * vy;
    }

    /**
     * Cette méthode rentre toutes les possibilités des monstres (images, couleurs).
     */

    public void possibilitesDesMonstres() {
        String nomImage = "0.png";
        for (int i = 0; i < 8; i++) {
            Image image = new Image(nomImage);
            listeImagesMonstre.add(image);
            nomImage = nomImage.replace(Integer.toString(i), Integer.toString(i + 1));
        }
        couleurPossible.add(Color.BLUE);
        couleurPossible.add(Color.GREEN);
        couleurPossible.add(Color.RED);
        couleurPossible.add(Color.ORANGE);
        couleurPossible.add(Color.YELLOW);
    }

    /**
     * Cette méthode va choisir un monstre au hasard selon les possibilités de la méthode possibilitesDesMonstres()
     */
    public void choisirMonstre() {
        choixDuMonstre = rd.nextInt(listeImagesMonstre.size());
        imageEntite = listeImagesMonstre.get(choixDuMonstre);
        int couleurIndex = rd.nextInt(couleurPossible.size());
        couleur = couleurPossible.get(couleurIndex);
        imageEntite = ImageHelpers.colorize(imageEntite, couleur);
    }
}

