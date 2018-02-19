package edu.courtcounter;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int time;
    int score2;
    int score1;
    int scoreTemp1;
    int scoreTemp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Seteaza activity_main.xml ca fiind Design-ul aplicatiei de start.
        displayForTeamA(score1);  //Apeleaza functia displayForTeamA,cu parametrul score1=0;
        displayForTeamB(score2);
    }

    private boolean isEmpty(EditText etText) {      //La apelare, returneaza false in momentul in care lungimea string-ului din parametrul EditText>0, sau true cand este egal cu 0;
        if (etText.getText().toString().trim().length() > 0)
            return false;
        else return true;
    }

    public void ShowSeconds(View v) {//este apelata pentru a incepe timer-ul,dar verifica mai intai daca spatiile de minute si secunde au fost completate.

        EditText txtname1 = (EditText) findViewById(R.id.minutes);

        if (isEmpty(txtname1))
            Toast.makeText(MainActivity.this, getString(R.string.setvalueminutes), Toast.LENGTH_LONG).show();
        else {
            String minute = txtname1.getText().toString();

            int minute3 = Integer.parseInt(minute);


            EditText txtname2 = (EditText) findViewById(R.id.seconds);
            if (isEmpty(txtname2))
                Toast.makeText(getApplicationContext(), getString(R.string.setvalueseconds), Toast.LENGTH_SHORT).show();
            else {


                String secunde = txtname2.getText().toString();

                int secunde3 = Integer.parseInt(secunde);

                time = (minute3 * 60 + secunde3) * 1000;
                startTimer();
            }
        }
    }

    CountDownTimer cTimer; //Declararea unui CountDownTimer cu numele cTimer.

    void startTimer() {  //Crearea unui subprogram startTimer(),in care vom apela CountDownTimer-ul delcarat anterior, acesta primind doi parametri:timpul initial,si timpul la care acesta actioneaza;
        cTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) { //la fiecare 1000 de milisecunde, se afiseaza pe ecran secundele ramase.
                TextView secundee = (TextView) findViewById(R.id.secundeRem);
                secundee.setText(getString(R.string.secrem) + millisUntilFinished / 1000);
            }

            //se apeleaza automat in momentul in care Timer-ul ajunge la 0.
            public void onFinish() {
                TextView secundee = (TextView) findViewById(R.id.secundeRem);

                TextView score1 = (TextView) findViewById(R.id.team_a_score);

                String scoreTeam1 = score1.getText().toString();
                // Am salvat in ScoreTeam 1 scorul primei echipe, cu ajutorul adresei team_a_score, aceasta avand o valoare de TextView,
                int ScoreTeam1 = Integer.parseInt(scoreTeam1);
                //pe care ulterior am transformat-o in String,si mai apoi in int.a
                TextView score2 = (TextView) findViewById(R.id.team_b_score);

                String scoreTeam2 = score2.getText().toString();

                int ScoreTeam2 = Integer.parseInt(scoreTeam2);
                //In locul secundelor, afisam textul "Done!"
                secundee.setText(getString(R.string.done));
                //Comparam scorurile,pentru ca mai apoi sa afisam un Toast(un pop-up temporar) cu echipa castigatoare.
                if (ScoreTeam1 > ScoreTeam2)

                    Toast.makeText(getApplicationContext(), getString(R.string.firstwin), Toast.LENGTH_LONG).show();

                if (ScoreTeam2 > ScoreTeam1)
                    Toast.makeText(getApplicationContext(), getString(R.string.secondwin), Toast.LENGTH_LONG).show();

                else
                    Toast.makeText(getApplicationContext(), getString(R.string.draw), Toast.LENGTH_LONG).show();
            }
        };
        cTimer.start();
    }

    //la apasarea uneia dintre butoane, scorul se mareste,si se apeleaza functia de afisare a noului scor.
    public void buton3(View v) {
        scoreTemp1 = score1;
        score1 += 3;
        displayForTeamA(score1);
    }

    public void buton1(View v) {
        scoreTemp1 = score1;
        score1++;
        displayForTeamA(score1);
    }

    public void buton2(View v) {
        scoreTemp1 = score1;
        score1 += 2;
        displayForTeamA(score1);
    }

    public void undoTeam1(View v) {
        score1 = scoreTemp1;
        displayForTeamA(score1);
    }

    public void displayForTeamA(int score1) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score1));
    }

    public void buton1b(View v) {
        scoreTemp2 = score2;
        score2++;
        displayForTeamB(score2);
    }

    public void buton2b(View v) {
        scoreTemp2 = score2;
        score2 += 2;
        displayForTeamB(score2);
    }

    public void buton3b(View v) {
        scoreTemp2 = score2;
        score2 += 3;
        displayForTeamB(score2);
    }

    //foloseste scoreTemp2,ce salveaza ultima modificare facuta scorului,si in momentul apelarii,da scorului final valoarea lui scoreTemp2.
    public void undoTeam2(View v) {
        score2 = scoreTemp2;
        displayForTeamB(score2);
    }

    //Este apelata la apasarea butonului "Reset"
    public void reset(View v) {
        //face scorurile echipelor=0
        score1 = 0;
        score2 = 0;
        //le afiseaza
        displayForTeamA(score1);
        displayForTeamB(score2);
        //opreste timer-ul
        cancelTimer();
        TextView secundee = (TextView) findViewById(R.id.secundeRem);
        //si seteaza textul secundelor ='0';
        secundee.setText("0");
    }

    void cancelTimer() {
        //in momentul apelarii,daca timer-ul este in functiune,il opreste.
        if (cTimer != null)
            cTimer.cancel();
    }

    //preia adresa scorului celei de-a doua echipe,team_b_score,si afiseaza in locul ei valoarea lui score2.
    public void displayForTeamB(int score2) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score2));

    }
}
