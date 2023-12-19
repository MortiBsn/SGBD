import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
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
    private JButton Save;
    private static int id ;

    private Vecteur V = new Vecteur();
    private JFreeChart jfc;
    private XYDataset dataset;

    TimeSeries seriesAccX = new TimeSeries("AccX");
    TimeSeries seriesAccY = new TimeSeries("AccY");
    TimeSeries seriesAccZ = new TimeSeries("AccZ");
    TimeSeries seriesGyrox = new TimeSeries("Gyrox");
    TimeSeries seriesGyroy = new TimeSeries("Gyroy");
    TimeSeries seriesGyroz = new TimeSeries("Gyroz");
    List<Voiture> dataObjects = new ArrayList<>();

    int pos=0;

    public IG()
    {
        textField1.setText("3581700");

        this.setContentPane(panel1);
        setSize(800,600);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Defiler.addActionListener(actionListener);
        Graphique.setLayout(new FlowLayout());
        defiler1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mettre à jour la plage d'affichage de 10 en 10
                dataset=Ajouter();
                jfc = ChartFactory.createTimeSeriesChart(
                        "Graphique",
                        "Timestamp", // x
                        "valeur", // y
                        dataset,
                        false, false, false
                );

                /*XYPlot plot = jfc.getXYPlot();
                NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
                rangeAxis.setRange(-3.0,3);*/

                ChartPanel cp = new ChartPanel(jfc);

                Graphique.add(cp);
                Graphique.revalidate();
                Graphique.repaint();
                Graphique.setLayout(new BorderLayout());
                Graphique.removeAll();
                Graphique.add(cp,BorderLayout.CENTER);
                Graphique.validate();

            }
        });

        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Button clicked: Save");
                try{


                    //on convertit notre JFC en byte

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ChartUtils.writeChartAsPNG(bos, jfc,800,600);
                    byte[] byteArray= bos.toByteArray();

                    String encoded = Base64.getEncoder().encodeToString(byteArray);
                    //System.out.println(encoded);
                    JSONObject json = new JSONObject();
                    String Jugement ="test";
                    json.put("jugement",Jugement);
                    json.put("image", encoded);

                    String urlParameters = json.toString();
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8 );
                    int postDataLength = postData.length;
                    URL url = new URL("http://192.168.195.135:8080/ords/hr/labo/blob");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestMethod("POST"); //Verbe POST
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                    conn.setUseCaches(false);

                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.write(postData);

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null)
                    {
                        System.out.println(line);
                    }
                    rd.close();
                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });

        Timestamp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                id=Integer.parseInt(textField1.getText());
                dataset = testGET();
                jfc = ChartFactory.createTimeSeriesChart(
                        "Graphique",
                        "Timestamp", // x
                        "valeur", // y
                        dataset,
                        false, false, false
                );

                XYPlot plot = jfc.getXYPlot();
                NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
                rangeAxis.setRange(-3.0,3);

                /*ChartPanel cp = new ChartPanel(jfc);
                Graphique.add(cp);
                Graphique.revalidate();
                Graphique.repaint();*/
                ChartPanel cp = new ChartPanel(jfc);

                Graphique.setLayout(new BorderLayout());
                Graphique.removeAll();
                Graphique.add(cp,BorderLayout.CENTER);
                Graphique.validate();
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
        /*
        Voiture v = new Voiture();
        v=dataObjects.get(dataObjects.size()-1);
        System.out.println(v.getAccx());*/
        // c"tait un test pour savoir si je récupérais les bonnes valeurs

        dataset=Ajouter();
        return dataset;

    }

    public XYDataset Ajouter(){

        Second current = new Second( );
        int i=0;
        for (i=pos; i<pos+10 && i<dataObjects.size();i++)
        {
            V.VaccX[i]=dataObjects.get(i).getAccx();
            V.VaccY[i] = dataObjects.get(i).getAccy();
            V.VaccZ[i] = dataObjects.get(i).getAccz();
            V.VGyrox[i] = dataObjects.get(i).getGyrox();
            V.VGyroy[i] = dataObjects.get(i).getGyroy();
            V.VGyroz[i] = dataObjects.get(i).getGyroz();

            seriesAccX.addOrUpdate(current, V.VaccX[i]);
            seriesAccY.addOrUpdate(current, V.VaccY[i]);
            seriesAccZ.addOrUpdate(current, V.VaccZ[i]);
            seriesGyrox.addOrUpdate(current, V.VGyrox[i]);
            seriesGyroy.addOrUpdate(current, V.VGyroy[i]);
            seriesGyroz.addOrUpdate(current, V.VGyroz[i]);
            current = ( Second ) current.next( );

        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        if(AccX.isSelected())
            dataset.addSeries(seriesAccX);

        if (AccY.isSelected())
            dataset.addSeries(seriesAccY);

        if(AccZ.isSelected())
            dataset.addSeries(seriesAccZ);

        if(GyroX.isSelected())
            dataset.addSeries(seriesGyrox);

        if(GyroY.isSelected())
            dataset.addSeries(seriesGyroy);

        if(GyroZ.isSelected())
            dataset.addSeries(seriesGyroz);

        pos = i;
        return dataset;
    }




    public static void main (String[] args){
        IG frame= new IG();
        frame.setVisible(true);
    }


}