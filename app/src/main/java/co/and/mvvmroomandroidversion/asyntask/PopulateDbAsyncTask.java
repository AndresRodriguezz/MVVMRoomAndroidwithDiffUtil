package co.and.mvvmroomandroidversion.asyntask;

import android.os.AsyncTask;

import co.and.mvvmroomandroidversion.NoteDataBase;
import co.and.mvvmroomandroidversion.interfaces.NoteDao;
import co.and.mvvmroomandroidversion.models.Note;

public class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
    private NoteDao noteDao;

    public PopulateDbAsyncTask(NoteDataBase db){
        noteDao = db.noteDao();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        noteDao.insert(new Note("Title 1", "Desciption 1",1));
        noteDao.insert(new Note("Title 2","Description 2", 2));
        noteDao.insert(new Note("Title 3", "Description 3",3));
        return null;
    }
}
