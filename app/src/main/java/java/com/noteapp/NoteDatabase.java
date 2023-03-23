package java.com.noteapp;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase{
    static NoteDatabase instance;

    //Room will implement this method
    abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, NoteDatabase.class, "note_databse")
                    .addCallback(roomCallBack)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    //populating notes in our db using callback
    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    //performing insert operation in the background thread
    private static class populateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        NoteDao noteDao;

        private populateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1", "Description1",1));
            noteDao.insert(new Note("Title2", "Description2",2));
            noteDao.insert(new Note("Title3", "Description3",3));
            return null;
        }
    }

}
