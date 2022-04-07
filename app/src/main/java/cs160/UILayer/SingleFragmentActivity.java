package cs160.UILayer;

import static android.content.ContentValues.TAG;

import cs160.dataLayer.*;

import android.content.Intent; // *** new
import android.os.Bundle;
import android.util.Log; // *** new
import android.view.View;
import android.widget.Button; // *** new

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    private Button goalBtn;
    private Button expenseBtn;
    private Button transactionBtn; // to be implemented later

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment) // Creates fragment transaction
//                    .addToBackStack(null)
                    .commit(); // Commits fragment transaction
        }

        goalBtn = (Button) findViewById(R.id.goalButton);
        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalListActivity gla = new GoalListActivity();
                Fragment goalFragment = gla.createListFragment();

                fm.beginTransaction()
                        .replace(R.id.fragment_container, goalFragment, "goal")
//                        .setReorderingAllowed(true)
                        .addToBackStack("goal")
                        .commit();
            }
        });

        expenseBtn = (Button) findViewById(R.id.expenseButton);
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseListActivity ela = new ExpenseListActivity();
                Fragment expenseFragment = ela.createListFragment();

                fm.beginTransaction()
                        .replace(R.id.fragment_container, expenseFragment, "expense")
//                        .setReorderingAllowed(true)
                        .addToBackStack("expense")
                        .commit();
            }
        });

        transactionBtn = (Button) findViewById(R.id.transactionButton);
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionListActivity tla = new TransactionListActivity();
                Fragment transactionFragment = tla.createListFragment();

                fm.beginTransaction()
                        .replace(R.id.fragment_container, transactionFragment, "transaction")
//                        .setReorderingAllowed(true)
                        .addToBackStack("transaction")
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        FragmentManager fm = getSupportFragmentManager();
//        if(fm.getBackStackEntryCount() > 0 ) {
//            fm.popBackStackImmediate("goal", 0);//Pops one of the added fragments
//        }
//        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
    }

}
