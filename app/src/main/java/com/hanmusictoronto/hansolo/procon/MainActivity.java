package com.hanmusictoronto.hansolo.procon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> items = new ArrayList<>();
    private List<Problem> allProblems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView problemsList = (ListView) findViewById(R.id.problemsList);

        allProblems = new Select().from(Problem.class).orderBy("ExpiryDate ASC").execute();

        if (allProblems.size() != 0) {
            // Only set adapter if there is result to show
            for (Problem problem : allProblems) {
                if (problem.name != null) {
                    items.add(problem.name + " " + problem.expiryDate);
                } else {
                    break;
                }
            }

            itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            problemsList.setAdapter(itemsAdapter);
        }

        CircleButton addButton = (CircleButton) findViewById(R.id.addProblem);
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddProblem.class);
                        startActivity(intent);
                    }
                }
        );

        problemsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, ReasonsList.class);
                        Problem problem = allProblems.get(position);
                        intent.putExtra("problemID", problem.getId());
                        startActivity(intent);
                    }
                }

        );

        problemsList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Problem.delete(Problem.class, id);
                        new Delete().from(Reason.class).where("Problem = ?", id).execute();

                        return true;
                    }
                }
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
