package org.itstep.organizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.itstep.organizer.databinding.FragmentFirstBinding;
import org.itstep.organizer.viewmodel.MainViewModel;
import org.itstep.organizer.viewmodel.ViewModelAdapter;


public class FirstFragment extends Fragment {
    //*
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.getRoot();

        // 2
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 3
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));

        // 4
        final ViewModelAdapter adapter = new ViewModelAdapter();
        recyclerView.setAdapter(adapter);

        // 5
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getNoteLiveData().observe(getViewLifecycleOwner(), adapter::setItems);

        // 6
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}