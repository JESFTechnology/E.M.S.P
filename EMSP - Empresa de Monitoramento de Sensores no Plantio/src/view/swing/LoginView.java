package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.LoginResult;
import model.ModelException;
import model.data.mysql.MySQLAuth;

public class LoginView extends JDialog {
    private boolean authenticated = false;
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    
    private LoginResult user;

    public LoginView() {
    		MySQLAuth auth = new MySQLAuth();
        setTitle("EMSP Login");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../img/logoIf.png"));
        setIconImage(icon);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Entrar");
        JButton cancelBtn = new JButton("Cancelar");
        buttons.add(loginBtn);
        buttons.add(cancelBtn);

        JButton registerBtn = new JButton("Cadastrar");
        
        registerBtn.addActionListener(e ->{
			RegisterView registerView = new RegisterView();
			registerView.setVisible(true);
        });

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(passwordField.getPassword());
            
            try {
            		this.user = auth.loginWithEmailAndPassword(email, senha);
				if(this.user.getId() > -1) {
					authenticated = true;
	                dispose();
				} else {
				    JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ModelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
        });

        cancelBtn.addActionListener(e -> {
            authenticated = false;
            dispose();
        });

        add(form, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
        add(registerBtn, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
    
    public LoginResult getLoginResult() {
    		return this.user;
    }
}

