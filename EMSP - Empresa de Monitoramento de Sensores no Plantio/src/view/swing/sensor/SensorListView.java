package view.swing.sensor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.SensorController;
import model.Sensors;

public class SensorListView extends JDialog implements ISensorListView {
    private static final long serialVersionUID = 1L;
	private SensorController controller;
    private final SensorTableModel tableModel = new SensorTableModel();
    private final JTable table = new JTable(tableModel);

    public SensorListView(JFrame parent) {
        super(parent, "Sensores", true);
        this.controller = new SensorController();
        this.controller.setSensorListView(this);

        setSize(900, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Tipo de Sensor");
        addButton.addActionListener(e -> {
        		SensorFormView form = new SensorFormView(this, null, controller);
            form.setVisible(true);
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { showPopup(e); }
            @Override
            public void mouseReleased(MouseEvent e) { showPopup(e); }
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
            		Sensors sensor = tableModel.getSensorAt(row);
                SensorFormView form = new SensorFormView(this, sensor, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
            	Sensors sensorType = tableModel.getSensorAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir esse tipo de sensor?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirSensors(sensorType);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadSensors();
    }

    @Override
    public void setSensorList(List<Sensors> sensor) {
        tableModel.setSensor(sensor);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadSensors();
    }

    static class SensorTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
		private final String[] columns = {"ID", "Nome do sensor", "Tipo de sensor", "Ultimo valor",  "Ultima atualização", "Modelo do sensor", "Nível de bateria", "Status", "Data de instalação", "Fazenda"};
        private List<Sensors> sensor = new ArrayList<>();

        public void setSensor(List<Sensors> sensor) {
            this.sensor = sensor;
            fireTableDataChanged();
        }

        public Sensors getSensorAt(int row) {
            return sensor.get(row);
        }

        @Override public int getRowCount() { return sensor.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
        		Sensors u = sensor.get(row);
            switch (col) {
                case 0: return u.getId();
                case 1: return u.getName();
                case 2: return u.getSensorType().toString();
                case 3: return u.getLast_value();
                case 4: return u.getLast_updateFormated();
                case 5: return u.getDevice_model();
                case 6: return u.getBattery_level();
                case 7: return u.getStatus();
                case 8: return u.getInstalled_atFormated();
                case 9: return u.getFarm().toString();
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    }
}
