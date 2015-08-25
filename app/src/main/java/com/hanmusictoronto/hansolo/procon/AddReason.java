package com.hanmusictoronto.hansolo.procon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class AddReason extends AppCompatActivity {
    private static Reason reason = new Reason();
    private Long problemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reason);

        final RadioButton proButton = (RadioButton) findViewById(R.id.proRadioButton);
        final RadioButton conButton = (RadioButton) findViewById(R.id.conRadioButton);
        final EditText reasonName = (EditText) findViewById(R.id.reasonInput);
        final EditText goalName = (EditText) findViewById(R.id.goalInput);
        final DiscreteSeekBar weight = (DiscreteSeekBar) findViewById(R.id.reasonWeightBar);
        final Button addReasonButton = (Button) findViewById(R.id.addReasonButton);

        Intent intent = getIntent();
        problemID = intent.getLongExtra("problemID", -1);

        addReasonButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reason.problem = Problem.load(Problem.class, problemID);
                        reason.weight = weight.getProgress();
                        reason.name = reasonName.getText().toString();

                        Goal goal = new Goal(goalName.getText().toString(), reason);

                        if (proButton.isChecked()) {
                            reason.polarity = 1;
                        }
                        if (conButton.isChecked()) {
                            reason.polarity = 0;
                        }

                        try {
                            reason.save();
                            goal.save();
                        } catch (Exception e) {
                            Log.e("first milestone", "Reason or goal save failed");
                        }

                        Intent returnIntent = new Intent(AddReason.this, ReasonsList.class);
                        returnIntent.putExtra("problemID", problemID);
                        startActivity(returnIntent);
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reason, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("backButton", "bb pressed");
                Intent returnIntent = new Intent(AddReason.this, ReasonsList.class);
                returnIntent.putExtra("problemID", problemID);
                startActivity(returnIntent);
        }

        return true;
    }
}
