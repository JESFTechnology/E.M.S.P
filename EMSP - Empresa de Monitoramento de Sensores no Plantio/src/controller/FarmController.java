package controller;

import java.util.List;

import model.Farm;
import model.ModelException;
import model.data.DAOFactory;
import model.data.FarmDAO;
import view.swing.farm.IFarmFormView;
import view.swing.farm.IFarmListView;

public class FarmController {
    private final FarmDAO farmDAO = DAOFactory.createFarmDAO();
    private IFarmListView farmListView;
    private IFarmFormView farmFormView;
    
    // Listagem
    public void loadFarms() {
        try {
            List<Farm> farms = farmDAO.findAll();
            farmListView.setFarmList(farms);
        } catch (ModelException e) {
        		farmListView.showMessage("Erro ao carregar fazendas: " + e.getMessage());
        }
    }
    
    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
        Farm farm = farmFormView.getFarmFromForm();

        try {
        		farm.validate();
        } catch (IllegalArgumentException e) {
            farmFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                farmDAO.save(farm);
            } else {
            		farmDAO.update(farm);
            }
            farmFormView.showInfoMessage("Fazenda salva com sucesso!");
            farmFormView.close();
        } catch (ModelException e) {
            farmFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }
    
 // Excluir
    public void excluirFarm(Farm farm) {
        try {
            farmDAO.delete(farm);
            farmListView.showMessage("Fazenda excluído!");
            loadFarms();
        } catch (ModelException e) {
            farmListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }
    
    public void setFarmFormView(IFarmFormView farmFormView) {
        this.farmFormView = farmFormView;
    }

    public void setFarmListView(IFarmListView farmListView) {
        this.farmListView = farmListView;
    }
}
