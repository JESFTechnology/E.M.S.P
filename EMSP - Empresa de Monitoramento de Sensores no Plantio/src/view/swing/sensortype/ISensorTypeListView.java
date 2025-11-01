package view.swing.sensortype;

import java.util.List;

import model.SensorType;

public interface ISensorTypeListView {
	void setSensorTypeList(List<SensorType> sensorType);
    void showMessage(String msg);
}
