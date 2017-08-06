package uk.co.kmsomers.neverhaveiever.views.questions_screen;

import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.kmsomers.neverhaveiever.R;
import uk.co.kmsomers.neverhaveiever.core.AppConstants;
import uk.co.kmsomers.neverhaveiever.presenters.questions_screen.QuestionsPresenter;
import uk.co.kmsomers.neverhaveiever.utils.FadingTextView;
import uk.co.kmsomers.neverhaveiever.views.dialogs.InstructionsDialogFragment;

public class QuestionsActivity extends AppCompatActivity implements QuestionsI{

    private String category;
    private List<String> questions;
    private int questionPosition = 0;

    private Window window;
    private ActionBar actionBar;

    private QuestionsPresenter presenter;

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
        questions = presenter.getQuestions("test");
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

    private void setNextQuestion(){
        questionPosition = (questionPosition + 1) % questions.size();
        tvQuestion.show();
        tvQuestion.setText(questions.get(questionPosition));
    }

    @OnClick(R.id.ivInstructions)
    public void showInstructionsDialog(){
        InstructionsDialogFragment instructionsDialog = InstructionsDialogFragment.getInstance();

        instructionsDialog.show(getSupportFragmentManager(), "Instructions Dialog");
    }
}
