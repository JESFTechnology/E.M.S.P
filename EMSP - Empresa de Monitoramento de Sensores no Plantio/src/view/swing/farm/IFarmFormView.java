package view.swing.farm;

import model.Farm;

public interface IFarmFormView {
	Farm getFarmFromForm();
    void setFarmInForm(Farm farm);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
