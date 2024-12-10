import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainApp {
    public static void main(String[] args) {
        ParcelManagementSystem system = new ParcelManagementSystem();

        JFrame frame = new JFrame("Système de Livraison de Colis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Destination", "État"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable parcelTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(parcelTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextArea destinationArea = new JTextArea(3, 15); // TextArea for multiple destinations
        JButton registerButton = new JButton("Enregistrer Colis");
        inputPanel.add(new JLabel("Destinations (séparées par des virgules): "));
        inputPanel.add(new JScrollPane(destinationArea)); // JScrollPane for the TextArea
        inputPanel.add(registerButton);
        frame.add(inputPanel, BorderLayout.NORTH);

        Timer timer = new Timer(1000, e -> {
            tableModel.setRowCount(0);
            for (Parcel parcel : system.getParcels()) {
                tableModel.addRow(new Object[]{parcel.getId(), parcel.getDestination(), parcel.getStatus()});
            }
        });
        timer.start();

        // Register Button Action
        registerButton.addActionListener(new ActionListener() {
            private int parcelId = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                String destinationsText = destinationArea.getText();
                String[] destinations = destinationsText.split(",");

                if (destinations.length > 0) {
                    for (String destination : destinations) {
                        String trimmedDestination = destination.trim();
                        if (!trimmedDestination.isEmpty()) {
                            Parcel parcel = new Parcel(parcelId++, trimmedDestination);
                            new ParcelDeliveryThread(system, parcel).start();
                        }
                    }
                    destinationArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer des destinations !");
                }
            }
        });


        frame.setVisible(true);
    }
}