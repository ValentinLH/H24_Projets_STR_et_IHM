package fr.polytech.vgl.timerecord.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class settingsController {
	@FXML
	private Label myAdressLabel;
	
	@FXML
	private TextField IpAdressTextField;
	
	@FXML
	private TextField portTextField;
	
	@FXML
	private TextField myPortTextField;
	
	private TimeRecordControler controler;
	
	public void setTimeRecordController(TimeRecordControler controler) {
        this.controler = controler;
    }
	
	public void updateLabelIPAddress(String ipAddress) {
        myAdressLabel.setText("My IP Address: " + ipAddress);
    }
	
	public void updateTextFieldIp(String ipAdress) {
		IpAdressTextField.setText(ipAdress);
	}
	
	public void updateTextFieldPort(String port) {
		portTextField.setText(port);
	}
	
	public void updateTextFieldMyPort(String myPort) {
		myPortTextField.setText(myPort);
	}
	
	@FXML
	public void setIp() {
		String text = myAdressLabel.getText();		
		String ret = controler.setIp(text);
		
		if (text.equals(ret))
		{
			//Ip r�ussi � �tre modifi�
		}
		else
		{
			myAdressLabel.setText(ret);
			//Erreur de l'ip entr�
		}
	}
	
	@FXML
	public void setPort() {
		String port = portTextField.getText();
		String ret = controler.setPort(port);
		if (port.equals(ret))
		{
			//Port r�ussi � �tre modifi�
		}
		else
		{
			portTextField.setText(ret);
			//Erreur du port entr�
		}
	}
	
	@FXML
	public void setMyPort() {
		String port = myPortTextField.getText();
		String ret = controler.setMyPort(port);
		if (port.equals(ret))
		{
			//My port r�ussi � �tre modifi�
		}
		else
		{
			myPortTextField.setText(ret);
			//Erreur de my port entr�
		}
	}
	
	

}
