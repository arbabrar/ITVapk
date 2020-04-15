package policiadnfr.gob.itvremolques.Data.Utils;

public class Utilidades {
    public static final
    String creartablaexpedido="create table expedido(id_expedido integer Primary Key, nombre text);";
    public static final String creartabladepartamento="create table departamento(id_departamento integer Primary Key, nombre text,abreviacion text);";
    public static final String creartablaprovincia="create table provincia(id_provincia integer Primary Key,id_departamento integer, nombre text);";
    public static final String creartablamunicipio="create table municipio(id_municipio integer Primary Key,id_provincia integer, nombre text);";
    public static final String creartablapais="create table pais(id_pais integer Primary Key, nombre text);";
    public static final String creartablacategorialicencia="create table categoria_licencia(id_categoria_licencia integer Primary Key, nombre text);";
    public static final String creartablaestadocivil="create table estado_civil(id_estado_civil integer Primary Key, nombre text);";
    public static final String creartablatipodocumento="create table tipo_documento(id_tipo_documento integer Primary Key, nombre text);";
    public static final String TABLA_TIPODOCUMENTO="tipo_documento";
    public static final String CAMPO_IDTIPODOC="id_tipo_documento";
    public static final String CAMPO_NOMBRE_DOC="nombre";
    public static final String TABLA_CATLIC="categoria_licencia";
    public static final String CAMPO_IDCATLIC="id_categoria_licencia";
    public static final String CAMPO_NOMBRE_CATLIC="nombre";
    public static final String TABLA_ESTADOCIVIL="estado_civil";
    public static final String CAMPO_IDESTCIVIL="id_estado_civil";
    public static final String CAMPO_NOMBRE_ESTCIVIL="nombre";
    public static final String TABLA_PAIS="pais";
    public static final String CAMPO_IDPAIS="id_pais";
    public static final String CAMPO_NOMBRE_PAIS="nombre";
    public static final String TABLA_EXPEDIDO="expedido";
    public static final String CAMPO_IDEXPEDIDO="id_expedido";
    public static final String CAMPO_NOMBRE_EXPEDIDO="nombre";
    public static final String TABLA_DEPARTAMENTO="departamento";
    public static final String CAMPO_ID_DEPARTAMENTO="id_departamento";
    public static final String CAMPO_NOMBRE_DEPARTAMENTO="nombre";
    public static final String TABLA_PROVINCIA="provincia";
    public static final String CAMPO_ID_PROVINCIA="id_provincia";
    public static final String CAMPO_NOMBRE_PROVINCIA="nombre";
    public static final String TABLA_MUNICIPIO="municipio";
    public static final String CAMPO_ID_MUNICIPIO="id_municipio";
    public static final String CAMPO_NOMBRE_MUNICIPIO="nombre";
}
