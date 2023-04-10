package hansel.ifab.kuis1pab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper myDB;
    private FloatingActionButton fabTambah;
    private RecyclerView rvBandara;
    private AdapterBandara adBandara;
    private ArrayList<String> arrID, arrNama, arrKota, arrAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBandara = findViewById(R.id.rv_bandara);
        myDB = new MyDatabaseHelper(MainActivity.this);
        fabTambah = findViewById(R.id.fab_tambah);
        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilPlayer();
    }

    private void SQLiteToArrayList(){
        Cursor varCursor = myDB.bacaDataBandara();
        if (varCursor.getCount() == 0)
        {
            Toast.makeText(this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (varCursor.moveToNext())
            {
                arrID.add(varCursor.getString(0));
                arrNama.add(varCursor.getString(1));
                arrKota.add(varCursor.getString(2));
                arrAlamat.add(varCursor.getString(3));
            }
        }
    }

    private void tampilPlayer(){
        arrID = new ArrayList<>();
        arrNama = new ArrayList<>();
        arrKota = new ArrayList<>();
        arrAlamat = new ArrayList<>();

        SQLiteToArrayList();

        adBandara = new AdapterBandara(MainActivity.this, arrID, arrNama, arrKota, arrAlamat);
        rvBandara.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvBandara.setAdapter(adBandara);
    }

}