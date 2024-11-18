package com.example.studybuddy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.Message;
import com.example.studybuddy.MessageAdapter;
import com.example.studybuddy.OpenAIClient;
import com.example.studybuddy.OpenAIResponse;
import com.example.studybuddy.R;

import java.util.ArrayList;
import java.util.List;

public class GenAIActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;

    MessageAdapter messageAdapter;
    private OpenAIClient openAIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genai);
        messageList = new ArrayList<>();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_button);

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//        userInput = findViewById(R.i.userInput);
//        Button sendButton = findViewById(R.id.sendButton);
//        responseTextView = findViewById(R.id.responseTextView);

        openAIClient = new OpenAIClient();

        sendButton.setOnClickListener(v -> {
            String inputText = messageEditText.getText().toString().trim();
            addMessage(inputText, Message.SENT_BY_USER);
            messageEditText.setText("");
            welcomeTextView.setVisibility(View.GONE);

            if (!inputText.isEmpty()) {
                try {
                    openAIClient.sendChatRequest(inputText, new OpenAIResponse() {
                        @Override
                        public void onSuccess(String content) {
//                            runOnUiThread(() -> responseTextView.setText(content));
                            addMessage(content, Message.SENT_BY_BOT);
                        }

                        @Override
                        public void onError(String errorMessage) {
//                            runOnUiThread(() -> responseTextView.setText(errorMessage));
                            addMessage(errorMessage, Message.SENT_BY_BOT);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void addMessage(String message, String sentBy) {
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());

        });

    }
}
