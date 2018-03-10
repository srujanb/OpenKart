package com.example.sbarai.openkart;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sbarai.openkart.Models.POChatMessage;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.IllegalFormatCodePointException;

public class POChatActivity extends AppCompatActivity {

    String POid;
    String currentUserId;
    String pathToChat = "appData/openOrders/prospectOrders/chat";
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pochat);
        POid = (String) getIntent().getExtras().get("POid");
        currentUserId = FirebaseAuth.getInstance().getUid();
        setRecyclerView();
        initSendMessage();

    }

    private void initSendMessage() {
        View view = findViewById(R.id.send_message);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        final EditText editText = findViewById(R.id.edittext);
        String content = String.valueOf(editText.getText());
        String userId = FirebaseAuth.getInstance().getUid();
        POChatMessage message = new POChatMessage(userId,content);

        DatabaseReference referenceToChat = FirebaseManager.getRefToSpecificProspectOrder(POid).child("chat");
        referenceToChat.push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                editText.setText("");
            }
        });
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.rv_chat);

        Query query = FirebaseManager
                .getRefToSpecificProspectOrder(POid)
                .child("chat");

        FirebaseRecyclerOptions<POChatMessage> options =
                new FirebaseRecyclerOptions.Builder<POChatMessage>()
                        .setQuery(query, POChatMessage.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<POChatMessage, ChatHolder>(options) {
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_chat_message, parent, false);

                return new ChatHolder(view);
            }

            @Override
            protected void onBindViewHolder(ChatHolder holder, int position, POChatMessage model) {
                // Bind the Chat object to the ChatHolder
                // ...
                holder.messageObject = model;
                if (model.getUserId().equals(currentUserId)){
                    try {
                        setSentMessage(holder, model);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try {
                        setReceivedMessage(holder, model);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onViewDetachedFromWindow(ChatHolder holder) {
                super.onViewDetachedFromWindow(holder);
                FirebaseManager.getRefToUserName(holder.messageObject.getUserId()).removeEventListener(holder.sentMessageListener);
                FirebaseManager.getRefToUserName(holder.messageObject.getUserId()).removeEventListener(holder.receivedMessageListener);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                scrollChatToEnd();
            }
        };

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//                scrollChatToEnd();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setSentMessage(final ChatHolder holder, POChatMessage model) {
        hideReceivedMessage(holder);
        showSentMessageAndClearValues(holder);
        holder.sentContent.setText(model.getMessageContent());
        FirebaseManager.getRefToUserName(model.getUserId()).addListenerForSingleValueEvent(holder.sentMessageListener);
    }

    private void setReceivedMessage(ChatHolder holder, POChatMessage model) {
        hideSentMessage(holder);
        showReceivedMessageAndClearValues(holder);
        holder.receivedContent.setText(model.getMessageContent());
        FirebaseManager.getRefToUserName(model.getUserId()).addListenerForSingleValueEvent(holder.receivedMessageListener);
    }

    private void hideReceivedMessage(ChatHolder holder) {
        holder.receivingMessageView.setVisibility(View.INVISIBLE);
    }

    private void hideSentMessage(ChatHolder holder) {
        holder.sendingMessageView.setVisibility(View.INVISIBLE);
    }

    private void showSentMessageAndClearValues(ChatHolder holder) {
        holder.sendingMessageView.setVisibility(View.VISIBLE);
        holder.sentSender.setText("");
        holder.sentContent.setText("something");
    }

    private void showReceivedMessageAndClearValues(ChatHolder holder) {
        holder.receivingMessageView.setVisibility(View.VISIBLE);
        holder.receivedSender.setText("");
        holder.receivedContent.setText("something");
    }

    private void scrollChatToEnd() {
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private class ChatHolder extends RecyclerView.ViewHolder {
        View receivingMessageView;
        View sendingMessageView;
        TextView sentSender;
        TextView sentContent;
        TextView receivedSender;
        TextView receivedContent;
        POChatMessage messageObject;

        ValueEventListener sentMessageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sentSender.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                sentSender.setText("[Error loading name]");
            }
        };

        ValueEventListener receivedMessageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                receivedSender.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                receivedContent.setText("[Error loading name]");
            }
        };

        public ChatHolder(View view) {
            super(view);
            receivingMessageView = view.findViewById(R.id.type_received);
            sendingMessageView = view.findViewById(R.id.type_sent);
            sentSender = view.findViewById(R.id.sent_sender);
            sentContent = view.findViewById(R.id.sent_content);
            receivedSender = view.findViewById(R.id.received_sender);
            receivedContent = view.findViewById(R.id.received_content);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
