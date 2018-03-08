package com.example.sbarai.openkart;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class POChatActivity extends AppCompatActivity {

    String POid;
    String pathToChat = "appData/openOrders/prospectOrders/chat";
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pochat);
        POid = (String) getIntent().getExtras().get("POid");
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
        EditText editText = findViewById(R.id.edittext);
        String content = String.valueOf(editText.getText());
        String userId = FirebaseAuth.getInstance().getUid();
        POChatMessage message = new POChatMessage(userId,content);

        DatabaseReference referenceToChat = FirebaseManager.getRefToSpecificProspectOrder(POid).child("chat");
        referenceToChat.push().setValue(message);
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
                holder.sender.setText("Srujan");
                holder.content.setText(model.getMessageContent());
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
                scrollChatToEnd();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void scrollChatToEnd() {
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private class ChatHolder extends RecyclerView.ViewHolder {
        TextView sender;
        TextView content;
        public ChatHolder(View view) {
            super(view);
            sender = view.findViewById(R.id.sender);
            content = view.findViewById(R.id.content);
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
