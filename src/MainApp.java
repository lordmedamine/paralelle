import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp {
    public static void main(String[] args) {
        ParcelManagementSystem system = new ParcelManagementSystem();

        JFrame frame = new JFrame("Système de Livraison de Colis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());


        JTextArea parcelList = new JTextArea();
        parcelList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(parcelList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JTextField destinationField = new JTextField(15);
        JButton registerButton = new JButton("Enregistrer Colis");
        inputPanel.add(new JLabel("Destination: "));
        inputPanel.add(destinationField);
        inputPanel.add(registerButton);
        frame.add(inputPanel, BorderLayout.NORTH);

        Timer timer = new Timer(1000, e -> {
            StringBuilder display = new StringBuilder();
            for (Parcel parcel : system.getParcels()) {
                display.append("ID: ").append(parcel.getId())
                        .append(", Destination: ").append(parcel.getDestination())
                        .append(", État: ").append(parcel.getStatus()).append("\n");
            }
            parcelList.setText(display.toString());
        });
        timer.start();

        registerButton.addActionListener(new ActionListener() {
            private int parcelId = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                String destination = destinationField.getText();
                if (!destination.isEmpty()) {
                    Parcel parcel = new Parcel(parcelId++, destination);
                    new ParcelDeliveryThread(system, parcel).start();
                    destinationField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer une destination !");
                }
            }
        });

        frame.setVisible(true);
    }
}