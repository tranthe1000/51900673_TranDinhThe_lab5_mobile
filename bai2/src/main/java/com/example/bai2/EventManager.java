package com.example.bai2;

import java.util.ArrayList;

public class EventManager {
    private static ArrayList<Event> events = new ArrayList<>();

    private EventManager() {
    }

    private volatile static EventManager uniqueInstance;

    public static EventManager getInstance() {
        if (uniqueInstance == null) {
            synchronized (EventManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EventManager();
                }
            }
        }
        return uniqueInstance;
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    public void removeEvent(int i) {
        this.events.remove(i);
    }

    public ArrayList<Event> getAllEvents() {
        return this.events;
    }

    public void removeAllEvents() {
        this.events.clear();
    }

    public int getCount() {
        if (events != null) {
            return this.events.size();
        }
        return -1;
    }
}