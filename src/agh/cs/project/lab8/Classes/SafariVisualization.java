package agh.cs.project.lab8.Classes;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SafariVisualization {
    private JFrame frame;
    private Timer timer;
    private JTextArea firstTextArea;
    private JTextArea secondTextArea;
    private JButton stopButton;
    private JButton endSimulationButton;
    private JButton searchButton;
    private JTextField searchField1;
    private JTextField searchField2;
    private JTextArea firstAnimalDetails;
    private JTextArea secondAnimalDetails;
    private ModuloMap firstModuloMap;
    private ModuloMap secondModuloMap;
    private PrintWriter statsToFile;

    public SafariVisualization(ModuloMap firstModuloMap, ModuloMap secondModuloMap){
        this.init();
        this.firstModuloMap = firstModuloMap;
        this.secondModuloMap = secondModuloMap;
    }

    private void init(){

        try {
            statsToFile = new PrintWriter("statistics.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.frame = new JFrame("Animals simulation");
        frame.setSize(1400,1200);
        frame.setLayout(null);
        frame.setVisible(true);

        this.firstTextArea=new JTextArea("");
        firstTextArea.setBounds(50,100, 500,500);
        firstTextArea.setEditable(false);

        this.secondTextArea = new JTextArea("");
        secondTextArea.setBounds(600,100, 500,500);
        secondTextArea.setEditable(false);

        this.firstAnimalDetails = new JTextArea("");
        firstAnimalDetails.setBounds(50,650,500,100);
        firstAnimalDetails.setEditable(false);

        this.secondAnimalDetails = new JTextArea("");
        secondAnimalDetails.setBounds(600,650,500,100);
        secondAnimalDetails.setEditable(false);

        this.searchField1 = new JTextField("");
        searchField1.setBounds(200, 620, 300, 20);
        searchField1.setEditable(true);

        this.searchField2 = new JTextField("");
        searchField2.setBounds(600, 620, 300, 20);
        searchField2.setEditable(true);


        this.searchButton = new JButton("Search");
        searchButton.setBounds(50,620,100,20);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                boolean flag1 = true;

                String s= searchField1.getText();

                int parsedX=-1;
                int parsedY=-1;
                for(int i=0; i<s.length(); i++){
                    if (s.charAt(i)=='(')
                        if (i + 1 < s.length())
                            parsedX = Character.getNumericValue(s.charAt(i+1));
                    else if (s.charAt(i)==',')
                        if(i+1<s.length())
                            parsedY = Character.getNumericValue(s.charAt(i + 1));

                }
                if(parsedX==-1 || parsedY==-1)  flag1=false;
                if(flag1){
                    Vector2d pos1ToObserver = new Vector2d(parsedX,parsedY);
                    firstModuloMap.setObservedAnimal(pos1ToObserver);
                    firstAnimalDetails.setText(firstModuloMap.getObservedAnimalStats());
                }
                else firstAnimalDetails.setText("Incorrect input");

                boolean flag2 = true;
                s=searchField2.getText();
                parsedX=-1;
                parsedY=-1;
                for(int i=0; i<s.length(); i++){
                    if (s.charAt(i)=='(')
                        if (i + 1 < s.length())
                            parsedX = Character.getNumericValue(s.charAt(i + 1));
                        else if (s.charAt(i)==',')
                            if(i+1<s.length())
                                parsedY = Character.getNumericValue(s.charAt(i+1));
                }
                if(parsedX==-1 || parsedY==-1)  flag2=false;
                if(flag2){
                    Vector2d pos2ToObserver = new Vector2d(parsedX,parsedY);
                    secondModuloMap.setObservedAnimal(pos2ToObserver);
                    secondAnimalDetails.setText(secondModuloMap.getObservedAnimalStats());
                }
                else secondAnimalDetails.setText("Incorrect input");
            }
        });


        this.stopButton = new JButton("");
        stopButton.setBounds(500,20,100,20);
        frame.add(stopButton);


        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (timer == null)
                    startAnimation();
                else
                    stopAnimation();
            }
        });

        this.endSimulationButton = new JButton("End");
        endSimulationButton.setBounds(500,60,100,20);
        endSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                statsToFile.close();
                System.exit(0);
            }
        });

        frame.add(stopButton);
        frame.add(endSimulationButton);
        frame.add(searchButton);
        searchButton.setVisible(false);


        frame.add(secondAnimalDetails);
        frame.add(firstAnimalDetails);
        frame.add(searchField1);
        frame.add(searchField2);
        secondAnimalDetails.setVisible(false);
        firstAnimalDetails.setVisible(false);
        searchField1.setVisible(false);
        searchField2.setVisible(false);
    }
    ActionListener timerListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(!firstModuloMap.areAnyAliveAnimals()){
                firstTextArea.setText(firstModuloMap.toString());
                stopButton.setVisible(false);
                timer.stop();
            }
            if(!secondModuloMap.areAnyAliveAnimals()){
                secondTextArea.setText(secondModuloMap.toString());
                stopButton.setVisible(false);
                timer.stop();
            }
            firstTextArea.setText(firstModuloMap.toString());
            frame.add(firstTextArea);
            secondTextArea.setText(secondModuloMap.toString());
            frame.add(secondTextArea);
            SwingUtilities.updateComponentTreeUI(frame);
            statsToFile.println("\n");
            statsToFile.println("First map:");
            statsToFile.println(firstModuloMap.writeMapStats());
            statsToFile.println("\n");
            statsToFile.println("Second map:");
            statsToFile.println(secondModuloMap.writeMapStats());
            statsToFile.println("\n");

            firstModuloMap.eraPasses();
            secondModuloMap.eraPasses();
        }
    };

    public void startAnimation(){
        if (timer == null) {
            timer = new Timer(100, timerListener);
            timer.start();
            stopButton.setText("Stop");
            searchButton.setVisible(false);
            secondAnimalDetails.setVisible(false);
            firstAnimalDetails.setVisible(false);
            searchField1.setVisible(false);
            searchField2.setVisible(false);
        }
    }

    private void stopAnimation() {
        if (timer != null) {
            timer.stop();
            timer = null;
            stopButton.setText("Start");

            firstTextArea.setText(firstModuloMap.toString());
            frame.add(firstTextArea);
            secondTextArea.setText(secondModuloMap.toString());
            frame.add(secondTextArea);
            SwingUtilities.updateComponentTreeUI(frame);
            searchButton.setVisible(true);
            secondAnimalDetails.setText("Choose the animal by typing position (lower left corener is (0,0) )");
            firstAnimalDetails.setText("Format is: (x,y). For two please type both then press search button");
            secondAnimalDetails.setVisible(true);
            firstAnimalDetails.setVisible(true);
            searchField1.setVisible(true);
            searchField2.setVisible(true);
        }
    }

}
