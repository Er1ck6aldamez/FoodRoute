package Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choche on 4/11/2017.
 */

public class HistorialDBHelper extends SQLiteOpenHelper {

    public HistorialDBHelper(Context context) {
        super(context, LugaresVisitados.DATABASE_NAME, null, LugaresVisitados.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LugaresVisitados.NOTES.TABLE_NAME + " ( " +
                LugaresVisitados.NOTES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LugaresVisitados.NOTES.ID_COL + " TEXT, " +
                LugaresVisitados.NOTES.NOMBRE_COL + " TEXT, " +
                LugaresVisitados.NOTES.HORAAPE_COL + " TEXT, " +
                LugaresVisitados.NOTES.HORACIE_COL + " TEXT, " +
                LugaresVisitados.NOTES.LAT_COL + " TEXT, " +
                LugaresVisitados.NOTES.LON_COL + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //El método onUpgrade se ejecuta cada vez que recompilamos e instalamos la app con un
        //nuevo numero de version de base de datos (DATABASE_VERSION), de tal mamera que en este
        // método lo que hacemos es:
        // 1. Con esta sentencia borramos la tabla "notes"
        db.execSQL(LugaresVisitados.NOTES_TABLE_DROP);

        // 2. Con esta sentencia llamamos de nuevo al método onCreate para que se cree de nuevo
        //la tabla "notes" la cual seguramente al cambiar de versión ha tenido modifciaciones
        // en cuanto a su estructura de columnas
        this.onCreate(db);
    }

    /*
    * OPERACIONES CRUD (Create, Read, Update, Delete)
    * A partir de aquí se definen los métodos para insertar, leer, actualizar y borrar registros de
    * la base de datos (BD)
    * */

    public void insertNote(Lugares book){
        //Con este método insertamos las notas nuevas que el usuario vaya creando

        // 1. Obtenemos una reference de la BD con permisos de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Creamos un obejto de tipo ContentValues para agregar los pares
        // de Claves de Columna y Valor
        ContentValues values = new ContentValues();
        //values.put(LugaresVisitados.NOTES.ID_COL,book.getId());
        values.put(LugaresVisitados.NOTES.NOMBRE_COL, book.getNombre()); // Titulo
        values.put(LugaresVisitados.NOTES.HORAAPE_COL, book.getHoraApertura()); // Titulo
        values.put(LugaresVisitados.NOTES.HORACIE_COL, book.getHoraCierre()); // Descripción
        values.put(LugaresVisitados.NOTES.LAT_COL, book.getLatitud());
        values.put(LugaresVisitados.NOTES.LON_COL, book.getLongitud());

        // 3. Insertamos los datos en la tabla "notes"
        db.insert(LugaresVisitados.NOTES.TABLE_NAME, null, values);

        // 4. Cerramos la conexión comn la BD
        db.close();
    }

    //Obtener uan Nota dado un ID
    public Lugares getNoteById(int id){
        // Declaramos un objeto Note para instanciarlo con el resultado del query
        Lugares aNote = null;

        // 1. Obtenemos una reference de la BD con permisos de lectura
        SQLiteDatabase db = this.getReadableDatabase();

        //Definimos un array con los nombres de las columnas que deseamos sacar
        String[] COLUMNS = {LugaresVisitados.NOTES.ID_COL, LugaresVisitados.NOTES.NOMBRE_COL, LugaresVisitados.NOTES.HORAAPE_COL, LugaresVisitados.NOTES.HORACIE_COL,LugaresVisitados.NOTES.LAT_COL,LugaresVisitados.NOTES.LON_COL};


        // 2. Construimos el query
        Cursor cursor =
                db.query(LugaresVisitados.NOTES.TABLE_NAME,  //Nombre de la tabla
                        COLUMNS, // b. Nombre de las Columnas
                        " id = ?", // c. Columnas de la clausula WHERE
                        new String[] { String.valueOf(id) }, // d. valores de las columnas de la clausula WHERE
                        null, // e. Clausula Group by
                        null, // f. Clausula having
                        null, // g. Clausula order by
                        null); // h. Limte de regsitros

        // 3. Si hemos obtenido algun resultado entonces sacamos el primero de ellos ya que se supone
        //que ha de existir un solo registro para un id
        if (cursor != null) {
            cursor.moveToFirst();
            // 4. Contruimos el objeto Note
            aNote = new Lugares();
            aNote.setNombre(cursor.getString(0));
            aNote.setHoraApertura(cursor.getString(1));
            aNote.setHoraApertura(cursor.getString(2));
            aNote.setLatitud(Double.parseDouble(cursor.getString(3)));
            aNote.setLongitud(Double.parseDouble(cursor.getString(4)));

        }

        // 5. Devolvemos le objeto Note
        return aNote;
    }

    // Obtener todas las notas
    public Cursor getAllNotes() {

        return getReadableDatabase()
                .query(
                        LugaresVisitados.NOTES.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    //Actualizar los datos en una nota
    public int updateNote(Lugares note) {

        // 1. Obtenemos una reference de la BD con permisos de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Creamos el objeto ContentValues con las claves "columna"/valor
        // que se desean actualizar
        ContentValues values = new ContentValues();
        values.put(LugaresVisitados.NOTES.NOMBRE_COL, note.getNombre()); // Titulo
        values.put(LugaresVisitados.NOTES.HORAAPE_COL, note.getHoraApertura()); // Titulo
        values.put(LugaresVisitados.NOTES.HORACIE_COL, note.getHoraCierre()); // Descripción
        values.put(LugaresVisitados.NOTES.LAT_COL, note.getLatitud());
        values.put(LugaresVisitados.NOTES.LON_COL, note.getLongitud());

        // 3. Actualizamos el registro con el método update el cual devuelve la cantidad
        // de registros actualizados
        int i = db.update(LugaresVisitados.NOTES.TABLE_NAME, //table
                values, // column/value
                LugaresVisitados.NOTES.ID_COL+" = ?", // selections
                new String[] { String.valueOf(note.getId()) }); //selection args

        // 4. Cerramos la conexión a la BD
        db.close();

        // Devolvemos la cantidad de registros actualizados
        return i;
    }

    // Borrar una Nota
    public void deleteNote(Lugares note) {

        // 1. Obtenemos una reference de la BD con permisos de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Borramos el registro
        db.delete(LugaresVisitados.NOTES.TABLE_NAME,
                LugaresVisitados.NOTES.ID_COL+" = ?",
                new String[] { String.valueOf(note.getId()) });

        // 3. Cerramos la conexión a la Bd
        db.close();
    }

    // Borrar todas Nota
    public void deleteAll(Lugares note) {

        // 1. Obtenemos una reference de la BD con permisos de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Borramos el registro
        db.delete(LugaresVisitados.NOTES.TABLE_NAME,null,null);
        // 3. Cerramos la conexión a la Bd
        db.close();
    }
}
