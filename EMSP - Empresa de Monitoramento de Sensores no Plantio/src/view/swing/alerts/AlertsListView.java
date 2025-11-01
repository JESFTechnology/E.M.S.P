package view.swing.alerts;

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

import controller.AlertsController;
import model.Alerts;

public class AlertsListView extends JDialog implements IAlertsListView {
    private static final long serialVersionUID = 1L;
	private AlertsController controller;
    private final AlertTableModel tableModel = new AlertTableModel();
    private final JTable table = new JTable(tableModel);

    public AlertsListView(JFrame parent) {
        super(parent, "Alertas", true);
        this.controller = new AlertsController();
        this.controller.setAlertsListView(this);

        setSize(900, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Alerta");
        addButton.addActionListener(e -> {
        		AlertsFormView form = new AlertsFormView(this, null, controller);
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
                Alerts alerts = tableModel.getAlertAt(row);
                AlertsFormView form = new AlertsFormView(this, alerts, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
            		Alerts alerts = tableModel.getAlertAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir Alerta?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirAlert(alerts);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        refresh();
    }

    @Override
    public void setAlertsList(List<Alerts> alerts) {
 
        tableModel.setAlerts(alerts);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadAlerts();
    }

    static class AlertTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
		private final String[] columns = {"ID","Sensor","Mensagem","Nível do alerta","Data e hora", "Status"};
        private List<Alerts> alerts = new ArrayList<>();

        public void setAlerts(List<Alerts> alerts) {
            this.alerts = alerts;
            fireTableDataChanged();
        }

        public Alerts getAlertAt(int row) {
            return alerts.get(row);
        }

        @Override public int getRowCount() { return alerts.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
        		Alerts u = alerts.get(row);
            switch (col) {
                case 0: return u.getId();
                case 1: return u.getSensor().getName();
                case 2: return u.getMessage();
                case 3: return u.getLevel();
                case 4: return u.getCreated_at_Formated();
                case 5: return u.isResolved() ? "Resolvido" : "Não resolvido";
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    }
}
