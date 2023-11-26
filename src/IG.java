import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import com.fasterxml.jackson.d

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.JSONObject;
import org.json.JSONArray;

public class IG extends javax.swing.JFrame {
    private JPanel panel1;
    private JButton Timestamp;
    private JButton defiler1;
    private JPanel Fenetre_Haut;
    private JTextField textField1;
    private JCheckBox GyroZ;
    private JCheckBox AccX;
    private JCheckBox GyroY;
    private JCheckBox GyroX;
    private JCheckBox AccY;
    private JCheckBox AccZ;
    private JPanel Graphique;
    private static int id ;

    private JFreeChart jfc;


    public IG()
    {
        textField1.setText("3581700");

        this.setContentPane(panel1);
        setSize(800,600);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Defiler.addActionListener(actionListener);

        Graphique.setLayout(new FlowLayout());


        Timestamp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                id=Integer.parseInt(textField1.getText());
                final XYDataset dataset = testGET();
                jfc = ChartFactory.createTimeSeriesChart(
                        "Graphique",
                        "Timestamp", // x
                        "valeur", // y
                        dataset,
                        false, false, false
                );
                ChartPanel cp = new ChartPanel(jfc);

                Graphique.add(cp);
                Graphique.revalidate();
                Graphique.repaint();
            }
        });


        System.out.println("coucou");


    }
    private XYDataset testGET()
    {
        String line;
        StringBuilder result = new StringBuilder();
        //int id = 3581700; //ça sera à faire pour après là je fais que tester
        try
        {

            System.out.println(id);
            URL url = new URL("http://192.168.195.135:8080/ords/hr/labo/test/"+id);
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
        // Créer un tableau pour stocker les objets
        List<Voiture> dataObjects = new ArrayList<>();
        //JSONParser parser = new JSONParser();
        //System.out.println(reponse);

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
        System.out.println(dataObjects);

        TimeSeries seriesAccX = new TimeSeries("AccX");
        TimeSeries seriesAccY = new TimeSeries("AccY");
        TimeSeries seriesAccZ = new TimeSeries("AccZ");
        TimeSeries seriesGyrox = new TimeSeries("Gyrox");
        TimeSeries seriesGyroy = new TimeSeries("Gyroy");
        TimeSeries seriesGyroz = new TimeSeries("Gyroz");
        TimeSeries seriesClass = new TimeSeries("Class");
        TimeSeries seriesTimestamp = new TimeSeries("Timestamp");

        Double[] VaccX = new Double[125];
        Double[] VaccY = new Double[125];
        Double[] VaccZ = new Double[125];
        Double[] VGyrox = new Double[125];
        Double[] VGyroy = new Double[125];
        Double[] VGyroz = new Double[125];
        String[] VClass = new String[125];
        int[] VTimestamp = new int [125];
        Second current = new Second( );
        for (int i=0; i<dataObjects.size();i++)
        {
            VaccX[i]=dataObjects.get(i).getAccx();
            VaccY[i] = dataObjects.get(i).getAccy();
            VaccZ[i] = dataObjects.get(i).getAccz();
            VGyrox[i] = dataObjects.get(i).getGyrox();
            VGyroy[i] = dataObjects.get(i).getGyroy();
            VGyroz[i] = dataObjects.get(i).getGyroz();
            VClass[i] = dataObjects.get(i).getMyClass();
            VTimestamp[i] = dataObjects.get(i).getTimestamp();

            seriesAccX.add(current, VaccX[i]);
            /*seriesAccY.add(new Second(new Date(VTimestamp[i] * 1000)), VaccY[i]);
            seriesAccZ.add(new Second(new Date(VTimestamp[i] * 1000)), VaccZ[i]);
            seriesGyrox.add(new Second(new Date(VTimestamp[i] * 1000)), VGyrox[i]);
            seriesGyroy.add(new Second(new Date(VTimestamp[i] * 1000)), VGyroy[i]);
            seriesGyroz.add(new Second(new Date(VTimestamp[i] * 1000)), VGyroz[i]);*/
            current = ( Second ) current.next( );

        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(seriesAccX);
        return dataset;
        /*dataset.addSeries(seriesAccY);
        dataset.addSeries(seriesAccZ);
        dataset.addSeries(seriesGyrox);
        dataset.addSeries(seriesGyroy);
        dataset.addSeries(seriesGyroz);
        dataset.addSeries(seriesClass);
        dataset.addSeries(seriesTimestamp);*/

    }
    public static void main (String[] args){
        IG frame= new IG();
        frame.setVisible(true);
    }


}


