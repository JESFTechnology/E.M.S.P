package view.swing.farm;

import java.util.List;

import model.Farm;

public interface IFarmListView {
	void setFarmList(List<Farm> farms);
    void showMessage(String msg);
}
