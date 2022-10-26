package com.japarejo.springmvc.alternating;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.japarejo.springmvc.alternating.exception.LuxuryMansionException;
import com.japarejo.springmvc.alternating.exception.ShittyMasterException;
import com.japarejo.springmvc.board.Board;
import com.japarejo.springmvc.board.BoardService;
import com.japarejo.springmvc.member.Member;
import com.japarejo.springmvc.member.MemberService;

@Service
public class AlternatingService {

	double PROBEXCEPTION = 0.1;
	int NPARLAMENTARIOS = 5;
	String[] sustantives = { "El milagro", "Trueno", "Vigilante", "Capitán", "Chico", "Aguijón", "Maestro", "Halcón",
			"Tornado", "Pecador", "Fistro" };
	String[] adjective = { "elástico", "espacial", "mágico", "biónico", "radiactivo", "maravilla", "letal", "mutante",
			"amoroso", "carmesí", "diodenarr", "de la pradera" };

	@Autowired
	MemberService memberService;

	@Autowired
	BoardService boardService;
	private TransactionTemplate transactionTemplate;

	public AlternatingService(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);   
	} 

	@Transactional(rollbackFor = {LuxuryMansionException.class,ShittyMasterException.class})
	public void alternanting() throws ShittyMasterException, LuxuryMansionException {		
		/*transactionTemplate.execute(
				  new TransactionCallbackWithoutResult() { 
				   protected void doInTransactionWithoutResult(TransactionStatus status) {
				    try {*/
				    	
				    	memberService.resetBoards();
						List<Member> parlamentarios = crearUObtenerParlamentarios();
						modificarOrganos(parlamentarios); 
				    
				    /*
				    } catch (CutreMasterException | CasoplonException ex) {
				       status.setRollbackOnly(); 
				    } 
				   } 
				  }); */

		
	}
 
	@Transactional(rollbackFor = {LuxuryMansionException.class,ShittyMasterException.class})
	private void modificarOrganos(List<Member> members) throws ShittyMasterException, LuxuryMansionException {
		
	    // Remove the previous members of the government
		Board gov = boardService.findByShortname("GOBIERNO");		
		for(Member m:gov.getMembers()) {
            m.getBoards().remove(gov);        
		}
		gov.getMembers().clear();
		boardService.save(gov);
		int i = 0;
        // Add the new members of the government		
		for (Member member : members) {
			double random = Math.random();
			if (i > 0) {
				if (random < PROBEXCEPTION) {
					throw new ShittyMasterException();
				} else if (random < 2 * PROBEXCEPTION) {
					throw new LuxuryMansionException();
				}
			}			
			member.getBoards().add(gov);
			gov.getMembers().add(member);			
			memberService.save(member);			
			i++;
		}
	}

	@Transactional
	public List<Member> crearUObtenerParlamentarios() {
		List<Member> result = new ArrayList<>();
		Member member = null;
		String name = null;
		for (int i = 0; i < NPARLAMENTARIOS; i++) {
			name = generateRandomName();
			member = memberService.findByNombre(name);
			if (member == null) {
				member = new Member();
				member.setName(name);
				member.setBoards(new ArrayList<Board>());
				memberService.save(member);
			}
			result.add(member);
		}
		return result;
	}

	private String generateRandomName() {
		String sustantive = sustantives[(int) (Math.random() * sustantives.length)];
		String adjetive = adjective[(int) (Math.random() * adjective.length)];
		return sustantive + " " + adjetive;
	}

	

}
