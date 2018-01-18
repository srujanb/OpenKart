package com.example.sbarai.openkart.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sbarai on 1/17/18.
 */

public class Collaborator {

    private User user;
    private List<CollaborationItem> collaborationItems;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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


}
