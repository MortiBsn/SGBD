import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.IntervalXYDataset;
import org.json.JSONArray;
import org.json.JSONObject;

public class histogram extends javax.swing.JFrame{
    private JPanel non;
    private JPanel histogr;
    private JButton Suivant;
    private TimeSeries seriesAccX;
    private TimeSeries seriesAccY;
    private TimeSeries seriesAccZ;
    private TimeSeries seriesGyrox;
    private TimeSeries seriesGyroy;
    private TimeSeries seriesGyroz;
    private Vecteur V = new Vecteur();
    double[] valueAXslow = new double[8000];
    double[] valueAXnormal = new double[8000];
    double[] valueAXaggresive = new double[8000];
    double[] valueAYslow = new double[8000];
    double[] valueAYnormal = new double[8000];
    double[] valueAYaggresive = new double[8000];
    double[] valueAZslow = new double[8000];
    double[] valueAZnormal = new double[8000];
    double[] valueAZaggresive = new double[8000];
    double[] valueGXslow = new double[8000];
    double[] valueGXnormal = new double[8000];
    double[] valueGXaggresive = new double[8000];
    double[] valueGYslow = new double[8000];
    double[] valueGYnormal = new double[8000];
    double[] valueGYaggresive = new double[8000];
    double[] valueGZslow = new double[8000];
    double[] valueGZnormal = new double[8000];
    double[] valueGZaggresive = new double[8000];
    private int compteur =0;


    HistogramDataset dataset = new HistogramDataset();
    HistogramDataset dataset2 = new HistogramDataset();
    HistogramDataset dataset3 = new HistogramDataset();
    HistogramDataset dataset4 = new HistogramDataset();
    HistogramDataset dataset5 = new HistogramDataset();
    HistogramDataset dataset6 = new HistogramDataset();
    HistogramDataset dataset7 = new HistogramDataset();
    HistogramDataset dataset8 = new HistogramDataset();
    HistogramDataset dataset9 = new HistogramDataset();
    HistogramDataset dataset10 = new HistogramDataset();
    HistogramDataset dataset11 = new HistogramDataset();
    HistogramDataset dataset12 = new HistogramDataset();
    HistogramDataset dataset13 = new HistogramDataset();
    HistogramDataset dataset14 = new HistogramDataset();
    HistogramDataset dataset15 = new HistogramDataset();
    HistogramDataset dataset16 = new HistogramDataset();
    HistogramDataset dataset17 = new HistogramDataset();
    HistogramDataset dataset18 = new HistogramDataset();


    private JFreeChart jfc;
    private JFreeChart jfc2;
    private JFreeChart jfc3;

    List<Voiture> dataObjects = new ArrayList<>();

    public void setSeries(List<Voiture> d) {

        this.dataObjects=d;

    }

    public void ConstructionHistograme()
    {
        Ajouter();
        CompteurGraphique();

        //histogr.validate();
    }




    public void Ajouter(){
        String line;
        StringBuilder result = new StringBuilder();
        //int id = 3581700; //ça sera à faire pour après là je fais que tester
        try
        {
            URL url = new URL("http://192.168.195.135:8080/ords/hr/labo/fin");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //On utilise le verbe GET pour une recherche par ID
            con.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //System.out.println("oui");
            while ((line = rd.readLine()) != null)
            {
                result.append(line);
                //System.out.println(line);
            }
            rd.close();
            //System.out.println(result);

            //System.out.println("Le JSON des détails:" + result);
        }catch (Exception e)
        {
            System.out.println("Error in  getting details : " + e);
        }
        String reponse=result.toString();

        // Analyser la réponse JSON
        JSONObject jsonResponse = new JSONObject(reponse);

        // Extraire le tableau 'items' de la réponse JSON
        JSONArray itemsArray = jsonResponse.getJSONArray("items");

        // Parcourir le tableau 'items' et extraire chaque objet
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject item = itemsArray.getJSONObject(i);

            Double accX = item.getDouble("accx");
            Double accY = item.getDouble("accy");
            Double accZ = item.getDouble("accz");
            Double Gyrox =  item.getDouble("gyrox");
            Double Gyroy =  item.getDouble("gyroy");
            Double Gyroz =  item.getDouble("gyroz");
            int Timestamp =  item.getInt("timestamp");
            String Class = item.getString("class");
            Voiture voiture = new Voiture(accX, accY, accZ, Gyrox, Gyroy, Gyroz, Class, Timestamp);
            // Ajouter l'objet à la liste
            dataObjects.add(voiture);
        }



        String aggresive = "AGGRESSIVE";
        String slow ="SLOW";
        String normal = "NORMAL";
        System.out.println(dataObjects.size());
        int i=0;
        for (i=0;i<dataObjects.size();i++)
        {
            V.VaccX[i]=dataObjects.get(i).getAccx();
            V.VaccY[i] = dataObjects.get(i).getAccy();
            V.VaccZ[i] = dataObjects.get(i).getAccz();
            V.VGyrox[i] = dataObjects.get(i).getGyrox();
            V.VGyroy[i] = dataObjects.get(i).getGyroy();
            V.VGyroz[i] = dataObjects.get(i).getGyroz();
            V.VString[i]= dataObjects.get(i).getMyClass();
            //System.out.println(V.VString[i]);
            //System.out.println(i);
            if (V.VString[i].equals(slow)) {
                valueAXslow[i] = V.VaccX[i];
                valueAYslow[i] = V.VaccY[i];
                valueAZslow[i] = V.VaccZ[i];
                valueGXslow[i] = V.VGyrox[i];
                valueGYslow[i] = V.VGyroy[i];
                valueGZslow[i] = V.VGyroz[i];
            } else if (V.VString[i].equals(normal)) {
                valueAXnormal[i] = V.VaccX[i];
                valueAYnormal[i] = V.VaccY[i];
                valueAZnormal[i] = V.VaccZ[i];
                valueGXnormal[i] = V.VGyrox[i];
                valueGYnormal[i] = V.VGyroy[i];
                valueGZnormal[i] = V.VGyroz[i];
            } else if (V.VString[i].equals(aggresive)) {
                valueAXaggresive[i] = V.VaccX[i];
                valueAYaggresive[i] = V.VaccY[i];
                valueAZaggresive[i] = V.VaccZ[i];
                valueGXaggresive[i] = V.VGyrox[i];
                valueGYaggresive[i] = V.VGyroy[i];
                valueGZaggresive[i] = V.VGyroz[i];
            }

        }

    }

    private void CompteurGraphique()
    {


        if (compteur == 0)
        {
            //
            dataset.addSeries("accXSlow",valueAXslow , 10);
            dataset2.addSeries("accXNormal",valueAXnormal , 10);
            dataset3.addSeries("accXAgressive",valueAXaggresive , 10);

            jfc = ChartFactory.createHistogram("Slow",
                    "Data", "Frequency", (IntervalXYDataset) dataset);
            jfc2 = ChartFactory.createHistogram("Normal",
                    "Data", "Frequency", (IntervalXYDataset) dataset2);
            jfc3 = ChartFactory.createHistogram("Aggressive",
                    "Data", "Frequency", (IntervalXYDataset) dataset3);

        } else if (compteur==1) {
            dataset4.addSeries("accYSlow",valueAYslow , 10);
            dataset5.addSeries("accYNormal",valueAYnormal , 10);
            dataset6.addSeries("accYAgressive",valueAYaggresive , 10);

            jfc = ChartFactory.createHistogram("Slow",
                    "Data", "Frequency", (IntervalXYDataset) dataset4);
            jfc2 = ChartFactory.createHistogram("Normal",
                    "Data", "Frequency", (IntervalXYDataset) dataset5);
            jfc3 = ChartFactory.createHistogram("Aggressive",
                    "Data", "Frequency", (IntervalXYDataset) dataset6);

        } else if (compteur==2) {
            dataset7.addSeries("accZSlow",valueAYslow , 10);
            dataset8.addSeries("accZNormal",valueAYnormal , 10);
            dataset9.addSeries("accZAgressive",valueAYaggresive , 10);

            jfc = ChartFactory.createHistogram("Slow",
                    "Data", "Frequency", (IntervalXYDataset) dataset7);
            jfc2 = ChartFactory.createHistogram("Normal",
                    "Data", "Frequency", (IntervalXYDataset) dataset8);
            jfc3 = ChartFactory.createHistogram("Aggressive",
                    "Data", "Frequency", (IntervalXYDataset) dataset9);

        } else if (compteur==3) {
            dataset10.addSeries("GyroXSlow",valueGXslow , 10);
            dataset11.addSeries("GyroXNormal",valueGXnormal , 10);
            dataset12.addSeries("GyroXAgressive",valueGXaggresive , 10);

            jfc = ChartFactory.createHistogram("Slow",
                    "Data", "Frequency", (IntervalXYDataset) dataset10);
            jfc2 = ChartFactory.createHistogram("Normal",
                    "Data", "Frequency", (IntervalXYDataset) dataset11);
            jfc3 = ChartFactory.createHistogram("Aggressive",
                    "Data", "Frequency", (IntervalXYDataset) dataset12);

        } else if (compteur==4){
            dataset13.addSeries("GyroYSlow",valueGYslow , 10);
            dataset14.addSeries("GyroYNormal",valueGYnormal , 10);
            dataset15.addSeries("GyroYAgressive",valueGYaggresive , 10);

            jfc = ChartFactory.createHistogram("Slow",
                    "Data", "Frequency", (IntervalXYDataset) dataset13);
            jfc2 = ChartFactory.createHistogram("Normal",
                    "Data", "Frequency", (IntervalXYDataset) dataset14);
            jfc3 = ChartFactory.createHistogram("Aggressive",
                    "Data", "Frequency", (IntervalXYDataset) dataset15);
        } else if (compteur ==5){
            dataset16.addSeries("GyroZSlow",valueGZslow , 10);
            dataset17.addSeries("GyroZNormal",valueGZnormal , 10);
            dataset18.addSeries("GyroZAgressive",valueGZaggresive , 10);

            jfc = ChartFactory.createHistogram("Slow",
                    "Data", "Frequency", (IntervalXYDataset) dataset16);
            jfc2 = ChartFactory.createHistogram("Normal",
                    "Data", "Frequency", (IntervalXYDataset) dataset17);
            jfc3 = ChartFactory.createHistogram("Aggressive",
                    "Data", "Frequency", (IntervalXYDataset) dataset18);
        }
        //System.out.println(compteur);
        compteur++;
        histogr.setVisible(true);


        ChartPanel cp = new ChartPanel(jfc);
        ChartPanel cp2 = new ChartPanel(jfc2);
        ChartPanel cp3 = new ChartPanel(jfc3);
        histogr.removeAll();
        histogr.setLayout(new GridLayout());
        histogr.add(cp);
        histogr.add(cp2);
        histogr.add(cp3);
        histogr.revalidate();
        histogr.repaint();


    }


    public histogram()
    {
        seriesAccX = new TimeSeries("AccX");
        seriesAccY = new TimeSeries("AccY");
        seriesAccZ = new TimeSeries("AccZ");
        seriesGyrox = new TimeSeries("Gyrox");
        seriesGyroy = new TimeSeries("Gyroy");
        seriesGyroz = new TimeSeries("Gyroz");

        this.setContentPane(non);
        setSize(800,600);
        setContentPane(non);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        histogr.setLayout(new FlowLayout());
        Suivant.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("bouton click");
                CompteurGraphique();
            }
        });




    }
}
