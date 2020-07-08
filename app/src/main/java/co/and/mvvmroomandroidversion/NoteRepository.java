package co.and.mvvmroomandroidversion;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import co.and.mvvmroomandroidversion.asyntask.DeleteAllNotesAsynTask;
import co.and.mvvmroomandroidversion.asyntask.DeleteNoteAsynTask;
import co.and.mvvmroomandroidversion.asyntask.InsertNoteAsynTask;
import co.and.mvvmroomandroidversion.asyntask.UpdateNoteAsyncTask;
import co.and.mvvmroomandroidversion.interfaces.NoteDao;
import co.and.mvvmroomandroidversion.models.Note;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAllNotes();

    }
    public void insert (Note note){
        new InsertNoteAsynTask(noteDao).execute(note);

    }
    public void update (Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }
    public void delete (Note note){
        new DeleteNoteAsynTask(noteDao).execute(note);

    }

    public void deleteAllNotes (){
        new DeleteAllNotesAsynTask(noteDao).execute();

    }
}
