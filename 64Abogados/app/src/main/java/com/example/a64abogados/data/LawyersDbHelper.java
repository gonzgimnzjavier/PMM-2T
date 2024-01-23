package com.example.a64abogados.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a64abogados.data.LawyersContract.LawyerEntry;

/**
 * Manejador de la base de datos
 */
public class LawyersDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lawyers.db";

    public LawyersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LawyerEntry.TABLE_NAME + " ("
                + LawyerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LawyerEntry.ID + " TEXT NOT NULL,"
                + LawyerEntry.NAME + " TEXT NOT NULL,"
                + LawyerEntry.SPECIALTY + " TEXT NOT NULL,"
                + LawyerEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + LawyerEntry.BIO + " TEXT NOT NULL,"
                + LawyerEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + LawyerEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Lawyer("Walter White", "Meth Dealer",
                "300 200 1111", "Jesse we need to coock.",
                "carlos_perez.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Jesse Pinkman", "Meth Dealer",
                "300 200 2222", "Yeah b*tch!.",
                "daniel_samper.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Skyler", "Ex-wife of Walter  White",
                "300 200 3333", "...",
                "lucia_aristizabal.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Jane", "Drogadict",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Olga Ortiz", "Abogado de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Pamela Briger", "Abogado fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Rodrigo Benavidez", "Abogado Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Jimmy McGill", "Abogado criminalista",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos penales.",
                "jimmy_mcgill.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Mario 64", "Abogado de setas",
                "300 200 64", "Gran profesional con experiencia de 5 años en casos penales.",
                "mario.jpg"));
    }

    public long mockLawyer(SQLiteDatabase db, Lawyer lawyer) {
        return db.insert(
                LawyerEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveLawyer(Lawyer lawyer) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                LawyerEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());

    }

    public Cursor getAllLawyers() {
        return getReadableDatabase()
                .query(
                        LawyerEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getLawyerById(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                LawyerEntry.TABLE_NAME,
                null,
                LawyerEntry.ID + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    public int deleteLawyer(String lawyerId) {
        return getWritableDatabase().delete(
                LawyerEntry.TABLE_NAME,
                LawyerEntry.ID + " LIKE ?",
                new String[]{lawyerId});
    }

    public int updateLawyer(Lawyer lawyer, String lawyerId) {
        return getWritableDatabase().update(
                LawyerEntry.TABLE_NAME,
                lawyer.toContentValues(),
                LawyerEntry.ID + " LIKE ?",
                new String[]{lawyerId}
        );
    }
}
