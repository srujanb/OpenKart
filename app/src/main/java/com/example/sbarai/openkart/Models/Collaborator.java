package com.example.sbarai.openkart.Models;

import android.widget.Toast;

import com.google.firebase.database.Exclude;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sbarai on 1/17/18.
 */

public class Collaborator {

    private String userId;

    private HashMap<String,CollaborationItem> collaborationItems;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, CollaborationItem> getCollaborationItems() {
        return collaborationItems;
    }

    public void setCollaborationItems(HashMap<String, CollaborationItem> collaborationItems) {
        this.collaborationItems = collaborationItems;
    }

    @Exclude
    public int getCollaborationItemsCount(){
        return getCollaborationItems().size();
    }

    public void addCollaborationItem(CollaborationItem item){
        if (collaborationItems == null)
            collaborationItems = new HashMap<>();
        if (collaborationItems.containsKey(item.getItemLink())){
            CollaborationItem oldItem = collaborationItems.get(item.getItemLink());
            item.setCount(item.getCount() + oldItem.getCount());
        }
        collaborationItems.put(item.getItemLink(),item);
    }


}
