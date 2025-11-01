package view.swing;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.LoginResult;
import model.ModelException;
import model.data.mysql.MySQLAlertsDAO;
import view.swing.alerts.AlertsListView;
import view.swing.farm.FarmListView;
import view.swing.sensor.SensorListView;
import view.swing.sensortype.SensorTypeListView;
import view.swing.user.UserListView;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    private LoginResult user;
    
    public MainView(LoginResult user) throws ModelException {
    		this.user = user;
        setTitle("Sales CRUD - Swing"); // troquei para refletir vendas
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logoIf.png"));
        setIconImage(icon);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // Menu Usuários e Funcionários
        JMenu userAndFarmMenu = new JMenu("Usuários e Fazendas");
        JMenuItem userListItem = new JMenuItem("Listar Usuários");
        userListItem.addActionListener(e -> new UserListView(this).setVisible(true));
        userAndFarmMenu.add(userListItem);
        
        JMenuItem farmListItem = new JMenuItem("Listar Fazendas");
        farmListItem.addActionListener(e -> new FarmListView(this).setVisible(true));
        userAndFarmMenu.add(farmListItem);
        menuBar.add(userAndFarmMenu);

        // Menu Sensores e Tipos de Sensores
        JMenu sensorMenu = new JMenu("Sensores");
        JMenuItem sensorsListItem = new JMenuItem("Listar Sensores");
        sensorsListItem.addActionListener(e -> new SensorListView(this).setVisible(true));
        sensorMenu.add(sensorsListItem);
        
        JMenuItem sensorTypeListItem = new JMenuItem("Listar Tipos de Sensores");
        sensorTypeListItem.addActionListener(e -> new SensorTypeListView(this).setVisible(true));
        sensorMenu.add(sensorTypeListItem);
        menuBar.add(sensorMenu);
        
        // 	Alertas
        JMenu alertsMenu = new JMenu("Alertas");
        JMenuItem alertsListItem = new JMenuItem("Listar Alertas");
        alertsListItem.addActionListener(e -> new AlertsListView(this).setVisible(true));
        alertsMenu.add(alertsListItem);
        
        menuBar.add(alertsMenu);

        // Empurra próximo menu para direita
        menuBar.add(Box.createHorizontalGlue());
        
        // Menu Sair
        JMenu meuPerfil = new JMenu(user.getName()+" / "+user.getFarmName());
        JMenu exitMenu = new JMenu("Opções");
        JMenuItem exitItem = new JMenuItem("Fechar o sistema");
        exitItem.addActionListener(e -> System.exit(0));
        exitMenu.add(exitItem);
        menuBar.add(meuPerfil);
        menuBar.add(exitMenu);

        setJMenuBar(menuBar);

        // Label central
        MySQLAlertsDAO mySqlAlertsDAO = new MySQLAlertsDAO();
        JLabel label = new JLabel(mySqlAlertsDAO.alertOfDay(user.getFarmId()), SwingConstants.CENTER);
        label.setFont(getFont());
        // Painel principal com padding
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(32, 32, 32, 32));
        contentPanel.add(label, BorderLayout.CENTER);
        
        
        setContentPane(contentPanel);
    }

    public static void main(String[] args) {
    		// TODO Main principal
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);

            // Garantir autenticação antes de abrir main
            if (login.isAuthenticated()) {
                MainView mainView;
				try {
					mainView = new MainView(login.getLoginResult());
					mainView.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximizado
	                mainView.setVisible(true);
				} catch (ModelException e) {
					e.printStackTrace();
				}
            } else {
                System.exit(0);
            }
        });
    }
}
