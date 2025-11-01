package controller;

import java.util.List;

import model.ModelException;
import model.SensorType;
import model.data.DAOFactory;
import model.data.SensorTypeDAO;
import view.swing.sensortype.ISensorTypeFormView;
import view.swing.sensortype.ISensorTypeListView;

public class SensorTypeController {
	final SensorTypeDAO sensorTypeDAO = DAOFactory.createSensorTypeDAO();
    private ISensorTypeListView sensorTypeListView;
    private ISensorTypeFormView sensorTypeFormView;
    
    // Listagem
    public void loadSensorTypes() {
        try {
            List<SensorType> sensorType = sensorTypeDAO.findAll();
            sensorTypeListView.setSensorTypeList(sensorType);
        } catch (ModelException e) {
        		sensorTypeListView.showMessage("Erro ao carregar usuários: " + e.getMessage());
        }
    }
    
    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
    		SensorType sensorType = sensorTypeFormView.getSensorTypeFromForm();

        try {
        		sensorType.validate();
        } catch (IllegalArgumentException e) {
        		sensorTypeFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
            		sensorTypeDAO.save(sensorType);
            } else {
            		sensorTypeDAO.update(sensorType);
            }
            		sensorTypeFormView.showInfoMessage("Modelo de sensor salvo com sucesso!");
            		sensorTypeFormView.close();
        } catch (ModelException e) {
        		sensorTypeFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }
    
    public void excluirSensorTypes(SensorType sensorType) {
        try {
        		sensorTypeDAO.delete(sensorType);
        		sensorTypeListView.showMessage("Modelo de sensor excluído!");
        		loadSensorTypes();
        } catch (ModelException e) {
        		sensorTypeListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }
    
    public void setSensorTypeFormView(ISensorTypeFormView sensorTypeFormView) {
        this.sensorTypeFormView = sensorTypeFormView;
    }

    public void setSensorTypeListView(ISensorTypeListView sensorTypeListView) {
        this.sensorTypeListView = sensorTypeListView;
    }
}
