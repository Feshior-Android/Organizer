package org.itstep.organizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.snackbar.Snackbar;

import org.itstep.organizer.databinding.FragmentSecondBinding;
import org.itstep.organizer.model.Note;

public class SecondFragment extends Fragment {

    private static final String EXTRA_NOTE = "EXTRA_NOTE";

    private Note note;
    private EditText editText;
    private FragmentActivity activity;
    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1
        super.onCreate(savedInstanceState);

        //**
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.getMenuInflater().inflate(R.menu.menu_save, menu);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == android.R.id.home) {
                    Snackbar.make(requireView(), "Перехід до списку нотаток", Snackbar.LENGTH_LONG)
                            .setAction("Повідомлення", null).show();
                    return false;
                } else if (itemId == R.id.action_save) {
                    Snackbar.make(requireView(), "Нотатку успішно збережено", Snackbar.LENGTH_LONG)
                            .setAction("Повідомлення", null).show();
                    if (editText.getText().length() > 0) {
                        note.text = editText.getText().toString();
                        note.done = false;
                        note.timestamp = System.currentTimeMillis();
                        //
                        if (activity.getIntent().hasExtra(EXTRA_NOTE)) {
                            App.getInstance().getNoteDao().update(note);
                        } else {
                            App.getInstance().getNoteDao().insert(note);
                        }
                    }
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        // 2
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        activity = getActivity();

        // 3
        View view = binding.getRoot();
        editText = view.findViewById(R.id.text);

        // 4
        if (activity.getIntent().hasExtra(EXTRA_NOTE)) {
            note = activity.getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
        } else {
            note = new Note();
        }

        // 5
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}