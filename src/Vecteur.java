public class Vecteur {
    Double[] VaccX = new Double[125];
    Double[] VaccY = new Double[125];
    Double[] VaccZ = new Double[125];
    Double[] VGyrox = new Double[125];
    Double[] VGyroy = new Double[125];
    Double[] VGyroz = new Double[125];


    public Vecteur() {
        for (int i = 0; i < 125; i++) {
            VaccX[i] = 0.0;
            VaccY[i] = 0.0;
            VaccZ[i] = 0.0;
            VGyrox[i] = 0.0;
            VGyroy[i] = 0.0;
            VGyroz[i] = 0.0;
        }

    }
}

