package com.example.projectmobileprog18p4496.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobileprog18p4496.MenuAdapter;
import com.example.projectmobileprog18p4496.MenuModel;
import com.example.projectmobileprog18p4496.RestaurantAdapter;
import com.example.projectmobileprog18p4496.RestaurantModel;
import com.example.projectmobileprog18p4496.UpdateMenu;
import com.example.projectmobileprog18p4496.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateMenu {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView,recyclerViewMenu;
    EditText searchBar;
    RestaurantAdapter restaurantsAdapter;
    ArrayList<RestaurantModel> RestaurantModels=new ArrayList<>();
    ArrayList<MenuModel> MenuModels=new ArrayList<>();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/").child("Restaurants");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recycler;
        recyclerViewMenu = binding.recyclerMenu;
        searchBar = binding.search;




        restaurantsAdapter = new RestaurantAdapter(this, RestaurantModels);
        MenuAdapter menuAdapter = new MenuAdapter(MenuModels);

        recyclerView.setAdapter(restaurantsAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearProductList();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RestaurantModel model = dataSnapshot.getValue(RestaurantModel.class);
                    RestaurantModels.add(model);}
                restaurantsAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);



        recyclerViewMenu.setAdapter(menuAdapter);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
searchBar.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        filter(editable.toString());
    }
});
        return root;
    }
    public void filter (String text){
        ArrayList<RestaurantModel> filteredList=new ArrayList<>();
        for (RestaurantModel item: RestaurantModels){
            if (item.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        restaurantsAdapter.filterList(filteredList);
    }
    public void clearProductList(){
        RestaurantModels.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void Call_Menu(String position, ArrayList<MenuModel> list) {
         MenuAdapter menuAdapter = new MenuAdapter(list);
         menuAdapter.notifyDataSetChanged();
         recyclerViewMenu.setAdapter(menuAdapter);

    }

}