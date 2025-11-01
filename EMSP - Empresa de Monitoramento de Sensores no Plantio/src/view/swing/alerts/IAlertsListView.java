package view.swing.alerts;

import java.util.List;

import model.Alerts;

public interface IAlertsListView {
	void setAlertsList(List<Alerts> alerts);
    void showMessage(String msg);
}
