package Modelo;

import android.provider.BaseColumns;

/**
 * Created by Choche on 4/11/2017.
 */

public class LugaresVisitados {
    //Nombre del esquema de Base de Datos
    public static final String DATABASE_NAME = "Historial";
    //Version de la Base de Datos (Este parámetro es importante  )
    public static final int DATABASE_VERSION = 1;

    public static abstract class NOTES implements BaseColumns {
        //Nombre de la tabla
        public static final String TABLE_NAME = "restaurantes";
        //Nombre de las Columnas que contiene la tabla
        public static final String ID_COL = "id";
        public static final String NOMBRE_COL = "nombre";
        public static final String HORAAPE_COL = "horaA";
        public static final String HORACIE_COL = "horaC";
        public static final String LAT_COL = "latitud";
        public static final String LON_COL = "longitud";
    }

    //Setencia SQL que permite crear la tabla Notes
    public static final String NOTES_TABLE_CREATE =
            "CREATE TABLE " + NOTES.TABLE_NAME + " (" +
                    NOTES.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTES.NOMBRE_COL + " TEXT, " +
                    NOTES.HORAAPE_COL + " TEXT, " +
                    NOTES.HORACIE_COL + " TEXT, " +
                    NOTES.LAT_COL + " TEXT, " +
                    NOTES.LON_COL + " TEXT);";

    //Setencia SQL que permite eliminar la tabla Notes
    public static final String NOTES_TABLE_DROP = "DROP TABLE IF EXISTS " + NOTES.TABLE_NAME;
}
