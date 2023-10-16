package com.example.zadanko1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zadanko1.Quiz.Question;

import java.util.List;

public class SummaryActivity extends AppCompatActivity {
    private List<Question> _questions;
    private List<Boolean> _userAnswers;

    private TextView _tvCorrectAnswers;
    private TextView _tvWrongAnswers;
    private TextView _tvNoAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        _questions = MainActivity._questions;     //I shouldn't have done that, but android enforces us to either use static members
        _userAnswers = MainActivity._userAnswers; //or serialize objects to pass them to another activity xd

        _tvCorrectAnswers = findViewById(R.id.correct_answers);
        _tvWrongAnswers = findViewById(R.id.wrong_answers);
        _tvNoAnswers = findViewById(R.id.no_answers);
        
        int noAnswers = 0;
        int correctAnswers = 0;
        int wrongAnswers = 0;

        for(int i = 0; i < _questions.size(); i++){
            var answer = _userAnswers.get(i);

            if(answer == null)
                noAnswers++;
            else if(answer == _questions.get(i).Answer())
                correctAnswers++;
            else
                wrongAnswers++;
        }

        _tvCorrectAnswers.setText(
                getString(R.string.correct_answers, correctAnswers)
        );
        _tvWrongAnswers.setText(
                getString(R.string.wrong_answers, wrongAnswers)
        );
        _tvNoAnswers.setText(
                getString(R.string.no_answers, noAnswers)
        );




    }
}