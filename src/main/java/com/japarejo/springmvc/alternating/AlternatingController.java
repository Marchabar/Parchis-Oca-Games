package com.japarejo.springmvc.alternating;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import com.japarejo.springmvc.board.Board;
import com.japarejo.springmvc.board.BoardService;
import com.japarejo.springmvc.member.MemberController;

@Controller
public class AlternatingController {
	@Autowired
	BoardService orgServ;
	@Autowired
	AlternatingService altServ;
	
	@Autowired
	MemberController pc;

	@GetMapping("/crearteMembers")
	public ModelAndView generarParlametarios(){
		altServ.crearUObtenerParlamentarios();
		return pc.showMembers();

	}

	@GetMapping("/alternancy")
	public ModelAndView alternancy() {		
		try {
			altServ.alternanting();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return gobierno();
	}
	
	@GetMapping("/government")
	public ModelAndView gobierno() {
		ModelAndView result=new ModelAndView("MembersListing");
		Board gobierno=orgServ.findByShortname("GOBIERNO");
		if(gobierno!=null)
			result.addObject("members", gobierno.getMembers());
		else
			result.addObject("members",Collections.EMPTY_LIST);
		result.addObject("message","Government");
		return result;
	}  
	
}
