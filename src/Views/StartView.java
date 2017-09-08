package Views;

import Business.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class StartView {
    JPanel StartViewPanel;
    JFrame jFrame;
    private JButton AdaugaProdus;
    private JButton adaugaComponentaButton;
    private JButton adaugaMasinarieButton;
    private JList list1;
    private JButton planificaFolosindPSOButton;
    private JButton planificaFolosindBacktrackingButton;
    private JButton genereazaProduseAleatoareButton;
    private JTextField textField1;

    public StartView() {

        AdaugaProdus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] listaComponente = Main.getNumeComponente().toArray(new String[0]);
                JList jList = new JList(listaComponente);
                jList.setVisibleRowCount(5);
                jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                JScrollPane jScrollPane = new JScrollPane(jList);

                JFrame jFrame = new JFrame("Tehnici de planificare a productiei");
//                JPanel adaugaProdusPanel = new AdaugaProdus().AdaugaProdusPanel;
//                adaugaProdusPanel.add(jScrollPane);

                AdaugaProdus adaugaProdus = new AdaugaProdus();
                adaugaProdus.setjScrollPaneAdaugaProdus(jScrollPane);
                JPanel jPanel = adaugaProdus.getAdaugaProdusPanel();
                jFrame.setContentPane(jPanel);
                jFrame.pack();
                jFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args){
        Main.genereazaDateInitiale();

        JFrame jFrame = new JFrame("Tehnici de planificare a productiei");
        jFrame.setContentPane(new StartView().StartViewPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
