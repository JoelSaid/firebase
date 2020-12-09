package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etAPaterno, etAMaterno, etSexo, etDireccion, etPuesto, etTelefono;
    FloatingActionButton fabGuardar, fabListar;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String updateId, updateNombre, updateApaterno, updateAmaterno, updateSexo, updateDireccion, updatePuesto, updateTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etAPaterno = findViewById(R.id.etAPaterno);
        etAMaterno = findViewById(R.id.etAMaterno);
        etSexo = findViewById(R.id.etSexo);
        etDireccion = findViewById(R.id.etDireccion);
        etPuesto = findViewById(R.id.etPuesto);
        etTelefono = findViewById(R.id.etTelefono);

        fabGuardar = findViewById(R.id.fabGuardar);
        fabListar = findViewById(R.id.fabListar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar registro");


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Actualizar Datos");

            updateId = bundle.getString("updateId");
            updateNombre = bundle.getString("updateNombre");
            updateApaterno = bundle.getString("updateApaterno");
            updateAmaterno = bundle.getString("updateAmaterno");
            updateSexo = bundle.getString("updateSexo");
            updateDireccion = bundle.getString("updateDireccion");
            updatePuesto = bundle.getString("updatePuesto");
            updateTelefono = bundle.getString("updateTelefono");

            etNombre.setText(updateNombre);
            etAPaterno.setText(updateApaterno);
            etAMaterno.setText(updateAmaterno);
            etSexo.setText(updateSexo);
            etDireccion.setText(updateDireccion);
            etPuesto.setText(updatePuesto);
            etTelefono.setText(updateTelefono);

        } else {
            actionBar.setTitle("Agregar");
        }


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = updateId;
                    String nombre = etNombre.getText().toString().trim();
                    String apaterno = etAPaterno.getText().toString().trim();
                    String amaterno = etAMaterno.getText().toString().trim();
                    String sexo = etSexo.getText().toString().trim();
                    String direccion = etDireccion.getText().toString().trim();
                    String puesto = etPuesto.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();

                    actualizarDatos(id, nombre, apaterno, amaterno, sexo, direccion, puesto, telefono);

                } else {
                    String nombre = etNombre.getText().toString().trim();
                    String apaterno = etAPaterno.getText().toString().trim();
                    String amaterno = etAMaterno.getText().toString().trim();
                    String sexo = etSexo.getText().toString().trim();
                    String direccion = etDireccion.getText().toString().trim();
                    String puesto = etPuesto.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();

                    cargarDatos(nombre, apaterno, amaterno, sexo, direccion, puesto, telefono);
                }
            }
        });


        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivityPerson.class));
                finish();
            }
        });

    }


    private void cargarDatos(String nombre, String apaterno, String amaterno, String sexo, String direccion, String puesto, String telefono) {
        progressDialog.setTitle("Agregar datos");
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("apaterno", apaterno);
        doc.put("amaterno", amaterno);
        doc.put("sexo", sexo);
        doc.put("direccion", direccion);
        doc.put("puesto", puesto);
        doc.put("telefono", telefono);


        db.collection("Documents").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Datos almacenados con éxito...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void actualizarDatos(String id, String nombre, String apaterno, String amaterno, String sexo, String direccion, String puesto, String telefono) {
        progressDialog.setTitle("Actualizando datos a Firebase");
        progressDialog.show();

        /*
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("apaterno", apaterno);
        doc.put("amaterno", amaterno);
        doc.put("sexo", sexo);
        doc.put("direccion", direccion);
        doc.put("facebook", facebook);
        doc.put("instagram", instagram);

         */


        db.collection("Documents")
                .document(id).update(
                "nombre", nombre,
                "apaterno", apaterno,
                "amaterno", amaterno,
                "sexo", sexo,
                "direccion", direccion,
                "puesto", puesto,
                "telefono", telefono
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Actualización exitosa...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}