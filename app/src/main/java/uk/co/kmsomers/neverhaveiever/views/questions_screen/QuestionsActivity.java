package uk.co.kmsomers.neverhaveiever.views.questions_screen;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.kmsomers.neverhaveiever.R;
import uk.co.kmsomers.neverhaveiever.core.AppConstants;
import uk.co.kmsomers.neverhaveiever.presenters.questions_screen.QuestionsPresenter;
import uk.co.kmsomers.neverhaveiever.utils.CommonUtils;
import uk.co.kmsomers.neverhaveiever.utils.FadingTextView;
import uk.co.kmsomers.neverhaveiever.views.dialogs.InstructionsDialogFragment;

public class QuestionsActivity extends AppCompatActivity implements QuestionsI{

    private String category;
    private List<String> questions;
    private int questionPosition = 0;

    private Window window;
    private ActionBar actionBar;

    private QuestionsPresenter presenter;

    private TextToSpeech textToSpeech;

    @BindView(R.id.ivInstructions)
    ImageView ivInstructions;
    @BindView(R.id.clBackground)
    ConstraintLayout clBackground;
    @BindView(R.id.ivCategoryIcon)
    ImageView ivCategoryIcon;
    @BindView(R.id.tvQuestion)
    FadingTextView tvQuestion;
    @BindView(R.id.tvCategoryTitle)
    TextView tvCategoryTitle;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnQuit)
    Button btnQuit;
    @BindView(R.id.avQuestions)
    AdView avQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        window = getWindow();
        actionBar = getSupportActionBar();

        if (getIntent().hasExtra(AppConstants.CATEGORY_INTENT)){
            category = getIntent().getStringExtra(AppConstants.CATEGORY_INTENT);
            tvCategoryTitle.setText(category);
            setupCategoryColours(category);
            setCategoryIcon(category);
        }

        presenter = new QuestionsPresenter(this);

        String[] questionsArray = getQuestionsArray(category);

        questions = presenter.randomiseQuestions(questionsArray);
        tvQuestion.setText(questions.get(questionPosition));
        tvQuestion.show();
        tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvQuestion.hide();
                setNextQuestion();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextQuestion();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.UK);
            }
        });

        speakQuestion(questions.get(questionPosition));

        AdRequest adRequest = new AdRequest.Builder().build();
        avQuestions.loadAd(adRequest);
    }

    @Override
    public void setupCategoryColours(String category) {

        switch (category){
            case AppConstants.CATEGORY_SEX_AND_RELATIONSHIPS:
                setBackgroundColours(R.color.material_pink_300, R.color.material_pink_500 ,R.color.material_pink_700);
                break;
            case AppConstants.CATEGORY_DRINKING:
                setBackgroundColours(R.color.material_light_green_300, R.color.material_light_green_500, R.color.material_light_green_700);
                break;
            case AppConstants.CATEGORY_ANIMALS:
                setBackgroundColours(R.color.material_deep_purple_300, R.color.material_deep_purple_500, R.color.material_deep_purple_700);
                break;
            case AppConstants.CATEGORY_FAMILY:
                setBackgroundColours(R.color.material_blue_300, R.color.material_blue_500, R.color.material_blue_700);
                break;
            case AppConstants.CATEGORY_RANDOM:
                setBackgroundColours(R.color.material_amber_300, R.color.material_amber_500, R.color.material_amber_700);
                break;
        }
    }

    private void setBackgroundColours(int background, int textColour, int statusBar){
        tvCategoryTitle.setTextColor(ResourcesCompat.getColor(getResources(), textColour, null));
        tvQuestion.setTextColor(ResourcesCompat.getColor(getResources(), textColour, null));
        btnNext.setBackgroundResource(textColour);
        btnQuit.setBackgroundResource(textColour);
        window.setStatusBarColor(ResourcesCompat.getColor(getResources(), statusBar, null));

        //Consider moving to new method
        Drawable instructionsIcon = getDrawable(R.drawable.ic_help);
        instructionsIcon.setTint(ResourcesCompat.getColor(getResources(), textColour, null));
        ivInstructions.setImageDrawable(instructionsIcon);

        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putInt(AppConstants.SHARED_PREFERENCES_STATUS_COLOUR, statusBar).apply();
    }

    private void setCategoryIcon(String category){

        switch (category){
            case AppConstants.CATEGORY_SEX_AND_RELATIONSHIPS:
                ivCategoryIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_heart, null));
                break;
            case AppConstants.CATEGORY_DRINKING:
                ivCategoryIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_beer, null));
                break;
            case AppConstants.CATEGORY_ANIMALS:
                ivCategoryIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_animal, null));
                break;
            case AppConstants.CATEGORY_FAMILY:
                ivCategoryIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_family, null));
                break;
            case AppConstants.CATEGORY_RANDOM:
                ivCategoryIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_question, null));
                break;
        }
    }

    private String[] getQuestionsArray(String category){
        switch (category){
            case AppConstants.CATEGORY_SEX_AND_RELATIONSHIPS:
                return getResources().getStringArray(R.array.category_sex_and_relationshps);
            case AppConstants.CATEGORY_DRINKING:
                return getResources().getStringArray(R.array.category_drinking);
            case AppConstants.CATEGORY_ANIMALS:
                return getResources().getStringArray(R.array.category_animals);
            case AppConstants.CATEGORY_FAMILY:
                return getResources().getStringArray(R.array.category_family);
            case AppConstants.CATEGORY_RANDOM:
                String[] sexAndRelationshipsQuestions = getResources().getStringArray(R.array.category_sex_and_relationshps);
                String[] drinkingQuestions = getResources().getStringArray(R.array.category_drinking);
                String[] animalsQuestions = getResources().getStringArray(R.array.category_animals);
                String[] familyQuestions = getResources().getStringArray(R.array.category_family);
                return CommonUtils.concatenate(CommonUtils.concatenate(sexAndRelationshipsQuestions, drinkingQuestions), CommonUtils.concatenate(animalsQuestions, familyQuestions));
            default: return new String[0];
        }
    }

    private void setNextQuestion(){
        questionPosition = (questionPosition + 1) % questions.size();
        tvQuestion.show();
        tvQuestion.setText(questions.get(questionPosition));
        speakQuestion(questions.get(questionPosition));
    }

    private void speakQuestion(String question){
        textToSpeech.speak(question, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @OnClick(R.id.ivInstructions)
    public void showInstructionsDialog(){
        InstructionsDialogFragment instructionsDialog = InstructionsDialogFragment.getInstance();
        instructionsDialog.show(getSupportFragmentManager(), "Instructions Dialog");
    }
}
