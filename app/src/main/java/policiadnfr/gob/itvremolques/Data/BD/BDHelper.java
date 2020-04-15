package policiadnfr.gob.itvremolques.Data.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import policiadnfr.gob.itvremolques.Data.Utils.Utilidades;


public class BDHelper extends SQLiteOpenHelper {
      public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.creartablaexpedido);
        db.execSQL(Utilidades.creartablapais);
        db.execSQL(Utilidades.creartabladepartamento);
        db.execSQL(Utilidades.creartablaprovincia);
        db.execSQL(Utilidades.creartablamunicipio);
        db.execSQL(Utilidades.creartablacategorialicencia);
        db.execSQL(Utilidades.creartablaestadocivil);
        db.execSQL(Utilidades.creartablatipodocumento);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expedido");
        db.execSQL("DROP TABLE IF EXISTS pais");
        db.execSQL("DROP TABLE IF EXISTS municipio");
        db.execSQL("DROP TABLE IF EXISTS provincia");
        db.execSQL("DROP TABLE IF EXISTS departamento");
        db.execSQL("DROP TABLE IF EXISTS categoria_licencia");
        db.execSQL("DROP TABLE IF EXISTS tipo_documento");
        db.execSQL("DROP TABLE IF EXISTS estado_civil");
        db.execSQL(Utilidades.creartablaexpedido);
        db.execSQL(Utilidades.creartablapais);
        db.execSQL(Utilidades.creartabladepartamento);
        db.execSQL(Utilidades.creartablaprovincia);
        db.execSQL(Utilidades.creartablamunicipio);
        db.execSQL(Utilidades.creartablacategorialicencia);
        db.execSQL(Utilidades.creartablaestadocivil);
        db.execSQL(Utilidades.creartablatipodocumento);
    }
}
