package hansel.ifab.kuis1pab2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBandara extends RecyclerView.Adapter<AdapterBandara.ViewHolderBandara> {
    private Context ctx;
    private ArrayList arrID, arrNama, arrKota, arrAlamat;

    public AdapterBandara(Context ctx, ArrayList arrID, ArrayList arrNama, ArrayList arrKota, ArrayList arrAlamat) {
        this.ctx = ctx;
        this.arrID = arrID;
        this.arrNama = arrNama;
        this.arrKota = arrKota;
        this.arrAlamat = arrAlamat;
    }

    @NonNull
    @Override
    public AdapterBandara.ViewHolderBandara onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_card, parent, false);
        return new ViewHolderBandara(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBandara.ViewHolderBandara holder, int position) {
        holder.tvID.setText(arrID.get(position).toString());
        holder.tvNama.setText(arrNama.get(position).toString());
        holder.tvKota.setText(arrKota.get(position).toString());
        holder.tvAlamat.setText(arrAlamat.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return arrNama.size();
    }

    public class ViewHolderBandara extends RecyclerView.ViewHolder {
        private TextView tvID, tvNama, tvKota, tvAlamat;
        public ViewHolderBandara(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKota = itemView.findViewById(R.id.tv_kota);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda Memilih " + tvNama.getText().toString() + " Pilih Perintah yang Anda Inginkan");
                    pesan.setCancelable(true);

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent varIntent = new Intent(ctx, UbahActivity.class);
                            varIntent.putExtra("varID", tvID.getText().toString());
                            varIntent.putExtra("varNama", tvNama.getText().toString());
                            varIntent.putExtra("varKota", tvKota.getText().toString());
                            varIntent.putExtra("varAlamat", tvAlamat.getText().toString());
                            ctx.startActivity(varIntent);
                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(ctx);
                            long eksekusi = myDB.hapusBandara(tvID.getText().toString());
                            if (eksekusi == -1)
                            {
                                Toast.makeText(ctx, "Gagal Menghapus Data!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ctx, "Sukses Menghapus Data!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                ((MainActivity) ctx).onResume();
                            }
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }
    }
}
