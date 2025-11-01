package view.swing.farm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.FarmController;
import model.Farm;

public class FarmFormView extends JDialog implements IFarmFormView {
	private final JTextField nameField = new JTextField(20);
    private final JTextField cnpjField = new JTextField(20);
    private final JTextField locationField = new JTextField(20);

    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private FarmController controller;
    private final boolean isNew;
    private final FarmListView parent;
    private Farm farm;
    
    public FarmFormView(FarmListView parent, Farm farm, FarmController controller) {
    		super(parent, true);
        this.controller = controller;
        this.controller.setFarmFormView(this);

        this.parent = parent;
        this.farm = farm;
        this.isNew = (farm == null);

        setTitle(isNew ? "Nova Fazenda" : "Editar Fazenda");
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
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("CNPJ:"), gbc);
        gbc.gridx = 1; add(cnpjField, gbc);

        // Local
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Localização:"), gbc);
        gbc.gridx = 1; add(locationField, gbc);
        
        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setFarmInForm(farm);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

	@Override
	public Farm getFarmFromForm() {
		if (farm == null) farm = new Farm(0);

		farm.setName(nameField.getText());
		farm.setCnpj(cnpjField.getText());
		farm.setLocation(locationField.getText());
        return farm;
	}

	@Override
	public void setFarmInForm(Farm farm) {
		// TODO Auto-generated method stub
		nameField.setText(farm.getName());
		cnpjField.setText(farm.getCnpj());
		locationField.setText(farm.getLocation());
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
