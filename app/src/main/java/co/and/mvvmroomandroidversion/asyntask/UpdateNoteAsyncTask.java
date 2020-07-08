package co.and.mvvmroomandroidversion.asyntask;

import android.os.AsyncTask;

import co.and.mvvmroomandroidversion.interfaces.NoteDao;
import co.and.mvvmroomandroidversion.models.Note;

public class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void> {
    NoteDao noteDao;

    public UpdateNoteAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.update(notes[0]);
        return null;
    }
}
