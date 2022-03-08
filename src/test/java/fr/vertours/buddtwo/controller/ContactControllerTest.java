package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.AddFriendDTO;
import fr.vertours.buddtwo.exception.EmailNotPresentInApplicationException;
import fr.vertours.buddtwo.exception.EmailNotPresentInFriendsException;
import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.service.ContactUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static fr.vertours.buddtwo.UtilUnitTestMethods.getMyUserDetailsOfJean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContactControllerTest {

    private final ContactUserService userService =
            mock(ContactUserService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);

    private final ContactController classUnderTest =
            new ContactController(userService);

    @Test
    void showProfilePage() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        ModelAndView mvActual = classUnderTest.showProfilePage(mud);
        assertEquals("contact", mvActual.getViewName());
    }

    @Test
    void showAddFriendForm() {
        ModelAndView mvActual = classUnderTest.showAddFriendForm();
        assertEquals("addFriend", mvActual.getViewName());
    }

    @Test
    void showDelFriendForm() {
        ModelAndView mvActual = classUnderTest.showDelFriendForm();
        assertEquals("delFriend", mvActual.getViewName());
    }

    @Test
    void submitAddFriendForm() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddFriendDTO dto = new AddFriendDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing()
                .when(userService)
                .addFriendByEmail(mud.getUsername(), dto.getFriendEmail());

         classUnderTest.submitAddFriendForm(
                 new AddFriendDTO(), bindingResult, mud);

        verify(userService, times(1))
                .addFriendByEmail(mud.getUsername(), dto.getFriendEmail());
    }
    @Test
    void submitAddFriendFormBindingErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddFriendDTO dto = new AddFriendDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        doNothing()
                .when(userService)
                .addFriendByEmail(mud.getUsername(), dto.getFriendEmail());

        ModelAndView mvActual =
                classUnderTest.submitAddFriendForm(
                new AddFriendDTO(), bindingResult, mud);

        assertEquals("addFriend", mvActual.getViewName());
    }
    @Test
    void submitAddFriendFormErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddFriendDTO dto = new AddFriendDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(EmailNotPresentInApplicationException.class)
                .when(userService)
                .addFriendByEmail(mud.getUsername(), dto.getFriendEmail());

        ModelAndView mvActual =
                classUnderTest.submitAddFriendForm(
                new AddFriendDTO(), bindingResult, mud);

        assertEquals("addFriend", mvActual.getViewName());
    }

    @Test
    void submitDelFriendForm() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddFriendDTO dto = new AddFriendDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing()
                .when(userService)
                .delFriendByEmail(mud.getUsername(), dto.getFriendEmail());

        classUnderTest.submitDelFriendForm(
                new AddFriendDTO(), bindingResult, mud);

        verify(userService, times(1))
                .delFriendByEmail(mud.getUsername(), dto.getFriendEmail());
    }

    @Test
    void submitdelFriendFormBindingErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddFriendDTO dto = new AddFriendDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        doNothing()
                .when(userService)
                .delFriendByEmail(mud.getUsername(), dto.getFriendEmail());

        ModelAndView mvActual =
                classUnderTest.submitDelFriendForm(
                        new AddFriendDTO(), bindingResult, mud);

        assertEquals("delFriend", mvActual.getViewName());
    }
    @Test
    void submitDelFriendFormErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddFriendDTO dto = new AddFriendDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(EmailNotPresentInFriendsException.class)
                .when(userService)
                .delFriendByEmail(mud.getUsername(), dto.getFriendEmail());

        ModelAndView mvActual =
                classUnderTest.submitDelFriendForm(
                        new AddFriendDTO(), bindingResult, mud);

        assertEquals("delFriend", mvActual.getViewName());
    }
}