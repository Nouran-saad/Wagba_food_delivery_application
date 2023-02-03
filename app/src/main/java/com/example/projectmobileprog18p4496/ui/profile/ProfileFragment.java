package com.example.projectmobileprog18p4496.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectmobileprog18p4496.LoginActivity;
import com.example.projectmobileprog18p4496.User;
import com.example.projectmobileprog18p4496.UserRoomDatebase;
import com.example.projectmobileprog18p4496.UserViewModel;
import com.example.projectmobileprog18p4496.databinding.FragmentProfileBinding;

import java.util.List;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    EditText username,gender,birthdate;
    TextView user,birth_value,genderr;
    Button add;

    private UserViewModel mUserViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // HistoryViewModel galleryViewModel =
        //    new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        birthdate=binding.birthdate;
        gender=binding.gender;
        username=binding.username;
        add=binding.btnAdd;

        user=binding.user;
        birth_value=binding.birth;
        genderr=binding.genderr;



        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mUserViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {

                for (int i = 0; i < users.size(); i++) {
                    user.setText(users.get(i).getUserName());
                    genderr.setText(users.get(i).getGender());
                    birth_value.setText(users.get(i).getBirthDate());
                }
            }
            });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name_val= username.getText().toString();
                String Gender_val= gender.getText().toString();
                String birth_val= birthdate.getText().toString();
                if (Name_val.isEmpty() || Gender_val.isEmpty() ||birth_val.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all your info", Toast.LENGTH_SHORT).show();

                }
                else{
                   // mUserViewModel.delete();
                    mUserViewModel.insert(
                        new User(Name_val,Gender_val,birth_val)
                );}

            }
        });



        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
