package com.foltan.chat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foltan.chat.component.WebSocketChatServer;

@RestController
@RequestMapping("/api")
public class ChattingApiController {

	@GetMapping("/onlineUsers")
	public ResponseEntity<List<String>> getMessage()  {
		return ResponseEntity.ok(new ArrayList<String>(WebSocketChatServer.users.values()));
	}
	
}
