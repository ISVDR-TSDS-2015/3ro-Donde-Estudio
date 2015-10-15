package com.javierpintosettlin.dondeestudio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Javier on 02/10/2015.
 */
public class ManejadorBase extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "DondeEstudioDB";

    // Contacts table name
    private static final String TABLE_CARRERA = "Carreras";

    // Contacts Table Columns names
    private static final String KEY_IDCARRERA = "id_carrera";
    private static final String KEY_NOMBRECARRERA = "nombre_carrera";
    private static final String KEY_IDCATEGORIA = "id_categoria";
    private static final String KEY_NOMBRECATEGORIA = "nombre_categoria";
    private static final String KEY_IDINSTITUCION = "id_institucion";
    private static final String KEY_NOMBREINSTITUCION = "nombre_institucion";
    private static final String KEY_GEOLATITUD = "geolatitud";
    private static final String KEY_GEOLONGITUD = "geolongitud";

        //Constructor de la Clase
        public ManejadorBase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLA_CARRERAS = "CREATE TABLE " + TABLE_CARRERA + "("
                    + KEY_IDCARRERA + " INTEGER,"
                    + KEY_NOMBRECARRERA + " TEXT,"
                    + KEY_IDCATEGORIA + " INTEGER,"
                    + KEY_NOMBRECATEGORIA + " TEXT,"
                    + KEY_IDINSTITUCION + " INTEGER,"
                    + KEY_NOMBREINSTITUCION + " TEXT,"
                    + KEY_GEOLATITUD + " TEXT,"
                    + KEY_GEOLONGITUD + " TEXT" + ")";
            db.execSQL(CREATE_TABLA_CARRERAS);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARRERA);

            // Create tables again
            onCreate(db);
        }

        /**
         * All CRUD(Create, Read, Update, Delete) Operations
         */

        // Borrar Todas las Carreras
        void borrarTodasCarreras() {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("DELETE FROM " + TABLE_CARRERA);

            db.close();
        }

        // Adding new Carrera
        void addCarrera(CarrerasWS carrera) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_IDCARRERA, carrera.getIdCarrera()); //
            values.put(KEY_NOMBRECARRERA, carrera.getNombreCarrera()); //
            values.put(KEY_IDCATEGORIA, carrera.getIdCategoria()); //
            values.put(KEY_NOMBRECATEGORIA, carrera.getNombreCategoria()); //
            values.put(KEY_IDINSTITUCION, carrera.getIdInstitucion()); //
            values.put(KEY_NOMBREINSTITUCION, carrera.getNombreInstitucion()); //
            values.put(KEY_GEOLATITUD, carrera.getGeolatitud()); //
            values.put(KEY_GEOLONGITUD, carrera.getGeolongitud()); //

            // Inserting Row
            db.insert(TABLE_CARRERA, null, values);
            db.close(); // Closing database connection
        }

        // All categorias Cursor
        public Cursor getCursorCategorias(long idCarrera) {

            // Select All Query
            String selectQuery = "SELECT DISTINCT " + KEY_IDCATEGORIA + " _id, " +
                    KEY_NOMBRECATEGORIA +
                    " FROM " + TABLE_CARRERA +
                    " WHERE 1=1";

            if (idCarrera != -1)
                selectQuery = selectQuery + " AND " + KEY_IDCARRERA + " = " + idCarrera;

            selectQuery = selectQuery + " ORDER BY " + KEY_NOMBRECATEGORIA;

            SQLiteDatabase db = this.getWritableDatabase();

            return db.rawQuery(selectQuery, null);
        }

        // All Carreras Cursor
        public Cursor getCursorCarreras(long idCategoria) {

            // Select All Query
            String selectQuery = "SELECT DISTINCT " + KEY_IDCARRERA + " _id, " +
                    KEY_NOMBRECARRERA +
                    " FROM " + TABLE_CARRERA +
                    " WHERE 1=1";

            if (idCategoria != -1)
                selectQuery = selectQuery + " AND " + KEY_IDCATEGORIA + " = " + idCategoria;

            selectQuery = selectQuery + " ORDER BY " + KEY_NOMBRECARRERA ;

            SQLiteDatabase db = this.getWritableDatabase();

            return db.rawQuery(selectQuery, null);
        }

        // Getting All Instituciones
        public List<Institucion> getInstituciones(long idCategoria, long idCarrera) {
            List<Institucion> listInstitucion = new ArrayList<Institucion>();
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + KEY_IDINSTITUCION + ", " +
                    KEY_NOMBREINSTITUCION + ", " +
                    KEY_GEOLATITUD + ", " +
                    KEY_GEOLONGITUD +
                    " FROM " + TABLE_CARRERA +
                    " WHERE 1=1";
            if (idCategoria != -1 )
                selectQuery = selectQuery + " AND " + KEY_IDCATEGORIA + " = " + idCategoria;
            if (idCarrera != -1 )
                selectQuery = selectQuery + " AND " + KEY_IDCARRERA + " = " + idCarrera;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Institucion institucion = new Institucion();
                    institucion.setIdInstitucion(Integer.parseInt(cursor.getString(0)));
                    institucion.setNombreInstitucion(cursor.getString(1));
                    institucion.setGeoLatitud(cursor.getString(2));
                    institucion.setGeoLongitud(cursor.getString(3));

                    // Adding persona to list
                    listInstitucion.add(institucion);
                } while (cursor.moveToNext());
            }

            // return lista de personas
            return listInstitucion;
        }

    // Get Institucion
    public Institucion getInstitucion(long idInstitucion) {
        Institucion institucion = new Institucion();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + KEY_IDINSTITUCION + ", " +
                KEY_NOMBREINSTITUCION + ", " +
                KEY_GEOLATITUD + ", " +
                KEY_GEOLONGITUD +
                " FROM " + TABLE_CARRERA +
                " WHERE " + KEY_IDINSTITUCION + " = " + idInstitucion;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
                institucion.setIdInstitucion(Integer.parseInt(cursor.getString(0)));
                institucion.setNombreInstitucion(cursor.getString(1));
                institucion.setGeoLatitud(cursor.getString(2));
                institucion.setGeoLongitud(cursor.getString(3));
        }

        // return lista de personas
        return institucion;
    }

    // Getting All Instituciones  Cursor
    public Cursor getInstitucionesCursor(long idCategoria, long idCarrera) {

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + KEY_IDINSTITUCION + " _id, " +
                KEY_NOMBREINSTITUCION + ", " +
                KEY_GEOLATITUD + ", " +
                KEY_GEOLONGITUD +
                " FROM " + TABLE_CARRERA +
                " WHERE 1=1";
        if (idCategoria != -1 )
            selectQuery = selectQuery + " AND " + KEY_IDCATEGORIA + " = " + idCategoria;
        if (idCarrera != -1 )
            selectQuery = selectQuery + " AND " + KEY_IDCARRERA + " = " + idCarrera;

        selectQuery = selectQuery + " ORDER BY " + KEY_NOMBREINSTITUCION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return lista de instituciones
        return cursor;
    }

//
//        // Getting personas por ID
//        Persona getPersonaPorId(int id) {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            Cursor cursor = db.query(TABLE_PERSONA, new String[] { KEY_ID,
//                            KEY_NOMBRE, KEY_DNI }, KEY_ID + "=?",
//                    new String[] { String.valueOf(id) }, null, null, null, null);
//            if (cursor != null)
//                cursor.moveToFirst();
//
//            Persona persona = new Persona(Integer.parseInt(cursor.getString(0)),
//                    cursor.getString(1), cursor.getString(2));
//            // return contact
//            return persona;
//        }
//
//        // Getting All Personas
//        public List<Persona> getAllPersonas() {
//            List<Persona> listPersonas = new ArrayList<Persona>();
//            // Select All Query
//            String selectQuery = "SELECT  * FROM " + TABLE_PERSONA;
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    Persona persona = new Persona();
//                    persona.set_id(Integer.parseInt(cursor.getString(0)));
//                    persona.set_nombre(cursor.getString(1));
//                    persona.set_dni(cursor.getString(2));
//                    // Adding persona to list
//                    listPersonas.add(persona);
//                } while (cursor.moveToNext());
//            }
//
//            // return lista de personas
//            return listPersonas;
//        }
//
//        // All Personas Cursor
//        public Cursor getCursorAllPersonas() {
//
//            // Select All Query
//            String selectQuery = "SELECT id _id, nombre, dni FROM " + TABLE_PERSONA;
//
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            return db.rawQuery(selectQuery, null);
//        }
//
//
//
//        // Updating single persona
//        public int updatePersona(Persona persona) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_NOMBRE, persona.get_nombre());
//            values.put(KEY_DNI, persona.get_dni());
//
//            // updating row
//            return db.update(TABLE_PERSONA, values, KEY_ID + " = ?",
//                    new String[] { String.valueOf(persona.get_id()) });
//        }
//
//        // Deleting un persona
//        public void deletePersona(Persona persona) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.delete(TABLE_PERSONA, KEY_ID + " = ?",
//                    new String[] { String.valueOf(persona.get_id()) });
//            db.close();
//        }
//
//
//        // Getting cantidad de personas
//        public int getPersonasCount() {
//            String countQuery = "SELECT  * FROM " + TABLE_PERSONA;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(countQuery, null);
//            cursor.close();
//
//            // return count
//            return cursor.getCount();
//        }
}
