package com.example.studybuddy.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.OpenAIClient;
import com.example.studybuddy.OpenAIResponse;
import com.example.studybuddy.R;

import java.text.BreakIterator;

public class GenAIActivity extends AppCompatActivity {
    private EditText userInput;
    private TextView responseTextView;
    private OpenAIClient openAIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genai);

        userInput = findViewById(R.id.userInput);
        Button sendButton = findViewById(R.id.sendButton);
        responseTextView = findViewById(R.id.responseTextView);

        openAIClient = new OpenAIClient();

        sendButton.setOnClickListener(v -> {
            String inputText = userInput.getText().toString();
            if (!inputText.isEmpty()) {
                try {
                    openAIClient.sendChatRequest(inputText, new OpenAIResponse() {
                        @Override
                        public void onSuccess(String content) {
                            runOnUiThread(() -> responseTextView.setText(content));
                        }

                        @Override
                        public void onError(String errorMessage) {
                            runOnUiThread(() -> responseTextView.setText(errorMessage));
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}