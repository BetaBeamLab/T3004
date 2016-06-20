package beam.t3004;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Random;


public class MainActivity extends AppCompatActivity
{

    private int state = 0; // represents whose turn it is 1 is X, 0 is O
    private int botTurn[] = new int[6];
    private TextView Output;
    private Button Board[] = new Button[9];
    public boolean bot = false;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String text1;

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme)
        {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Switch toggle1 = (Switch) findViewById(R.id.toggleButton1);
        Switch toggle2 = (Switch) findViewById(R.id.toggleButton2);

        assert toggle2 != null;
        toggle2.setChecked(useDarkTheme);
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked)
            {
                toggleTheme(isChecked);
            }
        });

        assert toggle1 != null;
        if(toggle1.isChecked())
        {
            bot = true;
            if(state == 0)
            {
                botMove();
            }
        }
        else
        {
            bot = false;
        }
        Output = (TextView) findViewById(R.id.textView1);
        Board[0] = (Button) findViewById(R.id.button1);
        Board[1] = (Button) findViewById(R.id.button2);
        Board[2] = (Button) findViewById(R.id.button3);
        Board[3] = (Button) findViewById(R.id.button4);
        Board[4] = (Button) findViewById(R.id.button5);
        Board[5] = (Button) findViewById(R.id.button6);
        Board[6] = (Button) findViewById(R.id.button7);
        Board[7] = (Button) findViewById(R.id.button8);
        Board[8] = (Button) findViewById(R.id.button9);
        Button restart = (Button) findViewById(R.id.button);
        if (restart != null) {
            restart.setVisibility(View.GONE);
        }

        text1 = (" O's Turn");
        Output.setText(text1);
    }

    public void turnonBot(View view)
    {
        String text1;
        Switch toggle1 = (Switch) view;
        if(toggle1.isChecked()){
            bot = true;
            reset(findViewById(R.id.button));
        }
        else {
            bot = false;
        }

        if(bot)
        {
            text1 = " \nBot on\nBot: O";
        }
        else
        {
            text1 = " \nBot is off";
        }
            Output.append(text1);
        if(bot && state == 0)
        {

            botMove();
        }
    }
    private void toggleTheme(boolean darkTheme)
    {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();
        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }
    public void onButtonPress(View view)
    {
        String text1;
        Button button = (Button) view;
        if(button.getText() == "")
        {
            button.setClickable(false);
            switch(state)
            {
                case 0:
                    button.setText("O");
                    button.setTextColor(Color.BLUE);
                    text1 = ("X's Turn");
                    state = 1;
                    break;
                case 1:
                    button.setText("X");
                    button.setTextColor(Color.RED);
                    text1 = ("O's Turn");
                    state = 0;
                    break;
                default:
                    text1 = "ERROR: STATE NOT 0 OR 1";
            }
            Output.setText(text1);

            if(check3())
            {
                lock();
            }
            else
            {
                if(state == 0 && bot)
                {
                    botMove();
                }
            }
        }
    }


    protected boolean check3()
    {

        String text1;
        boolean x = false, y = true;


        //top horizontal
        if(Board[0].getText() != "" && Board[0].getText() == Board[1].getText() && Board[1].getText() == Board[2].getText())
        {
            text1 = (Board[0].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        // diagonal descending
        if(Board[0].getText() != "" && Board[0].getText() == Board[4].getText() && Board[4].getText() == Board[8].getText())
        {
            text1 = (Board[0].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        //left vertical
        if(Board[0].getText() != "" && Board[0].getText() == Board[3].getText() && Board[3].getText() == Board[6].getText())
        {
            text1 = (Board[0].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        //middle vertical
        if(Board[1].getText() != "" && Board[1].getText() == Board[4].getText() && Board[4].getText() == Board[7].getText())
        {
            text1 = (Board[1].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        //right vertical
        if(Board[2].getText() != "" && Board[2].getText() == Board[5].getText() && Board[5].getText() == Board[8].getText())
        {
            text1 = (Board[2].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        // middle horizontal
        if(Board[3].getText() != "" && Board[3].getText() == Board[4].getText() && Board[4].getText() == Board[5].getText())
        {
            text1 = (Board[3].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        //bottom horizontal
        if(Board[6].getText() != "" && Board[6].getText() == Board[7].getText() && Board[7].getText() == Board[8].getText())
        {
            text1 = (Board[6].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }
        //diagonal ascending
        if(Board[6].getText() != "" && Board[6].getText() == Board[4].getText() && Board[4].getText() == Board[2].getText())
        {
            text1 = (Board[6].getText() + " Wins!");
            Output.setText(text1);
            x = true;
        }

        if(!x)
        {
            for (int i = 0; i < 9; i++)
            {
                if (Board[i].getText() == "")
                {
                    y = false;
                }
            }
            x = y;
            if(x)
            {
                text1 = ("It's a Tie!");
                Output.setText(text1);
            }
        }
        return x;
    }


    private boolean isBoardEmpty()
    {
        boolean y = true;
        for (int i = 0; i < 9; i++) // checks to see if board is empty
        {
            if (Board[i].getText() != "")
            {
                y = false;
            }
        }
        return y;
    }
    public void reset(View view)
    {
        String text1;
        for(int i = 0; i < 9; i++)
        {
            Board[i].setText("");
            Board[i].setClickable(true);
        }
        Button restart = (Button) findViewById(R.id.button);
        if (restart != null) {
            restart.setVisibility(View.GONE);
        }
        botTurn[0] = 0;
        if(state == 0)
        {
            text1 = ("O's Turn");
            Output.setText(text1);
            if(bot)
            {
                botMove();
            }
        }
        else if(state == 1)
        {
            text1 = ("X's Turn");
            Output.setText(text1);
        }


    }

    protected void lock()
    {
        for(int i = 0; i < 9; i++) {
            Board[i].setClickable(false);

        }
        Button restart = (Button) findViewById(R.id.button);
        if (restart != null) {
            restart.setVisibility(View.VISIBLE);
        }
       // botTurn = 0;

    }

    private int check2(String s) {
        int loc = -1;
        //top horizontal
        if ((Board[0].getText() == s && Board[1].getText() == s && Board[2].getText() == "") ||
                (Board[0].getText() == s && Board[1].getText() == "" && Board[2].getText() == s) ||
                (Board[0].getText() == "" && Board[1].getText() == s && Board[2].getText() == s))
        {
            if (Board[0].getText() == "") {
                loc = 0;
            } else if (Board[1].getText() == "") {
                loc = 1;
            } else if (Board[2].getText() == "") {
                loc = 2;
            }
        }
        // diagonal descending
        if((Board[0].getText() == s && Board[4].getText() == s && Board[8].getText() == "") ||
                (Board[0].getText() == s && Board[4].getText() == "" && Board[8].getText() == s) ||
                (Board[0].getText() == "" && Board[4].getText() == s && Board[8].getText() == s))
        {
            if (Board[0].getText() == "") {
                loc = 0;
            } else if (Board[4].getText() == "") {
                loc = 4;
            } else if (Board[8].getText() == "") {
                loc = 8;
            }
        }
        //left vertical
        if((Board[0].getText() == s && Board[3].getText() == s && Board[6].getText() == "") ||
                (Board[0].getText() == s && Board[3].getText() == "" && Board[6].getText() == s) ||
                (Board[0].getText() == "" && Board[3].getText() == s && Board[6].getText() == s))
        {
            if (Board[0].getText() == "") {
                loc = 0;
            } else if (Board[3].getText() == "") {
                loc = 3;
            } else if (Board[6].getText() == "") {
                loc = 6;
            }
        }
        //middle vertical
        if((Board[1].getText() == s && Board[4].getText() == s && Board[7].getText() == "") ||
                (Board[1].getText() == s && Board[4].getText() == "" && Board[7].getText() == s) ||
                (Board[1].getText() == "" && Board[4].getText() == s && Board[7].getText() == s))
        {
            if (Board[1].getText() == "") {
                loc = 1;
            } else if (Board[4].getText() == "") {
                loc = 4;
            } else if (Board[7].getText() == "") {
                loc = 7;
            }
        }
        //right vertical
        if((Board[2].getText() == s && Board[5].getText() == s && Board[8].getText() == "") ||
                (Board[2].getText() == s && Board[5].getText() == "" && Board[8].getText() == s) ||
                (Board[2].getText() == "" && Board[5].getText() == s && Board[8].getText() == s))
        {
            if (Board[2].getText() == "") {
                loc = 2;
            } else if (Board[5].getText() == "") {
                loc = 5;
            } else if (Board[8].getText() == "") {
                loc = 8;
            }
        }
        // middle horizontal
        if((Board[3].getText() == s && Board[4].getText() == s && Board[5].getText() == "") ||
                (Board[3].getText() == s && Board[4].getText() == "" && Board[5].getText() == s) ||
                (Board[3].getText() == "" && Board[4].getText() == s && Board[5].getText() == s))
        {
            if (Board[3].getText() == "") {
                loc = 3;
            } else if (Board[4].getText() == "") {
                loc = 4;
            } else if (Board[5].getText() == "") {
                loc = 5;
            }
        }
        //bottom horizontal
        if((Board[6].getText() == s && Board[7].getText() == s && Board[8].getText() == "") ||
                (Board[6].getText() == s && Board[7].getText() == "" && Board[8].getText() == s) ||
                (Board[6].getText() == "" && Board[7].getText() == s && Board[8].getText() == s))
        {
            if (Board[6].getText() == "") {
                loc = 6;
            } else if (Board[7].getText() == "") {
                loc = 7;
            } else if (Board[8].getText() == "") {
                loc = 8;
            }
        }
        //diagonal ascending
        if((Board[6].getText() == s && Board[4].getText() == s && Board[2].getText() == "") ||
                (Board[6].getText() == s && Board[4].getText() == "" && Board[2].getText() == s) ||
                (Board[6].getText() == "" && Board[4].getText() == s && Board[2].getText() == s))
        {
            if (Board[6].getText() == "") {
                loc = 6;
            } else if (Board[4].getText() == "") {
                loc = 4;
            } else if (Board[2].getText() == "") {
                loc = 2;
            }
        }

        return loc;

    }

    private void botMove()
    {
        Random rand = new Random();
        int x, y = -1, z, move = -1;
        String text1;
        if(isBoardEmpty() && botTurn[0] == 0) // check if bot is moving first
        {
            x = (rand.nextInt(5));
            switch(x)
            {
                case 0: // top left corner
                    move = 0;
                    break;
                case 1: // top right corner
                    move = 2;
                    break;
                case 2: // bottom left corner
                    move = 6;
                    break;
                case 3: // top right corner
                    move = 8;
                    break;
                case 4: // middle block
                    move = 4;
                    break;
            }


        }
        else if(!isBoardEmpty() && botTurn[0] == 0) // check if bot going second
        {
            for(int i = 0; i < 9; i++)
            {
                if(Board[i].getText() == "X")
                {
                    y = i;
                }
            }
            if(y == 0 || y == 2 || y == 6 || y == 8 || y == 4)
            {
                x = (rand.nextInt(2));
                switch(y)
                {
                    case 0: // top left corner
                            move = 4;
                        break;
                    case 2: // top right corner
                            move = 4;
                        break;
                    case 6: // bottom left corner
                            move = 4;
                        break;
                    case 8: // top right corner
                            move = 4;
                        break;
                    case 4: // middle block
                        if(x == 0)
                        {
                            z = (rand.nextInt(2));
                            if(z == 0) {
                                move = 0;
                            }
                            else
                            {
                                move = 6;
                            }
                        }
                        else
                        {
                            z = (rand.nextInt(2));
                            if(z == 0) {
                                move = 2;
                            }
                            else
                            {
                                move = 8;
                            }
                        }
                        break;
                }
            }
            else
            {
                x = (rand.nextInt(5));

                switch(x)
                {
                    case 0: // top left corner
                        move = 0;
                        break;
                    case 1: // top right corner
                        move = 2;
                        break;
                    case 2: // bottom left corner
                        move = 6;
                        break;
                    case 3: // top right corner
                        move = 8;
                        break;
                    case 4: // middle block
                        move = 4;
                        break;
                }
            }
        }
        else if(botTurn[0] == 1) // check if second turn
        {
            if(check2("O") != -1)
            {
                move = check2("O");
            }
            else if(check2("X") != -1)
            {
                move = check2("X");
            }
            else if(botTurn[botTurn[0]] == 0 ||botTurn[botTurn[0]] == 2 || botTurn[botTurn[0]] == 6 ||
                    botTurn[botTurn[0]] == 8 || botTurn[botTurn[0]] == 4)
            {
                    switch(botTurn[botTurn[0]])
                    {
                        case 0:
                            if(Board[8].getText() == "")
                            {
                                move = 8;
                            }
                            else if(Board[2].getText() == "")
                            {
                                move = 2;
                            }
                            else if(Board[6].getText() == "")
                            {
                                move = 6;
                            }
                            break;
                        case 2:
                            if(Board[6].getText() == "")
                            {
                                move = 6;
                            }
                            else if(Board[0].getText() == "")
                            {
                                move = 0;
                            }
                            else if(Board[8].getText() == "")
                            {
                                move = 8;
                            }
                            break;
                        case 6:
                            if(Board[2].getText() == "")
                            {
                                move = 2;
                            }
                            else if(Board[0].getText() == "")
                            {
                                move = 0;
                            }
                            else if(Board[8].getText() == "")
                            {
                                move = 8;
                            }
                            break;
                        case 8:
                            if(Board[0].getText() == "")
                            {
                                move = 0;
                            }
                            else if(Board[2].getText() == "")
                            {
                                move = 2;
                            }
                            else if(Board[6].getText() == "")
                            {
                                move = 6;
                            }
                            break;
                        case 4:
                            if(Board[1].getText() == "")
                            {
                                move = 1;
                            }
                            else if(Board[3].getText() == "")
                            {
                                move = 3;
                            }
                            else if(Board[5].getText() == "")
                            {
                                move = 5;
                            }
                            else if(Board[7].getText() == "")
                            {
                                move = 7;
                            }
                            break;

                    }
            }

        }
        else if(botTurn[0] == 2) // check if third turn
        {
            if(check2("O") != -1)
            {
                move = check2("O");
            }
            else if(check2("X") != -1)
            {
                move = check2("X");
            }
            else
            {
                for (int i = 0; i < 9; i++)
                {
                    if (Board[i].getText() == "") {
                        Board[i].setText("O");
                        Board[i].setTextColor(Color.BLUE);
                        break;
                    }
                }
            }
        }
        else if(botTurn[0] == 3) // check if fourth turn
        {
            if(check2("O") != -1)
            {
                move = check2("O");
            }
            else if(check2("X") != -1)
            {
                move = check2("X");
            }
            else
            {
                for (int i = 0; i < 9; i++)
                {
                    if (Board[i].getText() == "") {
                        Board[i].setText("O");
                        Board[i].setTextColor(Color.BLUE);
                        break;
                    }
                }
            }
        }
        else if(botTurn[0] == 4) // check if fifth turn
        {
            if(check2("O") != -1)
            {
                move = check2("O");
            }
            else if(check2("X") != -1)
            {
                move = check2("X");
            }
            else
            {
                for (int i = 0; i < 9; i++)
                {
                    if (Board[i].getText() == "") {
                        Board[i].setText("O");
                        Board[i].setTextColor(Color.BLUE);
                        break;
                    }
                }
            }
        }

        if(move > -1 && move < 9)
        {
            Board[move].setText("O");
            Board[move].setTextColor(Color.BLUE);
        }

        if(check3())
        {
            lock();
        }
        botTurn[0]++;
        if(botTurn[0] > 4)
        {
            botTurn[0] = 0;
        }
        else
        {
            botTurn[botTurn[0]] = move;
        }
        text1 = ("Bot turn:" + botTurn[0] + "\n X's Turn");
        state = 1;
        Output.setText(text1);

    }
}

