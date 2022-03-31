package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Model model;
	private String matricola;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbCorsi;
    
    @FXML
    private Label lblErrore;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextArea txtRisultato;

    @FXML
    void handleCercaCorsi(ActionEvent event) {
    	txtRisultato.clear();
    	lblErrore.setText("");
    	String matricola = txtMatricola.getText();
    	
    	//controllo
    	boolean trovato = false;
    	for(Studente s : model.getTuttiGliStudenti())
    		if(s.getMatricola().equals(matricola))
    			trovato = true;
    	if(trovato == false) {
    		lblErrore.setText("Errore, studente non trovato!");
    		return;
    	}
    	
    	for(Corso c : this.model.getCorsiByStudente(matricola)) {
    		txtRisultato.appendText(c.toString()+"\n");
    	}
    }

    @FXML
    void handleCercaIscritto(ActionEvent event) {
    	txtRisultato.clear();
    	lblErrore.setText("");
    	String codins="";
    	for(Corso c : model.getTuttiICorsi())
    		if(c.getNome().equals(cmbCorsi.getValue()))
    			codins = c.getCodins();
    	if(codins == null || codins.equals("")) {
    		lblErrore.setText("Inserire un codice di un corso!");
    		return;
    	}
    	
    	for(Studente s : this.model.getStudentiIscrittiAlCorso(codins)) {
    		txtRisultato.appendText(s.toString()+"\n");
    	}
    }

    @FXML
    void handleCompleta(ActionEvent event) {
    	lblErrore.setText("");
    	boolean trovato = false;
    	matricola = txtMatricola.getText();
    	for(Studente st : model.getTuttiGliStudenti()) {
    		if(st.getMatricola().equals(matricola)) {
    			txtNome.setText(st.getNome());
    			txtCognome.setText(st.getCognome());
    			trovato = true;
    		}
    	}
    	if(trovato == false) {
    		lblErrore.setText("Errore, studente non presente nel database");
    		return;
    	}
    }

    @FXML
    void handleIscrivi(ActionEvent event) {
    	Studente studente = null;
    	Corso corso = null;
    	
    	for(Studente s : model.getTuttiGliStudenti())
    		if(s.getMatricola().equals(txtMatricola.getText()))
    			studente = s;
    	corso = model.getCorso(cmbCorsi.getValue());
    	
    	if(studente == null || corso == null) {
    		lblErrore.setText("Errore");
    		return;
    	}
    	boolean inserito = model.inscriviStudenteACorso(studente, corso);
    	if(inserito == true)
    		txtRisultato.setText("Studente iscritto al corso!");
    	else
    		txtRisultato.setText("Studente già iscritto a questo corso");
    }

    @FXML
    void handleReset(ActionEvent event) {
    	txtRisultato.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	lblErrore.setText("");
    	cmbCorsi.setValue("Corsi");
    }

    @FXML
    void initialize() {
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrore != null : "fx:id=\"lblErrore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
        cmbCorsi.getItems().clear();
        for(Corso c : model.getTuttiICorsi()) {
        	cmbCorsi.getItems().add(c.getNome());
        } 
        cmbCorsi.getItems().add("*Non selezionare*");
        
    }
}
