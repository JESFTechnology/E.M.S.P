package view.swing.alerts;

import model.Alerts;

public interface IAlertsFormView {
	Alerts getAlertsFromForm();
    void setAlertsInForm(Alerts alerts);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
