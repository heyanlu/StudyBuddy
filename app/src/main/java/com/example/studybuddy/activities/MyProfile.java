//package com.example.studybuddy.activities;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.fragment.app.Fragment;
//
//import com.example.studybuddy.R;
//
//public class MyProfile extends Fragment {
//
//    ImageButton editTopic, editTime, editDifficulty, editProfile;
//
//    @Nullable
//    @Override
//    public View onCreate(@NonNull LayoutInflater inflater,
//                         @Nullable ViewGroup container,
//                         @Nullable Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        EdgeToEdge.enable(this);
////        setContentView(R.layout.activity_my_profile);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
//
//        View view = inflater.inflate(R.layout.activity_my_profile, container, false);
//        editTopic = view.findViewById(R.id.editTopicPreference);
//
//        editTopic.setOnClickListener(v ->{
//
//        });
//
//        return view;
//    }
//}