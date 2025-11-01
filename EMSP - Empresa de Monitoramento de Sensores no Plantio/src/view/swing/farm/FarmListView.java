package view.swing.farm;

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

import controller.FarmController;
import model.Farm;

public class FarmListView extends JDialog implements IFarmListView {
    private static final long serialVersionUID = 1L;
	private FarmController controller;
    private final FarmTableModel tableModel = new FarmTableModel();
    private final JTable table = new JTable(tableModel);

    public FarmListView(JFrame parent) {
        super(parent, "Fazendas", true);
        this.controller = new FarmController();
        this.controller.setFarmListView(this);

        setSize(900, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Fazenda");
        addButton.addActionListener(e -> {
            FarmFormView form = new FarmFormView(this, null, controller);
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
                Farm farm = tableModel.getFarmAt(row);
                FarmFormView form = new FarmFormView(this, farm, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Farm farm = tableModel.getFarmAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirFarm(farm);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadFarms();
    }

    @Override
    public void setFarmList(List<Farm> farms) {
        tableModel.setFarms(farms);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadFarms();
    }

    static class FarmTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
		private final String[] columns = {"ID", "Nome", "CNPJ", "Localização"};
        private List<Farm> farms = new ArrayList<>();

        public void setFarms(List<Farm> farms) {
            this.farms = farms;
            fireTableDataChanged();
        }

        public Farm getFarmAt(int row) {
            return farms.get(row);
        }

        @Override public int getRowCount() { return farms.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Farm u = farms.get(row);
            switch (col) {
                case 0: return u.getId();
                case 1: return u.getName();
                case 2: return u.getCnpj();
                case 3: return u.getLocation();
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    }
}
