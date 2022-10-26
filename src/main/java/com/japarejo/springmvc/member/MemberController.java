package com.japarejo.springmvc.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.japarejo.springmvc.board.BoardService;

@Controller
@RequestMapping("/members")
public class MemberController<ParalamentarioSevice> {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BoardService boardService;
	
	@GetMapping
	public ModelAndView showMembers(){
		Iterable<Member> parlamentarios=memberService.findAll();
		ModelAndView result=new ModelAndView("MembersListing");
		result.addObject("members", parlamentarios);
		return result;
		
	}
	
	@GetMapping(path="/create")
	public ModelAndView crearteMember(){		
		ModelAndView result=new ModelAndView("EditMember");	
		result.addObject("member", new Member());
		result.addObject("allBoards", boardService.findAll());
		return result;
	}

	@PostMapping(path="/create")
	public ModelAndView saveNewMember(@ModelAttribute("member")  Member member) {
		memberService.save(member);
		ModelAndView result=showMembers();	
		result.addObject("message", "Member created sucessfully!");
		result.addObject("messageType", "sucess");
		return result;
	}
	
	@GetMapping(path="/edit/{id}")
	public ModelAndView editarParlamentario(@PathVariable("id") long id){		
		ModelAndView result=new ModelAndView("EditMember");	
		result.addObject("member", memberService.findById(id));
		result.addObject("allBoards", boardService.findAll());
		return result;
	}
	
	@PostMapping(path="/edit/{id}")
	public ModelAndView grabarParlamentario(@ModelAttribute("member")  Member member, @PathVariable("id") long id) {
		memberService.save(member);
		ModelAndView result=showMembers();	
		result.addObject("message", "Member sucessfully updated");
		result.addObject("menssageType", "sucess");
		return result;
	}
	
	@GetMapping(path="/delete/{id}")
	public ModelAndView borrarParlamentario(@PathVariable("id") long id){
		memberService.deleteById(id);
		ModelAndView result=showMembers();	
		result.addObject("message", "Member deleted sucessfully!");
		result.addObject("messageType", "sucess");
		return result;
	}
}
