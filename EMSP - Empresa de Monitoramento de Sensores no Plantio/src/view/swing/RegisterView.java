package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
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

import controller.RegisterControler;
import model.Farm;
import model.User;
import model.UserGender;

public class RegisterView extends JDialog implements IRegisterFormView{
	private static final long serialVersionUID = 1L;
	private final JTextField nameField = new JTextField(20);
	private final JComboBox<String> genderBox = new JComboBox<>(new String[]{"M", "F"});
    private final JTextField cpfField = new JTextField(20);
    private final JTextField roleField = new JTextField(20);
    private final JComboBox<Farm> farmComboBox = new JComboBox<>();
	private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField passwordNewField = new JPasswordField(20);
	private User user;
	private RegisterControler controller;
	
	public RegisterView() {
		this.controller = new RegisterControler();
		this.controller.setRegisterFormView(this);
		setTitle("EMSP Cadastro");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../img/logoIf.png"));
        setIconImage(icon);
        
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.CENTER;
        form.add(new JLabel("Cadastre-se aqui!"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Gênero:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(genderBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(cpfField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(roleField, gbc);
        
        // Fazenda
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Fazenda:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(farmComboBox, gbc);
        
        // Preenche ComboBox com as fazendas
        List<Farm> farm = controller.getAllFarms();
        DefaultComboBoxModel<Farm> comboModel = new DefaultComboBoxModel<>();
        for (Farm u : farm) {
            comboModel.addElement(u);
        }
        farmComboBox.setModel(comboModel);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Login no sistema"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordNewField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 9; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Repita a Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);   
        
        JPanel buttons = new JPanel();
        JButton saveButton = new JButton("Cadastrar");
        JButton closeButton = new JButton("Cancelar");
        buttons.add(saveButton);
        buttons.add(closeButton);
        
        add(form, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        
        saveButton.addActionListener(e -> controller.saveOrUpdate(true));
        closeButton.addActionListener(e -> close());
	};
	
	// TODO Main principal
	public User getUserFromForm() {
		if (!new String(passwordNewField.getPassword()).equals(new String(passwordField.getPassword())))
			throw new IllegalArgumentException("As senhas não coincidem. Por favor, verifique e tente novamente.");
		
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
	
	public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void close() {
        dispose();
    }
}
