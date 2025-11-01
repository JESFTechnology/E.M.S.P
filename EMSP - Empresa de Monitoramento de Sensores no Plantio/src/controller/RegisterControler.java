package controller;

import java.util.List;

import model.Farm;
import model.ModelException;
import model.User;
import model.data.DAOFactory;
import model.data.FarmDAO;
import model.data.UserDAO;
import view.swing.IRegisterFormView;

public class RegisterControler {
	private final UserDAO userDAO = DAOFactory.createUserDAO();
	private final FarmDAO farmDAO = DAOFactory.createFarmDAO();
	private IRegisterFormView registerFormView;
	
	public void saveOrUpdate(boolean isNew) {
		User user = null;
		try {
			user = registerFormView.getUserFromForm();
			try {
	            user.validate();
	        } catch (IllegalArgumentException e) {
	        		registerFormView.showErrorMessage("Erro de validação: " + e.getMessage());
	            return;
	        }

	        try {
	            if (isNew) {
	                userDAO.save(user);
	            } else {
	                userDAO.update(user);
	            }
	            registerFormView.showInfoMessage("Usuário salvo com sucesso!");
	            registerFormView.close();
	        } catch (ModelException e) {
	        		registerFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
	        }
		}catch (IllegalArgumentException e) {
			registerFormView.showErrorMessage("Erro de validação: " + e.getMessage());
		}
    }
	
	public List<Farm> getAllFarms(){
		List<Farm> farm = null;
		try {
			farm = farmDAO.findAll();
		}catch(ModelException e) {
			registerFormView.showErrorMessage("Erro ao chamar farms: " + e.getMessage());
		}
		return farm;
}
	
	public void setRegisterFormView(IRegisterFormView registerFormView) {
        this.registerFormView = registerFormView;
    }
}
