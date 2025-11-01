package view.swing.user;

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

import controller.UserController;
import model.User;

public class UserListView extends JDialog implements IUserListView {
    private static final long serialVersionUID = 1L;
	private UserController controller;
    private final UserTableModel tableModel = new UserTableModel();
    private final JTable table = new JTable(tableModel);

    public UserListView(JFrame parent) {
        super(parent, "Usuários", true);
        this.controller = new UserController();
        this.controller.setUserListView(this);

        setSize(900, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Usuário");
        addButton.addActionListener(e -> {
            UserFormView form = new UserFormView(this, null, controller);
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
                User user = tableModel.getUserAt(row);
                UserFormView form = new UserFormView(this, user, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                User user = tableModel.getUserAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirUsuario(user);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadUsers();
    }

    @Override
    public void setUserList(List<User> users) {
        tableModel.setUsers(users);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadUsers();
    }

    static class UserTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
		private final String[] columns = {"ID", "Nome", "Sexo", "Email", "Senha", "CPF","Cargo", "Fazenda"};
        private List<User> users = new ArrayList<>();

        public void setUsers(List<User> users) {
            this.users = users;
            fireTableDataChanged();
        }

        public User getUserAt(int row) {
            return users.get(row);
        }

        @Override public int getRowCount() { return users.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            User u = users.get(row);
            switch (col) {
                case 0: return u.getId();
                case 1: return u.getName();
                case 2: return u.getGender();
                case 3: return u.getEmail();
                case 4: return u.getPassword() != null ? "*****" : "";
                case 5: return u.getCpf();
                case 6: return u.getRole();
                case 7: return u.getFarm().getName();
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    }
}
