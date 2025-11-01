package controller;

import java.util.List;

import model.Alerts;
import model.ModelException;
import model.Sensors;
import model.data.AlertsDAO;
import model.data.DAOFactory;
import model.data.SensorDAO;
import view.swing.alerts.IAlertsFormView;
import view.swing.alerts.IAlertsListView;

public class AlertsController {
	private final AlertsDAO alertsDAO = DAOFactory.createAlertsDAO();
	private final SensorDAO sensorsDAO = DAOFactory.createSensorDAO();
    private IAlertsListView alertsListView;
    private IAlertsFormView alertsFormView;
    
    // Listagem
    public void loadAlerts() {
        try {
            List<Alerts> alerts = alertsDAO.findAll();
            alertsListView.setAlertsList(alerts);
        } catch (ModelException e) {
        		alertsListView.showMessage("Erro ao carregar alertas: " + e.getMessage());
        }
    }
    
    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
        Alerts alerts = alertsFormView.getAlertsFromForm();

        try {
        		alerts.validate();
        } catch (IllegalArgumentException e) {
        		alertsFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
            		alertsDAO.save(alerts);
            } else {
            		alertsDAO.update(alerts);
            }
            alertsFormView.showInfoMessage("Alerta salvo com sucesso!");
            alertsFormView.close();
        } catch (ModelException e) {
        		alertsFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }
    
 // Excluir
    public void excluirAlert(Alerts alerts) {
        try {
        		alertsDAO.delete(alerts);
        		alertsListView.showMessage("Alerta excluído!");
            loadAlerts();
        } catch (ModelException e) {
        		alertsListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }
    
    public List<Sensors> getAllSensors(){
		List<Sensors> sensors = null;
		try {
			sensors = sensorsDAO.findAll();
		}catch(ModelException e) {
			alertsListView.showMessage("Erro ao chamar sensores: " + e.getMessage());
		}
		return sensors;
}
    
    public void setAlertsFormView(IAlertsFormView alertsFormView) {
        this.alertsFormView = alertsFormView;
    }

    public void setAlertsListView(IAlertsListView alertsListView) {
        this.alertsListView = alertsListView;
    }
}
