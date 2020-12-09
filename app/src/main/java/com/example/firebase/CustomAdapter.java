package com.example.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivityPerson listActivityPerson;
    List<PersonModel> mPersonModelList;

    public CustomAdapter(ListActivityPerson listActivity, List<PersonModel> personModelList) {
        this.listActivityPerson = listActivity;
        this.mPersonModelList = personModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nombre = mPersonModelList.get(position).getNombre();
                String apaterno = mPersonModelList.get(position).getApaterno();
                String amaterno = mPersonModelList.get(position).getAmaterno();
                Toast.makeText(listActivityPerson, nombre + " " + apaterno + " " + amaterno, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivityPerson);
                String[] options = {"Actualizar datos", "Eliminar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            String id = mPersonModelList.get(position).getId();
                            String nombre = mPersonModelList.get(position).getNombre();
                            String apaterno = mPersonModelList.get(position).getApaterno();
                            String amaterno = mPersonModelList.get(position).getAmaterno();
                            String sexo = mPersonModelList.get(position).getSexo();
                            String direccion = mPersonModelList.get(position).getDireccion();
                            String puesto = mPersonModelList.get(position).getPuesto();
                            String telefono = mPersonModelList.get(position).getTelefono();

                            Intent actualizarDatos = new Intent(listActivityPerson, com.example.firebase.MainActivity.class);
                            actualizarDatos.putExtra("updateId", id);
                            actualizarDatos.putExtra("updateNombre", nombre);
                            actualizarDatos.putExtra("updateApaterno", apaterno);
                            actualizarDatos.putExtra("updateAmaterno", amaterno);
                            actualizarDatos.putExtra("updateSexo", sexo);
                            actualizarDatos.putExtra("updateDireccion", direccion);
                            actualizarDatos.putExtra("updatePuesto", puesto);
                            actualizarDatos.putExtra("updateTelefono", telefono);

                            listActivityPerson.startActivity(actualizarDatos);
                            // String id, String nombre, String apaterno, String amaterno, String sexo, String direccion, String facebook, String instagram
                        }

                        if (which == 1) {
                            listActivityPerson.eliminarRegistro(position);
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvNombre.setText(
                mPersonModelList.get(i).getNombre()
                        + " " + mPersonModelList.get(i).getApaterno()
                        + " " + mPersonModelList.get(i).getAmaterno()
        );
    }

    @Override
    public int getItemCount() {
        return mPersonModelList.size();
    }
}
