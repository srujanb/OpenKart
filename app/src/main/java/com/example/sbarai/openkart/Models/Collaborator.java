package com.example.sbarai.openkart.Models;

import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by sbarai on 1/17/18.
 */

public class Collaborator {

    private String userId;
    private List<CollaborationItem> collaborationItems;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CollaborationItem> getCollaborationItems() {
        if (collaborationItems == null)
            return Collections.emptyList();
        return collaborationItems;
    }

    public void setCollaborationItems(List<CollaborationItem> collaborationItems) {
        this.collaborationItems = collaborationItems;
    }

    public int getCollaborationItemsCount(){
        return getCollaborationItems().size();
    }

    public void addCollaborationItem(CollaborationItem item){
        try {
            getCollaborationItems().add(item);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
