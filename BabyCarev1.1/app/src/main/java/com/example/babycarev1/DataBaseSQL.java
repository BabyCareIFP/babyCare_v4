package com.example.babycarev1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.CellSignalStrength;

import java.util.ArrayList;

public class DataBaseSQL extends SQLiteOpenHelper {

    /*TODO CRUD reservas
    TODO propagar foraneas idUsuario y idCuidador a reservas

     */


    protected SQLiteDatabase db;

    //CONSTRUCTOR
    public DataBaseSQL(Context context) {
        super(context, "babycareDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE table usuario (idUsuario integer primary key autoincrement not null, nombre TEXT, apellidos TEXT, dni TEXT, telefono TEXT, fechaNacimiento TEXT, sexo TEXT, nombreUsuario TEXT, contraseniaUsuario TEXT, correo TEXT, nacionalidad TEXT, direccion TEXT)");
        db.execSQL("CREATE table cuidadores (idCuidador integer primary key autoincrement not null, nombre TEXT, apellidos TEXT, telefono TEXT, fechaNacimiento TEXT, sexo TEXT, experiencia TEXT, disponibilidad TEXT, puntuacion INTEGER, resenias TEXT)");

        /*db.execSQL("CREATE table tutores (idTutor integer primary key autoincrement not null, numeroHijos integer, hijos text)");
        db.execSQL("CREATE table hijos (idHijo integer primary key autoincrement not null, nota text)");
        db.execSQL("CREATE table reservas (idReserva integer primary key autoincrement not null, nota text)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS cuidadores");
    }

    //CRUD USUARIOS---------------------------------------------------------------------------------
    //INSERT USUARIO
    public boolean insertarUsuario(String nombreUsuario, String correoUsuario, String contraseniaUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO usuarioTest VALUES (null, '" + nombreUsuario + "', '" + correoUsuario + "', '" + contraseniaUsuario + "')");
        return true;
    }

    public boolean insertarPerfil(String nombre, String apellidos, String dni, String telefono, String fechaNacimiento, String sexo, String nombreUsuario, String contraseniaUsuario, String correo, String nacionalidad, String direccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO usuario VALUES (null, '" + nombre + "', '" + apellidos + "', '"+dni+"', '"+telefono+"', '"+fechaNacimiento+"', '"+sexo+"', '"+nombreUsuario + "', '"+contraseniaUsuario+"', '"+correo+"', '"+nacionalidad+"', '"+direccion+"')");
        return true;
    }

    //SELECT USUARIO
    public ArrayList<String> consultarUsuario(String correoUsuarioActual){

        ArrayList<String> datos = new ArrayList<String>();

        //Abro la BBDD en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = null;
        res = db.rawQuery("SELECT * FROM usuario WHERE correo = '"+correoUsuarioActual+"'", null);
        res.moveToLast();

        if (res.getCount() > 0){
            res.moveToFirst();
            while (!res.isAfterLast()){

                datos.add(res.getString(res.getColumnIndex("nombre")));
                datos.add(res.getString(res.getColumnIndex("apellidos")));
                datos.add(res.getString(res.getColumnIndex("dni")));
                datos.add(res.getString(res.getColumnIndex("telefono")));
                datos.add(res.getString(res.getColumnIndex("fechaNacimiento")));
                datos.add(res.getString(res.getColumnIndex("sexo")));
                datos.add(res.getString(res.getColumnIndex("nombreUsuario")));
                datos.add(res.getString(res.getColumnIndex("contraseniaUsuario")));
                datos.add(res.getString(res.getColumnIndex("correo")));
                datos.add(res.getString(res.getColumnIndex("nacionalidad")));
                datos.add(res.getString(res.getColumnIndex("direccion")));

                res.moveToNext();
            }
        }

        return datos;
    }

    //SELECT USUARIOS
    public ArrayList<String> consultarUsuarios() {
        //DECLARACION DE VARIABLES A USAR
        db = this.getReadableDatabase();
        ArrayList<String> filas = new ArrayList<String>();
        Cursor res = null;
        String contenido = "";
        int cantidadResultados = 0;

        //IMPLEMENTACION DEL METODO
        res = db.rawQuery("SELECT * FROM usuario", null);
        cantidadResultados = (int) DatabaseUtils.queryNumEntries(db, "usuario");
        if (cantidadResultados > 0)
        {
            res.moveToFirst();
            while(res.isAfterLast()==false)
            {
                contenido = res.getString(res.getColumnIndex("nombre"))
                        + ".-" + res.getString(res.getColumnIndex("apellidos"))
                        + ".-" + res.getString(res.getColumnIndex("dni"))
                        + ".-" + res.getString(res.getColumnIndex("telefono"))
                        + ".-" + res.getString(res.getColumnIndex("fechaNacimiento"))
                        + ".-" + res.getString(res.getColumnIndex("direccion"))
                        + ".-" + res.getString(res.getColumnIndex("nacionalidad"))
                        + ".-" + res.getString(res.getColumnIndex("sexo"))
                        + ".-" + res.getString(res.getColumnIndex("nombreUsuario"))
                        + ".-" + res.getString(res.getColumnIndex("contraseniaUsuario"))
                        + ".-" + res.getString(res.getColumnIndex("correo"));
                filas.add(contenido);
                res.moveToNext();
            }
        }
        return filas;
    }

    //LOG IN
    @SuppressLint("Range")
    public boolean contraseniaCorrecta(String correoIntroducido, String contraseniaIntroducida) {

        String contraseniaUsuario = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("SELECT contraseniaUsuario FROM usuario WHERE correo = '" + correoIntroducido + "'", null);
        res.moveToLast();
        if (res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {

                contraseniaUsuario = res.getString(res.getColumnIndex("contraseniaUsuario"));
                res.moveToNext();
            }

        }
        if(contraseniaIntroducida.equals(contraseniaUsuario)){
            return true;
        }
        else{
            return false;
        }
    }

    //CRUD CUIDADORES------------------------------------------------------------------------------
    //INSERT CUIDADOR
    public boolean insertarCuidador(String nombre, String apellidos, String telefono, String fechaNacimiento, String sexo, String experiencia, String disponibilidad, String puntuacion, String resenias) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO cuidadores VALUES (null, '" + nombre + "', '" + apellidos + "', '"+telefono+"', '"+fechaNacimiento+"', '"+sexo+"', '"+experiencia + "', '"+disponibilidad+"', '"+puntuacion+"', '"+resenias+"')");
        return true;
    }

    //SELECT CUDADORES
    public ArrayList<String> consultarCuidadores() {
        //DECLARACION DE VARIABLES A USAR
        db = this.getReadableDatabase();
        ArrayList<String> filas = new ArrayList<String>();
        Cursor res = null;
        String contenido = "";
        int cantidadResultados = 0;

        //IMPLEMENTACION DEL METODO
        res = db.rawQuery("SELECT * FROM cuidadores", null);
        cantidadResultados = (int) DatabaseUtils.queryNumEntries(db, "cuidadores");
        if (cantidadResultados > 0)
        {
            res.moveToFirst();
            while(res.isAfterLast()==false)
            {
                contenido = res.getString(res.getColumnIndex("nombre"))
                        + ".-" + res.getString(res.getColumnIndex("sexo"))
                        + ".-" + res.getString(res.getColumnIndex("experiencia"))
                        + ".-" + res.getString(res.getColumnIndex("disponibilidad"))
                        + ".-" + res.getInt(res.getColumnIndex("puntuacion"));
                filas.add(contenido);
                res.moveToNext();
            }
        }
        return filas;
    }

    //SELECT CUIDADOR
    public ArrayList<String> consultarCuidador(int idCuidadorPulsado)
    {
        //DECLARACION DE VARIABLES A USAR
        ArrayList<String> cuidador = new ArrayList<String>();
        int id = 0;
        String nombre = "";
        String apellidos = "";
        String sexo = "";
        String experiencia = "";
        String disponibilidad = "";
        int puntuacion = 0;
        String resenias = "";
        Cursor res;
        db = this.getReadableDatabase();

        //IMPLEMENTACION
        res = db.rawQuery("SELECT * FROM cuidadores WHERE idCuidador = '"+idCuidadorPulsado+"'", null);
        if (res.getCount()>0)
        {
            res.moveToFirst();
            while(res.isAfterLast()==false)
            {
                //id = res.getInt(res.getColumnIndex("id"));
                nombre = res.getString(res.getColumnIndex("nombre"));
                apellidos = res.getString(res.getColumnIndex("apellidos"));
                sexo = res.getString(res.getColumnIndex("sexo"));
                experiencia = res.getString(res.getColumnIndex("experiencia"));
                disponibilidad = res.getString(res.getColumnIndex("disponibilidad"));
                puntuacion = res.getInt(res.getColumnIndex("puntuacion"));
                resenias = res.getString(res.getColumnIndex("disponibilidad"));

                //cuidador.add(Integer.toString(id));
                cuidador.add(nombre);
                cuidador.add(apellidos);
                cuidador.add(sexo);
                cuidador.add(experiencia);
                cuidador.add(disponibilidad);
                cuidador.add(Integer.toString(puntuacion));
                cuidador.add(resenias);
                res.moveToNext();
            }
        }
        return cuidador;
    }
    //UPDATE CUIDADOR
    //DELETE CUIDADOR

}