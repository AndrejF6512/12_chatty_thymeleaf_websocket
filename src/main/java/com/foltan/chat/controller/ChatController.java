package com.foltan.chat.controller;

import com.foltan.chat.component.WebSocketChatServer;
import com.foltan.chat.model.User;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

  @RequestMapping("/")
  public ModelAndView redirectToLogin(User user, ModelAndView modelAndView) {
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @RequestMapping("/chatroom")
  public ModelAndView redirectToChatRoom(@Valid User user, BindingResult result, ModelAndView modelAndView) {
    if (result.hasErrors()) {
      modelAndView.setViewName("login");
      return modelAndView;
    }
    System.out.println(user.getName() + " will be redirected to chat room");
    modelAndView.addObject("user", user);
    modelAndView.setViewName("chat");
    modelAndView.addObject("users", WebSocketChatServer.users);
    return modelAndView;
  }

}
