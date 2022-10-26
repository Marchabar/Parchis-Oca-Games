package com.japarejo.springmvc.room;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    public static final String ROOMS_LISTING="RoomsListing";
    public static final String ROOM_EDIT="EditRoom";

    @Autowired
    RoomService roomService;
    
     @GetMapping
     public ModelAndView showRoomsListing() {
         ModelAndView result=new ModelAndView(ROOMS_LISTING);
         result.addObject("rooms",roomService.getAllRooms());
         return result;
     }
     
     @GetMapping("/delete/{id}")
     public ModelAndView deleteRoom(@PathVariable("id") int id) {
         roomService.deleteRoom(id);
         ModelAndView result=showRoomsListing();
         result.addObject("message", "Room removed successfully");
         return result;
     }
     
     
     @GetMapping("/edit/{id}")
     public ModelAndView editRoom(@PathVariable("id") int id) {
         ModelAndView result=new ModelAndView(ROOM_EDIT);
         Room room=roomService.getRoomById(id);
         if(room!=null)
             result.addObject("room", room);
         else
             result=showRoomsListing();                          
         return result;
     }
     
     @PostMapping("/edit/{id}")
     public ModelAndView editRoom(@PathVariable("id") int id, @Valid Room room,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(ROOM_EDIT);
             result.addAllObjects(br.getModel());         
         }else {
             Room roomToUpdate=roomService.getRoomById(id);
             
             if(roomToUpdate!=null) {
                 BeanUtils.copyProperties(room, roomToUpdate,"id");                 
                 roomService.save(roomToUpdate);
                 result=showRoomsListing();
                 result.addObject("message", "Room saved succesfully!");
             }else {
                 result=showRoomsListing();             
                 result.addObject("message", "Room with id "+id+" not found!");
             }
         }                                                
         return result;
     }
     
     @GetMapping("/create")
     public ModelAndView createRoom() {
         ModelAndView result=new ModelAndView(ROOM_EDIT);
         Room room=new Room();         
         result.addObject("room", room);                                  
         return result;
     }
     
     
     @PostMapping("/create")
     public ModelAndView saveNewRoom(@Valid Room room,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(ROOM_EDIT);
             result.addAllObjects(br.getModel());         
         }else {                          
             roomService.save(room);
             result=showRoomsListing();
             result.addObject("message", "Room saved succesfully!");             
         }                                                
         return result;
     }
     
     
}
