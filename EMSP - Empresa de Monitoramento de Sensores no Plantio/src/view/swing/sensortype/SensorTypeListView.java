package view.swing.sensortype;

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

import controller.SensorTypeController;
import model.SensorType;

public class SensorTypeListView extends JDialog implements ISensorTypeListView {
    private static final long serialVersionUID = 1L;
	private SensorTypeController controller;
    private final SensorTypeTableModel tableModel = new SensorTypeTableModel();
    private final JTable table = new JTable(tableModel);

    public SensorTypeListView(JFrame parent) {
        super(parent, "Tipos de sensores", true);
        this.controller = new SensorTypeController();
        this.controller.setSensorTypeListView(this);

        setSize(900, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Tipo de Sensor");
        addButton.addActionListener(e -> {
        		SensorTypeFormView form = new SensorTypeFormView(this, null, controller);
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
            		SensorType sensorType = tableModel.getSensorTypeAt(row);
                SensorTypeFormView form = new SensorTypeFormView(this, sensorType, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
            	SensorType sensorType = tableModel.getSensorTypeAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir esse tipo de sensor?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirSensorTypes(sensorType);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadSensorTypes();
    }

    @Override
    public void setSensorTypeList(List<SensorType> sensorType) {
        tableModel.setSensorTypes(sensorType);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadSensorTypes();
    }

    static class SensorTypeTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
		private final String[] columns = {"ID", "Nome do sensor", "Unidade de medida"};
        private List<SensorType> sensorTypes = new ArrayList<>();

        public void setSensorTypes(List<SensorType> sensorType) {
            this.sensorTypes = sensorType;
            fireTableDataChanged();
        }

        public SensorType getSensorTypeAt(int row) {
            return sensorTypes.get(row);
        }

        @Override public int getRowCount() { return sensorTypes.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            SensorType u = sensorTypes.get(row);
            switch (col) {
                case 0: return u.getId();
                case 1: return u.getName();
                case 2: return u.getUnit();
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    }
}
