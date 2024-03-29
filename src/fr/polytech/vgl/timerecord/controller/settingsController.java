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
			//Ip réussi à être modifié
		}
		else
		{
			myAdressLabel.setText(ret);
			//Erreur de l'ip entré
		}
	}
	
	@FXML
	public void setPort() {
		String port = portTextField.getText();
		String ret = controler.setPort(port);
		if (port.equals(ret))
		{
			//Port réussi à être modifié
		}
		else
		{
			portTextField.setText(ret);
			//Erreur du port entré
		}
	}
	
	@FXML
	public void setMyPort() {
		String port = myPortTextField.getText();
		String ret = controler.setMyPort(port);
		if (port.equals(ret))
		{
			//My port réussi à être modifié
		}
		else
		{
			myPortTextField.setText(ret);
			//Erreur de my port entré
		}
	}
	
	

}
