package co.and.mvvmroomandroidversion.asyntask;

import android.os.AsyncTask;

import co.and.mvvmroomandroidversion.interfaces.NoteDao;

public class DeleteAllNotesAsynTask extends AsyncTask<Void,Void,Void> {
    NoteDao noteDao;

    public DeleteAllNotesAsynTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        noteDao.deleteAllNotes();
        return null;
    }
}
