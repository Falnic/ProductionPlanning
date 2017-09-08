package Views;

import Business.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaugaProdus {
    private JPanel AdaugaProdusPanel;
    private JTextField idTextField;
    private JTextField numeTextField;
    private JButton adaugăProdusNouButton;
    private JScrollPane jScrollPaneAdaugaProdus;

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

    public JPanel getAdaugaProdusPanel() {
        return AdaugaProdusPanel;
    }

    public void setAdaugaProdusPanel(JPanel adaugaProdusPanel) {
        AdaugaProdusPanel = adaugaProdusPanel;
    }

    public JScrollPane getjScrollPaneAdaugaProdus() {
        return jScrollPaneAdaugaProdus;
    }

    public void setjScrollPaneAdaugaProdus(JScrollPane jScrollPaneAdaugaProdus) {
        this.jScrollPaneAdaugaProdus = jScrollPaneAdaugaProdus;
    }
}
