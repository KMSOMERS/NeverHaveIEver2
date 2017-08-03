package uk.co.kmsomers.neverhaveiever.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kizer on 26/07/2017.
 */

public class QuestionsModel {

    public List<String> randomiseQuestions(String[] questions){
        List<String> randomisedQuestions = new ArrayList<>();

        for (int i = 0; i < questions.length; i++){
            randomisedQuestions.add(questions[i]);
        }

        return randomisedQuestions;
    }

}
