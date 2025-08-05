package com.example.sickslobby.Services;


import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ServicesInterface<T> {
    List <T> list();
    Boolean add(T post);
    Boolean edit(String id, T post) ;
    Boolean remove(String id);
}
