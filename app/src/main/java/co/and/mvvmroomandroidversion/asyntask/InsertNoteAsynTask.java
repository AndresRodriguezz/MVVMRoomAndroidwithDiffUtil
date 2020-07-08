package co.and.mvvmroomandroidversion.asyntask;

import android.os.AsyncTask;

import co.and.mvvmroomandroidversion.interfaces.NoteDao;
import co.and.mvvmroomandroidversion.models.Note;

public class InsertNoteAsynTask extends AsyncTask<Note,Void,Void> {
    private NoteDao noteDao;

    public InsertNoteAsynTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.insert(notes[0]);
        return null;
    }
}
