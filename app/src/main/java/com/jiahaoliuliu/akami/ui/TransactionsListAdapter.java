package com.jiahaoliuliu.akami.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.model.Withdraw;
import com.jiahaoliuliu.akami.utils.HeaderUtility;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/3/16.
 */
public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.ViewHolder> {

    private static final String TAG = "TransationsListAdapter";

    private static final String DATE_FORMAT = "EEE dd/MM HH:mm";
    private SimpleDateFormat mSimpleDateFormatter;

    private Context mContext;
    private List<ITransactions> mTransactionsList;
    private Map<String, Company> mCompaniesMap;
    private Map<Long, Float> mTransactionsPerMonth;

    public TransactionsListAdapter(Context context,
                                   List<ITransactions> transactionsList, Map<String, Company> companiesMap,
                                   Map<Long, Float> transactionsPerMonth) {
        this.mContext = context;
        this.mTransactionsList = transactionsList;
        this.mSimpleDateFormatter = new SimpleDateFormat(DATE_FORMAT);
        this.mCompaniesMap = companiesMap;
        this.mTransactionsPerMonth = transactionsPerMonth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.transactions_card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ITransactions transaction = mTransactionsList.get(position);

        // Set the header
        if (transaction.isFirstTransactionOfTheMonth() && position != 0) {
            holder.mHeaderLinearLayout.setVisibility(View.VISIBLE);
            long currentMonthlyKey = HeaderUtility.getHeaderMonthlyKeyByTransaction(transaction);
            // Set the month
            holder.mHeaderDateTextView.setText(MainActivity.sHeaderDateFormatter.format(transaction.getDate()));

            // Set the quantity
            holder.mHeaderQuantityTextView.setText(String.format("%.02f", mTransactionsPerMonth.get(currentMonthlyKey))
                    + " " + mContext.getResources().getString(R.string.currency_aed));
        } else {
            holder.mHeaderLinearLayout.setVisibility(View.GONE);
        }

        // Set the content
        // TODO: Create different view for Withdraws
        // Source name & logo
        String companyId = transaction.getSource();
        switch (transaction.getType()) {
            case EXPENSE:
                companyId = transaction.getDestination();
                if (mCompaniesMap.containsKey(companyId)) {
                    Company company = mCompaniesMap.get(companyId);
                    holder.mSourceTextView.setText(company.getName());

                    int sourceLogoId = company.getImageResourceId();
                    if (sourceLogoId != 0) {
                        holder.mSourceLogoImageView.setImageDrawable(mContext.getResources().getDrawable(sourceLogoId));
                        holder.mSourceLogoImageView.setVisibility(View.VISIBLE);
                    } else {
                        holder.mSourceLogoImageView.setVisibility(View.INVISIBLE);
                    }

                } else {
                    Log.v(TAG, "Source unknown \"" + companyId + "\"");

                    // TODO: Trying to guess the company the name name

                    // Set the first character as capital letter
                    if (!TextUtils.isEmpty(companyId)) {
                        String sourceNameFormatted = companyId.substring(0, 1).toUpperCase() + companyId.substring(1).toLowerCase();
                        holder.mSourceTextView.setText(sourceNameFormatted);
                    }

                    // Hide the company logo
                    holder.mSourceLogoImageView.setVisibility(View.INVISIBLE);
                }
                break;
            case WITHDRAW:
                // Get the branch
                holder.mSourceTextView.setText(Withdraw.WITHDRAW_TITLE);
                holder.mSourceLogoImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.logo_adcb));
                holder.mSourceLogoImageView.setVisibility(View.VISIBLE);
                break;
        }

        String quantity = String.format("%.02f", transaction.getQuantity()) + " " + mContext.getResources().getString(R.string.currency_aed);

        // If the
        if (transaction.getCurrency() != ITransactions.Currency.DIRHAMS) {
            quantity += " (" + String.format("%.02f", transaction.getOriginalCurrencyQuantity()) + " " + transaction.getCurrency().getCode() + ")";
        }

        holder.mQuantityTextView.setText(quantity);
        holder.mDateTextView.setText(mSimpleDateFormatter.format(transaction.getDate()));
    }

    @Override
    public int getItemCount() {
        if (mTransactionsList == null) {
            return 0;
        } else {
            return mTransactionsList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Header
        public LinearLayout mHeaderLinearLayout;
        public TextView mHeaderDateTextView;
        public TextView mHeaderQuantityTextView;

        // Content
        // TODO: Set credit card
        public TextView mSourceTextView;
        public ImageView mSourceLogoImageView;
        public TextView mQuantityTextView;
        public TextView mDateTextView;

        public ViewHolder(View view) {
            super(view);
            // Header
            this.mHeaderLinearLayout = (LinearLayout) view.findViewById(R.id.header_linear_layout);
            this.mHeaderDateTextView = (TextView) view.findViewById(R.id.header_date_text_view);
            this.mHeaderQuantityTextView = (TextView) view.findViewById(R.id.header_quantity_text_view);

            // Content
            this.mSourceTextView = (TextView) view.findViewById(R.id.source_text_view);
            this.mSourceLogoImageView = (ImageView) view.findViewById(R.id.source_logo_image_view);
            this.mQuantityTextView = (TextView) view.findViewById(R.id.quantity_text_view);
            this.mDateTextView = (TextView) view.findViewById(R.id.date_text_view);
        }
    }
}
