package view.swing.alerts;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AlertsController;
import model.Alerts;
import model.Sensors;

public class AlertsFormView extends JDialog implements IAlertsFormView {
	private static final long serialVersionUID = 1L;
	private final JTextField messageField = new JTextField(20);
	private final JComboBox<Sensors> sensorBox = new JComboBox<>();
    private final JComboBox<String> levelBox = new JComboBox<>(new String[]{"Baixo", "Médio", "Alto", "Evacuação"});
    private final JComboBox<String> resolvedBox = new JComboBox<>(new String[]{"Sim", "Não"});

    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private AlertsController controller;
    private final boolean isNew;
    private final AlertsListView parent;
    private Alerts alerts;
    
    @SuppressWarnings("deprecation")
	public AlertsFormView(AlertsListView parent, Alerts alerts, AlertsController controller) {
    		super(parent, true);
        this.controller = controller;
        this.controller.setAlertsFormView(this);

        this.parent = parent;
        this.alerts = alerts;
        this.isNew = (alerts == null);

        setTitle(isNew ? "Novo Alerta" : "Editar Alerta");
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        if(isNew) sensorBox.enable(); else sensorBox.disable();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fazenda
        
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Sensor:"), gbc);
        gbc.gridx = 1;
        add(sensorBox, gbc);
        
        // Preenche ComboBox com as fazendas
        List<Sensors> sensors = controller.getAllSensors();
        DefaultComboBoxModel<Sensors> sensorsModel = new DefaultComboBoxModel<>();
        for (Sensors u : sensors) {
        		sensorsModel.addElement(u);
        }
        sensorBox.setModel(sensorsModel);
        
        // Mensagem    
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Mensagem:"), gbc);
        gbc.gridx = 1; add(messageField, gbc);

        // Nível
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Nível:"), gbc);
        gbc.gridx = 1; add(levelBox, gbc);

        // Resolvido
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Resolvido?"), gbc);
        gbc.gridx = 1; add(resolvedBox, gbc);
        
        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setAlertsInForm(alerts);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

	@Override
	public Alerts getAlertsFromForm() {
		if (alerts == null) alerts = new Alerts(0);

		alerts.setMessage(messageField.getText());
		alerts.setLevel(levelBox.getSelectedItem().toString());
		alerts.setResolved(resolvedBox.getSelectedItem().toString() == "Sim");
		alerts.setSensor((Sensors) sensorBox.getSelectedItem());
        return alerts;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setAlertsInForm(Alerts alerts) {
		// TODO Auto-generated method stub
		messageField.setText(alerts.getMessage());
		for(int i = 0; i < levelBox.getItemCount(); i++) {
			if(alerts.getLevel().equals(levelBox.getItemAt(i))) {
				levelBox.setSelectedIndex(i);
				break;
			}
		}
		if(alerts.isResolved()) resolvedBox.setSelectedIndex(0); else resolvedBox.setSelectedIndex(1);
		if (alerts.getSensor() != null) {
            boolean found = false;
            for (int i = 0; i < sensorBox.getItemCount(); i++) {
            		Sensors u = sensorBox.getItemAt(i);
                if (u.getId() == alerts.getSensor().getId()) {
                		sensorBox.setSelectedIndex(i);
                		sensorBox.disable();
                    found = true;
                    break;
                }
            }
            // Se não encontrar, adiciona o usuário ao combobox e seleciona
            if (!found) {
            		sensorBox.addItem(alerts.getSensor());
            		sensorBox.setSelectedItem(alerts.getSensor());
            }
        }
	}

	@Override
	public void showInfoMessage(String msg) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void showErrorMessage(String msg) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		parent.refresh();
        dispose();
	}
}
