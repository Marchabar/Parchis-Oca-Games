package com.japarejo.springmvc.member;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japarejo.springmvc.board.Board;

@Service
public class MemberService {
	@Autowired
	private MemberRepository parlamentarioRepo;
	

	@Transactional(readOnly = true)
	public Iterable<Member> findAll() {
		return parlamentarioRepo.findAll();
	}
	
	@Transactional
	public void deleteById(long id) {
		parlamentarioRepo.deleteById(id);
	}

	@Transactional
	public void save(Member parlamentario) {
		parlamentarioRepo.save(parlamentario);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findById(long id) {
		return parlamentarioRepo.findById(id);
	}

	@Transactional(readOnly = true)
	public Member findByNombre(String nombre) {
		return parlamentarioRepo.findByName(nombre);
	}

	@Transactional
	public void resetBoards() {
		Iterable<Member> parlamentarios=parlamentarioRepo.findAll();
		List<Board> boardsToBeRemoved=new ArrayList<>();
		for(Member parlamentario:parlamentarios) {
			boardsToBeRemoved.clear();
			for(Board organo:parlamentario.getBoards())
				if(!organo.getDescription().startsWith("G.P."))
					boardsToBeRemoved.add(organo);
			parlamentario.getBoards().removeAll(boardsToBeRemoved);
			parlamentarioRepo.save(parlamentario);
		}
		
	}
}
