package view.swing.sensor;

import java.util.List;

import model.Sensors;

public interface ISensorListView {
	void setSensorList(List<Sensors> sensors);
    void showMessage(String msg);
}
