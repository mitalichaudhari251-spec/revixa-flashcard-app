package com.example.revixa.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.revixa.data.local.entity.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCategory(CategoryEntity category);

    @Update
    void updateCategory(CategoryEntity category);

    @Delete
    void deleteCategory(CategoryEntity category);

    @Query("SELECT c.*, (SELECT COUNT(*) FROM cards WHERE category_id = c.id) AS card_count FROM categories c ORDER BY c.name ASC")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Query("SELECT c.*, (SELECT COUNT(*) FROM cards WHERE category_id = c.id) AS card_count FROM categories c ORDER BY c.name ASC")
    List<CategoryEntity> getAllCategoriesSync();

    @Query("SELECT * FROM categories WHERE id = :id")
    CategoryEntity getCategoryByIdSync(long id);

    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
    CategoryEntity getCategoryByName(String name);

    @Query("UPDATE categories SET card_count = (SELECT COUNT(*) FROM cards WHERE category_id = categories.id)")
    void refreshCardCounts();

    @Query("UPDATE categories SET card_count = (SELECT COUNT(*) FROM cards WHERE category_id = :categoryId) WHERE id = :categoryId")
    void refreshCardCountForCategory(long categoryId);

    @Query("SELECT COUNT(*) FROM categories")
    int getCategoryCount();

    @Query("DELETE FROM categories WHERE id = :id")
    void deleteById(long id);
}
