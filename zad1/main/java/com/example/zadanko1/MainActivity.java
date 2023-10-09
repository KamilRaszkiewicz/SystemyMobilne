package com.example.zadanko1;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zadanko1.Quiz.Question;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<Question> _questions;
    public static List<Boolean> _userAnswers = new ArrayList<>();
    private Iterator<Question> _questionsIterator;
    private Question _currentQuestion;

    private Button btnNext;
    private Button btnTrue;
    private Button btnFalse;
    private TextView questionTextView;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        btnNext = findViewById(R.id.btn_next);
        btnTrue = findViewById(R.id.btn_true);
        btnFalse = findViewById(R.id.btn_false);
        questionTextView = findViewById(R.id.text_question);

        _questions = readQuestionsFromXml(
                getResources(),
                R.xml.questions
        );

        Collections.shuffle(_questions);
        _questionsIterator = _questions.iterator();

        btnTrue.setOnClickListener(
                new View.OnClickListener( ){
                    @Override
                    public void onClick(View v) {
                        CheckAnswer(true);
                        TryMoveToNextQuestion();
                    }
                }
        );

        btnFalse.setOnClickListener(
                new View.OnClickListener( ){
                    @Override
                    public void onClick(View v) {
                        CheckAnswer(false);
                        TryMoveToNextQuestion();
                    }
                }
        );

        btnNext.setOnClickListener(
                new View.OnClickListener( ){
                    @Override
                    public void onClick(View v) {
                        _userAnswers.add(null);
                        TryMoveToNextQuestion();
                    }
                }
        );

        TryMoveToNextQuestion();
    }


    private boolean CheckAnswer(boolean usersAnswer) {
        _userAnswers.add(usersAnswer);
        boolean result = _currentQuestion.Answer() == usersAnswer;

        if(result)
            Toast.makeText(this, R.string.answer_correct, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.answer_wrong, Toast.LENGTH_SHORT).show();

        return result;
    }

    private void TryMoveToNextQuestion(){
        if(_questionsIterator.hasNext())
        {
            _currentQuestion = _questionsIterator.next();
        }
        else
        {
            startActivity(new Intent(this, SummaryActivity.class));
        }


        Log.d("Question: ", Integer.toString(_currentQuestion.Id()) + " " + _currentQuestion.Text() + " " + String.valueOf(_currentQuestion.Answer()));
        questionTextView.setText(_currentQuestion.Text());
    }

    private List<Question> readQuestionsFromXml(Resources resources, int xmlResourceId) {
        List<Question> questionList = new ArrayList<>();

        try {
            XmlResourceParser parser = resources.getXml(xmlResourceId);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("question")) {
                    int id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                    boolean answer = Boolean.parseBoolean(parser.getAttributeValue(null, "answer"));
                    String text = parser.getAttributeValue(null, "text");

                    questionList.add(new Question(id, text, answer));
                }

                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return questionList;
    }
}
