package com.isunican.eventossantander.view.events;


import com.isunican.eventossantander.model.Event;

import java.util.List;

public interface IGestionarListas {

    boolean createList(String listName);

    boolean checkListExists(String listName);

}
