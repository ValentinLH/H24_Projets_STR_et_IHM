package fr.polytech.vgl.timerecord.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class settingsController {
	@FXML
	private Label myAdressLabel;
	
	@FXML
	private Label responseLabel;
	
	@FXML
	private TextField IpAdressTextField;
	
	@FXML
	private TextField portTextField;
	
	@FXML
	private TextField myPortTextField;
	
	@FXML
	private Pane paneSetting;
	
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
			this.responseLabel.setText("New Ip selected");
			this.responseLabel.setVisible(true);
			this.changeBackground("7EA1C9");
		}
		else
		{
			myAdressLabel.setText(ret);
			this.responseLabel.setText("Error with the port selected");
			this.responseLabel.setVisible(true);
			this.changeBackground("FF7070");
		}
	}
	
	@FXML
	public void setPort() {
		String port = portTextField.getText();
		String ret = controler.setPort(port);
		if (port.equals(ret))
		{
			this.responseLabel.setText("New port selected");
			this.responseLabel.setVisible(true);
			this.changeBackground("7EA1C9");
		}
		else
		{
			portTextField.setText(ret);
			this.responseLabel.setText("Error with the port selected");
			this.responseLabel.setVisible(true);
			this.changeBackground("FF7070");
		}
	}
	
	@FXML
	public void setMyPort() {
		String port = myPortTextField.getText();
		String ret = controler.setMyPort(port);
		if (port.equals(ret))
		{
			this.responseLabel.setText("New port selected");
			this.responseLabel.setVisible(true);
			this.changeBackground("7EA1C9");
		}
		else
		{
			myPortTextField.setText(ret);
			this.responseLabel.setText("Error with the port selected");
			this.responseLabel.setVisible(true);
			this.changeBackground("FF7070");
		}
	}
	
	public void hideLabel()
	{
		this.responseLabel.setVisible(false);
	}
	
	public void changeBackground(String color)
	{
		this.paneSetting.setStyle("-fx-background-color: #" + color + ";");
	}
	

}
