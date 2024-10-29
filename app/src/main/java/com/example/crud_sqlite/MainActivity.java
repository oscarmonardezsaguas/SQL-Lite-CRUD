package com.example.crud_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText ed_nombre,ed_apellido,ed_edad;
    private Button b_agregar,b_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);

        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);

        // boton ver
        b_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), Leer.class);
                startActivity(i);
            }
        });

        // boton agregar registro
        b_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
    }

    // metodo insertar registro
    public void insertar()
    {
        try
        {
            //Se obtienen los valores de los campos de texto de la interfaz de usuario,
            //correspondientes a los atributos de una persona: nombre, apellido, y edad.
            //Estos datos se extraen utilizando los métodos getText() y
            //toString() para convertir el valor ingresado en un String.
            String nombre = ed_nombre.getText().toString();
            String apellido = ed_apellido.getText().toString();
            String edad = ed_edad.getText().toString();


            // Utiliza el método openOrCreateDatabase() para abrir la base de datos SQLite llamada
            //"BD_EJEMPLO". Si no existe, el método la creará. La base de datos es abierta en modo privado
            //(Context.MODE_PRIVATE), lo que significa que solo la aplicación que la creó puede acceder a ella.
            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE,null);


            //Ejecuta una sentencia SQL que crea la tabla persona si no existe previamente.
            //Esta tabla tiene cuatro columnas: id (entero y clave primaria que se autoincrementa),
            //nombre, apellido, y edad (todos de tipo VARCHAR).
            db.execSQL("CREATE TABLE IF NOT EXISTS persona(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre VARCHAR,apellido VARCHAR,edad VARCHAR)");


            //Se define una sentencia SQL de inserción que utiliza parámetros para evitar la inyección de SQL.
            //Esta sentencia almacena los datos en las columnas correspondientes de la tabla persona.
            String sql = "insert into persona(nombre,apellido,edad)values(?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);

            //Utilizando el objeto SQLiteStatement, se asignan los valores que el usuario ingresó
            //en la interfaz a los tres parámetros de la sentencia SQL (nombre, apellido, y edad),
            //utilizando los métodos bindString().
            statement.bindString(1,nombre);
            statement.bindString(2,apellido);
            statement.bindString(3,edad);

            //Se ejecuta la sentencia preparada, lo que inserta los datos en la tabla persona.
            statement.execute();

            Toast.makeText(this,"Datos agregados satisfactoriamente en la base de datos.",Toast.LENGTH_LONG).show();

            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_edad.setText("");
            ed_nombre.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }
}