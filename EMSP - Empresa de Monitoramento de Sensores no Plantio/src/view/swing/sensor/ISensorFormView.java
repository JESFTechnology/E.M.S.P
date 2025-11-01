package view.swing.sensor;

import model.Sensors;

public interface ISensorFormView {
	Sensors getSensorFromForm();
    void setSensorInForm(Sensors sensor);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
