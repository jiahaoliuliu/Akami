package com.jiahaoliuliu.akami.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.Expense;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/3/16.
 */
public class ExpensesListAdapter extends RecyclerView.Adapter<ExpensesListAdapter.ViewHolder> {

    private static final String TAG = "ExpensesListAdapter";

    private static final String DATE_FORMAT = "EEE dd/MM HH:mm";
    private SimpleDateFormat mSimpleDateFormatter;

    private Context mContext;
    private List<Expense> mExpensesList;
    private Map<String, Company> mCompaniesMap;

    public ExpensesListAdapter(Context context, List<Expense> expensesList, Map<String, Company> companiesMap) {
        this.mContext = context;
        this.mExpensesList = expensesList;
        this.mSimpleDateFormatter = new SimpleDateFormat(DATE_FORMAT);
        this.mCompaniesMap = companiesMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.expense_card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Expense expense = mExpensesList.get(position);

        // Company name & logo
        String companyId = expense.getCompanyId();
        if (mCompaniesMap.containsKey(companyId)) {
            Company company = mCompaniesMap.get(companyId);
            holder.mCompanyTextView.setText(company.getName());

            int companyLogoId = company.getImageResourceId();
            if (companyLogoId != 0) {
                holder.mLogoImageView.setImageDrawable(mContext.getResources().getDrawable(companyLogoId));
                holder.mLogoImageView.setVisibility(View.VISIBLE);
            } else {
                holder.mLogoImageView.setVisibility(View.INVISIBLE);
            }

        } else {
            Log.v(TAG, "Company id unknown \"" + companyId + "\"");

            // TODO: Trying to guess the company the name name

            // Set the first character as capital letter
            String companyNameFormatted = companyId.substring(0, 1).toUpperCase() + companyId.substring(1).toLowerCase();
            holder.mCompanyTextView.setText(companyNameFormatted);

            // Hide the company logo
            holder.mLogoImageView.setVisibility(View.INVISIBLE);
        }

        holder.mExpensesTextView.setText(expense.getQuantity() + " " + mContext.getResources().getString(R.string.currency_aed));
        holder.mDateTextView.setText(mSimpleDateFormatter.format(expense.getDate()));
    }

    @Override
    public int getItemCount() {
        if (mExpensesList == null) {
            return 0;
        } else {
            return mExpensesList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // TODO: Set credit card
        public TextView mCompanyTextView;
        public ImageView mLogoImageView;
        public TextView mExpensesTextView;
        public TextView mDateTextView;

        public ViewHolder(View view) {
            super(view);
            this.mCompanyTextView = (TextView) view.findViewById(R.id.company_name_text_view);
            this.mLogoImageView = (ImageView) view.findViewById(R.id.logo_image_view);
            this.mExpensesTextView = (TextView) view.findViewById(R.id.expense_text_view);
            this.mDateTextView = (TextView) view.findViewById(R.id.date_text_view);
        }
    }
}
