import java.sql.Time;

public class Voiture {
    Double accx;
    Double accy;
    Double accz;
    Double gyrox;
    Double gyroy;
    Double gyroz;

    String Class;
    int Timestamp;

    //initialisation
    public Voiture (){

    }
    public Voiture(double accx, double accy, double accz, double gyrox, double gyroy, double gyroz, String myClass, int timestamp) {
        this.accx = accx;
        this.accy = accy;
        this.accz = accz;
        this.gyrox = gyrox;
        this.gyroy = gyroy;
        this.gyroz = gyroz;
        this.Class = myClass;
        this.Timestamp = timestamp;
    }
    // Méthodes getter (accès) pour accx
    public double getAccx() {
        return accx;
    }

    // Méthodes setter (modification) pour accx
    public void setAccx(double accx) {
        this.accx = accx;
    }

    // Méthodes getter (accès) pour accy
    public double getAccy() {
        return accy;
    }

    // Méthodes setter (modification) pour accy
    public void setAccy(double accy) {
        this.accy = accy;
    }

    // Méthodes getter (accès) pour accz
    public double getAccz() {
        return accz;
    }

    // Méthodes setter (modification) pour accz
    public void setAccz(double accz) {
        this.accz = accz;
    }

    // Répétez le même modèle pour gyrox, gyroy et gyroz

    // Méthodes getter (accès) pour myClass
    public String getMyClass() {
        return Class;
    }

    // Méthodes setter (modification) pour myClass
    public void setMyClass(String Class) {
        this.Class = Class;
    }

    // Méthodes getter (accès) pour timestamp
    public int getTimestamp() {
        return Timestamp;
    }

    // Méthodes setter (modification) pour timestamp
    public void setTimestamp(int Timestamp) {
        this.Timestamp = Timestamp;
    }

    public Double getGyrox() {
        return gyrox;
    }

    public void setGyrox(Double gyrox) {
        this.gyrox = gyrox;
    }

    public Double getGyroy() {
        return gyroy;
    }

    public void setGyroy(Double gyroy) {
        this.gyroy = gyroy;
    }

    public Double getGyroz() {
        return gyroz;
    }

    public void setGyroz(Double gyroz) {
        this.gyroz = gyroz;
    }

}


