package uk.co.kmsomers.neverhaveiever.questions_screen;

import java.util.List;

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
