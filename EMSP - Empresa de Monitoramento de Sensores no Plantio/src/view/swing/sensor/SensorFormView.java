package view.swing.sensor;

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

import controller.SensorController;
import model.Farm;
import model.SensorType;
import model.Sensors;

public class SensorFormView extends JDialog implements ISensorFormView {
	private static final long serialVersionUID = 1L;
	private final JTextField nameField = new JTextField(20);
	private final JComboBox<SensorType> sensorTypeComboBox = new JComboBox<>();
    private final JTextField device_modelField = new JTextField(20);
    private final JComboBox<String> statusBox = new JComboBox<>(new String[]{"Ativo", "Desativado", "Em manutenção", "Desatualizado"});
    private final JComboBox<Farm> farmComboBox = new JComboBox<>();
    
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private SensorController controller;
    private final boolean isNew;
    private final SensorListView parent;
    private Sensors sensor;
    
    public SensorFormView(SensorListView parent, Sensors sensor, SensorController controller) {
    		super(parent, true);
        this.controller = controller;
        this.controller.setSensorFormView(this);

        this.parent = parent;
        this.sensor = sensor;
        this.isNew = (sensor == null);

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

        // Modelo do sensor
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Modelo do sensor:"), gbc);
        gbc.gridx = 1; add(device_modelField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Status do sensor:"), gbc);
        gbc.gridx = 1; add(statusBox, gbc);
        
        // Tipo de sensor
        gbc.gridx = 0; gbc.gridy = 3;add(new JLabel("Tipos de sensores:"), gbc);
        gbc.gridx = 1; add(sensorTypeComboBox, gbc);
        
        // Preenche ComboBox com os tipos de sensores
        List<SensorType> sensorType = controller.getAllSensorTypes();
        DefaultComboBoxModel<SensorType> comboModelSensorType = new DefaultComboBoxModel<>();
        for (SensorType u : sensorType) {
            comboModelSensorType.addElement(u);
        }
        sensorTypeComboBox.setModel(comboModelSensorType);
        
        // Fazenda
        
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Fazenda:"), gbc);
        gbc.gridx = 1; add(farmComboBox, gbc);
        
        // Preenche ComboBox com as fazendas
        List<Farm> farm = controller.getAllFarms();
        DefaultComboBoxModel<Farm> comboModelFarm = new DefaultComboBoxModel<>();
        for (Farm u : farm) {
        		comboModelFarm.addElement(u);
        }
        farmComboBox.setModel(comboModelFarm);
        
        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setSensorInForm(sensor);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

	@Override
	public Sensors getSensorFromForm() {
		if (sensor == null) sensor = new Sensors(0);

		sensor.setName(nameField.getText());
		sensor.setDevice_model(device_modelField.getText());
		sensor.setStatus(statusBox.getSelectedItem().toString());
		sensor.setSensorType((SensorType) sensorTypeComboBox.getSelectedItem());
		sensor.setFarm((Farm) farmComboBox.getSelectedItem());
        return sensor;
	}

	@Override
	public void setSensorInForm(Sensors sensor) {
		// TODO Auto-generated method stub
		nameField.setText(sensor.getName());
		device_modelField.setText(sensor.getDevice_model());
		// Atualiza o combobox para refletir o status do sensor
        if (sensor.getStatus() != null) {
            boolean found = false;
            for (int i = 0; i < statusBox.getItemCount(); i++) {
                if (statusBox.getItemAt(i) == sensor.getStatus()) {
                	statusBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            // Se não encontrar, adiciona o usuário ao combobox e seleciona
            if (!found) {
            		statusBox.addItem(sensor.getStatus());
            		statusBox.setSelectedItem(sensor.getStatus());
            }
        }
        // Atualiza o combobox para refletir o tipo de sensor do sensor
        if (sensor.getSensorType() != null) {
            boolean found = false;
            for (int i = 0; i < sensorTypeComboBox.getItemCount(); i++) {
            		SensorType u = sensorTypeComboBox.getItemAt(i);
                if (u.getId() == sensor.getSensorType().getId()) {
                		sensorTypeComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            // Se não encontrar, adiciona o usuário ao combobox e seleciona
            if (!found) {
            		sensorTypeComboBox.addItem(sensor.getSensorType());
            		sensorTypeComboBox.setSelectedItem(sensor.getSensorType());
            }
        }
		// Atualiza o combobox para refletir a fazenda do sensor
        if (sensor.getFarm() != null) {
            boolean found = false;
            for (int i = 0; i < farmComboBox.getItemCount(); i++) {
                Farm u = farmComboBox.getItemAt(i);
                if (u.getId() == sensor.getFarm().getId()) {
                		farmComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            // Se não encontrar, adiciona o usuário ao combobox e seleciona
            if (!found) {
            		farmComboBox.addItem(sensor.getFarm());
            		farmComboBox.setSelectedItem(sensor.getFarm());
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
