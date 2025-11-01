package controller;

import java.util.List;

import model.Farm;
import model.ModelException;
import model.SensorType;
import model.Sensors;
import model.data.DAOFactory;
import model.data.FarmDAO;
import model.data.SensorDAO;
import model.data.SensorTypeDAO;
import view.swing.sensor.ISensorFormView;
import view.swing.sensor.ISensorListView;

public class SensorController {
	final SensorDAO sensorDAO = DAOFactory.createSensorDAO();
	final FarmDAO farmDAO = DAOFactory.createFarmDAO();
	final SensorTypeDAO sensorTypeDAO = DAOFactory.createSensorTypeDAO();
    private ISensorListView sensorListView;
    private ISensorFormView sensorFormView;
    
    // Listagem
    public void loadSensors() {
        try {
            List<Sensors> sensor = sensorDAO.findAll();
            sensorListView.setSensorList(sensor);
        } catch (ModelException e) {
        		sensorListView.showMessage("Erro ao carregar sensores: " + e.getMessage());
        }
    }
    
    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
    		Sensors sensor = sensorFormView.getSensorFromForm();

        try {
        		sensor.validate();
        } catch (IllegalArgumentException e) {
        		sensorFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
            		sensorDAO.save(sensor);
            } else {
            		sensorDAO.update(sensor);
            }
            		sensorFormView.showInfoMessage("Modelo de sensor salvo com sucesso!");
            		sensorFormView.close();
        } catch (ModelException e) {
        		sensorFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }
    
    public void excluirSensors(Sensors sensor) {
        try {
        		sensorDAO.delete(sensor);
        		sensorListView.showMessage("Modelo de sensor excluído!");
        		loadSensors();
        } catch (ModelException e) {
        		sensorListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }
    
    public List<Farm> getAllFarms(){
		List<Farm> farm = null;
		try {
			farm = farmDAO.findAll();
		}catch(ModelException e) {
			sensorListView.showMessage("Erro ao chamar farms: " + e.getMessage());
		}
		return farm;
    }
    
    public List<SensorType> getAllSensorTypes(){
		List<SensorType> sensorType = null;
		try {
			sensorType = sensorTypeDAO.findAll();
		}catch(ModelException e) {
			sensorListView.showMessage("Erro ao chamar farms: " + e.getMessage());
		}
		return sensorType;
}
    
    public void setSensorFormView(ISensorFormView sensorFormView) {
        this.sensorFormView = sensorFormView;
    }

    public void setSensorListView(ISensorListView sensorListView) {
        this.sensorListView = sensorListView;
    }
}
