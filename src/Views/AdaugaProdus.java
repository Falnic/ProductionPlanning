package Views;

import Models.Componenta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdaugaProdus {
    private JPanel panelAdaugaProdus;
    private JTextField idTextField;
    private JTextField numeTextField;
    private JButton adaugăProdusNouButton;
    private JTextField componenteDeAdaugattextField;
    private JTextArea textArea1;

    public AdaugaProdus() {
        adaugăProdusNouButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = Integer.parseInt(idTextField.getText());
                String nume = numeTextField.getText();

//                Main.creazaProdusNou()
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
