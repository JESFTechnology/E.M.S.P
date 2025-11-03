package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

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
import web.Notification;

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

        JPanel buttons_login = new JPanel();
        JButton loginBtn = new JButton("Entrar");
        JButton cancelBtn = new JButton("Cancelar");
        buttons_login.add(loginBtn);
        buttons_login.add(cancelBtn);

        JPanel buttons_actions = new JPanel();
        JButton registerBtn = new JButton("Cadastrar");
        JButton forgetMyPassword = new JButton("Esqueci minha senha");
        
        //buttons_actions.add(new JLabel("Outras opções"));
        buttons_actions.add(registerBtn);
        buttons_actions.add(forgetMyPassword);
        
        registerBtn.addActionListener(e ->{
			RegisterView registerView = new RegisterView();
			registerView.setVisible(true);
        });
        
        forgetMyPassword.addActionListener(e ->{
        		String email = JOptionPane.showInputDialog(null, "Por favor, insira seu email:", "Entrada de Email", JOptionPane.QUESTION_MESSAGE);
        
        		// Verifica se o usuário cancelou a primeira entrada
            if (email == null || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            try {
            		LoginResult loginResult = auth.verifyTheExistenceOfTheEmail(email);
            		if(loginResult.getName() != null && loginResult.getName() != ""){
            			Notification notification = new Notification();
            			int resultCode = notification.forgotMyPasswordEmail(email, loginResult.getName());
                		int code = -1;
                		try {
                			code = Integer.parseInt(JOptionPane.showInputDialog(null, "Agora, insira o código recebido:", "Entrada de Código", JOptionPane.QUESTION_MESSAGE));
                		}catch (Exception e2) {
						code = -1;
					}
                		if (code < 0) {
                        JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                		
                		if(code == resultCode) {
                			this.user = loginResult;
                			authenticated = true;
        	                dispose();
                		}else {
                			JOptionPane.showMessageDialog(null, "Código inválido!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                		}
                }else {
                	JOptionPane.showMessageDialog(null, "Email não encontrado na empresa", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
			} catch (ModelException e1) {
				e1.printStackTrace();
			}
        });

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(passwordField.getPassword());
            
            try {
            		this.user = auth.loginWithEmailAndPassword(email, senha);
				if(this.user.getName() != null && this.user.getName() != "") {
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
        add(buttons_login, BorderLayout.CENTER);
        add(buttons_actions, BorderLayout.SOUTH);
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

