package com.isunican.eventossantander.view.events;


import com.isunican.eventossantander.model.Event;

import java.util.List;

public interface IGestionarListas {

    String createList(String listName);

    boolean checkListExists(String listName);

}
