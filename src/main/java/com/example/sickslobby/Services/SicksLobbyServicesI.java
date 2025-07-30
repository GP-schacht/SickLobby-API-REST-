package com.example.sickslobby.Services;

import com.example.sickslobby.PostDTO;

import java.util.List;

public interface SicksLobbyServicesI {
    List <PostDTO> list();

    Boolean add(PostDTO post);
    Boolean edit(String id, PostDTO post);
    Boolean remove(String id);
}
