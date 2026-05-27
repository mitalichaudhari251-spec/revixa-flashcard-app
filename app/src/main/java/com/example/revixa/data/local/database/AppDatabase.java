package com.example.revixa.data.local.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.revixa.data.local.dao.CardDao;
import com.example.revixa.data.local.dao.CategoryDao;
import com.example.revixa.data.local.entity.CardEntity;
import com.example.revixa.data.local.entity.CategoryEntity;
import com.example.revixa.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {CardEntity.class, CategoryEntity.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
        Executors.newFixedThreadPool(4);

    public abstract CardDao cardDao();
    public abstract CategoryDao categoryDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        Constants.DATABASE_NAME
                    )
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            databaseWriteExecutor.execute(() -> {
                                AppDatabase database = INSTANCE;
                                if (database != null) {
                                    seedDefaultCategories(database.categoryDao());
                                }
                            });
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void seedDefaultCategories(CategoryDao categoryDao) {
        String[][] defaults = {
            {"Physics",      "#5C6BC0", "⚛️"},
            {"Chemistry",    "#26A69A", "🧪"},
            {"Mathematics",  "#EF7C00", "📐"},
            {"Biology",      "#E53935", "🧬"},
            {"Computer Sci", "#7B1FA2", "💻"},
            {"History",      "#6D4C41", "📜"},
            {"Geography",    "#2E7D32", "🌍"},
            {"Literature",   "#AD1457", "📖"},
            {"Economics",    "#0277BD", "📊"},
            {"General",      "#546E7A", "📚"}
        };
        for (String[] row : defaults) {
            CategoryEntity cat = new CategoryEntity(row[0], row[1], row[2]);
            categoryDao.insertCategory(cat);
        }
    }
}
