package com.example.money_split_f1.Event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.SuperApplication;

import java.time.LocalDate;

public class PaymentDialogue extends AppCompatDialogFragment {
    String recipientEmail;

    public PaymentDialogue(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(SuperApplication.getContext().getString(R.string.AddPayment));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.payment_dialog, (ViewGroup) getView(), false);
        final EditText paymentText = (EditText) view.findViewById(R.id.input_payment);

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            double payment = 2 * Double.parseDouble(paymentText.getText().toString());
            EventRepository.getInstance().makeCreatePaymentRequest(LocalDate.now().toString(), payment, "", recipientEmail);
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        return builder.create();
    }


}
