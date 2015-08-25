package com.hanmusictoronto.hansolo.procon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

public class ReasonsList extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> items = new ArrayList<>();
    private List<Reason> reasons;
    private Long problemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasons_list);

        if (problemID == null) {
            Intent intent = getIntent();
            problemID = intent.getLongExtra("problemID", -1);
        }

        Problem problem = Problem.load(Problem.class, problemID);
        reasons = new Select().from(Reason.class).where("Problem = ?", problem.getId()).execute();

        if (reasons.size() != 0) {
            // Only set adapter if there is result to show
            for (Reason r : reasons) {
                if (r.name != null) {
                    Goal goal = new Select().from(Goal.class).where("Reason = ?", r.getId()).executeSingle();
                    items.add(goal.name + ", because " + r.name);
                } else {
                    break;
                }
            }

            itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            ListView reasonsList = (ListView) findViewById(R.id.reasonsList);
            reasonsList.setAdapter(itemsAdapter);
        }

        CircleButton addButton = (CircleButton) findViewById(R.id.addReason);
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ReasonsList.this, AddReason.class);
                        intent.putExtra("problemID", problemID);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reasons_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
