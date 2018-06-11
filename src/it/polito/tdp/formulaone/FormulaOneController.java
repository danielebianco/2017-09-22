package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {

	Model model = new Model();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Season> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaStagione"
    private Button btnSelezionaStagione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGara"
    private ComboBox<Race> boxGara; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulaGara"
    private Button btnSimulaGara; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK"
    private TextField textInputK; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK1"
    private TextField textInputK1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	try {
    		
    		Season s = (Season) boxAnno.getValue();
    		if(s == null) {
    			txtResult.setText("Selezionare una stagione!");
    			return;
    		}
    		model.creaGrafo(s);
    		String d = model.findMaxDegree();
    		txtResult.setText(d.toString());
    		
    		this.boxGara.getItems().clear();
        	this.boxGara.getItems().addAll(model.getRaces());
//        	this.boxGara.setDisable(false);
    		
    	} catch(RuntimeException e) {
    		e.printStackTrace(); // poi commenta
    		System.out.println("Errore connessione al DB");
    		txtResult.setText("Errore connessione al DB");
    	}
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	
    	Season s = (Season) boxAnno.getValue();
    	if(s == null) {
			txtResult.setText("Selezionare una stagione!");
			return;
		}
    	
    	Race r = (Race) boxGara.getValue();
		if(r == null) {
			txtResult.setText("Selezionare una gara!");
			return;
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";
//        this.boxGara.setDisable(true);
    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.getSeasons());
		this.boxGara.getItems().clear();
	}
}
