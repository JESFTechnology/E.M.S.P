package view.swing;

import model.User;

public interface IRegisterFormView {
	User getUserFromForm();
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
