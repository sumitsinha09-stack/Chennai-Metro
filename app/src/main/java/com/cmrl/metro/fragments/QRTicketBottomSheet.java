package com.cmrl.metro.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cmrl.metro.R;
import com.cmrl.metro.models.Ticket;
import com.cmrl.metro.utils.QRGenerator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class QRTicketBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_TICKET_ID = "ticket_id";
    private static final String ARG_FROM      = "from";
    private static final String ARG_TO        = "to";
    private static final String ARG_FARE      = "fare";
    private static final String ARG_DATE      = "date";
    private static final String ARG_QR_DATA   = "qr_data";
    private static final String ARG_STATUS    = "status";
    private static final String ARG_PAX       = "pax";

    public static QRTicketBottomSheet newInstance(Ticket ticket) {
        QRTicketBottomSheet sheet = new QRTicketBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_TICKET_ID, ticket.getId());
        args.putString(ARG_FROM,      ticket.getFromStation());
        args.putString(ARG_TO,        ticket.getToStation());
        args.putInt   (ARG_FARE,      ticket.getFareINR());
        args.putString(ARG_DATE,      ticket.getDate());
        args.putString(ARG_QR_DATA,   ticket.getQrData());
        args.putString(ARG_STATUS,    ticket.getStatus().name());
        args.putInt   (ARG_PAX,       ticket.getPassengerCount());
        sheet.setArguments(args);
        return sheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_qr_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = requireArguments();

        TextView tvFrom   = view.findViewById(R.id.tv_from);
        TextView tvTo     = view.findViewById(R.id.tv_to);
        TextView tvFare   = view.findViewById(R.id.tv_fare);
        TextView tvDate   = view.findViewById(R.id.tv_date);
        TextView tvStatus = view.findViewById(R.id.tv_status);
        TextView tvPax    = view.findViewById(R.id.tv_pax);
        TextView tvId     = view.findViewById(R.id.tv_ticket_id);
        ImageView ivQR    = view.findViewById(R.id.iv_qr_code);

        tvFrom.setText(args.getString(ARG_FROM));
        tvTo.setText(args.getString(ARG_TO));
        tvFare.setText("₹" + args.getInt(ARG_FARE));
        tvDate.setText(args.getString(ARG_DATE));
        tvStatus.setText(args.getString(ARG_STATUS));
        tvPax.setText(args.getInt(ARG_PAX) + " passenger(s)");
        tvId.setText("ID: " + args.getString(ARG_TICKET_ID));

        // Generate QR code bitmap
        String qrData = args.getString(ARG_QR_DATA, "");
        Bitmap qrBitmap = QRGenerator.generate(qrData, 400, 400);
        if (qrBitmap != null) {
            ivQR.setImageBitmap(qrBitmap);
        }
    }
}
