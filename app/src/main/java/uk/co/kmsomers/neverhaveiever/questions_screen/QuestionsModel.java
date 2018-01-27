package uk.co.kmsomers.neverhaveiever.questions_screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kizer on 26/07/2017.
 */

public class QuestionsModel {

    public List<String> randomiseQuestions(String[] questions){
        List<String> randomisedQuestions = new ArrayList<>();

        Random random = new Random();

        //Randomise questions
        for (int pos = 0; pos < questions.length; pos++){
            int changePos = pos + random.nextInt(questions.length - pos);
            swap(questions, pos, changePos);
        }

        //Add random questions to ArrayList to return
        for (int i = 0; i < questions.length; i++){
            randomisedQuestions.add(questions[i]);
        }

        return randomisedQuestions;
    }

    private void swap(String[] questions, int pos, int changePos){
        String tempString = questions[pos];
        questions[pos] = questions[changePos];
        questions[changePos] = tempString;
    }

}
