package hansel.ifab.kuis1pab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UbahActivity extends AppCompatActivity {
    private EditText etNama, etKota, etAlamat;
    private Button btnUbah;
    MyDatabaseHelper myDB = new MyDatabaseHelper(UbahActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etKota = findViewById(R.id.et_kota);
        etAlamat = findViewById(R.id.et_alamat);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent varIntent = getIntent();
        String id = varIntent.getStringExtra("varID");
        String nama = varIntent.getStringExtra("varNama");
        String kota = varIntent.getStringExtra("varNomor");
        String alamat = varIntent.getStringExtra("varKlub");

        etNama.setText(nama);
        etKota.setText(kota);
        etAlamat.setText(alamat);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNama, getKota, getAlamat;

                getNama = etNama.getText().toString();
                getKota = etKota.getText().toString();
                getAlamat = etAlamat.getText().toString();

                if (getNama.trim().equals("")) {
                    etNama.setError("Nama Bandara Tidak Boleh Kosong");
                } else if (getKota.trim().equals("")) {
                    etKota.setError("Kota Bandara Tidak Boleh Kosong");
                } else if (getAlamat.trim().equals("")) {
                    etAlamat.setError("Alamat Bandara Tidak Boleh Kosong");
                } else {
                    long eks = myDB.ubahBandara(id, getNama, getKota, getAlamat);
                    if (eks == -1)
                    {
                        Toast.makeText(UbahActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT).show();
                        etNama.requestFocus();
                    }
                    else {
                        Toast.makeText(UbahActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}