package org.itstep.organizer.viewmodel;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.google.android.material.snackbar.Snackbar;

import org.itstep.organizer.App;
import org.itstep.organizer.R;
import org.itstep.organizer.model.Note;

import java.util.List;


public class ViewModelAdapter extends RecyclerView.Adapter<ViewModelAdapter.NoteViewHolder> {

    private final SortedList<Note> sortedList;

    public ViewModelAdapter() {
        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int position, int count) {
                notifyItemMoved(position, count);
            }

            @Override
            public int compare(Note n1, Note n2) {
                if (!n2.done && n1.done) {
                    return 1;
                }
                if (n2.done && !n1.done) {
                    return -1;
                }
                return (int) (n2.timestamp - n1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.id == item2.id;
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Note> notes) {
        sortedList.replaceAll(notes);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        //** private
        TextView noteText;
        CheckBox completed;
        View delete;
        Note note;
        boolean silentUpdate;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note_text);
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(view -> {
                FragmentActivity activity = (FragmentActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                Fragment navFragment =
                        fragmentManager.findFragmentById(R.id.nav_host_fragment_content_main);  // nav_host
                if (navFragment != null) {
                    NavHostFragment.findNavController(navFragment)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);  // action1
                } else {
                    Snackbar.make(view, "Фрагмент не знайдено", Snackbar.LENGTH_LONG)
                            .setAction("Помилка виконання", null).show();
                }
            });

            delete.setOnClickListener(view -> App.getInstance().getNoteDao().delete(note));

            completed.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!silentUpdate) {
                    note.done = isChecked;
                    App.getInstance().getNoteDao().update(note);
                }
                updateStrokeOut();
            });
        }

        public void bind(Note note) {
            this.note = note;
            noteText.setText(note.text);
            updateStrokeOut();
            //
            silentUpdate = true;
            completed.setChecked(note.done);
            silentUpdate = false;
        }

        private void updateStrokeOut() {
            if (note.done) {
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
