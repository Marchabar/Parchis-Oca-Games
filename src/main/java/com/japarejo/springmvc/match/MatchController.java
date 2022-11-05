package com.japarejo.springmvc.match;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/matches")
public class MatchController {

    public static final String MATCHES_LISTING = "MatchesListing";
    public static final String MATCH_EDIT = "EditMatch";

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ModelAndView showMatchesListing(){
        ModelAndView result= new ModelAndView(MATCHES_LISTING);
        result.addObject("matches", matchService.findAll());
        return result;
    }
    
    /*@GetMapping("/create")
     public ModelAndView createMatch() {
         ModelAndView result=new ModelAndView(MATCH_EDIT);        
         result.addObject("match",new Match());   
         return result;
     }
     
     
     @PostMapping("/create")
     public ModelAndView saveNewMatch(@Valid Match match,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(MATCH_EDIT);
             result.addAllObjects(br.getModel());         
         }else {                          
             matchService.save(match);
             result=showMatchesListing();
             result.addObject("message", "Match saved succesfully!");             
         }                                                
         return result;
     }*/

    
}
