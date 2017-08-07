package uk.co.kmsomers.neverhaveiever.views.category_select;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.kmsomers.neverhaveiever.R;
import uk.co.kmsomers.neverhaveiever.core.AppConstants;
import uk.co.kmsomers.neverhaveiever.views.questions_screen.QuestionsActivity;

import static uk.co.kmsomers.neverhaveiever.core.AppConstants.CATEGORY_INTENT;

public class CategorySelectActivity extends AppCompatActivity {

    @BindView(R.id.tvAppTitle) TextView tvTitle;
    @BindView(R.id.btnSexAndRelationships) Button btnSexAndRelationships;
    @BindView(R.id.btnDrinking) Button btnDrinking;
    @BindView(R.id.btnAnimals) Button btnAnimals;
    @BindView(R.id.btnFamily) Button btnFamily;
    @BindView(R.id.btnRandom) Button btnRandom;
    @BindView(R.id.avCategory) AdView avCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        ButterKnife.bind(this);

        Typeface newsCycleFont = Typeface.createFromAsset(getAssets(), "fonts/NewsCycle-Regular.ttf");
        tvTitle.setTypeface(newsCycleFont);

        View.OnClickListener categoryButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btnSexAndRelationships:
                        openQuestionsScreen(AppConstants.CATEGORY_SEX_AND_RELATIONSHIPS);
                        break;
                    case R.id.btnDrinking:
                        openQuestionsScreen(AppConstants.CATEGORY_DRINKING);
                        break;
                    case R.id.btnAnimals:
                        openQuestionsScreen(AppConstants.CATEGORY_ANIMALS);
                        break;
                    case R.id.btnFamily:
                        openQuestionsScreen(AppConstants.CATEGORY_FAMILY);
                        break;
                    case R.id.btnRandom:
                        openQuestionsScreen(AppConstants.CATEGORY_RANDOM);
                        break;
                }
            }
        };

        btnSexAndRelationships.setOnClickListener(categoryButtonListener);
        btnDrinking.setOnClickListener(categoryButtonListener);
        btnAnimals.setOnClickListener(categoryButtonListener);
        btnFamily.setOnClickListener(categoryButtonListener);
        btnRandom.setOnClickListener(categoryButtonListener);

        AdRequest adRequest = new AdRequest.Builder().build();
        avCategory.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        int statusBarColour = ResourcesCompat.getColor(getResources(), sharedPreferences.getInt(AppConstants.SHARED_PREFERENCES_STATUS_COLOUR, R.color.material_pink_700), null);
        getWindow().setStatusBarColor(statusBarColour);
    }

    private void openQuestionsScreen(String category){
        Intent i = new Intent(CategorySelectActivity.this, QuestionsActivity.class);
        i.putExtra(CATEGORY_INTENT, category);
        startActivity(i);
    }


}
