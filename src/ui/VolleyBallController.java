package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Participant;
import model.VolleyBallEvent;

public class VolleyBallController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField path;

    @FXML
    private Label message;

    @FXML
    private TextField idSpectators;

    @FXML
    private Label spectatorId;

    @FXML
    private Label timeSpectators;

    @FXML
    private TextField idParticipant;

    @FXML
    private Label participantId;

    @FXML
    private Label timeParticipant;

    @FXML
    private ImageView image;

    @FXML
    private Label data;
    
    @FXML
    private Label id;

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label email;

    @FXML
    private Label gender;

    @FXML
    private Label country;

    @FXML
    private Label birthday;
    
    private VolleyBallEvent volleyBallEvent;

    @FXML
    void exportFile(ActionEvent event) {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	fileChooser.showOpenDialog(stage);
    }

    @FXML
    void loadFile(ActionEvent event) throws IOException {
    	path.setText(volleyBallEvent.LoadFileAndAddToTree());
    	message.setVisible(true);
    }

    @FXML
    void searchParticipant(ActionEvent event) {
    	long time = System.currentTimeMillis();
    	try {
    		int id = Integer.parseInt(idParticipant.getText());
    		showDataInScreen(volleyBallEvent.searchParticipant(id));
    	}catch (NumberFormatException e) {
    		Alert score = new Alert(AlertType.ERROR);
        	score.setTitle(" IV Copa Panamericana de Voleibol Masculino Sub-21");
        	score.initStyle(StageStyle.DECORATED);
        	score.setContentText("Please introduce a number");
        	score.show();
    	}
    	timeParticipant.setText(""+ (System.currentTimeMillis()-time));
    }

    @FXML
    void searchSpectators(ActionEvent event) {
    	long time = System.currentTimeMillis();
    	try {
    		int id = Integer.parseInt(idSpectators.getText());
    	}catch (NumberFormatException e) {
    		Alert score = new Alert(AlertType.ERROR);
        	score.setTitle(" IV Copa Panamericana de Voleibol Masculino Sub-21");
        	score.initStyle(StageStyle.DECORATED);
        	score.setContentText("Please introduce a number");
        	score.show();
    	}
    	
    	
    	timeSpectators.setText(""+ (System.currentTimeMillis()-time));
    }
    
    public void showDataInScreen(Participant p) {
    	image.setImage(p.getPhoto());
    	id.setText(p.getId()+"");
    	firstName.setText(p.getFirstName());
    	lastName.setText(p.getLastName());
    	email.setText(p.getEmail());
    	gender.setText(p.getGender());
    	country.setText(p.getCountry());
    	birthday.setText(p.getBirthday());
    }

    @FXML
    void initialize() {
    	volleyBallEvent = new VolleyBallEvent();
    	message.setVisible(false);
    }
}
