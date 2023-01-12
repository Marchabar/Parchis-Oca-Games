package com.ling1.springmvc;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.user.UserStatusEnum;
import com.ling1.springmvc.user.UserStatusFormatter;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserStatusFormatterTest {

    @Mock
    UserService us;

    UserStatusFormatter usf;

    UserStatusEnum userStatus1;
    UserStatusEnum userStatus2;

    @BeforeEach
    void setup() {
        usf = new UserStatusFormatter(us);
        userStatus1 = new UserStatusEnum();
        userStatus1.setName("ONLINE");
        userStatus1.setId(1);

        userStatus2 = new UserStatusEnum();
        userStatus2.setName("OFFLINE");
        userStatus2.setId(2);
    }

    @Test
    void testPrint() {
        assertEquals("ONLINE", usf.print(userStatus1, null));
        assertEquals("OFFLINE", usf.print(userStatus2, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ONLINE", "OFFLINE"})
    void testParseSuccess(String input) throws Exception {
        given(us.findStatus()).willReturn(Lists.newArrayList(userStatus1, userStatus2));
        
        UserStatusEnum use = usf.parse(input, null);
        assertNotNull(use);
    }

    @Test
    void testParseCorrect() throws Exception {
        given(us.findStatus()).willReturn(Lists.newArrayList(userStatus1, userStatus2));

        UserStatusEnum use = usf.parse("ONLINE", null);
        assertEquals("ONLINE", use.getName());
        assert(1 == use.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"BUSY", "AWAY"}) 
    void testParseFail(String input) {
        given(us.findStatus()).willReturn(Lists.newArrayList(userStatus1, userStatus2));
        
        assertThrows(ParseException.class, () -> {
            usf.parse(input, null);
        });
    }
    
}
