import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.json.JSONArray;
import org.json.JSONObject;

public class histogram extends javax.swing.JFrame{
    private JPanel non;
    private JPanel histogr;
    private TimeSeries seriesAccX;
    private TimeSeries seriesAccY;
    private TimeSeries seriesAccZ;
    private TimeSeries seriesGyrox;
    private TimeSeries seriesGyroy;
    private TimeSeries seriesGyroz;
    private Vecteur V = new Vecteur();
    /*private XYDataset dataset;
    private XYDataset dataset2;
    private XYDataset dataset3;*/

    HistogramDataset dataset = new HistogramDataset();
    HistogramDataset dataset2 = new HistogramDataset();
    HistogramDataset dataset3 = new HistogramDataset();


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
        histogr.setVisible(true);
        System.out.println("je suis après le ajouter");

        jfc = ChartFactory.createHistogram("Slow",
                "Data", "Frequency", (IntervalXYDataset) dataset);
        jfc2 = ChartFactory.createHistogram("Normal",
                "Data", "Frequency", (IntervalXYDataset) dataset2);
        jfc3 = ChartFactory.createHistogram("Aggressive",
                "Data", "Frequency", (IntervalXYDataset) dataset3);
        ChartPanel cp = new ChartPanel(jfc);
        ChartPanel cp2 = new ChartPanel(jfc2);
        ChartPanel cp3 = new ChartPanel(jfc3);


        //histogr.add(cp);
        //histogr.setLayout(new FlowLayout(FlowLayout.LEFT));
        //histogr.add(cp);
        //histogr.add(cp2);
        //histogr.add(cp3);
        histogr.setLayout(new GridLayout());
        histogr.add(cp);
        histogr.add(cp2);
        histogr.add(cp3);
        histogr.revalidate();
        histogr.repaint();
        histogr.validate();
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


        dataset.addSeries("accXSlow",valueAXslow , 10);
        dataset2.addSeries("accXNormal",valueAXnormal , 10);
        dataset3.addSeries("accXAgressive",valueAXaggresive , 10);
        /*dataset.addSeries("accY",valueAY , 10);
        dataset.addSeries("accZ",valueAZ , 10);
        dataset.addSeries("GyroX",valueGX , 10);
        dataset.addSeries("GyroY",valueGY , 10);
        dataset.addSeries("GyroZ",valueGZ , 10);*/

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
    }
}
