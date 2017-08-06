package uk.co.kmsomers.neverhaveiever.presenters.questions_screen;

import java.util.List;

import uk.co.kmsomers.neverhaveiever.models.QuestionsModel;
import uk.co.kmsomers.neverhaveiever.views.questions_screen.QuestionsI;

import static android.R.attr.category;

/**
 * Created by kizer on 26/07/2017.
 */

public class QuestionsPresenter {

    private QuestionsI view;
    private QuestionsModel model;

    public QuestionsPresenter(QuestionsI view){
        this.view = view;
        model = new QuestionsModel();
    }

    public List<String> randomiseQuestions(String[] questions){
        return model.randomiseQuestions(questions);
    }

}
