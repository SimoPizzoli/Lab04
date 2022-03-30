package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi() {
		return this.corsoDao.getTuttiICorsi();
	}
	
	public List<Studente> getTuttiGliStudenti() {
		return this.studenteDao.getTuttiGliStudenti();
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(String codins){
		return this.corsoDao.getStudentiIscrittiAlCorso(codins);
	}
	
	public List<Corso> getCorsiByStudente(String matricola){
		return this.studenteDao.getCorsiByStudente(matricola);
	}
	
	public Corso getCorso(Corso corso) {
		return this.corsoDao.getCorso(corso);
	}
}
