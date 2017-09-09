package Views;

import Business.Main;
import Models.Componenta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaugaProdus {
    JFrame jFrame;
    JLabel headerJLabel;
    JPanel[][] panelHolder;

    JLabel idJLabel;
    JLabel numeJLabel;
    JLabel listaComponenteJLabel;
    JList jList;

    private JPanel panelAdaugaProdus;
    private JTextField idTextField;
    private JTextField numeTextField;
    private JButton adaugaProdusNouButton;

    private void pregareGUI(){
        jFrame = new JFrame("Tehnici de planificare a productiei");
        jFrame.setSize(800,800);

        GridLayout layout = new GridLayout(5,2);

        jFrame.setLayout(layout);
    }

    private void initPanelHolder(){
        panelHolder = new JPanel[5][2];

        for(int m = 0; m < 5; m++) {
            for(int n = 0; n < 2; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setSize(20, 20);
                jFrame.add(panelHolder[m][n]);
            }
        }
    }

    private void configureazaStartView(){
        headerJLabel = new JLabel("Adauga Produs");
        panelHolder[0][0].add(headerJLabel);

        idJLabel = new JLabel("Id =");
        panelHolder[1][0].add(idJLabel);

        idTextField = new JTextField();
        idTextField.setColumns(10);
        panelHolder[1][1].add(idTextField);

        numeJLabel = new JLabel("Nume = ");
        panelHolder[2][0].add(numeJLabel);

        numeTextField = new JTextField();
        numeTextField.setColumns(10);
        panelHolder[2][1].add(numeTextField);

        listaComponenteJLabel = new JLabel("Componente =");
        panelHolder[3][0].add(listaComponenteJLabel);


        Main main = new Main();
        String[] listaStringuri = main.getNumeComponente().toArray(new String[0]);
        jList = new JList(listaStringuri);

        jList.setSelectedIndex(0);
        jList.setVisibleRowCount(3);
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(jList);
        panelHolder[3][1].add(listScrollPane);

        adaugaProdusNouButton = new JButton("Adauga produs");
        panelHolder[4][1].add(adaugaProdusNouButton);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public AdaugaProdus() {
        pregareGUI();
        initPanelHolder();
        configureazaStartView();

        adaugaProdusNouButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean rezultat = false;
                try {
                    Main main = new Main();

                    Integer id = Integer.parseInt(idTextField.getText());
                    String nume = numeTextField.getText();
                    int[] indiciComponente = jList.getSelectedIndices();
                    rezultat = main.creazaProdusNou(id, nume, indiciComponente);
                } catch (NumberFormatException e1){
                    e1.printStackTrace();
                }

                if (rezultat == true){
                    JOptionPane.showMessageDialog( null, "Produsul a fost creat cu succes"," Produs adaugat cu succes" , JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog( null, "Eroare, Produsul nu a fost creat"," Eroare" , JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }

    public JPanel getPanelAdaugaProdus() {
        return panelAdaugaProdus;
    }

    public void setPanelAdaugaProdus(JPanel panelAdaugaProdus) {
        this.panelAdaugaProdus = panelAdaugaProdus;
    }
}
