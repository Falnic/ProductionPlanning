package Views;

import Business.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaugaComponenta {
    JFrame jFrame;
    JLabel headerJLabel;
    JPanel[][] panelHolder;

    JLabel idJLabel;
    JLabel numeJLabel;
    JLabel timpMontareJLabel;

    private JTextField idTextField;
    private JTextField numeTextField;
    private JTextField timpMontareTextField;
    private JButton adaugaComponentaNouaButton;

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
        headerJLabel = new JLabel("Adauga Componenta");
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

        timpMontareJLabel = new JLabel("Timp Montare =");
        panelHolder[3][0].add(timpMontareJLabel);

        timpMontareTextField = new JTextField();
        timpMontareTextField.setColumns(10);
        panelHolder[3][1].add(timpMontareTextField);

        adaugaComponentaNouaButton = new JButton("Adauga Componenta");
        panelHolder[4][1].add(adaugaComponentaNouaButton);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public AdaugaComponenta() {
        pregareGUI();
        initPanelHolder();
        configureazaStartView();

        adaugaComponentaNouaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean rezultat = false;
                try {
                    Main main = new Main();

                    Integer id = Integer.parseInt(idTextField.getText());
                    String nume = numeTextField.getText();
                    Integer timpMontare = Integer.parseInt(timpMontareTextField.getText());
                    main.creazaComponentaNoua(id, nume, timpMontare);
                } catch (NumberFormatException e1){
                    e1.printStackTrace();
                }

                if (rezultat == true){
                    JOptionPane.showMessageDialog( null, "Produsul a fost creat cu succes"," Produs adaugat cu succes" , JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog( null, "Eroare, Componenta nu a fost creata"," Eroare" , JOptionPane.PLAIN_MESSAGE);
                }

            }
        });
    }
}
