package view.swing.user;

import java.awt.*;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import model.Farm;
import model.User;
import model.UserGender;

class UserFormView extends JDialog implements IUserFormView {
    private static final long serialVersionUID = 1L;
	private final JTextField nameField = new JTextField(20);
    private final JComboBox<String> genderBox = new JComboBox<>(new String[]{"M", "F"});
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<Farm> farmComboBox = new JComboBox<>();
    private final JTextField cpfField = new JTextField(20);
    private final JTextField roleField = new JTextField(20);

    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private UserController controller;
    private final boolean isNew;
    private final UserListView parent;
    private User user;

    public UserFormView(UserListView parent, User user, UserController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setUserFormView(this);

        this.parent = parent;
        this.user = user;
        this.isNew = (user == null);

        setTitle(isNew ? "Novo Usuário" : "Editar Usuário");
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; add(nameField, gbc);

        // Sexo
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1; add(genderBox, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; add(emailField, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; add(passwordField, gbc);

        // CPF
        gbc.gridx = 0; gbc.gridy = 5; add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; add(cpfField, gbc);

        // Cargo
        gbc.gridx = 0; gbc.gridy = 6; add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; add(roleField, gbc);

        // Fazenda
       
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Fazenda:"), gbc);
        gbc.gridx = 1;
        add(farmComboBox, gbc);
        
        // Preenche ComboBox com as fazendas
        List<Farm> farm = controller.getAllFarms();
        DefaultComboBoxModel<Farm> comboModel = new DefaultComboBoxModel<>();
        for (Farm u : farm) {
            comboModel.addElement(u);
        }
        farmComboBox.setModel(comboModel);

        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setUserInForm(user);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public User getUserFromForm() {
        if (user == null) user = new User(0);

        user.setName(nameField.getText());
        user.setGender(genderBox.getSelectedItem().toString().equals("M") ? UserGender.M : UserGender.F);
        user.setEmail(emailField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        user.setCpf(cpfField.getText());
        user.setRole(roleField.getText());
        user.setFarm((Farm) farmComboBox.getSelectedItem());
        return user;
    }

    @Override
    public void setUserInForm(User user) {
        nameField.setText(user.getName());
        genderBox.setSelectedItem(user.getGender().toString());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        cpfField.setText(user.getCpf());
        roleField.setText(user.getRole());
        // Atualiza o combobox para refletir o usuário do post
        if (user.getFarm() != null) {
            boolean found = false;
            for (int i = 0; i < farmComboBox.getItemCount(); i++) {
                Farm u = farmComboBox.getItemAt(i);
                if (u.getId() == user.getFarm().getId()) {
                		farmComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            // Se não encontrar, adiciona o usuário ao combobox e seleciona
            if (!found) {
            		farmComboBox.addItem(user.getFarm());
            		farmComboBox.setSelectedItem(user.getFarm());
            }
        }
    }

    @Override
    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void close() {
        parent.refresh();
        dispose();
    }
}
