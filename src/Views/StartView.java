package Views;

import Business.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartView {
    JFrame jFrame;
    JLabel headerJLabel;
    JPanel[][] panelHolder;

    JPanel jPanel;
    JTextArea textArea;

    private JButton adaugaProdusButton;
    private JButton adaugaComponentaButton;
    private JButton adaugaMasinarieButton;
    private JButton planificaFolosindPSOButton;
    private JButton planificaFolosindBacktrackingButton;
    private JButton genereazaProduseAleatoareButton;
    private JTextField genereazaProduseTextField;
    private JList jList;

    private void pregareGUI(){
        jFrame = new JFrame("Tehnici de planificare a productiei");
        jFrame.setSize(800,800);

        GridLayout layout = new GridLayout(6,3);

        jFrame.setLayout(layout);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initPanelHolder(){
        panelHolder = new JPanel[6][3];

        for(int m = 0; m < 6; m++) {
            for(int n = 0; n < 3; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setSize(20, 20);
                jFrame.add(panelHolder[m][n]);
            }
        }
    }

    private void configureazaStartView(){

        headerJLabel = new JLabel("Tehnici de Planificare a Productiei");
        panelHolder[0][1].add(headerJLabel);

        adaugaProdusButton = new JButton("Adauga Produs");
        adaugaProdusButton.setSize(20, 20);
        panelHolder[1][0].add(adaugaProdusButton);

        adaugaComponentaButton = new JButton("Adauga Componenta");
        panelHolder[1][1].add(adaugaComponentaButton);

        adaugaMasinarieButton = new JButton("Adauga Masinarie");
        panelHolder[1][2].add(adaugaMasinarieButton);


        textArea = new JTextArea();
        textArea.setColumns(20);
        textArea.setRows(5);

        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        panelHolder[2][1].add(textAreaScrollPane);

        planificaFolosindPSOButton = new JButton("Planifica PSO");
        panelHolder[3][0].add(planificaFolosindPSOButton);

        planificaFolosindBacktrackingButton = new JButton("Planifica Backtracking");
        panelHolder[3][2].add(planificaFolosindBacktrackingButton);

        genereazaProduseAleatoareButton = new JButton("Genereaza Produse");
        panelHolder[4][1].add(genereazaProduseAleatoareButton);

        genereazaProduseTextField = new JTextField();
        genereazaProduseTextField.setColumns(13);
        panelHolder[5][1].add(genereazaProduseTextField);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public StartView() {

        pregareGUI();
        initPanelHolder();
        configureazaStartView();

        jFrame.pack();
        jFrame.setVisible(true);

        adaugaProdusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdaugaProdus();
            }
        });


        adaugaComponentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdaugaComponenta();
            }
        });
        adaugaMasinarieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdaugaMasinarie();
            }
        });
        genereazaProduseAleatoareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numarProduse = Integer.parseInt(genereazaProduseTextField.getText());
                    Main.genereazaNumarMareProduse(numarProduse);
                    textArea.setText(Main.getNumeProduse());
                } catch (NumberFormatException e1){
                    e1.printStackTrace();
                    textArea.setText("Eroare");
                }
            }
        });
        planificaFolosindPSOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(Main.getCeaMaiBunaSolutie());
            }
        });
        planificaFolosindBacktrackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(Main.getCeaMaiBunaPermutare());
            }
        });
    }

    public static void main(String[] args){
        Main.genereazaDateInitiale();

        new StartView();

    }
}
