package view.swing.sensortype;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.SensorTypeController;
import model.SensorType;

public class SensorTypeFormView extends JDialog implements ISensorTypeFormView {
	private static final long serialVersionUID = 1L;
	private final JTextField nameField = new JTextField(20);
    private final JTextField unitField = new JTextField(20);

    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private SensorTypeController controller;
    private final boolean isNew;
    private final SensorTypeListView parent;
    private SensorType sensorType;
    
    public SensorTypeFormView(SensorTypeListView parent, SensorType sensorType, SensorTypeController controller) {
    		super(parent, true);
        this.controller = controller;
        this.controller.setSensorTypeFormView(this);

        this.parent = parent;
        this.sensorType = sensorType;
        this.isNew = (sensorType == null);

        setTitle(isNew ? "Novo Tipo de sensor" : "Editar Tipo de Sensor");
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; add(nameField, gbc);

        // CNPJ
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Unidade de medida:"), gbc);
        gbc.gridx = 1; add(unitField, gbc);
        
        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setSensorTypeInForm(sensorType);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

	@Override
	public SensorType getSensorTypeFromForm() {
		if (sensorType == null) sensorType = new SensorType(0);

		sensorType.setName(nameField.getText());
		sensorType.setUnit(unitField.getText());
        return sensorType;
	}

	@Override
	public void setSensorTypeInForm(SensorType sensorType) {
		// TODO Auto-generated method stub
		nameField.setText(sensorType.getName());
		unitField.setText(sensorType.getUnit());
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
