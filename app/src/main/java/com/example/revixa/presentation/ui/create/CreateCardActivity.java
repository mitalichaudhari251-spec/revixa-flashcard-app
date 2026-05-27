package com.example.revixa.presentation.ui.create;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.revixa.R;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CreateCardActivity extends AppCompatActivity {

    private CreateCardViewModel viewModel;

    private Spinner   spinnerCategory, spinnerType;
    private EditText  etTitle, etQuestion, etAnswer, etTags;
    private RadioGroup rgPriority, rgDifficulty;
    private Button    btnSave, btnGenerate, btnCancel;
    private TextView  tvCharCountQ, tvCharCountA, tvScreenTitle;

    private List<Category> categoryList = new ArrayList<>();
    private boolean editMode   = false;
    private long    editCardId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        viewModel  = new ViewModelProvider(this).get(CreateCardViewModel.class);
        editMode   = getIntent().getBooleanExtra(Constants.EXTRA_EDIT_MODE, false);
        editCardId = getIntent().getLongExtra(Constants.EXTRA_CARD_ID, -1);

        bindViews();
        setupCategorySpinner();
        setupTypeSpinner();
        setupListeners();
        observeViewModel();

        if (editMode && editCardId > 0) loadCardForEdit();
    }

    private void bindViews() {
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerType     = findViewById(R.id.spinner_type);
        etTitle         = findViewById(R.id.et_title);
        etQuestion      = findViewById(R.id.et_question);
        etAnswer        = findViewById(R.id.et_answer);
        etTags          = findViewById(R.id.et_tags);
        rgPriority      = findViewById(R.id.rg_priority);
        rgDifficulty    = findViewById(R.id.rg_difficulty);
        btnSave         = findViewById(R.id.btn_save);
        btnGenerate     = findViewById(R.id.btn_generate);
        btnCancel       = findViewById(R.id.btn_cancel);
        tvCharCountQ    = findViewById(R.id.tv_char_count_q);
        tvCharCountA    = findViewById(R.id.tv_char_count_a);
        tvScreenTitle   = findViewById(R.id.tv_screen_title);

        if (editMode) {
            btnSave.setText("UPDATE CARD");
            tvScreenTitle.setText("Edit Card");
        }
    }

    private void setupCategorySpinner() {
        viewModel.getCategories().observe(this, categories -> {
            if (categories == null) return;
            categoryList = categories;
            List<String> names = new ArrayList<>();
            for (Category c : categories) {
                names.add(c.getIcon() + " " + c.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);
        });
    }

    private void setupTypeSpinner() {
        String[] types = {
            Constants.TYPE_DEFINITION,
            Constants.TYPE_FORMULA,
            Constants.TYPE_FACT,
            Constants.TYPE_THEOREM,
            Constants.TYPE_CASE_STUDY,
            Constants.TYPE_DIAGRAM
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }

    private void setupListeners() {
        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            if (editMode) updateCard();
            else          saveCard();
        });

        btnGenerate.setOnClickListener(v -> {
            String catName = getSelectedCategoryName();
            viewModel.generateCard(catName);
        });

        etQuestion.addTextChangedListener(new SimpleWatcher(s ->
            tvCharCountQ.setText(s.length() + "/500")));

        etAnswer.addTextChangedListener(new SimpleWatcher(s ->
            tvCharCountA.setText(s.length() + "/1000")));
    }

    private void observeViewModel() {
        viewModel.getSaveSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(this,
                    editMode ? "Card updated!" : "Card saved!",
                    Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, msg -> {
            if (msg != null && !msg.isEmpty())
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        });

        viewModel.getGeneratedCard().observe(this, card -> {
            if (card == null) return;
            etTitle.setText(card.getTitle());
            etQuestion.setText(card.getQuestion());
            etAnswer.setText(card.getAnswer());
            Toast.makeText(this, "Card generated! Feel free to edit.", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveCard() {
        Card card = buildCardFromForm();
        if (card == null) return;
        viewModel.saveCard(card);
    }

    private void updateCard() {
        Card card = buildCardFromForm();
        if (card == null) return;
        card.setId(editCardId);
        viewModel.updateCard(card);
    }

    private Card buildCardFromForm() {
        String title    = etTitle.getText().toString().trim();
        String question = etQuestion.getText().toString().trim();
        String answer   = etAnswer.getText().toString().trim();
        String tags     = etTags.getText().toString().trim();

        if (title.isEmpty())    { etTitle.setError("Required");    return null; }
        if (question.isEmpty()) { etQuestion.setError("Required"); return null; }
        if (answer.isEmpty())   { etAnswer.setError("Required");   return null; }

        Card card = new Card();
        card.setTitle(title);
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setTags(tags);
        card.setCardType(spinnerType.getSelectedItem() != null
            ? spinnerType.getSelectedItem().toString() : Constants.TYPE_DEFINITION);
        card.setCategoryId(getSelectedCategoryId());
        card.setPriority(getSelectedPriority());
        card.setDifficulty(getSelectedDifficulty());
        return card;
    }

    private long getSelectedCategoryId() {
        int pos = spinnerCategory.getSelectedItemPosition();
        if (pos >= 0 && pos < categoryList.size()) return categoryList.get(pos).getId();
        return categoryList.isEmpty() ? 0 : categoryList.get(0).getId();
    }

    private String getSelectedCategoryName() {
        int pos = spinnerCategory.getSelectedItemPosition();
        if (pos >= 0 && pos < categoryList.size()) return categoryList.get(pos).getName();
        return "General";
    }

    private int getSelectedPriority() {
        int id = rgPriority.getCheckedRadioButtonId();
        if (id == R.id.rb_priority_low)  return Constants.PRIORITY_LOW;
        if (id == R.id.rb_priority_high) return Constants.PRIORITY_HIGH;
        return Constants.PRIORITY_MEDIUM;
    }

    private int getSelectedDifficulty() {
        int id = rgDifficulty.getCheckedRadioButtonId();
        if (id == R.id.rb_diff_easy) return Constants.DIFFICULTY_EASY;
        if (id == R.id.rb_diff_hard) return Constants.DIFFICULTY_HARD;
        return Constants.DIFFICULTY_MEDIUM;
    }

    private void loadCardForEdit() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Card card = viewModel.getCardByIdSync(editCardId);
            if (card == null) return;
            runOnUiThread(() -> {
                etTitle.setText(card.getTitle());
                etQuestion.setText(card.getQuestion());
                etAnswer.setText(card.getAnswer());
                if (card.getTags() != null) etTags.setText(card.getTags());
            });
        });
    }

    private static class SimpleWatcher implements TextWatcher {
        interface Cb { void on(CharSequence s); }
        private final Cb cb;
        SimpleWatcher(Cb cb) { this.cb = cb; }
        @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
        @Override public void onTextChanged(CharSequence s, int st, int b, int c) { cb.on(s); }
        @Override public void afterTextChanged(Editable e) {}
    }
}
