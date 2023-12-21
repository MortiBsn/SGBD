public class Vecteur {
    Double[] VaccX = new Double[8000];
    Double[] VaccY = new Double[8000];
    Double[] VaccZ = new Double[8000];
    Double[] VGyrox = new Double[8000];
    Double[] VGyroy = new Double[8000];
    Double[] VGyroz = new Double[8000];

    String[] VString = new String[8000];


    public Vecteur() {
        for (int i = 0; i < 125; i++) {
            VaccX[i] = 0.0;
            VaccY[i] = 0.0;
            VaccZ[i] = 0.0;
            VGyrox[i] = 0.0;
            VGyroy[i] = 0.0;
            VGyroz[i] = 0.0;
            VString[i]="null";
        }

    }
}

