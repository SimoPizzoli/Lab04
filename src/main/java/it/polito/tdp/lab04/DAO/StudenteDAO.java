package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;


public class StudenteDAO {
	public List<Studente> getTuttiGliStudenti() {

		final String sql = "SELECT * FROM studente";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String matricola = rs.getString("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String CDS = rs.getString("CDS");

				System.out.println(matricola + " " + cognome + " " + nome + " " + CDS);

				// Crea un nuovo JAVA Bean Studente
				// Aggiungi il nuovo oggetto Studente alla lista studenti
				studenti.add(new Studente(matricola,cognome,nome,CDS));
			}

			conn.close();
			rs.close();
			st.close();
			
			return studenti;
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Corso> getCorsiByStudente(String matricola){
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND i.matricola = ?";
		
		List<Corso> corsi = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				Integer crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				Integer pd = rs.getInt("pd");
				
				corsi.add(new Corso(codins,crediti,nome,pd));
			} 
			
			st.close();
			conn.close();
			rs.close();
			
			return corsi;
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
}
