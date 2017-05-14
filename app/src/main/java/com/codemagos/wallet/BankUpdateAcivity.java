package com.codemagos.wallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codemagos.wallet.Spstore.SharedPreferenceStore;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankUpdateAcivity extends AppCompatActivity {
    RadioGroup radio_banks;
    Button btn_update;
    String bank;
    String bank_number = "";
    ImageView image_logo;
    SharedPreferenceStore spStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_update_acivity);
        radio_banks = (RadioGroup) findViewById(R.id.radio_banks);
        btn_update = (Button) findViewById(R.id.btn_update);
        image_logo = (ImageView) findViewById(R.id.image_logo);
        spStore = new SharedPreferenceStore(getApplicationContext());
        radio_banks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int bank_id = radio_banks.getCheckedRadioButtonId();
                RadioButton radio_bank = (RadioButton) findViewById(bank_id);
                bank = (String) radio_bank.getTag();
                switch (bank_id) {
                    case R.id.radio_federal:
                        image_logo.setImageResource(R.drawable.federal);
                        break;
                    case R.id.radio_axis:
                        image_logo.setImageResource(R.drawable.axis);
                        break;
                    case R.id.radio_sib:
                        image_logo.setImageResource(R.drawable.sib);
                        break;
                }
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bank_id = radio_banks.getCheckedRadioButtonId();
                RadioButton radio_bank = (RadioButton) findViewById(bank_id);
                bank = (String) radio_bank.getTag();
                switch (bank_id) {
                    case R.id.radio_federal:
                        bank_number = "8431900900";
                        break;
                    case R.id.radio_axis:
                        bank_number = "";
                        break;
                    case R.id.radio_sib:
                        bank_number = "09223008488";
                        break;
                }
                if (!bank_number.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bank_number));
                    spStore.setBank(bank);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Toast.makeText(getApplicationContext(), "Permission Required..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(getApplicationContext(), "Updating your Balance....", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Select One Bank" +
                            "", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
