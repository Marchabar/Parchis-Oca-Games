package com.japarejo.springmvc.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardService {

	@Autowired
	private BoardRepository organoRepo;
		

	
	@Transactional
	public void initializeBoards() throws Exception {
		Iterable<Board> organoIter = organoRepo.findAll();
		Iterator<Board> iterador = organoIter.iterator();
		if (!iterador.hasNext()) {
			// No existen organos, vamos a crearlos:
			System.out.println("No hay organos!, vamos a incializarlos...");
			String[][] organos = { 
					{ "MESA", "Mesa del Parlamento"}, { "JP", "Junta de Portavoces"},
					{ "PPA", "Pleno del Parlamento"}, { "DIPPER", "Diputación Permamente"},
					{ "GPS", "G.P. Socialista"},
					{ "IULV-CA", "G.P. Izquierda Unida Los Verdes-Convocatoria por Andalucía"},
					{ "GPP", "G.P. Popular Andaluz", "2" }, { "GPPD", "G.P. Podemos Andalucía"},
					{ "GPC", "G.P. Ciudadanos"},{"GOBIERNO","Gobierno"} };
			Board organo = null;
			for (int i = 0; i < organos.length; i++) {
				organo = new Board();
				organo.setShortname(organos[i][0]);
				organo.setDescription(organos[i][1]);				
				organoRepo.save(organo);
			}
			System.out.println("Done!");
			
		}
	}
	
	@Transactional(readOnly = true)
	public Optional<Board> findById(Long idOrgano) {
		return organoRepo.findById(idOrgano);
	}

	@Transactional(readOnly = true)
	public Iterable<Board> findAll() {
		return organoRepo.findAll();
	}

	@Transactional
	public void save(Board organo) {		
		organoRepo.save(organo);
	}
	
	@Transactional
	public void delete(long id){
		organoRepo.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Board findByShortname(String string) {
		return organoRepo.findByShortname(string);
	}

}
