package com.example.zad3;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskListFragment extends Fragment {
    private static Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private TaskAdapter adapter;
    private boolean subtitleVisible = false;
    public static final String KEY_EXTRA_TASK_ID = "extra_task_id";

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.new_task:
                Task task = new Task();
                TaskStorage.getInstance().addTask(task);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(TaskListFragment.KEY_EXTRA_TASK_ID, task.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                subtitleVisible = !subtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);

        if(subtitleVisible){
            updateSubtitle();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
        updateSubtitle();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_task_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void updateSubtitle(){
        long tasksNotDone = TaskStorage.getInstance().getTasks().stream().filter(x -> !x.isDone()).count();
        String s = getString(R.string.subtitle_format, tasksNotDone);
        AppCompatActivity a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setSubtitle(subtitleVisible ? s : null);
    }


    private void updateView() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();

        if (adapter == null) {
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView, dateTextView;
        private CheckBox doneCheckBox;
        private ImageView categoryImageView;
        private Task task;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            nameTextView = itemView.findViewById(R.id.task_item_name);
            dateTextView = itemView.findViewById(R.id.task_item_date);
            dateTextView.setSelected(true);
            doneCheckBox = itemView.findViewById(R.id.task_item_done);

            categoryImageView = itemView.findViewById(R.id.task_item_category);
        }

        public void bind(Task task) {
            this.task = task;

            nameTextView.setText(task.getName());
            nameTextView.setPaintFlags(task.isDone()
                    ? nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                    : nameTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            dateTextView.setText(formatter.format(task.getDate()));
            doneCheckBox.setChecked(task.isDone());

            doneCheckBox.setOnCheckedChangeListener(
                    (e, isChecked) -> {
                        task.setDone(isChecked);
                        nameTextView.setPaintFlags(task.isDone()
                                ? nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                                : nameTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        updateSubtitle();
                    });
            categoryImageView.setImageResource(task.getCategory() == Category.HOME ? R.drawable.ic_home : R.drawable.ic_studies) ;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
        }

        public CheckBox getCheckbox(){
            return doneCheckBox;
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = tasks.get(position);
            holder.bind(task);

            CheckBox checkBox = holder.getCheckbox();

            checkBox.setChecked(tasks.get(position).isDone());
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }
}
