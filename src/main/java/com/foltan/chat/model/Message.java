package com.foltan.chat.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

  private String userName;
  private String msg;
  private String type;
  private Integer onlineCount;
  private List<String> users;
}
