package view.swing.sensortype;

import model.SensorType;

public interface ISensorTypeFormView {
	SensorType getSensorTypeFromForm();
    void setSensorTypeInForm(SensorType sensorType);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
