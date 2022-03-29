package cs160.UILayer;

import cs160.dataLayer.*;

import static android.widget.CompoundButton.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.UUID;

public class GoalFragment extends Fragment {
    private static final String ARG_GOAL_ID = "goal_id";

    private Goal mGoal;
    private EditText mTitleField;
    private EditText mAmountField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static GoalFragment newInstance(UUID goalId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GOAL_ID, goalId);
        GoalFragment fragment = new GoalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mExpense = new Expense();
//        UUID expenseId = (UUID) getActivity().getIntent()
//                .getSerializableExtra(ExpenseActivity.EXTRA_EXPENSE_ID);
        UUID goalId = (UUID) getArguments().getSerializable(ARG_GOAL_ID);
        mGoal = GoalLab.get(getActivity()).getGoal(goalId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The third argument of the method below tells whether to add the inflated view to the view's parent
        // Here, we pass in false because we will add the view in the activity's code
        View v = inflater.inflate(R.layout.fragment_goal, container, false);

        mTitleField = (EditText) v.findViewById(R.id.goal_title);
        mTitleField.setText(mGoal.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                mGoal.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });

        // Change amount for expense in onPause() method
        mAmountField = (EditText) v.findViewById(R.id.expense_amount);
        mAmountField.setText(mGoal.getProposedAmount().toString());
        mAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    mGoal.setProposedAmount(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e) {
                    // on empty string (when user backspaces) or non-number input, amount should be 0
                    mGoal.setProposedAmount(0.0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.goal_date);
        mDateButton.setText(mGoal.getDate().toString());
        DateFormat df = new DateFormat();
        CharSequence formattedDate = df.format("E, MMM d, yyyy", mGoal.getDate());
        mDateButton.setText(formattedDate);
        mDateButton.setEnabled(false);

//        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.expense_solved);
//        mSolvedCheckBox.setChecked(mExpense.isSolved());
//        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mExpense.setSolved(isChecked);
//            }
//        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        double amount;
        try {
            amount = Double.parseDouble(mAmountField.getText().toString());
        } catch (Exception e) {
            amount = 0.0;
        }
        mGoal.setProposedAmount(amount);
    }
}