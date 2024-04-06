package fr.polytech.vgl.timerecord.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import fr.polytech.vgl.model.Company;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class companyController {
	@FXML
	private ComboBox<Company> comboBoxCompany;
	
	@FXML
	private TextField repositoryTextField;
	
	private TimeRecordControler controler;
	
	public void updateComboBoxElement(List<Company> companyList)
	{
		this.comboBoxCompany.getItems().clear();
		for (Company comp : companyList)
		{
			this.comboBoxCompany.getItems().add(comp);
		}
	}
	
	@FXML
	public void addCompany()
	{
		String a = this.controler.deserialiseCompany();
		if (a.equals("No company found in the file")) {
			//Pas de compagnie trouvé
		} else if (a.equals("File not found")) {
			//Pas de fichier trouvé
		} else {
			//Tout est bon, la compagnie est crée
		}
	}
	
	@FXML
	public void deleteCompany()
	{
		if (this.comboBoxCompany.getSelectionModel().isEmpty())
		{
			Alert emptyAlert = new Alert(Alert.AlertType.INFORMATION);
            emptyAlert.setTitle("No company selected");
            emptyAlert.setHeaderText(null);
            emptyAlert.setContentText("Please, choose a company before deleting it");
            emptyAlert.showAndWait();
			return;
		}
		
		Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Are you sure?");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Do you want to delete " + this.comboBoxCompany.getSelectionModel().getSelectedItem().toString() + " ?");
        
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	boolean delete = this.controler.delCompany(this.comboBoxCompany.getSelectionModel().getSelectedItem());
        	if (delete)
        	{
        		//Supression valide
        		this.updateComboBoxElement(this.controler.getListCompany());
        	}
        	else
        	{
        		//Erreur lors de la suppression
        	}
        }
	}
	
	@FXML
	public void selectCompanyFile()
	{
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selection d'une compagnie");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save Files", "*.sav"));
        File initialDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(initialDirectory);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            this.repositoryTextField.setText(selectedFile.getAbsolutePath());
        }
	}

	public TimeRecordControler getControler() {
		return controler;
	}

	public void setControler(TimeRecordControler controler) {
		this.controler = controler;
	}
}
