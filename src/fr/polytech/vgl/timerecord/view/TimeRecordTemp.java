package fr.polytech.vgl.timerecord.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;
//import fr.polytech.vgl.model.Frame;
import fr.polytech.vgl.timerecord.controller.DateLabel;
import fr.polytech.vgl.timerecord.controller.RoundedLabel;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;

import fr.polytech.vgl.timerecord.controller.settingsController;
import fr.polytech.vgl.timerecord.controller.companyController;





import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;


import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


/**
 * TimeRecordMainFrame is the main Frame of the Time Record
* @author Lino Touret - Valentin L'Hermite
*
*/
public class TimeRecordTemp extends Application {

	@FXML
    private ComboBox<Employee> comboBoxEmp;
	
	@FXML
	private Label dateLabel;
	
	@FXML
	private Label timeLabel;
	
	@FXML
	private Label roundedTimeLabel;
	
	@FXML
	private TextField testTextField;
	
	
	
	private Stage mainStage;
	private boolean testMode;
	
	private TimeRecordControler controler;

	private JComboBox<Employee> comboBox, comboBox_1;
	private JComboBox<Company> comboBox_2;

	private JFrame frmTimerecord;
	MaskFormatter  dateFormatter;
	final static Color[] colors = { Color.decode("#C8DDF2"), Color.decode("#9CB5E1"), Color.decode("#DFF5E9"),
			Color.decode("#F0D0D0") };
	
	
	/*
	 * TimeRecordMainFrame Default Constructor
	 */
	public TimeRecordTemp() {
		super();
		controler = new TimeRecordControler();
		
		//initializeSwing();
		//frmTimerecord.setVisible(true);
		
	}

	/**
	 * TimeRecordMainFrame Main Contructer
	 * @param _controler
	 */
	public TimeRecordTemp(TimeRecordControler _controler) {
		super();
		controler = _controler;

		
		initializeSwing();
		frmTimerecord.setVisible(true);
	

	}
	
	
	
	@Override    
	public void start(Stage primaryStage) throws Exception { 
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("file\\timeRecord.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setTitle("Time Record");
        primaryStage.setScene(new Scene(root, 600, 400));
        mainStage = primaryStage;
        //primaryStage.getScene().getStylesheets().add(getClass().getResource("file\\style.css").toExternalForm());
        primaryStage.show();
        
	}
	
	@FXML
    public void initialize() {
		this.updateComboBox();
		this.testTextField.setVisible(false);
		this.testMode = false;
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter roundedTime = DateTimeFormatter.ofPattern("HH:mm");
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime currentLocalDate = LocalDateTime.now();
            LocalDateTime currentRoundedTime;
            if (currentLocalDate.getMinute()%15 < 7) {
            	currentRoundedTime = LocalDateTime.now().minusMinutes(currentLocalDate.getMinute()%15);
            }
            else {
            	currentRoundedTime = LocalDateTime.now().plusMinutes(currentLocalDate.getMinute()%15);
            }
            String formatedDate = currentLocalDate.format(date);
            String formatedTime = currentLocalDate.format(time);
            String formatedRoundedTime = currentRoundedTime.format(roundedTime);
            dateLabel.setText(formatedDate);
            timeLabel.setText(formatedTime);
            roundedTimeLabel.setText("Let's says : " + formatedRoundedTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
	
	public void updateComboBox()
	{
		this.comboBoxEmp.getItems().clear();
		for (Employee emp : this.controler.getAllEmp())
    	{
    		comboBoxEmp.getItems().add(emp);
    	}
	}
	
	@FXML
    public void click() {
        System.out.println(comboBoxEmp.getSelectionModel().getSelectedItem().toString() + " " + 
    comboBoxEmp.getSelectionModel().getSelectedItem().getId());
        
        if (comboBoxEmp.getSelectionModel().getSelectedItem() != null)
        {
        	int ret = controler.sendRecord(comboBoxEmp.getSelectionModel().getSelectedItem());
			if (ret == 1)
			{
				//Validation							
			}
			else if (ret == 0)
			{
				//Attente de connection;							
			}
			else if (ret == -1)
			{
				//Error						
			}
        }
    }
	
	@FXML
	public void showSettings()
	{
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("file\\settings.fxml"));
            Parent root = loader.load();
            settingsController controllerSetting = loader.getController();
            controllerSetting.setTimeRecordController(this.controler);
            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.setScene(new Scene(root, 600, 300));
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.initOwner(mainStage);
            controllerSetting.updateLabelIPAddress(this.controler.getMyIp());
            controllerSetting.updateTextFieldIp("Localhost");
            controllerSetting.updateTextFieldPort(String.valueOf(this.controler.getPort()));
            controllerSetting.updateTextFieldMyPort(String.valueOf(this.controler.getMyPort()));
            settingsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void showCompanies()
	{
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("file\\company.fxml"));
            Parent root = loader.load();
            companyController controllerCompany = loader.getController();
            controllerCompany.setControler(this.controler);
            Stage companyStage = new Stage();
            companyStage.setTitle("companies update");
            companyStage.setScene(new Scene(root, 600, 400));
            companyStage.initModality(Modality.APPLICATION_MODAL);
            companyStage.initOwner(mainStage);
            controllerCompany.updateComboBoxElement(this.controler.getListCompany());
            companyStage.showAndWait();
            this.updateComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void selectTestMode()
	{
		this.switchTestMode();
	}
	
	private void switchTestMode()
	{
		if (!this.testMode)
		{
			this.testMode = true;
			this.testTextField.setVisible(true);
			LocalDateTime currentLocalDate = LocalDateTime.now();
			DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			this.testTextField.setText(currentLocalDate.format(dateTime));
			return;
		}
	}

	/**
	 * Initialize the contents of the frame. The Tabbed pane and the others panels
	 */
	private void initializeSwing() {

		DefaultComboBoxModel<Employee> modelEmployee = new DefaultComboBoxModel<Employee>();
		DefaultComboBoxModel<Company> modelCompany = new DefaultComboBoxModel<Company>();

		// DefaultComboBoxModel<Employee> comboboxmodel_1 = new
		// DefaultComboBoxModel<Employee>();

		try {
			dateFormatter = new MaskFormatter("##/##/####;##:##");

		} catch (ParseException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
		

		frmTimerecord = new JFrame();
		frmTimerecord.setTitle("TimeRecord");
		frmTimerecord.setBounds(100, 100, 500, 300);
		frmTimerecord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		frmTimerecord.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Seguro que quiere salir?");
                if(i==0)
                    System.exit(0);//cierra aplicacion
            }
        });
		*/

		frmTimerecord.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	//System.out.println("Hey");
            	controler.closeWindow();	
            	
            	
            }
        });
		
		//Tabbec Pannel
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setForeground(new Color(0, 0, 0));
		tabbedPane.setBackground(colors[1]);
		tabbedPane.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 14));
		frmTimerecord.getContentPane().add(tabbedPane);

		final JPanel checkPanel = new JPanel();
		checkPanel.setBackground(colors[0]);
		tabbedPane.addTab("Check", null, checkPanel, null);
		GridBagLayout gbl_checkPanel = new GridBagLayout();
		gbl_checkPanel.columnWidths = new int[] { 50, 100, 150, 100, 0, 0 };
		gbl_checkPanel.rowHeights = new int[] { 50, 0, 45, 0, 45, 35, 0 };
		gbl_checkPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_checkPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		checkPanel.setLayout(gbl_checkPanel);

		DateLabel lblDate = new DateLabel();
		lblDate.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.gridwidth = 3;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 1;
		gbc_lblDate.gridy = 0;
		checkPanel.add(lblDate, gbc_lblDate);

		DateLabel lblHour = new DateLabel(true);
		lblHour.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblHour = new GridBagConstraints();
		gbc_lblHour.gridwidth = 3;
		gbc_lblHour.insets = new Insets(0, 0, 5, 5);
		gbc_lblHour.gridx = 1;
		gbc_lblHour.gridy = 1;
		checkPanel.add(lblHour, gbc_lblHour);

		RoundedLabel lblRoundedHour = new RoundedLabel();
		lblRoundedHour.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblRoundedHour = new GridBagConstraints();
		gbc_lblRoundedHour.gridwidth = 3;
		gbc_lblRoundedHour.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoundedHour.gridx = 1;
		gbc_lblRoundedHour.gridy = 2;
		checkPanel.add(lblRoundedHour, gbc_lblRoundedHour);

		JButton btnNewButton_1 = new JButton("Check In/Out");
		

		// ComboBoxModel<Employee> c = new ComboBoxModel<Employee>();
		comboBox = new JComboBox<Employee>(modelEmployee);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		checkPanel.add(comboBox, gbc_comboBox);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 3;
		checkPanel.add(btnNewButton_1, gbc_btnNewButton_1);

		final JLabel lblValidateMessage = new JLabel("");
		lblValidateMessage.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblValidateMessage = new GridBagConstraints();
		gbc_lblValidateMessage.gridwidth = 3;
		gbc_lblValidateMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblValidateMessage.gridx = 1;
		gbc_lblValidateMessage.gridy = 4;
		checkPanel.add(lblValidateMessage, gbc_lblValidateMessage);

		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if ((Employee) comboBox.getSelectedItem() != null) {
						int ret = 	controler.sendRecord((Employee) comboBox.getSelectedItem());
						if (ret == 1)
						{
							checkPanel.setBackground(colors[2]);
							lblValidateMessage.setText("Record Sended");							
						}
						else if (ret == 0)
						{
							checkPanel.setBackground(colors[1]);
							lblValidateMessage.setText("Waiting Connection to Send");							
						}
						else if (ret == -1)
						{
							checkPanel.setBackground(colors[3]);
							lblValidateMessage.setText("Already sended record in the last "+ Record.getRounded() + " min");							
						}
						//checkPanel.setBackground(new Color(223, 245, 233));
						// Thread.sleep(5000);
						
					}
				} catch (Exception exc) {
					// nothinf
				}
			}
		});
		
		
		//Settings Panel 
		final JPanel settingsPanel = new JPanel();
		settingsPanel.setBackground(colors[0]);
		tabbedPane.addTab("Settings", null, settingsPanel, null);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{50, 120, 140, 110, 0, 0};
		gbl_settingsPanel.rowHeights = new int[]{40, 60, 40, 40, 50, 0, 0};
		gbl_settingsPanel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		JButton btnSetIp = new JButton("Set Ip");
		btnSetIp.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		
		
		final JLabel settingLabel = new JLabel("");
		settingLabel.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 17));
		GridBagConstraints gbc_settingLabel = new GridBagConstraints();
		gbc_settingLabel.gridwidth = 3;
		gbc_settingLabel.insets = new Insets(0, 0, 5, 5);
		gbc_settingLabel.gridx = 1;
		gbc_settingLabel.gridy = 0;
		settingsPanel.add(settingLabel, gbc_settingLabel);
		
		JLabel lblNewLabel = new JLabel("IP Address");
		lblNewLabel.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		settingsPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		final JFormattedTextField textIp = new JFormattedTextField("Localhost");
		GridBagConstraints gbc_textIp = new GridBagConstraints();
		gbc_textIp.insets = new Insets(0, 0, 5, 5);
		gbc_textIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textIp.gridx = 2;
		gbc_textIp.gridy = 1;
		settingsPanel.add(textIp, gbc_textIp);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 1;
		settingsPanel.add(btnSetIp, gbc_btnNewButton);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 2;
		settingsPanel.add(lblPort, gbc_lblPort);
		
		final JFormattedTextField textPort = new JFormattedTextField(""+controler.getPort());
		textPort.setFont(new Font("Verdana Pro", Font.PLAIN, 13));
		GridBagConstraints gbc_formattedTextField_1 = new GridBagConstraints();
		gbc_formattedTextField_1.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_1.gridx = 2;
		gbc_formattedTextField_1.gridy = 2;
		settingsPanel.add(textPort, gbc_formattedTextField_1);
		
		JButton btnSetPort = new JButton("Set Port");
		btnSetPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSetPort = new GridBagConstraints();
		gbc_btnSetPort.insets = new Insets(0, 0, 5, 5);
		gbc_btnSetPort.gridx = 3;
		gbc_btnSetPort.gridy = 2;
		settingsPanel.add(btnSetPort, gbc_btnSetPort);
		
		JLabel lblMyIpAddress = new JLabel("My IP Address :");
		lblMyIpAddress.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMyIpAddress = new GridBagConstraints();
		gbc_lblMyIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblMyIpAddress.gridx = 1;
		gbc_lblMyIpAddress.gridy = 3;
		settingsPanel.add(lblMyIpAddress, gbc_lblMyIpAddress);
		
		JLabel lblIp = new JLabel(controler.getMyIp());
		lblIp.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 2;
		gbc_lblIp.gridy = 3;
		settingsPanel.add(lblIp, gbc_lblIp);
		
		JLabel lblMyPort = new JLabel("My Port :");
		lblMyPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMyPorts = new GridBagConstraints();
		gbc_lblMyPorts.insets = new Insets(0, 0, 5, 5);
		gbc_lblMyPorts.gridx = 1;
		gbc_lblMyPorts.gridy = 4;
		settingsPanel.add(lblMyPort, gbc_lblMyPorts);
		
		
		final JFormattedTextField textMyPort = new JFormattedTextField(""+controler.getMyPort());
		textMyPort.setFont(new Font("Verdana Pro", Font.PLAIN, 13));
		GridBagConstraints gbc_formattedTextField_1_1 = new GridBagConstraints();
		gbc_formattedTextField_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_1_1.gridx = 2;
		gbc_formattedTextField_1_1.gridy = 4;
		settingsPanel.add(textMyPort, gbc_formattedTextField_1_1);
		
		JButton btnSetMyPort = new JButton("Set My Port");
		btnSetMyPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSetPort_1 = new GridBagConstraints();
		gbc_btnSetPort_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnSetPort_1.gridx = 3;
		gbc_btnSetPort_1.gridy = 4;
		settingsPanel.add(btnSetMyPort, gbc_btnSetPort_1);
		
		btnSetIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println();
				String text = textIp.getText();
				String ret = controler.setIp(text);
				if (text.equals(ret))
				{
					settingLabel.setText("IP selected");
					settingsPanel.setBackground(colors[2]);
				}
				else
				{
					textIp.setText(ret);
					settingLabel.setText("Error IP");
					settingsPanel.setBackground(colors[3]);
				}

				
				
			}
		});
		

		btnSetPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String port = textPort.getText();
				String ret = controler.setPort(port);
				if (port.equals(ret))
				{
					settingLabel.setText("Port selected");
					settingsPanel.setBackground(colors[2]);
				}
				else
				{
					textPort.setText(ret);
					settingLabel.setText("Error Port");
					settingsPanel.setBackground(colors[3]);
				}
			}
		});

		btnSetMyPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String port = textMyPort.getText();
				String ret = controler.setMyPort(port);
				if (port.equals(ret))
				{
					settingLabel.setText("Port selected");
					settingsPanel.setBackground(colors[2]);
				}
				else
				{
					textMyPort.setText(ret);
					settingLabel.setText("Error Port");
					settingsPanel.setBackground(colors[3]);
				}
			}
		});

		//FIle company panel
		final JPanel filePanel = new JPanel();
		filePanel.setBackground(colors[0]);
		tabbedPane.addTab("File", null, filePanel, null);
		GridBagLayout gbl_filePanel = new GridBagLayout();
		gbl_filePanel.columnWidths = new int[] { 50, 138, 160, 0, 0 };
		gbl_filePanel.rowHeights = new int[] { 40, 60, 40, 40, 50, 0, 0 };
		gbl_filePanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_filePanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		filePanel.setLayout(gbl_filePanel);

		JLabel lblNewLabel_1 = new JLabel("File");
		lblNewLabel_1.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		filePanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		final JTextPane textFile = new JTextPane();
		textFile.setEnabled(true);
		textFile.setEditable(false);
		GridBagConstraints gbc_textIp_1 = new GridBagConstraints();
		gbc_textIp_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textIp_1.insets = new Insets(0, 0, 5, 5);
		gbc_textIp_1.gridx = 1;
		gbc_textIp_1.gridy = 1;
		filePanel.add(textFile, gbc_textIp_1);

		JButton btnSelectCompany = new JButton("Select a Company");
		btnSelectCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser(new File("."));

				choose.setDialogTitle("TimeRecord> Select a Company Serialized File");
				choose.setAcceptAllFileFilterUsed(true);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".sav .ser .dat", "sav", "ser", "dat");
				choose.addChoosableFileFilter(filter);
				int res = choose.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					System.out.println(choose.getSelectedFile().getPath());
					controler.setFile(choose.getSelectedFile());
					textFile.setText(choose.getSelectedFile().getName());
				}
			}
		});
		btnSelectCompany.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 1;
		filePanel.add(btnSelectCompany, gbc_btnNewButton_2);

		JButton btnAddCompany = new JButton("Add Company");

		btnAddCompany.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		GridBagConstraints gbc_btnAddCompany = new GridBagConstraints();
		gbc_btnAddCompany.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddCompany.gridx = 2;
		gbc_btnAddCompany.gridy = 2;
		filePanel.add(btnAddCompany, gbc_btnAddCompany);

		comboBox_2 = new JComboBox<Company>(modelCompany);
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 3;
		filePanel.add(comboBox_2, gbc_comboBox_2);

		JButton btnDelCompany = new JButton("Delete Company");
		btnDelCompany.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));

		GridBagConstraints gbc_btnDelCompany = new GridBagConstraints();
		gbc_btnDelCompany.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelCompany.gridx = 2;
		gbc_btnDelCompany.gridy = 3;
		filePanel.add(btnDelCompany, gbc_btnDelCompany);

		final JLabel fileLabel = new JLabel("");
		fileLabel.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 17));
		GridBagConstraints gbc_fileLabel = new GridBagConstraints();
		gbc_fileLabel.gridwidth = 2;
		gbc_fileLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fileLabel.gridx = 1;
		gbc_fileLabel.gridy = 4;
		filePanel.add(fileLabel, gbc_fileLabel);

		btnAddCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = controler.deserialiseCompany();
				if (a.equals("No company found in the file")) {
					filePanel.setBackground(colors[3]);
					fileLabel.setText(a);
				} else if (a.equals("File not found")) {
					fileLabel.setText(a);
					filePanel.setBackground(colors[3]);
				} else {
					fileLabel.setText(a);
					filePanel.setBackground(colors[2]);
				}
			}
		});

		btnDelCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ret = ConfirmDel(((Company)comboBox_2.getSelectedItem()).getCompanyName());
				if (ret == 0)
				{
					boolean r = controler.delCompany((Company)comboBox_2.getSelectedItem());
					if (r == true)
					{
						fileLabel.setText("Company Successfully Deleted");
						filePanel.setBackground(colors[2]);
					}
					else
					{
						fileLabel.setText("Error During Deletion");
						filePanel.setBackground(colors[3]);
					}
				}
				else
				{
					fileLabel.setText("Company Not Deleted");
					filePanel.setBackground(colors[0]);
				}
					//System.exit(0);
			}
		});

		// Test Panel
		final JPanel testPanel = new JPanel();
		testPanel.setBackground(colors[0]);
		tabbedPane.addTab("Test Mode", null, testPanel, null);
		GridBagLayout gbl_testPanel = new GridBagLayout();
		gbl_testPanel.columnWidths = new int[] { 50, 100, 150, 100, 0, 0 };
		gbl_testPanel.rowHeights = new int[] { 50, 0, 45, 0, 0, 35, 35, 0 };
		gbl_testPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_testPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		testPanel.setLayout(gbl_testPanel);

		DateLabel lblDate_1 = new DateLabel();
		lblDate_1.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.gridwidth = 3;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_1.gridx = 1;
		gbc_lblDate_1.gridy = 0;
		testPanel.add(lblDate_1, gbc_lblDate_1);

		DateLabel lblHour_1 = new DateLabel(true);
		lblHour_1.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblHour_1 = new GridBagConstraints();
		gbc_lblHour_1.gridwidth = 3;
		gbc_lblHour_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblHour_1.gridx = 1;
		gbc_lblHour_1.gridy = 1;
		testPanel.add(lblHour_1, gbc_lblHour_1);

		RoundedLabel lblRoundedHour_1 = new RoundedLabel();
		lblRoundedHour_1.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblRoundedHour_1 = new GridBagConstraints();
		gbc_lblRoundedHour_1.gridwidth = 3;
		gbc_lblRoundedHour_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoundedHour_1.gridx = 1;
		gbc_lblRoundedHour_1.gridy = 2;
		testPanel.add(lblRoundedHour_1, gbc_lblRoundedHour_1);

		// EmployeesComboBox comboBox_1 = new EmployeesComboBox();
		// JComboBox<Employee> comboBox_1 = new JComboBox<Employee>();
		comboBox_1 = new JComboBox<Employee>(modelEmployee);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 3;
		testPanel.add(comboBox_1, gbc_comboBox_1);

		JButton btnNewButton_1_1 = new JButton("Check In/Out");

		GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
		gbc_btnNewButton_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1_1.gridx = 3;
		gbc_btnNewButton_1_1.gridy = 3;
		testPanel.add(btnNewButton_1_1, gbc_btnNewButton_1_1);

		final JFormattedTextField formattedTextField_2 = new JFormattedTextField(dateFormatter);
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm");
		formattedTextField_2.setText(LocalDateTime.now().format(formatter));
		GridBagConstraints gbc_formattedTextField_2 = new GridBagConstraints();
		gbc_formattedTextField_2.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_2.gridx = 2;
		gbc_formattedTextField_2.gridy = 4;
		testPanel.add(formattedTextField_2, gbc_formattedTextField_2);

		final JLabel lblValidateMessage_1 = new JLabel("");
		lblValidateMessage_1.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblValidateMessage_1 = new GridBagConstraints();
		gbc_lblValidateMessage_1.gridwidth = 3;
		gbc_lblValidateMessage_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblValidateMessage_1.gridx = 1;
		gbc_lblValidateMessage_1.gridy = 5;
		testPanel.add(lblValidateMessage_1, gbc_lblValidateMessage_1);

		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = formattedTextField_2.getText();
				// System.out.println(LocalDateTime.parse(text,formatter));
				try {
					LocalDateTime date = LocalDateTime.parse(text, formatter);
					if ((Employee) comboBox.getSelectedItem() != null) {
						controler.sendRecordTest((Employee) comboBox_1.getSelectedItem(), date);
					}
				} catch (Exception exc) {
					// nothinf
					lblValidateMessage_1.setText("Erreur Date");
					testPanel.setBackground(colors[3]);

				}

			}
		});

	}

	/**
	 * getComboBox
	 * @return comboBox
	 */
	public JComboBox<Employee> getComboBox() {
		return comboBox;
	}

	/**
	 * getComboBox_1
	 * @return comboBox
	 */
	public JComboBox<Employee> getComboBox_1() {
		return comboBox_1;
	}

	/**
	 * addEmployee
	 * @param employee
	 */
	public void addEmployee(Employee emp) {
		// comboBox_1.addItem(emp);
		comboBox.addItem(emp);
		
		//comboBoxFX.getItems().add(emp.toString());

	}

	/**
	 * delEmployee
	 * @param employee
	 */
	public void delEmployee(Employee emp) {
		// comboBox_1.addItem(emp);
		comboBox.removeItem(emp);

	}
	
	/**
	 * addCompany
	 * @param company
	 */
	public void addCompany(Company com) {
		comboBox_2.addItem(com);
	}

	/**
	 * delCompany 
	 * @param company
	 */
	public void delCompany(Company com) {
		comboBox_2.removeItem(com);
	}

	/**
	 * ConfirmDel is a confirmation dialog
	 * @param companyName
	 * @return JOptionPane result
	 */
	static int ConfirmDel(String companyName){
		return JOptionPane.showConfirmDialog(
		       null,
		       "Do you really want to delete the Company: "+ companyName+" ?",
		       "Confirmation",
		       JOptionPane.YES_NO_OPTION);
		 }
}