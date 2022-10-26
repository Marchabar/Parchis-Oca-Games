package com.japarejo.springmvc.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	private BoardService boardsService;
	
	@GetMapping
	public ModelAndView listadoOrganos(){		
		ModelAndView result=new ModelAndView("BoardsListing");		
		result.addObject("boards", boardsService.findAll());
		return result;
	}
	
	@GetMapping(path="/create")
	public ModelAndView crearOrgano(){		
		ModelAndView result=new ModelAndView("EditBoard");	
		result.addObject("board", new Board());		
		return result;
	}
	
	@GetMapping(path="/edit/{id}")
	public ModelAndView editarOrgano(@PathVariable("id") long id){		
		ModelAndView result=new ModelAndView("EditBoard");	
		result.addObject("board", boardsService.findById(id));		
		return result;
	}
	
	
	@PostMapping(path="/save")
	public ModelAndView salvarOrgano(   @ModelAttribute("board")  Board organo){		
		boardsService.save(organo);
		ModelAndView result=listadoOrganos();		
		result.addObject("mensaje", "Board saved sucessfully!");		
		result.addObject("tipomensaje", "success");
		return result;
	}
	
	@GetMapping(path="/delete/{id}")
	public ModelAndView delteOrgano(@PathVariable("id") long id){		
		boardsService.delete(id);
		ModelAndView result=listadoOrganos();		
		result.addObject("mensaje", "Organo borrado con éxito!");		
		result.addObject("tipomensaje", "success");
		return result;
	}
	

}
