package com.example.studybuddy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.OpenAIClient;
import com.example.studybuddy.OpenAIResponse;
import com.example.studybuddy.R;

public class GenAIActivity extends AppCompatActivity {
//    private EditText userInput;
//    private TextView responseTextView;
//    private OpenAIClient openAIClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_genai);
//
//        ImageButton backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(v -> {
//            onBackPressed();
//        });
//
//        userInput = findViewById(R.id.userInput);
//        Button sendButton = findViewById(R.id.sendButton);
//        responseTextView = findViewById(R.id.responseTextView);
//
//        openAIClient = new OpenAIClient();
//
//        sendButton.setOnClickListener(v -> {
//            String inputText = userInput.getText().toString();
//            if (!inputText.isEmpty()) {
//                try {
//                    openAIClient.sendChatRequest(inputText, new OpenAIResponse() {
//                        @Override
//                        public void onSuccess(String content) {
//                            runOnUiThread(() -> responseTextView.setText(content));
//                        }
//
//                        @Override
//                        public void onError(String errorMessage) {
//                            runOnUiThread(() -> responseTextView.setText(errorMessage));
//                        }
//                    });
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
}
