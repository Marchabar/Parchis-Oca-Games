package com.ling1.springmvc;

import com.ling1.springmvc.chat.MessageChat;
import com.ling1.springmvc.chat.MessageChatRepository;
import com.ling1.springmvc.chat.MessageChatService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestMessageChatService {

    @Autowired
    MessageChatService messageChatService;

    @Autowired
    UserService userService;

    @MockBean
    SessionRegistry sr;

    @Test
    void testFindAll(){
        List<MessageChat> messageChats = messageChatService.findAll();
        assertEquals(messageChats.size(),3);
    }
    @Test
    void testFindByMatch(){

        List<MessageChat> allMessageChats = messageChatService.findAll();
        List<MessageChat> messageChatsMatchA = messageChatService.findByMatch(1);
        List<MessageChat> messageChatsMatchB = messageChatService.findByMatch(5);

        // check if working proberly
        assertNotEquals(null,allMessageChats);
        assertNotEquals(null,messageChatsMatchA);
        assertNotEquals(null,messageChatsMatchB);

        short countA = (short) allMessageChats.stream().filter(mm -> mm.getMatch().getId()==1).count();
        short countB = (short) allMessageChats.stream().filter(mm ->  mm.getMatch().getId()==5).count();

        assertEquals(messageChatsMatchA.size(),countA);
        assertEquals(messageChatsMatchB.size(),countB);
    }
    @Test
    void ntestFindByMatchNotFound(){

        List<MessageChat> messageChatsMatchA = messageChatService.findByMatch(9999);
        List<MessageChat> messageChatsMatchB = messageChatService.findByMatch(8888);

        // returns an empty list if messageChat with ID not found
        assertEquals(new ArrayList<>(),messageChatsMatchA);
        assertEquals(new ArrayList<>(),messageChatsMatchB);

    }
    @Test
    void testFindByMatchCheckDescription(){
        List<MessageChat> messageChats = messageChatService.findByMatch(1);

        assertNotEquals(null,messageChats);

        List<String> descriptions = messageChats.stream().map(mm -> mm.getDescription()).collect(Collectors.toList());
        List<String> targetDescriptions = Lists.newArrayList("GG guys","well played");

        assertTrue(targetDescriptions.containsAll(descriptions),
                "Expected list: "+targetDescriptions.toString() +" || Actual list: "+ descriptions.toString());

    }
    @Test
    void testFindMatchById(){
        Match match = messageChatService.findMatchById(1);
        assertNotEquals(null,match);

        assertEquals(match.getId(),1);

    }
    @Test
    void ntestFindMatchByIdNotFound(){
        Match match = messageChatService.findMatchById(99);
        assertEquals(null,match);

    }
    @Test
    void testSave(){
        MessageChat chat = new MessageChat();
        chat.setId(6);
        chat.setTime("15:56:12");
        User user = userService.getUserById(1);
        assertNotEquals(null,user);
        chat.setUser(user);
        chat.setDescription("this is my chat");
        Match match = messageChatService.findMatchById(1);
        assertNotEquals(null,match);
        chat.setMatch(match);

        List<MessageChat> allMessageChats = messageChatService.findAll();

        messageChatService.save(chat);

        List<MessageChat> allMessageChatsAfterSave = messageChatService.findAll();
        String description = allMessageChatsAfterSave.get(allMessageChatsAfterSave.size()-1).getDescription();

        assertEquals(allMessageChats.size(),allMessageChatsAfterSave.size()-1);
        assertEquals(description,allMessageChatsAfterSave.get(allMessageChatsAfterSave.size()-1).getDescription());

    }
}
