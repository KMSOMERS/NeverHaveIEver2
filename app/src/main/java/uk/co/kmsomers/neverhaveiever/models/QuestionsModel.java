package uk.co.kmsomers.neverhaveiever.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kizer on 26/07/2017.
 */

public class QuestionsModel {

    public List<String> getQuestions(String category){
        List<String> questions = new ArrayList<>();

        questions.add("Never have I ever cried on a night out");
        questions.add("Never have I ever had a boyfriend");
        questions.add("Never have I ever had a girlfriend");
        questions.add("Never have I ever had sex");
        questions.add("Never have I ever had a threesome");

        return questions;
    }

}
