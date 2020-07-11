package co.and.mvvmroomandroidversion.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.and.mvvmroomandroidversion.R;
import co.and.mvvmroomandroidversion.adapters.NoteAdapter;
import co.and.mvvmroomandroidversion.interfaces.OnItemClickListener;
import co.and.mvvmroomandroidversion.models.Note;
import co.and.mvvmroomandroidversion.viewmodel.NoteViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.buttonAddNote);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this,AddEditNoteActivity.class);
                startActivityForResult(mIntent,ADD_NOTE_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnclickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this,AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY,note.getPriority());

                startActivityForResult(intent,EDIT_NOTE_REQUEST);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){

         String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
         String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
         int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,1);

         Note note = new Note(title,description,priority);
         noteViewModel.insert(note);

         Toast.makeText(this, "Note Saved" + title + description + priority, Toast.LENGTH_SHORT).show();

     }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
         int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);

         if(id == -1){
             Toast.makeText(this, "Note Can't updated", Toast.LENGTH_SHORT).show();
         }

         String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
         String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
         int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,1);

         Note note = new Note(title,description,priority);
         note.setId(id);
         noteViewModel.update(note);

     }else{
         Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();

     }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAllNotes:
                noteViewModel.deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}