package com.cesvimexico.qagenericj.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    final private static String DB_NAME = "bd_qa_generic.db";
    final private static int DB_VERSION = 3;

    private Context context;

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE `qa_area` (\n" +
                "  `id_area` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `area` char(30) DEFAULT NULL\n" +
                ",  `status` varchar(5) NOT NULL DEFAULT 'ALTA'\n" +
                ",  UNIQUE (`id_area`)\n" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE `qa_camp` (\n" +
                "  `id_campo` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_servicio` integer DEFAULT NULL\n" +
                ",  `categoria` char(50) DEFAULT NULL\n" +
                ",  `tipo` char(30) DEFAULT NULL\n" +
                ",  `etiqueta` char(100) DEFAULT NULL\n" +
                ",  `mostrar` integer NOT NULL DEFAULT '1'\n" +
                ",  `status` varchar(5) DEFAULT 'ALTA'\n" +
                ",  `long` integer DEFAULT NULL\n" +
                ",  `orden` integer DEFAULT NULL\n" +
                ",  `nom_campo_bd` varchar(25) DEFAULT NULL\n" +
                ",  UNIQUE (`id_campo`)\n" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE `qa_comb` (\n" +
                "  `id_combo` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_campo` integer DEFAULT NULL\n" +
                ",  `nombre` char(50) DEFAULT NULL\n" +
                ",  `etiqueta` char(30) DEFAULT NULL\n" +
                ",  `valor` char(30) DEFAULT NULL\n" +
                ",  `status` varchar(5) DEFAULT 'ALTA'\n" +
                ",  UNIQUE (`id_combo`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_crit` (\n" +
                "  `id_criterio` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `criterio` text COLLATE BINARY\n" +
                ",  `status` varchar(30) DEFAULT 'ALTA'\n" +
                ",  UNIQUE (`id_criterio`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_pre` (\n" +
                "  `id_pregunta` integer NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_criterio` integer DEFAULT NULL\n" +
                ",  `id_seccion` integer DEFAULT NULL\n" +
                ",  `pregunta` text COLLATE BINARY\n" +
                ",  `tipo` char(2) DEFAULT NULL\n" +
                ",  `puntaje` integer DEFAULT NULL\n" +
                ",  `orden` integer DEFAULT NULL\n" +
                ",  `status` varchar(5) DEFAULT 'ALTA'\n" +
                ",  UNIQUE (`id_pregunta`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_rsp` (\n" +
                "  `id_respuesta` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_pregunta` integer DEFAULT NULL\n" +
                ",  `respuesta` text COLLATE BINARY\n" +
                ",  `puntaje` integer DEFAULT NULL\n" +
                ",  `status` varchar(5) DEFAULT 'ALTA'\n" +
                ",  `default` varchar(1) DEFAULT NULL\n" +
                ",  UNIQUE (`id_respuesta`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_sec` (\n" +
                "  `id_seccion` integer NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_servicio` integer DEFAULT NULL\n" +
                ",  `instrucciones` text\n" +
                ",  `nombre` text\n" +
                ",  `orden` integer DEFAULT NULL\n" +
                ",  `status` varchar(5) DEFAULT 'ALTA'\n" +
                ",  `color` varchar(8) DEFAULT 'ALTA'\n" +
                ",  `icono` varchar(50) DEFAULT 'ALTA'\n" +
                ",  `tipo` varchar(10) DEFAULT 'ALTA'\n" +
                ",  UNIQUE (`id_seccion`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_serv` (\n" +
                "  `id_servicio` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_area` integer DEFAULT NULL\n" +
                ",  `servicio` char(100) NOT NULL DEFAULT ''\n" +
                ",  `descripcion` text NOT NULL\n" +
                ",  `instrucciones` text COLLATE BINARY\n" +
                ",  `status` varchar(5) DEFAULT 'ALTA'\n" +
                ",  UNIQUE (`id_servicio`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_zeval` (\n" +
                "  `id_evaluacion` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_servicio` integer DEFAULT NULL\n" +
                ",  `id_usuario` varchar(100) DEFAULT NULL\n" +
                ",  `f_creada` datetime DEFAULT NULL\n" +
                ",  `f_iniciada` datetime DEFAULT NULL\n" +
                ",  `f_final` datetime DEFAULT NULL\n" +
                ",  `f_ult_diag` datetime DEFAULT NULL\n" +
                ",  `lugar` varchar(50) DEFAULT NULL\n" +
                ",  `pic` varchar(50) DEFAULT NULL\n" +
                ",  `status` varchar(20) DEFAULT NULL\n" +
                ",  `prc_avance` integer NOT NULL DEFAULT '0'\n" +
                ",  `evaluado` varchar(100) DEFAULT NULL\n" +
                ",  `calificacion` decimal(2,0) DEFAULT NULL\n" +
                ",  `cod_acceso` varchar(40) DEFAULT NULL\n" +
                ",  `id_evaluacion_fb` varchar(25) DEFAULT NULL\n" +
                ",  `sign` varchar(50) DEFAULT NULL\n" +
                ",  UNIQUE (`id_evaluacion`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_zeval_det` (\n" +
                "  `id_registro` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_evaluacion` integer  NOT NULL\n" +
                ",  `id_campo` integer DEFAULT NULL\n" +
                ",  `valor_numerico` integer DEFAULT NULL\n" +
                ",  `valor_fecha` datetime DEFAULT NULL\n" +
                ",  `valor_cadena` char(100) DEFAULT NULL\n" +
                ",  UNIQUE (`id_registro`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_zeval_evid` (\n" +
                "  `id_evidencia` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_evaluacion` integer  NOT NULL\n" +
                ",  `id_pregunta` integer DEFAULT NULL\n" +
                ",  `evidencia` varchar(50) DEFAULT NULL\n" +
                ",  UNIQUE (`id_evidencia`)\n" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE `qa_zeval_resp` (\n" +
                "  `id_evdo_resp` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `id_evaluacion` integer  NOT NULL\n" +
                ",  `id_pregunta` integer DEFAULT NULL\n" +
                ",  `id_respuesta` integer DEFAULT NULL\n" +
                ",  `respuesta` text COLLATE BINARY\n" +
                ",  `aplica` char(2) DEFAULT NULL\n" +
                ",  `comentario` text COLLATE BINARY\n" +
                ",  UNIQUE (`id_evdo_resp`)\n" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE `qa_cat_estados` (\n" +
                "  `id_estado` integer  NOT NULL PRIMARY KEY AUTOINCREMENT\n" +
                ",  `descripcion` char(100)  DEFAULT NULL\n" +
                ",  UNIQUE (`id_estado`)\n" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE `qa_cat_municipios` (\n" +
                "  `id_estado` integer  NOT NULL\n" +
                ", `id_municipio` integer  NOT NULL\n" +
                ", `municipio` char(100)  DEFAULT NULL\n" +
                ")";
        db.execSQL(sql);


        /*
        sql = "";
        db.execSQL(sql);
        */

        sql = "INSERT INTO `qa_area` (`id_area`, `area`, `status`) VALUES\n" +
                "  (1,'Seguridad Vial','ALTA'),\n" +
                "  (2,'Vin Plus','ALTA'),\n" +
                "  (3,'Formación','ALTA'),\n" +
                "  (4,'Valuación','ALTA');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_camp` (`id_campo`, `id_servicio`, `categoria`, `tipo`, `etiqueta`, `mostrar`, `status`, `long`, `orden`,`nom_campo_bd`  ) VALUES\n" +
                "  (1,1,'DATOS GENERALES','DATETIME','Fecha de evaluación',1,'ALTA',NULL,1,'f_ini'),\n" +
                "  (2,1,'DATOS GENERALES','SELECT','Tipo de evaluación',1,'ALTA',NULL,2,NULL),\n" +
                "  (3,1,'DATOS GENERALES','TEXT','Nombre del conductor',1,'ALTA',NULL,3,'evaluado'),\n" +
                "  (4,1,'DATOS GENERALES','NUMB','Edad',1,'ALTA',2,4,NULL),\n" +
                "  (5,1,'DATOS GENERALES','NUMB','Años de experiencia',1,'ALTA',2,5,NULL),\n" +
                "  (6,1,'DATOS GENERALES','TEXT','Puesto',1,'ALTA',NULL,6,NULL),\n" +
                "  (7,1,'DATOS GENERALES','SELECT','Tipo de vehiculo',1,'ALTA',NULL,7,NULL),\n" +
                "  (8,1,'DATOS GENERALES','TEXT','Numero de licencia',1,'ALTA',NULL,8,NULL),\n" +
                "  (9,1,'DATOS GENERALES','DATE','Fecha de vencimiento',1,'ALTA',NULL,9,NULL),\n" +
                "  (10,1,'DATOS GENERALES','TEXT','Tipo de licencia',1,'ALTA',NULL,10,NULL),\n" +
                "  (11,1,'DATOS GENERALES','SELECT','Utiliza anteojos o lentes de contacto',1,'ALTA',NULL,11,NULL),\n" +
                "  (12,1,'DATOS GENERALES','TEXT','Sede',0,'ALTA',NULL,12,'sede'),\n" +
                "  (13,1,'DATOS GENERALES','DATE','Fecha última evaluación',1,'ALTA',NULL,13,'f_ult_d'),\n" +
                "  (14,1,'DATOS GENERALES','TEXT','Observaciones',1,'ALTA',NULL,14,NULL),\n" +
                "  (15,1,'DATOS GENERALES','CANVAS','Nombre y firma del evaluador',1,'ALTA',NULL,15,NULL),\n" +
                "  (16,1,'DATOS GENERALES','CANVAS','Nombre y firma del evaluado',1,'ALTA',NULL,16,NULL),\n" +
                "  (17,1,'DATOS GENERALES','SELECT','Estado',1,'ALTA',NULL,17,'estado'),\n" +
                "  (18,1,'DATOS GENERALES','SELECT','Municipio',1,'ALTA',NULL,18,'municipio'),\n" +
                "  (19,1,'DATOS GENERALES','SELECT','Empresa',1,'ALTA',NULL,19,'empresa'),\n" +
                "  (20,1,'DATOS GENERALES','TEXT','Marca',1,'ALTA',NULL,20,NULL),\n" +
                "  (21,1,'DATOS GENERALES','TEXT','Modelo',1,'ALTA',NULL,21,NULL),\n" +
                "  (22,1,'DATOS GENERALES','NUMB','Año',1,'ALTA',NULL,22,NULL),\n"+
                "  (23,1,'DATOS GENERALES','SELECT','Transmisión',1,'ALTA',NULL,23,NULL);";

        db.execSQL(sql);
        sql = "INSERT INTO `qa_comb` (`id_combo`, `id_campo`, `nombre`, `etiqueta`, `valor`, `status`) VALUES\n" +
                "  (1,2,'tipoevaluacion','Inicial','0','ALTA'),\n" +
                "  (2,2,'tipoevaluacion','Final','1','ALTA'),\n" +
                "  (3,1,'puesto','Ajustador','0','ALTA'),\n" +
                "  (4,1,'puesto','Operador','1','ALTA'),\n" +
                "  (5,7,'tipovehiculo','Tracto','0','ALTA'),\n" +
                "  (6,7,'tipovehiculo','Auto','1','ALTA'),\n" +
                "  (7,7,'tipovehiculo','Motocicleta','2','ALTA'),\n" +
                "  (8,11,'ateojoslentes','SI','1','ALTA'),\n" +
                "  (9,11,'ateojoslentes','NO','2','ALTA'),\n" +
                "  (10,19,'empresa','QUALITAS','0','ALTA'),\n" +
                "  (11,19,'empresa','FEMSA','0','ALTA'),\n" +
                "  (12,19,'empresa','HOLCIM','0','ALTA'),\n"+
                "  (13,23,'transmision','AUTOMATICA','0','ALTA'),\n" +
                "  (14,23,'transmision','ESTANDAR','1','ALTA');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_crit` (`id_criterio`, `criterio`, `status`) VALUES\n" +
                "  (1,'Aseguramiento del vehículo','ALTA'),\n" +
                "  (2,'Imagen exterior','ALTA'),\n" +
                "  (3,'Documentación','ALTA'),\n" +
                "  (4,'Vidrios y parabrisas','ALTA'),\n" +
                "  (5,'Espejos','ALTA'),\n" +
                "  (6,'Limpiaparabrisas','ALTA'),\n" +
                "  (7,'Motor','ALTA'),\n" +
                "  (8,'Niveles','ALTA'),\n" +
                "  (9,'Fugas','ALTA'),\n" +
                "  (10,'Neumáticos','ALTA'),\n" +
                "  (11,'Luces','ALTA'),\n" +
                "  (12,'Objetos sueltos','ALTA'),\n" +
                "  (13,'Percepción de riesgos','ALTA'),\n" +
                "  (14,'Ascenso y descenso','ALTA'),\n" +
                "  (15,'Asiento','ALTA'),\n" +
                "  (16,'Apoya cabezas','ALTA'),\n" +
                "  (17,'Equipos de seguridad','ALTA'),\n" +
                "  (18,'Cinturón de seguridad','ALTA'),\n" +
                "  (19,'Acompañante','ALTA'),\n" +
                "  (20,'Luces y claxón','ALTA'),\n" +
                "  (21,'Alarma sonora  de reversa','ALTA'),\n" +
                "  (22,'Frenos','ALTA'),\n" +
                "  (23,'Volante','ALTA'),\n" +
                "  (24,'Entorno','ALTA'),\n" +
                "  (25,'Rebase','ALTA'),\n" +
                "  (26,'Relación espacio','ALTA'),\n" +
                "  (27,'Posición de manejo','ALTA'),\n" +
                "  (28,'Prevención de riesgos','ALTA'),\n" +
                "  (29,'Cortesía','ALTA'),\n" +
                "  (30,'Habilidades','ALTA'),\n" +
                "  (31,'Tecinas de frenado','ALTA'),\n" +
                "  (32,'Control de velocidad','ALTA'),\n" +
                "  (33,'Curvas','ALTA'),\n" +
                "  (34,'Superficie','ALTA'),\n" +
                "  (35,'','ALTA');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_pre` (`id_pregunta`, `id_criterio`, `id_seccion`, `pregunta`, `tipo`, `puntaje`, `orden`, `status`) VALUES\n" +
                "  (1,1,1,'Revisa que los frenos estén aplicados y cuñas puestas','RM',0,1,'ALTA'),\n" +
                "  (2,2,1,'Revisa el estado de la carrocería','RM',0,2,'ALTA'),\n" +
                "  (3,3,1,'Revisa las placas  de circulación','RM',0,3,'ALTA'),\n" +
                "  (4,3,1,'Revisa que el vehículo tenga engomados y calcomanías','RM',0,4,'ALTA'),\n" +
                "  (5,4,1,'Revisa las condiciones de los vidrios laterales y parabrisas','RM',0,5,'ALTA'),\n" +
                "  (6,5,1,'Revisa las condiciones de los espejos laterales','RM',0,6,'ALTA'),\n" +
                "  (7,5,1,'Revisa espejo retrovisor','RM',0,7,'ALTA'),\n" +
                "  (8,6,1,'Revisa el estado físico de los limpiaparabrisas','RM',0,8,'ALTA'),\n" +
                "  (9,7,1,'Inspección visual de la batería y terminales','RM',0,9,'ALTA'),\n" +
                "  (10,7,1,'Inspección visual de las bandas, poleas y ventilador','RM',0,10,'ALTA'),\n" +
                "  (11,8,1,'Revisa niveles de combustible, aceite y anticongelante','RM',0,11,'ALTA'),\n" +
                "  (12,8,1,'Revisa niveles: aceite, anticongelante, dirección, etc.','RM',0,12,'ALTA'),\n" +
                "  (13,9,1,'Revisa que no haya fugas alrededor y debajo del vehículo','RM',0,13,'ALTA'),\n" +
                "  (14,10,1,'Verifica las condiciones de los neumáticos','RM',0,14,'ALTA'),\n" +
                "  (15,11,1,'Verifica el funcionamiento de las  luces, frontales, traseras','RM',0,15,'ALTA'),\n" +
                "  (16,12,1,'Sujeta y/o guarda objetos sueltos en habitáculo','RM',0,16,'ALTA'),\n" +
                "  (17,13,2,'Realiza el técnica de cronos antes de ascender','RM',0,1,'ALTA'),\n" +
                "  (18,14,2,'Utiliza la técnica de los tres puntos','RM',0,2,'ALTA'),\n" +
                "  (19,15,2,'Regula posición, altura y respaldo del asiento','RM',0,3,'ALTA'),\n" +
                "  (20,16,2,'Regula apoya cabezas correctamente','RM',0,4,'ALTA'),\n" +
                "  (21,5,2,'Regula espejos retrovisores laterales correctamente','RM',0,5,'ALTA'),\n" +
                "  (22,12,2,'Revisa que no existan objetos sueltos en la cabina','RM',0,6,'ALTA'),\n" +
                "  (23,17,2,'Revisa estado de los cinturones de seguridad','RM',0,7,'ALTA'),\n" +
                "  (24,17,2,'Revisa extintor, triángulos, herramienta, etc.','RM',0,8,'ALTA'),\n" +
                "  (25,18,2,'Se coloca el cinturón antes de encender el vehículo','RM',0,9,'ALTA'),\n" +
                "  (26,18,2,'Se lo coloca correctamente','RM',0,10,'ALTA'),\n" +
                "  (27,18,2,'Utiliza el cinturón durante todo el viaje','RM',0,11,'ALTA'),\n" +
                "  (28,19,2,'Invita al acompañante a utilizar el cinturón de seguridad','RM',0,12,'ALTA'),\n" +
                "  (29,20,2,'Verifica que las luces y claxón','RM',0,13,'ALTA'),\n" +
                "  (30,6,2,'Revisa el funcionamiento de los limpiaparabrisas','RM',0,14,'ALTA'),\n" +
                "  (31,21,2,'Asegura el funcionamiento de la reversa','RM',0,15,'ALTA'),\n" +
                "  (32,3,2,'Verifica la documentación del vehículo para circular','RM',0,16,'ALTA'),\n" +
                "  (33,22,2,'Revisa los frenos considerando los distintos tipos posibles','RM',0,17,'ALTA'),\n" +
                "  (34,23,2,'Toma correcta distancia del volante','RM',0,18,'ALTA'),\n" +
                "  (35,23,2,'Sujeta el volante en posición 9:15','RM',0,19,'ALTA'),\n" +
                "  (36,11,2,'Comprueba el funcionamiento de las luces','RM',0,20,'ALTA'),\n" +
                "  (37,24,3,'Se incorpora lentamente al flujo de tránsito','RM',0,1,'ALTA'),\n" +
                "  (38,24,3,'Utiliza las luces direccionales antes de iniciar la maniobra','RM',0,2,'ALTA'),\n" +
                "  (39,24,3,'Utiliza zonas seguras para detenerse momentáneamente','RM',0,3,'ALTA'),\n" +
                "  (40,24,3,'Utiliza el claxon de forma segura y adecuada','RM',0,4,'ALTA'),\n" +
                "  (41,24,3,'Utiliza correctamente las luces intermitentes','RM',0,5,'ALTA'),\n" +
                "  (42,24,3,'Revisa los espejos en una maniobra de cambio de carril','RM',0,6,'ALTA'),\n" +
                "  (43,24,3,'Respeta a  peatones y ciclistas','RM',0,7,'ALTA'),\n" +
                "  (44,25,3,'Toma correcta distancia del vehículo al rebasar','RM',0,8,'ALTA'),\n" +
                "  (45,25,3,'Verifica la existencia de tránsito en el mismo sentido','RM',0,9,'ALTA'),\n" +
                "  (46,25,3,'Utiliza los espejos para iniciar la maniobra de rebase','RM',0,10,'ALTA'),\n" +
                "  (47,25,3,'Utiliza las luces intermitentes y/o direccionales','RM',0,11,'ALTA'),\n" +
                "  (48,25,3,'Desarrolla una velocidad adecuada para rebasar','RM',0,12,'ALTA'),\n" +
                "  (49,25,3,'Permite ser rebasado','RM',0,13,'ALTA'),\n" +
                "  (50,26,3,'Evita colocarse en puntos ciegos de otros conductores','RM',0,14,'ALTA'),\n" +
                "  (51,26,3,'Mantiene una distancia de seguimiento de al menos 6 seg','RM',0,15,'ALTA'),\n" +
                "  (52,26,3,'Se detiene a una distancia apropiada de otros vehículos','RM',0,16,'ALTA'),\n" +
                "  (53,26,3,'Revisa I-D-I o D-I-D en las intersecciones','RM',0,17,'ALTA'),\n" +
                "  (54,27,3,'Sujeta el  volante  durante toda la conducción  (9:15)','RM',0,18,'ALTA'),\n" +
                "  (55,26,3,'Se ajusta al flujo vehicular sin exceder la velocidad','RM',0,19,'ALTA'),\n" +
                "  (56,26,3,'Los virajes son  precisos','RM',0,20,'ALTA'),\n" +
                "  (57,26,3,'El frenado es suave y eficiente','RM',0,21,'ALTA'),\n" +
                "  (58,26,3,'Demuestra técnicas de maniobra de reversas seguras','RM',0,22,'ALTA'),\n" +
                "  (59,26,3,'Mantiene espacio, generalmente a los costados','RM',0,23,'ALTA'),\n" +
                "  (60,26,3,'Se estaciona adecuadamente','RM',0,24,'ALTA'),\n" +
                "  (61,28,3,'Identifica riesgos con las 5 técnicas de observación','RM',0,25,'ALTA'),\n" +
                "  (62,28,3,'Mantiene la  distancia de seguimiento.','RM',0,26,'ALTA'),\n" +
                "  (63,28,3,'Mantiene la vista en el camino','RM',0,27,'ALTA'),\n" +
                "  (64,29,3,'Muestra una actitud de cortesía con los usuarios de la vía','RM',0,28,'ALTA'),\n" +
                "  (65,30,4,'Ajusta su velocidad a las condiciones del camino','RM',0,1,'ALTA'),\n" +
                "  (66,30,4,'Conduce lo más a la  derecha posible','RM',0,2,'ALTA'),\n" +
                "  (67,30,4,'Mantiene la distancia entre los vehículos','RM',0,3,'ALTA'),\n" +
                "  (68,30,4,'Respeta a los demás conductores','RM',0,4,'ALTA'),\n" +
                "  (69,31,4,'Posee conocimiento ante una frenada de emergencia','RM',0,5,'ALTA'),\n" +
                "  (70,32,4,'Controla la velocidad utilizando el freno motor','RM',0,6,'ALTA'),\n" +
                "  (71,33,4,'Observa la señalización para identificar el tipo de curva','RM',0,7,'ALTA'),\n" +
                "  (72,33,4,'Disminuye la velocidad antes de ingresar a la curva','RM',0,8,'ALTA'),\n" +
                "  (73,33,4,'Mantiene la velocidad constante durante la curva','RM',0,9,'ALTA'),\n" +
                "  (74,33,4,'Mantiene las dos manos al volante.','RM',0,10,'ALTA'),\n" +
                "  (75,34,4,'Disminuye la velocidad.','RM',0,11,'ALTA'),\n" +
                "  (76,35,5,'Areas de Oportunidad','TX',0,1,'ALTA'),\n" +
                "  (77,35,5,'Aptitudes','TX',0,2,'ALTA');";

        db.execSQL(sql);
        sql = "INSERT INTO `qa_rsp` (`id_respuesta`, `id_pregunta`, `respuesta`, `puntaje`, `status`, `default`) VALUES\n" +
                "  (1,1,'Si',1,'ALTA','*'),\n" +
                "  (2,1,'No',0,'ALTA',NULL),\n" +
                "  (3,2,'Si',1,'ALTA','*'),\n" +
                "  (4,2,'No',0,'ALTA',NULL),\n" +
                "  (5,3,'Si',1,'ALTA','*'),\n" +
                "  (6,3,'No',0,'ALTA',NULL),\n" +
                "  (7,4,'Si',1,'ALTA','*'),\n" +
                "  (8,4,'No',0,'ALTA',NULL),\n" +
                "  (9,5,'Si',1,'ALTA','*'),\n" +
                "  (10,5,'No',0,'ALTA',NULL),\n" +
                "  (11,6,'Si',1,'ALTA','*'),\n" +
                "  (12,6,'No',0,'ALTA',NULL),\n" +
                "  (13,7,'Si',1,'ALTA','*'),\n" +
                "  (14,7,'No',0,'ALTA',NULL),\n" +
                "  (15,8,'Si',1,'ALTA','*'),\n" +
                "  (16,8,'No',0,'ALTA',NULL),\n" +
                "  (17,9,'Si',1,'ALTA','*'),\n" +
                "  (18,9,'No',0,'ALTA',NULL),\n" +
                "  (19,10,'Si',1,'ALTA','*'),\n" +
                "  (20,10,'No',0,'ALTA',NULL),\n" +
                "  (21,11,'Si',1,'ALTA','*'),\n" +
                "  (22,11,'No',0,'ALTA',NULL),\n" +
                "  (23,12,'Si',1,'ALTA','*'),\n" +
                "  (24,12,'No',0,'ALTA',NULL),\n" +
                "  (25,13,'Si',1,'ALTA','*'),\n" +
                "  (26,13,'No',0,'ALTA',NULL),\n" +
                "  (27,14,'Si',1,'ALTA','*'),\n" +
                "  (28,14,'No',0,'ALTA',NULL),\n" +
                "  (29,15,'Si',1,'ALTA','*'),\n" +
                "  (30,15,'No',0,'ALTA',NULL),\n" +
                "  (31,16,'Si',1,'ALTA','*'),\n" +
                "  (32,16,'No',0,'ALTA',NULL),\n" +
                "  (33,17,'Si',1,'ALTA','*'),\n" +
                "  (34,17,'No',0,'ALTA',NULL),\n" +
                "  (35,18,'Si',1,'ALTA','*'),\n" +
                "  (36,18,'No',0,'ALTA',NULL),\n" +
                "  (37,19,'Si',1,'ALTA','*'),\n" +
                "  (38,19,'No',0,'ALTA',NULL),\n" +
                "  (39,20,'Si',1,'ALTA','*'),\n" +
                "  (40,20,'No',0,'ALTA',NULL),\n" +
                "  (41,21,'Si',1,'ALTA','*'),\n" +
                "  (42,21,'No',0,'ALTA',NULL),\n" +
                "  (43,22,'Si',1,'ALTA','*'),\n" +
                "  (44,22,'No',0,'ALTA',NULL),\n" +
                "  (45,23,'Si',1,'ALTA','*'),\n" +
                "  (46,23,'No',0,'ALTA',NULL),\n" +
                "  (47,24,'Si',1,'ALTA','*'),\n" +
                "  (48,24,'No',0,'ALTA',NULL),\n" +
                "  (49,25,'Si',1,'ALTA','*'),\n" +
                "  (50,25,'No',0,'ALTA',NULL),\n" +
                "  (51,26,'Si',1,'ALTA','*'),\n" +
                "  (52,26,'No',0,'ALTA',NULL),\n" +
                "  (53,27,'Si',1,'ALTA','*'),\n" +
                "  (54,27,'No',0,'ALTA',NULL),\n" +
                "  (55,28,'Si',1,'ALTA','*'),\n" +
                "  (56,28,'No',0,'ALTA',NULL),\n" +
                "  (57,29,'Si',1,'ALTA','*'),\n" +
                "  (58,29,'No',0,'ALTA',NULL),\n" +
                "  (59,30,'Si',1,'ALTA','*'),\n" +
                "  (60,30,'No',0,'ALTA',NULL),\n" +
                "  (61,31,'Si',1,'ALTA','*'),\n" +
                "  (62,31,'No',0,'ALTA',NULL),\n" +
                "  (63,32,'Si',1,'ALTA','*'),\n" +
                "  (64,32,'No',0,'ALTA',NULL),\n" +
                "  (65,33,'Si',1,'ALTA','*'),\n" +
                "  (66,33,'No',0,'ALTA',NULL),\n" +
                "  (67,34,'Si',1,'ALTA','*'),\n" +
                "  (68,34,'No',0,'ALTA',NULL),\n" +
                "  (69,35,'Si',1,'ALTA','*'),\n" +
                "  (70,35,'No',0,'ALTA',NULL),\n" +
                "  (71,36,'Si',1,'ALTA','*'),\n" +
                "  (72,36,'No',0,'ALTA',NULL),\n" +
                "  (73,37,'Si',1,'ALTA','*'),\n" +
                "  (74,37,'No',0,'ALTA',NULL),\n" +
                "  (75,38,'Si',1,'ALTA','*'),\n" +
                "  (76,38,'No',0,'ALTA',NULL),\n" +
                "  (77,39,'Si',1,'ALTA','*'),\n" +
                "  (78,39,'No',0,'ALTA',NULL),\n" +
                "  (79,40,'Si',1,'ALTA','*'),\n" +
                "  (80,40,'No',0,'ALTA',NULL),\n" +
                "  (81,41,'Si',1,'ALTA','*'),\n" +
                "  (82,41,'No',0,'ALTA',NULL),\n" +
                "  (83,42,'Si',1,'ALTA','*'),\n" +
                "  (84,42,'No',0,'ALTA',NULL),\n" +
                "  (85,43,'Si',1,'ALTA','*'),\n" +
                "  (86,43,'No',0,'ALTA',NULL),\n" +
                "  (87,44,'Si',1,'ALTA','*'),\n" +
                "  (88,44,'No',0,'ALTA',NULL),\n" +
                "  (89,45,'Si',1,'ALTA','*'),\n" +
                "  (90,45,'No',0,'ALTA',NULL),\n" +
                "  (91,46,'Si',1,'ALTA','*'),\n" +
                "  (92,46,'No',0,'ALTA',NULL),\n" +
                "  (93,47,'Si',1,'ALTA','*'),\n" +
                "  (94,47,'No',0,'ALTA',NULL),\n" +
                "  (95,48,'Si',1,'ALTA','*'),\n" +
                "  (96,48,'No',0,'ALTA',NULL),\n" +
                "  (97,49,'Si',1,'ALTA','*'),\n" +
                "  (98,49,'No',0,'ALTA',NULL),\n" +
                "  (99,50,'Si',1,'ALTA','*'),\n" +
                "  (100,50,'No',0,'ALTA',NULL),\n" +
                "  (101,51,'Si',1,'ALTA','*'),\n" +
                "  (102,51,'No',0,'ALTA',NULL),\n" +
                "  (103,52,'Si',1,'ALTA','*'),\n" +
                "  (104,52,'No',0,'ALTA',NULL),\n" +
                "  (105,53,'Si',1,'ALTA','*'),\n" +
                "  (106,53,'No',0,'ALTA',NULL),\n" +
                "  (107,54,'Si',1,'ALTA','*'),\n" +
                "  (108,54,'No',0,'ALTA',NULL),\n" +
                "  (109,55,'Si',1,'ALTA','*'),\n" +
                "  (110,55,'No',0,'ALTA',NULL),\n" +
                "  (111,56,'Si',1,'ALTA','*'),\n" +
                "  (112,56,'No',0,'ALTA',NULL),\n" +
                "  (113,57,'Si',1,'ALTA','*'),\n" +
                "  (114,57,'No',0,'ALTA',NULL),\n" +
                "  (115,58,'Si',1,'ALTA','*'),\n" +
                "  (116,58,'No',0,'ALTA',NULL),\n" +
                "  (117,59,'Si',1,'ALTA','*'),\n" +
                "  (118,59,'No',0,'ALTA',NULL),\n" +
                "  (119,60,'Si',1,'ALTA','*'),\n" +
                "  (120,60,'No',0,'ALTA',NULL),\n" +
                "  (121,61,'Si',1,'ALTA','*'),\n" +
                "  (122,61,'No',0,'ALTA',NULL),\n" +
                "  (123,62,'Si',1,'ALTA','*'),\n" +
                "  (124,62,'No',0,'ALTA',NULL),\n" +
                "  (125,63,'Si',1,'ALTA','*'),\n" +
                "  (126,63,'No',0,'ALTA',NULL),\n" +
                "  (127,64,'Si',1,'ALTA','*'),\n" +
                "  (128,64,'No',0,'ALTA',NULL),\n" +
                "  (129,65,'Si',1,'ALTA','*'),\n" +
                "  (130,65,'No',0,'ALTA',NULL),\n" +
                "  (131,66,'Si',1,'ALTA','*'),\n" +
                "  (132,66,'No',0,'ALTA',NULL),\n" +
                "  (133,67,'Si',1,'ALTA','*'),\n" +
                "  (134,67,'No',0,'ALTA',NULL),\n" +
                "  (135,68,'Si',1,'ALTA','*'),\n" +
                "  (136,68,'No',0,'ALTA',NULL),\n" +
                "  (137,69,'Si',1,'ALTA','*'),\n" +
                "  (138,69,'No',0,'ALTA',NULL),\n" +
                "  (139,70,'Si',1,'ALTA','*'),\n" +
                "  (140,70,'No',0,'ALTA',NULL),\n" +
                "  (141,71,'Si',1,'ALTA','*'),\n" +
                "  (142,71,'No',0,'ALTA',NULL),\n" +
                "  (143,72,'Si',1,'ALTA','*'),\n" +
                "  (144,72,'No',0,'ALTA',NULL),\n" +
                "  (145,73,'Si',1,'ALTA','*'),\n" +
                "  (146,73,'No',0,'ALTA',NULL),\n" +
                "  (147,74,'Si',1,'ALTA','*'),\n" +
                "  (148,74,'No',0,'ALTA',NULL),\n" +
                "  (149,75,'Si',1,'ALTA','*'),\n" +
                "  (150,75,'No',0,'ALTA',NULL);";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_sec` (`id_seccion`, `id_servicio`, `instrucciones`, `nombre`, `orden`, `status`, `color`, `icono`, `tipo`) VALUES\n" +
                "  (1,1,NULL,'Inspección vehicular',2,'ALTA', '#1976d2',NULL,'OPCIONM'),\n" +
                "  (2,1,NULL,'Antes de la conducción',3,'ALTA', '#212121',NULL,'OPCIONM'),\n" +
                "  (3,1,NULL,'Durante la conducción',4,'ALTA', '#1976d2',NULL,'OPCIONM'),\n" +
                "  (4,1,NULL,'Conducción en cualquier tipo de superficie',4,'ALTA', '#212121',NULL,'OPCIONM'),\n" +
                "  (5,1,NULL,'Comentarios',1,'ALTA', '#1976d2',NULL,'TEXT');";
        db.execSQL(sql);
        sql = "INSERT INTO qa_serv (id_servicio, id_area, servicio, descripcion, instrucciones, status) VALUES\n" +
                "  (1,1,'Evaluación de manejo, equipo ligero','Esta evaluación es aplicada a ...','Importante:\\r\\nAntes de iniciar la evaluación...','ALTA'),\n" +
                "    (2,1,'Evaluación de manejo, equipo pesado','Esta evaluación es aplicada esta dirigida a los ...','Para aplicar esta evaluación es necesario ..','ALTA');";
        db.execSQL(sql);

        sql = "INSERT INTO `qa_cat_estados` (`id_estado`, `descripcion`) VALUES\n" +
                "  (1,'AGUASCALIENTES'),\n" +
                "  (2,'BAJA CALIFORNIA'),\n" +
                "  (3,'BAJA CALIFORNIA SUR'),\n" +
                "  (4,'CAMPECHE'),\n" +
                "  (5,'CHIAPAS'),\n" +
                "  (6,'CHIHUAHUA'),\n" +
                "  (7,'COAHUILA'),\n" +
                "  (8,'COLIMA'),\n" +
                "  (9,'CIUDAD DE MÉXICO'),\n" +
                "  (10,'DURANGO'),\n" +
                "  (11,'GUANAJUATO'),\n" +
                "  (12,'GUERRERO'),\n" +
                "  (13,'HIDALGO'),\n" +
                "  (14,'JALISCO'),\n" +
                "  (15,'ESTADO DE MÉXICO'),\n" +
                "  (16,'MICHOACÁN'),\n" +
                "  (17,'MORELOS'),\n" +
                "  (18,'NAYARIT'),\n" +
                "  (19,'NUEVO LEÓN'),\n" +
                "  (20,'OAXACA'),\n" +
                "  (21,'PUEBLA'),\n" +
                "  (22,'QUERÉTARO'),\n" +
                "  (23,'QUINTANA ROO'),\n" +
                "  (24,'SAN LUIS POTOSÍ'),\n" +
                "  (25,'SINALOA'),\n" +
                "  (26,'SONORA'),\n" +
                "  (27,'TABASCO'),\n" +
                "  (28,'TAMAULIPAS'),\n" +
                "  (29,'TLAXCALA'),\n" +
                "  (30,'VERACRUZ'),\n" +
                "  (31,'YUCATÁN'),\n" +
                "  (32,'ZACATECAS');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_cat_municipios` (`id_estado`, `id_municipio`, `municipio`) VALUES\n" +
                "  (1,1,'AGUASCALIENTES'),\n" +
                "  (1,2,'ASIENTOS'),\n" +
                "  (1,3,'CALVILLO'),\n" +
                "  (1,4,'COSÍO'),\n" +
                "  (1,5,'JESÚS MARÍA'),\n" +
                "  (1,6,'PABELLÓN DE ARTEAGA'),\n" +
                "  (1,7,'RINCÓN DE ROMOS'),\n" +
                "  (1,8,'SAN JOSÉ DE GRACIA'),\n" +
                "  (1,9,'TEPEZALÁ'),\n" +
                "  (1,10,'EL LLANO'),\n" +
                "  (1,11,'SAN FRANCISCO DE LOS ROMO'),\n" +
                "  (2,1,'ENSENADA'),\n" +
                "  (2,2,'MEXICALI'),\n" +
                "  (2,3,'TECATE'),\n" +
                "  (2,4,'TIJUANA'),\n" +
                "  (2,5,'PLAYAS DE ROSARITO'),\n" +
                "  (3,1,'COMONDÚ'),\n" +
                "  (3,2,'MULEGÉ'),\n" +
                "  (3,3,'LA PAZ'),\n" +
                "  (3,8,'LOS CABOS'),\n" +
                "  (3,9,'LORETO'),\n" +
                "  (4,1,'CALKINÍ'),\n" +
                "  (4,2,'CAMPECHE'),\n" +
                "  (4,3,'CARMEN'),\n" +
                "  (4,4,'CHAMPOTÓN'),\n" +
                "  (4,5,'HECELCHAKÁN'),\n" +
                "  (4,6,'HOPELCHÉN'),\n" +
                "  (4,7,'PALIZADA'),\n" +
                "  (4,8,'TENABO'),\n" +
                "  (4,9,'ESCÁRCEGA'),\n" +
                "  (4,10,'CALAKMUL'),\n" +
                "  (4,11,'CANDELARIA'),\n" +
                "  (5,1,'ACACOYAGUA'),\n" +
                "  (5,2,'ACALA'),\n" +
                "  (5,3,'ACAPETAHUA'),\n" +
                "  (5,4,'ALTAMIRANO'),\n" +
                "  (5,5,'AMATÁN'),\n" +
                "  (5,6,'AMATENANGO DE LA FRONTERA'),\n" +
                "  (5,7,'AMATENANGO DEL VALLE'),\n" +
                "  (5,8,'ANGEL ALBINO CORZO'),\n" +
                "  (5,9,'ARRIAGA'),\n" +
                "  (5,10,'BEJUCAL DE OCAMPO'),\n" +
                "  (5,11,'BELLA VISTA'),\n" +
                "  (5,12,'BERRIOZÁBAL'),\n" +
                "  (5,13,'BOCHIL'),\n" +
                "  (5,14,'EL BOSQUE'),\n" +
                "  (5,15,'CACAHOATÁN'),\n" +
                "  (5,16,'CATAZAJÁ'),\n" +
                "  (5,17,'CINTALAPA'),\n" +
                "  (5,18,'COAPILLA'),\n" +
                "  (5,19,'COMITÁN DE DOMÍNGUEZ'),\n" +
                "  (5,20,'LA CONCORDIA'),\n" +
                "  (5,21,'COPAINALÁ'),\n" +
                "  (5,22,'CHALCHIHUITÁN'),\n" +
                "  (5,23,'CHAMULA'),\n" +
                "  (5,24,'CHANAL'),\n" +
                "  (5,25,'CHAPULTENANGO'),\n" +
                "  (5,26,'CHENALHÓ'),\n" +
                "  (5,27,'CHIAPA DE CORZO'),\n" +
                "  (5,28,'CHIAPILLA'),\n" +
                "  (5,29,'CHICOASÉN'),\n" +
                "  (5,30,'CHICOMUSELO'),\n" +
                "  (5,31,'CHILÓN'),\n" +
                "  (5,32,'ESCUINTLA'),\n" +
                "  (5,33,'FRANCISCO LEÓN'),\n" +
                "  (5,34,'FRONTERA COMALAPA'),\n" +
                "  (5,35,'FRONTERA HIDALGO'),\n" +
                "  (5,36,'LA GRANDEZA'),\n" +
                "  (5,37,'HUEHUETÁN'),\n" +
                "  (5,38,'HUIXTÁN'),\n" +
                "  (5,39,'HUITIUPÁN'),\n" +
                "  (5,40,'HUIXTLA'),\n" +
                "  (5,41,'LA INDEPENDENCIA'),\n" +
                "  (5,42,'IXHUATÁN'),\n" +
                "  (5,43,'IXTACOMITÁN'),\n" +
                "  (5,44,'IXTAPA'),\n" +
                "  (5,45,'IXTAPANGAJOYA'),\n" +
                "  (5,46,'JIQUIPILAS'),\n" +
                "  (5,47,'JITOTOL'),\n" +
                "  (5,48,'JUÁREZ'),\n" +
                "  (5,49,'LARRÁINZAR'),\n" +
                "  (5,50,'LA LIBERTAD'),\n" +
                "  (5,51,'MAPASTEPEC'),\n" +
                "  (5,52,'LAS MARGARITAS'),\n" +
                "  (5,53,'MAZAPA DE MADERO'),\n" +
                "  (5,54,'MAZATÁN'),\n" +
                "  (5,55,'METAPA'),\n" +
                "  (5,56,'MITONTIC'),\n" +
                "  (5,57,'MOTOZINTLA'),\n" +
                "  (5,58,'NICOLÁS RUÍZ'),\n" +
                "  (5,59,'OCOSINGO'),\n" +
                "  (5,60,'OCOTEPEC'),\n" +
                "  (5,61,'OCOZOCOAUTLA DE ESPINOSA'),\n" +
                "  (5,62,'OSTUACÁN'),\n" +
                "  (5,63,'OSUMACINTA'),\n" +
                "  (5,64,'OXCHUC'),\n" +
                "  (5,65,'PALENQUE'),\n" +
                "  (5,66,'PANTELHÓ'),\n" +
                "  (5,67,'PANTEPEC'),\n" +
                "  (5,68,'PICHUCALCO'),\n" +
                "  (5,69,'PIJIJIAPAN'),\n" +
                "  (5,70,'EL PORVENIR'),\n" +
                "  (5,71,'VILLA COMALTITLÁN'),\n" +
                "  (5,72,'PUEBLO NUEVO SOLISTAHUACÁN'),\n" +
                "  (5,73,'RAYÓN'),\n" +
                "  (5,74,'REFORMA'),\n" +
                "  (5,75,'LAS ROSAS'),\n" +
                "  (5,76,'SABANILLA'),\n" +
                "  (5,77,'SALTO DE AGUA'),\n" +
                "  (5,78,'SAN CRISTÓBAL DE LAS CASAS'),\n" +
                "  (5,79,'SAN FERNANDO'),\n" +
                "  (5,80,'SILTEPEC'),\n" +
                "  (5,81,'SIMOJOVEL'),\n" +
                "  (5,82,'SITALÁ'),\n" +
                "  (5,83,'SOCOLTENANGO'),\n" +
                "  (5,84,'SOLOSUCHIAPA'),\n" +
                "  (5,85,'SOYALÓ'),\n" +
                "  (5,86,'SUCHIAPA'),\n" +
                "  (5,87,'SUCHIATE'),\n" +
                "  (5,88,'SUNUAPA'),\n" +
                "  (5,89,'TAPACHULA'),\n" +
                "  (5,90,'TAPALAPA'),\n" +
                "  (5,91,'TAPILULA'),\n" +
                "  (5,92,'TECPATÁN'),\n" +
                "  (5,93,'TENEJAPA'),\n" +
                "  (5,94,'TEOPISCA'),\n" +
                "  (5,96,'TILA'),\n" +
                "  (5,97,'TONALÁ'),\n" +
                "  (5,98,'TOTOLAPA'),\n" +
                "  (5,99,'LA TRINITARIA'),\n" +
                "  (5,100,'TUMBALÁ'),\n" +
                "  (5,101,'TUXTLA GUTIÉRREZ'),\n" +
                "  (5,102,'TUXTLA CHICO'),\n" +
                "  (5,103,'TUZANTÁN'),\n" +
                "  (5,104,'TZIMOL'),\n" +
                "  (5,105,'UNIÓN JUÁREZ'),\n" +
                "  (5,106,'VENUSTIANO CARRANZA'),\n" +
                "  (5,107,'VILLA CORZO'),\n" +
                "  (5,108,'VILLAFLORES'),\n" +
                "  (5,109,'YAJALÓN'),\n" +
                "  (5,110,'SAN LUCAS'),\n" +
                "  (5,111,'ZINACANTÁN'),\n" +
                "  (5,112,'SAN JUAN CANCUC'),\n" +
                "  (5,113,'ALDAMA'),\n" +
                "  (5,114,'BENEMÉRITO DE LAS AMÉRICAS'),\n" +
                "  (5,115,'MARAVILLA TENEJAPA'),\n" +
                "  (5,116,'MARQUÉS DE COMILLAS'),\n" +
                "  (5,117,'MONTECRISTO DE GUERRERO'),\n" +
                "  (5,118,'SAN ANDRÉS DURAZNAL'),\n" +
                "  (5,119,'SANTIAGO EL PINAR'),\n" +
                "  (6,1,'AHUMADA'),\n" +
                "  (6,2,'ALDAMA'),\n" +
                "  (6,3,'ALLENDE'),\n" +
                "  (6,4,'AQUILES SERDÁN'),\n" +
                "  (6,5,'ASCENSIÓN'),\n" +
                "  (6,6,'BACHÍNIVA'),\n" +
                "  (6,7,'BALLEZA'),\n" +
                "  (6,8,'BATOPILAS'),\n" +
                "  (6,9,'BOCOYNA'),\n" +
                "  (6,10,'BUENAVENTURA'),\n" +
                "  (6,11,'CAMARGO'),\n" +
                "  (6,12,'CARICHÍ'),\n" +
                "  (6,13,'CASAS GRANDES'),\n" +
                "  (6,14,'CORONADO'),\n" +
                "  (6,15,'COYAME DEL SOTOL'),\n" +
                "  (6,16,'LA CRUZ'),\n" +
                "  (6,17,'CUAUHTÉMOC'),\n" +
                "  (6,18,'CUSIHUIRIACHI'),\n" +
                "  (6,19,'CHIHUAHUA'),\n" +
                "  (6,20,'CHÍNIPAS'),\n" +
                "  (6,21,'DELICIAS'),\n" +
                "  (6,22,'DR. BELISARIO DOMÍNGUEZ'),\n" +
                "  (6,23,'GALEANA'),\n" +
                "  (6,24,'SANTA ISABEL'),\n" +
                "  (6,25,'GÓMEZ FARÍAS'),\n" +
                "  (6,26,'GRAN MORELOS'),\n" +
                "  (6,27,'GUACHOCHI'),\n" +
                "  (6,28,'GUADALUPE'),\n" +
                "  (6,29,'GUADALUPE Y CALVO'),\n" +
                "  (6,30,'GUAZAPARES'),\n" +
                "  (6,31,'GUERRERO'),\n" +
                "  (6,32,'HIDALGO DEL PARRAL'),\n" +
                "  (6,33,'HUEJOTITÁN'),\n" +
                "  (6,34,'IGNACIO ZARAGOZA'),\n" +
                "  (6,35,'JANOS'),\n" +
                "  (6,36,'JIMÉNEZ'),\n" +
                "  (6,37,'JUÁREZ'),\n" +
                "  (6,38,'JULIMES'),\n" +
                "  (6,39,'LÓPEZ'),\n" +
                "  (6,40,'MADERA'),\n" +
                "  (6,41,'MAGUARICHI'),\n" +
                "  (6,42,'MANUEL BENAVIDES'),\n" +
                "  (6,43,'MATACHÍ'),\n" +
                "  (6,44,'MATAMOROS'),\n" +
                "  (6,45,'MEOQUI'),\n" +
                "  (6,46,'MORELOS'),\n" +
                "  (6,47,'MORIS'),\n" +
                "  (6,48,'NAMIQUIPA'),\n" +
                "  (6,49,'NONOAVA'),\n" +
                "  (6,50,'NUEVO CASAS GRANDES'),\n" +
                "  (6,51,'OCAMPO'),\n" +
                "  (6,52,'OJINAGA'),\n" +
                "  (6,53,'PRAXEDIS G. GUERRERO'),\n" +
                "  (6,54,'RIVA PALACIO'),\n" +
                "  (6,55,'ROSALES'),\n" +
                "  (6,56,'ROSARIO'),\n" +
                "  (6,57,'SAN FRANCISCO DE BORJA'),\n" +
                "  (6,58,'SAN FRANCISCO DE CONCHOS'),\n" +
                "  (6,59,'SAN FRANCISCO DEL ORO'),\n" +
                "  (6,60,'SANTA BÁRBARA'),\n" +
                "  (6,61,'SATEVÓ'),\n" +
                "  (6,62,'SAUCILLO'),\n" +
                "  (6,63,'TEMÓSACHIC'),\n" +
                "  (6,64,'EL TULE'),\n" +
                "  (6,65,'URIQUE'),\n" +
                "  (6,66,'URUACHI'),\n" +
                "  (6,67,'VALLE DE ZARAGOZA'),\n" +
                "  (7,1,'ABASOLO'),\n" +
                "  (7,2,'ACUÑA'),\n" +
                "  (7,3,'ALLENDE'),\n" +
                "  (7,4,'ARTEAGA'),\n" +
                "  (7,5,'CANDELA'),\n" +
                "  (7,6,'CASTAÑOS'),\n" +
                "  (7,7,'CUATRO CIÉNEGAS'),\n" +
                "  (7,8,'ESCOBEDO'),\n" +
                "  (7,9,'FRANCISCO I. MADERO'),\n" +
                "  (7,10,'FRONTERA'),\n" +
                "  (7,11,'GENERAL CEPEDA'),\n" +
                "  (7,12,'GUERRERO'),\n" +
                "  (7,13,'HIDALGO'),\n" +
                "  (7,14,'JIMÉNEZ'),\n" +
                "  (7,15,'JUÁREZ'),\n" +
                "  (7,16,'LAMADRID'),\n" +
                "  (7,17,'MATAMOROS'),\n" +
                "  (7,18,'MONCLOVA'),\n" +
                "  (7,19,'MORELOS'),\n" +
                "  (7,20,'MÚZQUIZ'),\n" +
                "  (7,21,'NADADORES'),\n" +
                "  (7,22,'NAVA'),\n" +
                "  (7,23,'OCAMPO'),\n" +
                "  (7,24,'PARRAS'),\n" +
                "  (7,25,'PIEDRAS NEGRAS'),\n" +
                "  (7,26,'PROGRESO'),\n" +
                "  (7,27,'RAMOS ARIZPE'),\n" +
                "  (7,28,'SABINAS'),\n" +
                "  (7,29,'SACRAMENTO'),\n" +
                "  (7,30,'SALTILLO'),\n" +
                "  (7,31,'SAN BUENAVENTURA'),\n" +
                "  (7,32,'SAN JUAN DE SABINAS'),\n" +
                "  (7,33,'SAN PEDRO'),\n" +
                "  (7,34,'SIERRA MOJADA'),\n" +
                "  (7,35,'TORREÓN'),\n" +
                "  (7,36,'VIESCA'),\n" +
                "  (7,37,'VILLA UNIÓN'),\n" +
                "  (7,38,'ZARAGOZA'),\n" +
                "  (8,1,'ARMERÍA'),\n" +
                "  (8,2,'COLIMA'),\n" +
                "  (8,3,'COMALA'),\n" +
                "  (8,4,'COQUIMATLÁN'),\n" +
                "  (8,5,'CUAUHTÉMOC'),\n" +
                "  (8,6,'IXTLAHUACÁN'),\n" +
                "  (8,7,'MANZANILLO'),\n" +
                "  (8,8,'MINATITLÁN'),\n" +
                "  (8,9,'TECOMÁN'),\n" +
                "  (8,10,'VILLA DE ÁLVAREZ'),\n" +
                "  (9,2,'AZCAPOTZALCO'),\n" +
                "  (9,3,'COYOACÁN'),\n" +
                "  (9,4,'CUAJIMALPA DE MORELOS'),\n" +
                "  (9,5,'GUSTAVO A. MADERO'),\n" +
                "  (9,6,'IZTACALCO'),\n" +
                "  (9,7,'IZTAPALAPA'),\n" +
                "  (9,8,'LA MAGDALENA CONTRERAS'),\n" +
                "  (9,9,'MILPA ALTA'),\n" +
                "  (9,10,'ÁLVARO OBREGÓN'),\n" +
                "  (9,11,'TLÁHUAC'),\n" +
                "  (9,12,'TLALPAN'),\n" +
                "  (9,13,'XOCHIMILCO'),\n" +
                "  (9,14,'BENITO JUÁREZ'),\n" +
                "  (9,15,'CUAUHTÉMOC'),\n" +
                "  (9,16,'MIGUEL HIDALGO'),\n" +
                "  (9,17,'VENUSTIANO CARRANZA'),\n" +
                "  (10,1,'CANATLÁN'),\n" +
                "  (10,2,'CANELAS'),\n" +
                "  (10,3,'CONETO DE COMONFORT'),\n" +
                "  (10,4,'CUENCAMÉ'),\n" +
                "  (10,5,'DURANGO'),\n" +
                "  (10,6,'GENERAL SIMÓN BOLÍVAR'),\n" +
                "  (10,7,'GÓMEZ PALACIO'),\n" +
                "  (10,8,'GUADALUPE VICTORIA'),\n" +
                "  (10,9,'GUANACEVÍ'),\n" +
                "  (10,10,'HIDALGO'),\n" +
                "  (10,11,'INDÉ'),\n" +
                "  (10,12,'LERDO'),\n" +
                "  (10,13,'MAPIMÍ'),\n" +
                "  (10,14,'MEZQUITAL'),\n" +
                "  (10,15,'NAZAS'),\n" +
                "  (10,16,'NOMBRE DE DIOS'),\n" +
                "  (10,17,'OCAMPO'),\n" +
                "  (10,18,'EL ORO'),\n" +
                "  (10,19,'OTÁEZ'),\n" +
                "  (10,20,'PÁNUCO DE CORONADO'),\n" +
                "  (10,21,'PEÑÓN BLANCO'),\n" +
                "  (10,22,'POANAS'),\n" +
                "  (10,23,'PUEBLO NUEVO'),\n" +
                "  (10,24,'RODEO'),\n" +
                "  (10,25,'SAN BERNARDO'),\n" +
                "  (10,26,'SAN DIMAS'),\n" +
                "  (10,27,'SAN JUAN DE GUADALUPE'),\n" +
                "  (10,28,'SAN JUAN DEL RÍO'),\n" +
                "  (10,29,'SAN LUIS DEL CORDERO'),\n" +
                "  (10,30,'SAN PEDRO DEL GALLO'),\n" +
                "  (10,31,'SANTA CLARA'),\n" +
                "  (10,32,'SANTIAGO PAPASQUIARO'),\n" +
                "  (10,33,'SÚCHIL'),\n" +
                "  (10,34,'TAMAZULA'),\n" +
                "  (10,35,'TEPEHUANES'),\n" +
                "  (10,36,'TLAHUALILO'),\n" +
                "  (10,37,'TOPIA'),\n" +
                "  (10,38,'VICENTE GUERRERO'),\n" +
                "  (10,39,'NUEVO IDEAL'),\n" +
                "  (11,1,'ABASOLO'),\n" +
                "  (11,2,'ACÁMBARO'),\n" +
                "  (11,3,'SAN MIGUEL DE ALLENDE'),\n" +
                "  (11,4,'APASEO EL ALTO'),\n" +
                "  (11,5,'APASEO EL GRANDE'),\n" +
                "  (11,6,'ATARJEA'),\n" +
                "  (11,7,'CELAYA'),\n" +
                "  (11,8,'MANUEL DOBLADO'),\n" +
                "  (11,9,'COMONFORT'),\n" +
                "  (11,10,'CORONEO'),\n" +
                "  (11,11,'CORTAZAR'),\n" +
                "  (11,12,'CUERÁMARO'),\n" +
                "  (11,13,'DOCTOR MORA'),\n" +
                "  (11,14,'DOLORES HIDALGO CUNA DE LA INDEPENDENCIA NACIONAL'),\n" +
                "  (11,15,'GUANAJUATO'),\n" +
                "  (11,16,'HUANÍMARO'),\n" +
                "  (11,17,'IRAPUATO'),\n" +
                "  (11,18,'JARAL DEL PROGRESO'),\n" +
                "  (11,19,'JERÉCUARO'),\n" +
                "  (11,20,'LEÓN'),\n" +
                "  (11,21,'MOROLEÓN'),\n" +
                "  (11,22,'OCAMPO'),\n" +
                "  (11,23,'PÉNJAMO'),\n" +
                "  (11,24,'PUEBLO NUEVO'),\n" +
                "  (11,25,'PURÍSIMA DEL RINCÓN'),\n" +
                "  (11,26,'ROMITA'),\n" +
                "  (11,27,'SALAMANCA'),\n" +
                "  (11,28,'SALVATIERRA'),\n" +
                "  (11,29,'SAN DIEGO DE LA UNIÓN'),\n" +
                "  (11,30,'SAN FELIPE'),\n" +
                "  (11,31,'SAN FRANCISCO DEL RINCÓN'),\n" +
                "  (11,32,'SAN JOSÉ ITURBIDE'),\n" +
                "  (11,33,'SAN LUIS DE LA PAZ'),\n" +
                "  (11,34,'SANTA CATARINA'),\n" +
                "  (11,35,'SANTA CRUZ DE JUVENTINO ROSAS'),\n" +
                "  (11,36,'SANTIAGO MARAVATÍO'),\n" +
                "  (11,37,'SILAO DE LA VICTORIA'),\n" +
                "  (11,38,'TARANDACUAO'),\n" +
                "  (11,39,'TARIMORO'),\n" +
                "  (11,40,'TIERRA BLANCA'),\n" +
                "  (11,41,'URIANGATO'),\n" +
                "  (11,42,'VALLE DE SANTIAGO'),\n" +
                "  (11,43,'VICTORIA'),\n" +
                "  (11,44,'VILLAGRÁN'),\n" +
                "  (11,45,'XICHÚ'),\n" +
                "  (11,46,'YURIRIA'),\n" +
                "  (12,1,'ACAPULCO DE JUÁREZ'),\n" +
                "  (12,2,'AHUACUOTZINGO'),\n" +
                "  (12,3,'AJUCHITLÁN DEL PROGRESO'),\n" +
                "  (12,4,'ALCOZAUCA DE GUERRERO'),\n" +
                "  (12,5,'ALPOYECA'),\n" +
                "  (12,6,'APAXTLA'),\n" +
                "  (12,7,'ARCELIA'),\n" +
                "  (12,8,'ATENANGO DEL RÍO'),\n" +
                "  (12,9,'ATLAMAJALCINGO DEL MONTE'),\n" +
                "  (12,10,'ATLIXTAC'),\n" +
                "  (12,11,'ATOYAC DE ÁLVAREZ'),\n" +
                "  (12,12,'AYUTLA DE LOS LIBRES'),\n" +
                "  (12,13,'AZOYÚ'),\n" +
                "  (12,14,'BENITO JUÁREZ'),\n" +
                "  (12,15,'BUENAVISTA DE CUÉLLAR'),\n" +
                "  (12,16,'COAHUAYUTLA DE JOSÉ MARÍA IZAZAGA'),\n" +
                "  (12,17,'COCULA'),\n" +
                "  (12,18,'COPALA'),\n" +
                "  (12,19,'COPALILLO'),\n" +
                "  (12,20,'COPANATOYAC'),\n" +
                "  (12,21,'COYUCA DE BENÍTEZ'),\n" +
                "  (12,22,'COYUCA DE CATALÁN'),\n" +
                "  (12,23,'CUAJINICUILAPA'),\n" +
                "  (12,24,'CUALÁC'),\n" +
                "  (12,25,'CUAUTEPEC'),\n" +
                "  (12,26,'CUETZALA DEL PROGRESO'),\n" +
                "  (12,27,'CUTZAMALA DE PINZÓN'),\n" +
                "  (12,28,'CHILAPA DE ÁLVAREZ'),\n" +
                "  (12,29,'CHILPANCINGO DE LOS BRAVO'),\n" +
                "  (12,30,'FLORENCIO VILLARREAL'),\n" +
                "  (12,31,'GENERAL CANUTO A. NERI'),\n" +
                "  (12,32,'GENERAL HELIODORO CASTILLO'),\n" +
                "  (12,33,'HUAMUXTITLÁN'),\n" +
                "  (12,34,'HUITZUCO DE LOS FIGUEROA'),\n" +
                "  (12,35,'IGUALA DE LA INDEPENDENCIA'),\n" +
                "  (12,36,'IGUALAPA'),\n" +
                "  (12,37,'IXCATEOPAN DE CUAUHTÉMOC'),\n" +
                "  (12,38,'ZIHUATANEJO DE AZUETA'),\n" +
                "  (12,39,'JUAN R. ESCUDERO'),\n" +
                "  (12,40,'LEONARDO BRAVO'),\n" +
                "  (12,41,'MALINALTEPEC'),\n" +
                "  (12,42,'MÁRTIR DE CUILAPAN'),\n" +
                "  (12,43,'METLATÓNOC'),\n" +
                "  (12,44,'MOCHITLÁN'),\n" +
                "  (12,45,'OLINALÁ'),\n" +
                "  (12,46,'OMETEPEC'),\n" +
                "  (12,47,'PEDRO ASCENCIO ALQUISIRAS'),\n" +
                "  (12,48,'PETATLÁN'),\n" +
                "  (12,49,'PILCAYA'),\n" +
                "  (12,50,'PUNGARABATO'),\n" +
                "  (12,51,'QUECHULTENANGO'),\n" +
                "  (12,52,'SAN LUIS ACATLÁN'),\n" +
                "  (12,53,'SAN MARCOS'),\n" +
                "  (12,54,'SAN MIGUEL TOTOLAPAN'),\n" +
                "  (12,55,'TAXCO DE ALARCÓN'),\n" +
                "  (12,56,'TECOANAPA'),\n" +
                "  (12,57,'TÉCPAN DE GALEANA'),\n" +
                "  (12,58,'TELOLOAPAN'),\n" +
                "  (12,59,'TEPECOACUILCO DE TRUJANO'),\n" +
                "  (12,60,'TETIPAC'),\n" +
                "  (12,61,'TIXTLA DE GUERRERO'),\n" +
                "  (12,62,'TLACOACHISTLAHUACA'),\n" +
                "  (12,63,'TLACOAPA'),\n" +
                "  (12,64,'TLALCHAPA'),\n" +
                "  (12,65,'TLALIXTAQUILLA DE MALDONADO'),\n" +
                "  (12,66,'TLAPA DE COMONFORT'),\n" +
                "  (12,67,'TLAPEHUALA'),\n" +
                "  (12,68,'LA UNIÓN DE ISIDORO MONTES DE OCA'),\n" +
                "  (12,69,'XALPATLÁHUAC'),\n" +
                "  (12,70,'XOCHIHUEHUETLÁN'),\n" +
                "  (12,71,'XOCHISTLAHUACA'),\n" +
                "  (12,72,'ZAPOTITLÁN TABLAS'),\n" +
                "  (12,73,'ZIRÁNDARO'),\n" +
                "  (12,74,'ZITLALA'),\n" +
                "  (12,75,'EDUARDO NERI'),\n" +
                "  (12,76,'ACATEPEC'),\n" +
                "  (12,77,'MARQUELIA'),\n" +
                "  (12,78,'COCHOAPA EL GRANDE'),\n" +
                "  (12,79,'JOSÉ JOAQUÍN DE HERRERA'),\n" +
                "  (12,80,'JUCHITÁN'),\n" +
                "  (12,81,'ILIATENCO'),\n" +
                "  (13,1,'ACATLÁN'),\n" +
                "  (13,2,'ACAXOCHITLÁN'),\n" +
                "  (13,3,'ACTOPAN'),\n" +
                "  (13,4,'AGUA BLANCA DE ITURBIDE'),\n" +
                "  (13,5,'AJACUBA'),\n" +
                "  (13,6,'ALFAJAYUCAN'),\n" +
                "  (13,7,'ALMOLOYA'),\n" +
                "  (13,8,'APAN'),\n" +
                "  (13,9,'EL ARENAL'),\n" +
                "  (13,10,'ATITALAQUIA'),\n" +
                "  (13,11,'ATLAPEXCO'),\n" +
                "  (13,12,'ATOTONILCO EL GRANDE'),\n" +
                "  (13,13,'ATOTONILCO DE TULA'),\n" +
                "  (13,14,'CALNALI'),\n" +
                "  (13,15,'CARDONAL'),\n" +
                "  (13,16,'CUAUTEPEC DE HINOJOSA'),\n" +
                "  (13,17,'CHAPANTONGO'),\n" +
                "  (13,18,'CHAPULHUACÁN'),\n" +
                "  (13,19,'CHILCUAUTLA'),\n" +
                "  (13,20,'ELOXOCHITLÁN'),\n" +
                "  (13,21,'EMILIANO ZAPATA'),\n" +
                "  (13,22,'EPAZOYUCAN'),\n" +
                "  (13,23,'FRANCISCO I. MADERO'),\n" +
                "  (13,24,'HUASCA DE OCAMPO'),\n" +
                "  (13,25,'HUAUTLA'),\n" +
                "  (13,26,'HUAZALINGO'),\n" +
                "  (13,27,'HUEHUETLA'),\n" +
                "  (13,28,'HUEJUTLA DE REYES'),\n" +
                "  (13,29,'HUICHAPAN'),\n" +
                "  (13,30,'IXMIQUILPAN'),\n" +
                "  (13,31,'JACALA DE LEDEZMA'),\n" +
                "  (13,32,'JALTOCÁN'),\n" +
                "  (13,33,'JUÁREZ HIDALGO'),\n" +
                "  (13,34,'LOLOTLA'),\n" +
                "  (13,35,'METEPEC'),\n" +
                "  (13,36,'SAN AGUSTÍN METZQUITITLÁN'),\n" +
                "  (13,37,'METZTITLÁN'),\n" +
                "  (13,38,'MINERAL DEL CHICO'),\n" +
                "  (13,39,'MINERAL DEL MONTE'),\n" +
                "  (13,40,'LA MISIÓN'),\n" +
                "  (13,41,'MIXQUIAHUALA DE JUÁREZ'),\n" +
                "  (13,42,'MOLANGO DE ESCAMILLA'),\n" +
                "  (13,43,'NICOLÁS FLORES'),\n" +
                "  (13,44,'NOPALA DE VILLAGRÁN'),\n" +
                "  (13,45,'OMITLÁN DE JUÁREZ'),\n" +
                "  (13,46,'SAN FELIPE ORIZATLÁN'),\n" +
                "  (13,47,'PACULA'),\n" +
                "  (13,48,'PACHUCA DE SOTO'),\n" +
                "  (13,49,'PISAFLORES'),\n" +
                "  (13,50,'PROGRESO DE OBREGÓN'),\n" +
                "  (13,51,'MINERAL DE LA REFORMA'),\n" +
                "  (13,52,'SAN AGUSTÍN TLAXIACA'),\n" +
                "  (13,53,'SAN BARTOLO TUTOTEPEC');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_cat_municipios` (`id_estado`, `id_municipio`, `municipio`) VALUES\n" +
                "  (13,54,'SAN SALVADOR'),\n" +
                "  (13,55,'SANTIAGO DE ANAYA'),\n" +
                "  (13,56,'SANTIAGO TULANTEPEC DE LUGO GUERRERO'),\n" +
                "  (13,57,'SINGUILUCAN'),\n" +
                "  (13,58,'TASQUILLO'),\n" +
                "  (13,59,'TECOZAUTLA'),\n" +
                "  (13,60,'TENANGO DE DORIA'),\n" +
                "  (13,61,'TEPEAPULCO'),\n" +
                "  (13,62,'TEPEHUACÁN DE GUERRERO'),\n" +
                "  (13,63,'TEPEJI DEL RÍO DE OCAMPO'),\n" +
                "  (13,64,'TEPETITLÁN'),\n" +
                "  (13,65,'TETEPANGO'),\n" +
                "  (13,66,'VILLA DE TEZONTEPEC'),\n" +
                "  (13,67,'TEZONTEPEC DE ALDAMA'),\n" +
                "  (13,68,'TIANGUISTENGO'),\n" +
                "  (13,69,'TIZAYUCA'),\n" +
                "  (13,70,'TLAHUELILPAN'),\n" +
                "  (13,71,'TLAHUILTEPA'),\n" +
                "  (13,72,'TLANALAPA'),\n" +
                "  (13,73,'TLANCHINOL'),\n" +
                "  (13,74,'TLAXCOAPAN'),\n" +
                "  (13,75,'TOLCAYUCA'),\n" +
                "  (13,76,'TULA DE ALLENDE'),\n" +
                "  (13,77,'TULANCINGO DE BRAVO'),\n" +
                "  (13,78,'XOCHIATIPAN'),\n" +
                "  (13,79,'XOCHICOATLÁN'),\n" +
                "  (13,80,'YAHUALICA'),\n" +
                "  (13,81,'ZACUALTIPÁN DE ÁNGELES'),\n" +
                "  (13,82,'ZAPOTLÁN DE JUÁREZ'),\n" +
                "  (13,83,'ZEMPOALA'),\n" +
                "  (13,84,'ZIMAPÁN'),\n" +
                "  (14,1,'ACATIC'),\n" +
                "  (14,2,'ACATLÁN DE JUÁREZ'),\n" +
                "  (14,3,'AHUALULCO DE MERCADO'),\n" +
                "  (14,4,'AMACUECA'),\n" +
                "  (14,5,'AMATITÁN'),\n" +
                "  (14,6,'AMECA'),\n" +
                "  (14,7,'SAN JUANITO DE ESCOBEDO'),\n" +
                "  (14,8,'ARANDAS'),\n" +
                "  (14,9,'EL ARENAL'),\n" +
                "  (14,10,'ATEMAJAC DE BRIZUELA'),\n" +
                "  (14,11,'ATENGO'),\n" +
                "  (14,12,'ATENGUILLO'),\n" +
                "  (14,13,'ATOTONILCO EL ALTO'),\n" +
                "  (14,14,'ATOYAC'),\n" +
                "  (14,15,'AUTLÁN DE NAVARRO'),\n" +
                "  (14,16,'AYOTLÁN'),\n" +
                "  (14,17,'AYUTLA'),\n" +
                "  (14,18,'LA BARCA'),\n" +
                "  (14,19,'BOLAÑOS'),\n" +
                "  (14,20,'CABO CORRIENTES'),\n" +
                "  (14,21,'CASIMIRO CASTILLO'),\n" +
                "  (14,22,'CIHUATLÁN'),\n" +
                "  (14,23,'ZAPOTLÁN EL GRANDE'),\n" +
                "  (14,24,'COCULA'),\n" +
                "  (14,25,'COLOTLÁN'),\n" +
                "  (14,26,'CONCEPCIÓN DE BUENOS AIRES'),\n" +
                "  (14,27,'CUAUTITLÁN DE GARCÍA BARRAGÁN'),\n" +
                "  (14,28,'CUAUTLA'),\n" +
                "  (14,29,'CUQUÍO'),\n" +
                "  (14,30,'CHAPALA'),\n" +
                "  (14,31,'CHIMALTITÁN'),\n" +
                "  (14,32,'CHIQUILISTLÁN'),\n" +
                "  (14,33,'DEGOLLADO'),\n" +
                "  (14,34,'EJUTLA'),\n" +
                "  (14,35,'ENCARNACIÓN DE DÍAZ'),\n" +
                "  (14,36,'ETZATLÁN'),\n" +
                "  (14,37,'EL GRULLO'),\n" +
                "  (14,38,'GUACHINANGO'),\n" +
                "  (14,39,'GUADALAJARA'),\n" +
                "  (14,40,'HOSTOTIPAQUILLO'),\n" +
                "  (14,41,'HUEJÚCAR'),\n" +
                "  (14,42,'HUEJUQUILLA EL ALTO'),\n" +
                "  (14,43,'LA HUERTA'),\n" +
                "  (14,44,'IXTLAHUACÁN DE LOS MEMBRILLOS'),\n" +
                "  (14,45,'IXTLAHUACÁN DEL RÍO'),\n" +
                "  (14,46,'JALOSTOTITLÁN'),\n" +
                "  (14,47,'JAMAY'),\n" +
                "  (14,48,'JESÚS MARÍA'),\n" +
                "  (14,49,'JILOTLÁN DE LOS DOLORES'),\n" +
                "  (14,50,'JOCOTEPEC'),\n" +
                "  (14,51,'JUANACATLÁN'),\n" +
                "  (14,52,'JUCHITLÁN'),\n" +
                "  (14,53,'LAGOS DE MORENO'),\n" +
                "  (14,54,'EL LIMÓN'),\n" +
                "  (14,55,'MAGDALENA'),\n" +
                "  (14,56,'SANTA MARÍA DEL ORO'),\n" +
                "  (14,57,'LA MANZANILLA DE LA PAZ'),\n" +
                "  (14,58,'MASCOTA'),\n" +
                "  (14,59,'MAZAMITLA'),\n" +
                "  (14,60,'MEXTICACÁN'),\n" +
                "  (14,61,'MEZQUITIC'),\n" +
                "  (14,62,'MIXTLÁN'),\n" +
                "  (14,63,'OCOTLÁN'),\n" +
                "  (14,64,'OJUELOS DE JALISCO'),\n" +
                "  (14,65,'PIHUAMO'),\n" +
                "  (14,66,'PONCITLÁN'),\n" +
                "  (14,67,'PUERTO VALLARTA'),\n" +
                "  (14,68,'VILLA PURIFICACIÓN'),\n" +
                "  (14,69,'QUITUPAN'),\n" +
                "  (14,70,'EL SALTO'),\n" +
                "  (14,71,'SAN CRISTÓBAL DE LA BARRANCA'),\n" +
                "  (14,72,'SAN DIEGO DE ALEJANDRÍA'),\n" +
                "  (14,73,'SAN JUAN DE LOS LAGOS'),\n" +
                "  (14,74,'SAN JULIÁN'),\n" +
                "  (14,75,'SAN MARCOS'),\n" +
                "  (14,76,'SAN MARTÍN DE BOLAÑOS'),\n" +
                "  (14,77,'SAN MARTÍN HIDALGO'),\n" +
                "  (14,78,'SAN MIGUEL EL ALTO'),\n" +
                "  (14,79,'GÓMEZ FARÍAS'),\n" +
                "  (14,80,'SAN SEBASTIÁN DEL OESTE'),\n" +
                "  (14,81,'SANTA MARÍA DE LOS ÁNGELES'),\n" +
                "  (14,82,'SAYULA'),\n" +
                "  (14,83,'TALA'),\n" +
                "  (14,84,'TALPA DE ALLENDE'),\n" +
                "  (14,85,'TAMAZULA DE GORDIANO'),\n" +
                "  (14,86,'TAPALPA'),\n" +
                "  (14,87,'TECALITLÁN'),\n" +
                "  (14,88,'TECOLOTLÁN'),\n" +
                "  (14,89,'TECHALUTA DE MONTENEGRO'),\n" +
                "  (14,90,'TENAMAXTLÁN'),\n" +
                "  (14,91,'TEOCALTICHE'),\n" +
                "  (14,92,'TEOCUITATLÁN DE CORONA'),\n" +
                "  (14,93,'TEPATITLÁN DE MORELOS'),\n" +
                "  (14,94,'TEQUILA'),\n" +
                "  (14,95,'TEUCHITLÁN'),\n" +
                "  (14,96,'TIZAPÁN EL ALTO'),\n" +
                "  (14,97,'TLAJOMULCO DE ZÚÑIGA'),\n" +
                "  (14,98,'SAN PEDRO TLAQUEPAQUE'),\n" +
                "  (14,99,'TOLIMÁN'),\n" +
                "  (14,100,'TOMATLÁN'),\n" +
                "  (14,101,'TONALÁ'),\n" +
                "  (14,102,'TONAYA'),\n" +
                "  (14,103,'TONILA'),\n" +
                "  (14,104,'TOTATICHE'),\n" +
                "  (14,105,'TOTOTLÁN'),\n" +
                "  (14,106,'TUXCACUESCO'),\n" +
                "  (14,107,'TUXCUECA'),\n" +
                "  (14,108,'TUXPAN'),\n" +
                "  (14,109,'UNIÓN DE SAN ANTONIO'),\n" +
                "  (14,110,'UNIÓN DE TULA'),\n" +
                "  (14,111,'VALLE DE GUADALUPE'),\n" +
                "  (14,112,'VALLE DE JUÁREZ'),\n" +
                "  (14,113,'SAN GABRIEL'),\n" +
                "  (14,114,'VILLA CORONA'),\n" +
                "  (14,115,'VILLA GUERRERO'),\n" +
                "  (14,116,'VILLA HIDALGO'),\n" +
                "  (14,117,'CAÑADAS DE OBREGÓN'),\n" +
                "  (14,118,'YAHUALICA DE GONZÁLEZ GALLO'),\n" +
                "  (14,119,'ZACOALCO DE TORRES'),\n" +
                "  (14,120,'ZAPOPAN'),\n" +
                "  (14,121,'ZAPOTILTIC'),\n" +
                "  (14,122,'ZAPOTITLÁN DE VADILLO'),\n" +
                "  (14,123,'ZAPOTLÁN DEL REY'),\n" +
                "  (14,124,'ZAPOTLANEJO'),\n" +
                "  (14,125,'SAN IGNACIO CERRO GORDO'),\n" +
                "  (15,1,'ACAMBAY DE RUÍZ CASTAÑEDA'),\n" +
                "  (15,2,'ACOLMAN'),\n" +
                "  (15,3,'ACULCO'),\n" +
                "  (15,4,'ALMOLOYA DE ALQUISIRAS'),\n" +
                "  (15,5,'ALMOLOYA DE JUÁREZ'),\n" +
                "  (15,6,'ALMOLOYA DEL RÍO'),\n" +
                "  (15,7,'AMANALCO'),\n" +
                "  (15,8,'AMATEPEC'),\n" +
                "  (15,9,'AMECAMECA'),\n" +
                "  (15,10,'APAXCO'),\n" +
                "  (15,11,'ATENCO'),\n" +
                "  (15,12,'ATIZAPÁN'),\n" +
                "  (15,13,'ATIZAPÁN DE ZARAGOZA'),\n" +
                "  (15,14,'ATLACOMULCO'),\n" +
                "  (15,15,'ATLAUTLA'),\n" +
                "  (15,16,'AXAPUSCO'),\n" +
                "  (15,17,'AYAPANGO'),\n" +
                "  (15,18,'CALIMAYA'),\n" +
                "  (15,19,'CAPULHUAC'),\n" +
                "  (15,20,'COACALCO DE BERRIOZÁBAL'),\n" +
                "  (15,21,'COATEPEC HARINAS'),\n" +
                "  (15,22,'COCOTITLÁN'),\n" +
                "  (15,23,'COYOTEPEC'),\n" +
                "  (15,24,'CUAUTITLÁN'),\n" +
                "  (15,25,'CHALCO'),\n" +
                "  (15,26,'CHAPA DE MOTA'),\n" +
                "  (15,27,'CHAPULTEPEC'),\n" +
                "  (15,28,'CHIAUTLA'),\n" +
                "  (15,29,'CHICOLOAPAN'),\n" +
                "  (15,30,'CHICONCUAC'),\n" +
                "  (15,31,'CHIMALHUACÁN'),\n" +
                "  (15,32,'DONATO GUERRA'),\n" +
                "  (15,33,'ECATEPEC DE MORELOS'),\n" +
                "  (15,34,'ECATZINGO'),\n" +
                "  (15,35,'HUEHUETOCA'),\n" +
                "  (15,36,'HUEYPOXTLA'),\n" +
                "  (15,37,'HUIXQUILUCAN'),\n" +
                "  (15,38,'ISIDRO FABELA'),\n" +
                "  (15,39,'IXTAPALUCA'),\n" +
                "  (15,40,'IXTAPAN DE LA SAL'),\n" +
                "  (15,41,'IXTAPAN DEL ORO'),\n" +
                "  (15,42,'IXTLAHUACA'),\n" +
                "  (15,43,'XALATLACO'),\n" +
                "  (15,44,'JALTENCO'),\n" +
                "  (15,45,'JILOTEPEC'),\n" +
                "  (15,46,'JILOTZINGO'),\n" +
                "  (15,47,'JIQUIPILCO'),\n" +
                "  (15,48,'JOCOTITLÁN'),\n" +
                "  (15,49,'JOQUICINGO'),\n" +
                "  (15,50,'JUCHITEPEC'),\n" +
                "  (15,51,'LERMA'),\n" +
                "  (15,52,'MALINALCO'),\n" +
                "  (15,53,'MELCHOR OCAMPO'),\n" +
                "  (15,54,'METEPEC'),\n" +
                "  (15,55,'MEXICALTZINGO'),\n" +
                "  (15,56,'MORELOS'),\n" +
                "  (15,57,'NAUCALPAN DE JUÁREZ'),\n" +
                "  (15,58,'NEZAHUALCÓYOTL'),\n" +
                "  (15,59,'NEXTLALPAN'),\n" +
                "  (15,60,'NICOLÁS ROMERO'),\n" +
                "  (15,61,'NOPALTEPEC'),\n" +
                "  (15,62,'OCOYOACAC'),\n" +
                "  (15,63,'OCUILAN'),\n" +
                "  (15,64,'EL ORO'),\n" +
                "  (15,65,'OTUMBA'),\n" +
                "  (15,66,'OTZOLOAPAN'),\n" +
                "  (15,67,'OTZOLOTEPEC'),\n" +
                "  (15,68,'OZUMBA'),\n" +
                "  (15,69,'PAPALOTLA'),\n" +
                "  (15,70,'LA PAZ'),\n" +
                "  (15,71,'POLOTITLÁN'),\n" +
                "  (15,72,'RAYÓN'),\n" +
                "  (15,73,'SAN ANTONIO LA ISLA'),\n" +
                "  (15,74,'SAN FELIPE DEL PROGRESO'),\n" +
                "  (15,75,'SAN MARTÍN DE LAS PIRÁMIDES'),\n" +
                "  (15,76,'SAN MATEO ATENCO'),\n" +
                "  (15,77,'SAN SIMÓN DE GUERRERO'),\n" +
                "  (15,78,'SANTO TOMÁS'),\n" +
                "  (15,79,'SOYANIQUILPAN DE JUÁREZ'),\n" +
                "  (15,80,'SULTEPEC'),\n" +
                "  (15,81,'TECÁMAC'),\n" +
                "  (15,82,'TEJUPILCO'),\n" +
                "  (15,83,'TEMAMATLA'),\n" +
                "  (15,84,'TEMASCALAPA'),\n" +
                "  (15,85,'TEMASCALCINGO'),\n" +
                "  (15,86,'TEMASCALTEPEC'),\n" +
                "  (15,87,'TEMOAYA'),\n" +
                "  (15,88,'TENANCINGO'),\n" +
                "  (15,89,'TENANGO DEL AIRE'),\n" +
                "  (15,90,'TENANGO DEL VALLE'),\n" +
                "  (15,91,'TEOLOYUCAN'),\n" +
                "  (15,92,'TEOTIHUACÁN'),\n" +
                "  (15,93,'TEPETLAOXTOC'),\n" +
                "  (15,94,'TEPETLIXPA'),\n" +
                "  (15,95,'TEPOTZOTLÁN'),\n" +
                "  (15,96,'TEQUIXQUIAC'),\n" +
                "  (15,97,'TEXCALTITLÁN'),\n" +
                "  (15,98,'TEXCALYACAC'),\n" +
                "  (15,99,'TEXCOCO'),\n" +
                "  (15,100,'TEZOYUCA'),\n" +
                "  (15,101,'TIANGUISTENCO'),\n" +
                "  (15,102,'TIMILPAN'),\n" +
                "  (15,103,'TLALMANALCO'),\n" +
                "  (15,104,'TLALNEPANTLA DE BAZ'),\n" +
                "  (15,105,'TLATLAYA'),\n" +
                "  (15,106,'TOLUCA'),\n" +
                "  (15,107,'TONATICO'),\n" +
                "  (15,108,'TULTEPEC'),\n" +
                "  (15,109,'TULTITLÁN'),\n" +
                "  (15,110,'VALLE DE BRAVO'),\n" +
                "  (15,111,'VILLA DE ALLENDE'),\n" +
                "  (15,112,'VILLA DEL CARBÓN'),\n" +
                "  (15,113,'VILLA GUERRERO'),\n" +
                "  (15,114,'VILLA VICTORIA'),\n" +
                "  (15,115,'XONACATLÁN'),\n" +
                "  (15,116,'ZACAZONAPAN'),\n" +
                "  (15,117,'ZACUALPAN'),\n" +
                "  (15,118,'ZINACANTEPEC'),\n" +
                "  (15,119,'ZUMPAHUACÁN'),\n" +
                "  (15,120,'ZUMPANGO'),\n" +
                "  (15,121,'CUAUTITLÁN IZCALLI'),\n" +
                "  (15,122,'VALLE DE CHALCO SOLIDARIDAD'),\n" +
                "  (15,123,'LUVIANOS'),\n" +
                "  (15,124,'SAN JOSÉ DEL RINCÓN'),\n" +
                "  (15,125,'TONANITLA'),\n" +
                "  (16,1,'ACUITZIO'),\n" +
                "  (16,2,'AGUILILLA'),\n" +
                "  (16,3,'ÁLVARO OBREGÓN'),\n" +
                "  (16,4,'ANGAMACUTIRO'),\n" +
                "  (16,5,'ANGANGUEO'),\n" +
                "  (16,6,'APATZINGÁN'),\n" +
                "  (16,7,'APORO'),\n" +
                "  (16,8,'AQUILA'),\n" +
                "  (16,9,'ARIO'),\n" +
                "  (16,10,'ARTEAGA'),\n" +
                "  (16,11,'BRISEÑAS'),\n" +
                "  (16,12,'BUENAVISTA'),\n" +
                "  (16,13,'CARÁCUARO'),\n" +
                "  (16,14,'COAHUAYANA'),\n" +
                "  (16,15,'COALCOMÁN DE VÁZQUEZ PALLARES'),\n" +
                "  (16,16,'COENEO'),\n" +
                "  (16,17,'CONTEPEC'),\n" +
                "  (16,18,'COPÁNDARO'),\n" +
                "  (16,19,'COTIJA'),\n" +
                "  (16,20,'CUITZEO'),\n" +
                "  (16,21,'CHARAPAN'),\n" +
                "  (16,22,'CHARO'),\n" +
                "  (16,23,'CHAVINDA'),\n" +
                "  (16,24,'CHERÁN'),\n" +
                "  (16,25,'CHILCHOTA'),\n" +
                "  (16,26,'CHINICUILA'),\n" +
                "  (16,27,'CHUCÁNDIRO'),\n" +
                "  (16,28,'CHURINTZIO'),\n" +
                "  (16,29,'CHURUMUCO'),\n" +
                "  (16,30,'ECUANDUREO'),\n" +
                "  (16,31,'EPITACIO HUERTA'),\n" +
                "  (16,32,'ERONGARÍCUARO'),\n" +
                "  (16,33,'GABRIEL ZAMORA'),\n" +
                "  (16,34,'HIDALGO'),\n" +
                "  (16,35,'LA HUACANA'),\n" +
                "  (16,36,'HUANDACAREO'),\n" +
                "  (16,37,'HUANIQUEO'),\n" +
                "  (16,38,'HUETAMO'),\n" +
                "  (16,39,'HUIRAMBA'),\n" +
                "  (16,40,'INDAPARAPEO'),\n" +
                "  (16,41,'IRIMBO'),\n" +
                "  (16,42,'IXTLÁN'),\n" +
                "  (16,43,'JACONA'),\n" +
                "  (16,44,'JIMÉNEZ'),\n" +
                "  (16,45,'JIQUILPAN'),\n" +
                "  (16,46,'JUÁREZ'),\n" +
                "  (16,47,'JUNGAPEO'),\n" +
                "  (16,48,'LAGUNILLAS'),\n" +
                "  (16,49,'MADERO'),\n" +
                "  (16,50,'MARAVATÍO'),\n" +
                "  (16,51,'MARCOS CASTELLANOS'),\n" +
                "  (16,52,'LÁZARO CÁRDENAS'),\n" +
                "  (16,53,'MORELIA'),\n" +
                "  (16,54,'MORELOS'),\n" +
                "  (16,55,'MÚGICA'),\n" +
                "  (16,56,'NAHUATZEN'),\n" +
                "  (16,57,'NOCUPÉTARO'),\n" +
                "  (16,58,'NUEVO PARANGARICUTIRO'),\n" +
                "  (16,59,'NUEVO URECHO'),\n" +
                "  (16,60,'NUMARÁN'),\n" +
                "  (16,61,'OCAMPO'),\n" +
                "  (16,62,'PAJACUARÁN'),\n" +
                "  (16,63,'PANINDÍCUARO'),\n" +
                "  (16,64,'PARÁCUARO'),\n" +
                "  (16,65,'PARACHO'),\n" +
                "  (16,66,'PÁTZCUARO'),\n" +
                "  (16,67,'PENJAMILLO'),\n" +
                "  (16,68,'PERIBÁN'),\n" +
                "  (16,69,'LA PIEDAD'),\n" +
                "  (16,70,'PURÉPERO'),\n" +
                "  (16,71,'PURUÁNDIRO'),\n" +
                "  (16,72,'QUERÉNDARO'),\n" +
                "  (16,73,'QUIROGA'),\n" +
                "  (16,74,'COJUMATLÁN DE RÉGULES'),\n" +
                "  (16,75,'LOS REYES'),\n" +
                "  (16,76,'SAHUAYO'),\n" +
                "  (16,77,'SAN LUCAS'),\n" +
                "  (16,78,'SANTA ANA MAYA'),\n" +
                "  (16,79,'SALVADOR ESCALANTE'),\n" +
                "  (16,80,'SENGUIO'),\n" +
                "  (16,81,'SUSUPUATO'),\n" +
                "  (16,82,'TACÁMBARO'),\n" +
                "  (16,83,'TANCÍTARO'),\n" +
                "  (16,84,'TANGAMANDAPIO'),\n" +
                "  (16,85,'TANGANCÍCUARO'),\n" +
                "  (16,86,'TANHUATO'),\n" +
                "  (16,87,'TARETAN'),\n" +
                "  (16,88,'TARÍMBARO'),\n" +
                "  (16,89,'TEPALCATEPEC'),\n" +
                "  (16,90,'TINGAMBATO'),\n" +
                "  (16,91,'TINGÜINDÍN'),\n" +
                "  (16,92,'TIQUICHEO DE NICOLÁS ROMERO'),\n" +
                "  (16,93,'TLALPUJAHUA'),\n" +
                "  (16,94,'TLAZAZALCA'),\n" +
                "  (16,95,'TOCUMBO'),\n" +
                "  (16,96,'TUMBISCATÍO'),\n" +
                "  (16,97,'TURICATO'),\n" +
                "  (16,98,'TUXPAN'),\n" +
                "  (16,99,'TUZANTLA'),\n" +
                "  (16,100,'TZINTZUNTZAN'),\n" +
                "  (16,101,'TZITZIO'),\n" +
                "  (16,102,'URUAPAN'),\n" +
                "  (16,103,'VENUSTIANO CARRANZA'),\n" +
                "  (16,104,'VILLAMAR'),\n" +
                "  (16,105,'VISTA HERMOSA'),\n" +
                "  (16,106,'YURÉCUARO'),\n" +
                "  (16,107,'ZACAPU'),\n" +
                "  (16,108,'ZAMORA'),\n" +
                "  (16,109,'ZINÁPARO'),\n" +
                "  (16,110,'ZINAPÉCUARO'),\n" +
                "  (16,111,'ZIRACUARETIRO'),\n" +
                "  (16,112,'ZITÁCUARO'),\n" +
                "  (16,113,'JOSÉ SIXTO VERDUZCO'),\n" +
                "  (17,1,'AMACUZAC'),\n" +
                "  (17,2,'ATLATLAHUCAN'),\n" +
                "  (17,3,'AXOCHIAPAN'),\n" +
                "  (17,4,'AYALA'),\n" +
                "  (17,5,'COATLÁN DEL RÍO'),\n" +
                "  (17,6,'CUAUTLA'),\n" +
                "  (17,7,'CUERNAVACA'),\n" +
                "  (17,8,'EMILIANO ZAPATA'),\n" +
                "  (17,9,'HUITZILAC'),\n" +
                "  (17,10,'JANTETELCO'),\n" +
                "  (17,11,'JIUTEPEC'),\n" +
                "  (17,12,'JOJUTLA'),\n" +
                "  (17,13,'JONACATEPEC DE LEANDRO VALLE'),\n" +
                "  (17,14,'MAZATEPEC'),\n" +
                "  (17,15,'MIACATLÁN'),\n" +
                "  (17,16,'OCUITUCO'),\n" +
                "  (17,17,'PUENTE DE IXTLA'),\n" +
                "  (17,18,'TEMIXCO'),\n" +
                "  (17,19,'TEPALCINGO'),\n" +
                "  (17,20,'TEPOZTLÁN'),\n" +
                "  (17,21,'TETECALA'),\n" +
                "  (17,22,'TETELA DEL VOLCÁN'),\n" +
                "  (17,23,'TLALNEPANTLA'),\n" +
                "  (17,24,'TLALTIZAPÁN DE ZAPATA'),\n" +
                "  (17,25,'TLAQUILTENANGO'),\n" +
                "  (17,26,'TLAYACAPAN'),\n" +
                "  (17,27,'TOTOLAPAN'),\n" +
                "  (17,28,'XOCHITEPEC'),\n" +
                "  (17,29,'YAUTEPEC'),\n" +
                "  (17,30,'YECAPIXTLA'),\n" +
                "  (17,31,'ZACATEPEC'),\n" +
                "  (17,32,'ZACUALPAN DE AMILPAS'),\n" +
                "  (17,33,'TEMOAC'),\n" +
                "  (18,1,'ACAPONETA'),\n" +
                "  (18,2,'AHUACATLÁN'),\n" +
                "  (18,3,'AMATLÁN DE CAÑAS'),\n" +
                "  (18,4,'COMPOSTELA'),\n" +
                "  (18,5,'HUAJICORI'),\n" +
                "  (18,6,'IXTLÁN DEL RÍO'),\n" +
                "  (18,7,'JALA'),\n" +
                "  (18,8,'XALISCO'),\n" +
                "  (18,9,'DEL NAYAR'),\n" +
                "  (18,10,'ROSAMORADA'),\n" +
                "  (18,11,'RUÍZ'),\n" +
                "  (18,12,'SAN BLAS'),\n" +
                "  (18,13,'SAN PEDRO LAGUNILLAS'),\n" +
                "  (18,14,'SANTA MARÍA DEL ORO'),\n" +
                "  (18,15,'SANTIAGO IXCUINTLA'),\n" +
                "  (18,16,'TECUALA'),\n" +
                "  (18,17,'TEPIC'),\n" +
                "  (18,18,'TUXPAN'),\n" +
                "  (18,19,'LA YESCA'),\n" +
                "  (18,20,'BAHÍA DE BANDERAS'),\n" +
                "  (19,1,'ABASOLO'),\n" +
                "  (19,2,'AGUALEGUAS'),\n" +
                "  (19,3,'LOS ALDAMAS'),\n" +
                "  (19,4,'ALLENDE'),\n" +
                "  (19,5,'ANÁHUAC'),\n" +
                "  (19,6,'APODACA'),\n" +
                "  (19,7,'ARAMBERRI'),\n" +
                "  (19,8,'BUSTAMANTE'),\n" +
                "  (19,9,'CADEREYTA JIMÉNEZ'),\n" +
                "  (19,10,'EL CARMEN'),\n" +
                "  (19,11,'CERRALVO'),\n" +
                "  (19,12,'CIÉNEGA DE FLORES'),\n" +
                "  (19,13,'CHINA'),\n" +
                "  (19,14,'DOCTOR ARROYO'),\n" +
                "  (19,15,'DOCTOR COSS'),\n" +
                "  (19,16,'DOCTOR GONZÁLEZ'),\n" +
                "  (19,17,'GALEANA'),\n" +
                "  (19,18,'GARCÍA'),\n" +
                "  (19,19,'SAN PEDRO GARZA GARCÍA'),\n" +
                "  (19,20,'GENERAL BRAVO'),\n" +
                "  (19,21,'GENERAL ESCOBEDO'),\n" +
                "  (19,22,'GENERAL TERÁN'),\n" +
                "  (19,23,'GENERAL TREVIÑO'),\n" +
                "  (19,24,'GENERAL ZARAGOZA'),\n" +
                "  (19,25,'GENERAL ZUAZUA'),\n" +
                "  (19,26,'GUADALUPE'),\n" +
                "  (19,27,'LOS HERRERAS'),\n" +
                "  (19,28,'HIGUERAS'),\n" +
                "  (19,29,'HUALAHUISES'),\n" +
                "  (19,30,'ITURBIDE'),\n" +
                "  (19,31,'JUÁREZ'),\n" +
                "  (19,32,'LAMPAZOS DE NARANJO'),\n" +
                "  (19,33,'LINARES'),\n" +
                "  (19,34,'MARÍN'),\n" +
                "  (19,35,'MELCHOR OCAMPO'),\n" +
                "  (19,36,'MIER Y NORIEGA'),\n" +
                "  (19,37,'MINA'),\n" +
                "  (19,38,'MONTEMORELOS'),\n" +
                "  (19,39,'MONTERREY'),\n" +
                "  (19,40,'PARÁS'),\n" +
                "  (19,41,'PESQUERÍA'),\n" +
                "  (19,42,'LOS RAMONES'),\n" +
                "  (19,43,'RAYONES'),\n" +
                "  (19,44,'SABINAS HIDALGO'),\n" +
                "  (19,45,'SALINAS VICTORIA'),\n" +
                "  (19,46,'SAN NICOLÁS DE LOS GARZA'),\n" +
                "  (19,47,'HIDALGO'),\n" +
                "  (19,48,'SANTA CATARINA'),\n" +
                "  (19,49,'SANTIAGO'),\n" +
                "  (19,50,'VALLECILLO'),\n" +
                "  (19,51,'VILLALDAMA'),\n" +
                "  (20,1,'ABEJONES'),\n" +
                "  (20,2,'ACATLÁN DE PÉREZ FIGUEROA');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_cat_municipios` (`id_estado`, `id_municipio`, `municipio`) VALUES\n" +
                "  (20,3,'ASUNCIÓN CACALOTEPEC'),\n" +
                "  (20,4,'ASUNCIÓN CUYOTEPEJI'),\n" +
                "  (20,5,'ASUNCIÓN IXTALTEPEC'),\n" +
                "  (20,6,'ASUNCIÓN NOCHIXTLÁN'),\n" +
                "  (20,7,'ASUNCIÓN OCOTLÁN'),\n" +
                "  (20,8,'ASUNCIÓN TLACOLULITA'),\n" +
                "  (20,9,'AYOTZINTEPEC'),\n" +
                "  (20,10,'EL BARRIO DE LA SOLEDAD'),\n" +
                "  (20,11,'CALIHUALÁ'),\n" +
                "  (20,12,'CANDELARIA LOXICHA'),\n" +
                "  (20,13,'CIÉNEGA DE ZIMATLÁN'),\n" +
                "  (20,14,'CIUDAD IXTEPEC'),\n" +
                "  (20,15,'COATECAS ALTAS'),\n" +
                "  (20,16,'COICOYÁN DE LAS FLORES'),\n" +
                "  (20,17,'LA COMPAÑÍA'),\n" +
                "  (20,18,'CONCEPCIÓN BUENAVISTA'),\n" +
                "  (20,19,'CONCEPCIÓN PÁPALO'),\n" +
                "  (20,20,'CONSTANCIA DEL ROSARIO'),\n" +
                "  (20,21,'COSOLAPA'),\n" +
                "  (20,22,'COSOLTEPEC'),\n" +
                "  (20,23,'CUILÁPAM DE GUERRERO'),\n" +
                "  (20,24,'CUYAMECALCO VILLA DE ZARAGOZA'),\n" +
                "  (20,25,'CHAHUITES'),\n" +
                "  (20,26,'CHALCATONGO DE HIDALGO'),\n" +
                "  (20,27,'CHIQUIHUITLÁN DE BENITO JUÁREZ'),\n" +
                "  (20,28,'HEROICA CIUDAD DE EJUTLA DE CRESPO'),\n" +
                "  (20,29,'ELOXOCHITLÁN DE FLORES MAGÓN'),\n" +
                "  (20,30,'EL ESPINAL'),\n" +
                "  (20,31,'TAMAZULÁPAM DEL ESPÍRITU SANTO'),\n" +
                "  (20,32,'FRESNILLO DE TRUJANO'),\n" +
                "  (20,33,'GUADALUPE ETLA'),\n" +
                "  (20,34,'GUADALUPE DE RAMÍREZ'),\n" +
                "  (20,35,'GUELATAO DE JUÁREZ'),\n" +
                "  (20,36,'GUEVEA DE HUMBOLDT'),\n" +
                "  (20,37,'MESONES HIDALGO'),\n" +
                "  (20,38,'VILLA HIDALGO'),\n" +
                "  (20,39,'HEROICA CIUDAD DE HUAJUAPAN DE LEÓN'),\n" +
                "  (20,40,'HUAUTEPEC'),\n" +
                "  (20,41,'HUAUTLA DE JIMÉNEZ'),\n" +
                "  (20,42,'IXTLÁN DE JUÁREZ'),\n" +
                "  (20,43,'HEROICA CIUDAD DE JUCHITÁN DE ZARAGOZA'),\n" +
                "  (20,44,'LOMA BONITA'),\n" +
                "  (20,45,'MAGDALENA APASCO'),\n" +
                "  (20,46,'MAGDALENA JALTEPEC'),\n" +
                "  (20,47,'SANTA MAGDALENA JICOTLÁN'),\n" +
                "  (20,48,'MAGDALENA MIXTEPEC'),\n" +
                "  (20,49,'MAGDALENA OCOTLÁN'),\n" +
                "  (20,50,'MAGDALENA PEÑASCO'),\n" +
                "  (20,51,'MAGDALENA TEITIPAC'),\n" +
                "  (20,52,'MAGDALENA TEQUISISTLÁN'),\n" +
                "  (20,53,'MAGDALENA TLACOTEPEC'),\n" +
                "  (20,54,'MAGDALENA ZAHUATLÁN'),\n" +
                "  (20,55,'MARISCALA DE JUÁREZ'),\n" +
                "  (20,56,'MÁRTIRES DE TACUBAYA'),\n" +
                "  (20,57,'MATÍAS ROMERO AVENDAÑO'),\n" +
                "  (20,58,'MAZATLÁN VILLA DE FLORES'),\n" +
                "  (20,59,'MIAHUATLÁN DE PORFIRIO DÍAZ'),\n" +
                "  (20,60,'MIXISTLÁN DE LA REFORMA'),\n" +
                "  (20,61,'MONJAS'),\n" +
                "  (20,62,'NATIVIDAD'),\n" +
                "  (20,63,'NAZARENO ETLA'),\n" +
                "  (20,64,'NEJAPA DE MADERO'),\n" +
                "  (20,65,'IXPANTEPEC NIEVES'),\n" +
                "  (20,66,'SANTIAGO NILTEPEC'),\n" +
                "  (20,67,'OAXACA DE JUÁREZ'),\n" +
                "  (20,68,'OCOTLÁN DE MORELOS'),\n" +
                "  (20,69,'LA PE'),\n" +
                "  (20,70,'PINOTEPA DE DON LUIS'),\n" +
                "  (20,71,'PLUMA HIDALGO'),\n" +
                "  (20,72,'SAN JOSÉ DEL PROGRESO'),\n" +
                "  (20,73,'PUTLA VILLA DE GUERRERO'),\n" +
                "  (20,74,'SANTA CATARINA QUIOQUITANI'),\n" +
                "  (20,75,'REFORMA DE PINEDA'),\n" +
                "  (20,76,'LA REFORMA'),\n" +
                "  (20,77,'REYES ETLA'),\n" +
                "  (20,78,'ROJAS DE CUAUHTÉMOC'),\n" +
                "  (20,79,'SALINA CRUZ'),\n" +
                "  (20,80,'SAN AGUSTÍN AMATENGO'),\n" +
                "  (20,81,'SAN AGUSTÍN ATENANGO'),\n" +
                "  (20,82,'SAN AGUSTÍN CHAYUCO'),\n" +
                "  (20,83,'SAN AGUSTÍN DE LAS JUNTAS'),\n" +
                "  (20,84,'SAN AGUSTÍN ETLA'),\n" +
                "  (20,85,'SAN AGUSTÍN LOXICHA'),\n" +
                "  (20,86,'SAN AGUSTÍN TLACOTEPEC'),\n" +
                "  (20,87,'SAN AGUSTÍN YATARENI'),\n" +
                "  (20,88,'SAN ANDRÉS CABECERA NUEVA'),\n" +
                "  (20,89,'SAN ANDRÉS DINICUITI'),\n" +
                "  (20,90,'SAN ANDRÉS HUAXPALTEPEC'),\n" +
                "  (20,91,'SAN ANDRÉS HUAYÁPAM'),\n" +
                "  (20,92,'SAN ANDRÉS IXTLAHUACA'),\n" +
                "  (20,93,'SAN ANDRÉS LAGUNAS'),\n" +
                "  (20,94,'SAN ANDRÉS NUXIÑO'),\n" +
                "  (20,95,'SAN ANDRÉS PAXTLÁN'),\n" +
                "  (20,96,'SAN ANDRÉS SINAXTLA'),\n" +
                "  (20,97,'SAN ANDRÉS SOLAGA'),\n" +
                "  (20,98,'SAN ANDRÉS TEOTILÁLPAM'),\n" +
                "  (20,99,'SAN ANDRÉS TEPETLAPA'),\n" +
                "  (20,100,'SAN ANDRÉS YAÁ'),\n" +
                "  (20,101,'SAN ANDRÉS ZABACHE'),\n" +
                "  (20,102,'SAN ANDRÉS ZAUTLA'),\n" +
                "  (20,103,'SAN ANTONINO CASTILLO VELASCO'),\n" +
                "  (20,104,'SAN ANTONINO EL ALTO'),\n" +
                "  (20,105,'SAN ANTONINO MONTE VERDE'),\n" +
                "  (20,106,'SAN ANTONIO ACUTLA'),\n" +
                "  (20,107,'SAN ANTONIO DE LA CAL'),\n" +
                "  (20,108,'SAN ANTONIO HUITEPEC'),\n" +
                "  (20,109,'SAN ANTONIO NANAHUATÍPAM'),\n" +
                "  (20,110,'SAN ANTONIO SINICAHUA'),\n" +
                "  (20,111,'SAN ANTONIO TEPETLAPA'),\n" +
                "  (20,112,'SAN BALTAZAR CHICHICÁPAM'),\n" +
                "  (20,113,'SAN BALTAZAR LOXICHA'),\n" +
                "  (20,114,'SAN BALTAZAR YATZACHI EL BAJO'),\n" +
                "  (20,115,'SAN BARTOLO COYOTEPEC'),\n" +
                "  (20,116,'SAN BARTOLOMÉ AYAUTLA'),\n" +
                "  (20,117,'SAN BARTOLOMÉ LOXICHA'),\n" +
                "  (20,118,'SAN BARTOLOMÉ QUIALANA'),\n" +
                "  (20,119,'SAN BARTOLOMÉ YUCUAÑE'),\n" +
                "  (20,120,'SAN BARTOLOMÉ ZOOGOCHO'),\n" +
                "  (20,121,'SAN BARTOLO SOYALTEPEC'),\n" +
                "  (20,122,'SAN BARTOLO YAUTEPEC'),\n" +
                "  (20,123,'SAN BERNARDO MIXTEPEC'),\n" +
                "  (20,124,'SAN BLAS ATEMPA'),\n" +
                "  (20,125,'SAN CARLOS YAUTEPEC'),\n" +
                "  (20,126,'SAN CRISTÓBAL AMATLÁN'),\n" +
                "  (20,127,'SAN CRISTÓBAL AMOLTEPEC'),\n" +
                "  (20,128,'SAN CRISTÓBAL LACHIRIOAG'),\n" +
                "  (20,129,'SAN CRISTÓBAL SUCHIXTLAHUACA'),\n" +
                "  (20,130,'SAN DIONISIO DEL MAR'),\n" +
                "  (20,131,'SAN DIONISIO OCOTEPEC'),\n" +
                "  (20,132,'SAN DIONISIO OCOTLÁN'),\n" +
                "  (20,133,'SAN ESTEBAN ATATLAHUCA'),\n" +
                "  (20,134,'SAN FELIPE JALAPA DE DÍAZ'),\n" +
                "  (20,135,'SAN FELIPE TEJALÁPAM'),\n" +
                "  (20,136,'SAN FELIPE USILA'),\n" +
                "  (20,137,'SAN FRANCISCO CAHUACUÁ'),\n" +
                "  (20,138,'SAN FRANCISCO CAJONOS'),\n" +
                "  (20,139,'SAN FRANCISCO CHAPULAPA'),\n" +
                "  (20,140,'SAN FRANCISCO CHINDÚA'),\n" +
                "  (20,141,'SAN FRANCISCO DEL MAR'),\n" +
                "  (20,142,'SAN FRANCISCO HUEHUETLÁN'),\n" +
                "  (20,143,'SAN FRANCISCO IXHUATÁN'),\n" +
                "  (20,144,'SAN FRANCISCO JALTEPETONGO'),\n" +
                "  (20,145,'SAN FRANCISCO LACHIGOLÓ'),\n" +
                "  (20,146,'SAN FRANCISCO LOGUECHE'),\n" +
                "  (20,147,'SAN FRANCISCO NUXAÑO'),\n" +
                "  (20,148,'SAN FRANCISCO OZOLOTEPEC'),\n" +
                "  (20,149,'SAN FRANCISCO SOLA'),\n" +
                "  (20,150,'SAN FRANCISCO TELIXTLAHUACA'),\n" +
                "  (20,151,'SAN FRANCISCO TEOPAN'),\n" +
                "  (20,152,'SAN FRANCISCO TLAPANCINGO'),\n" +
                "  (20,153,'SAN GABRIEL MIXTEPEC'),\n" +
                "  (20,154,'SAN ILDEFONSO AMATLÁN'),\n" +
                "  (20,155,'SAN ILDEFONSO SOLA'),\n" +
                "  (20,156,'SAN ILDEFONSO VILLA ALTA'),\n" +
                "  (20,157,'SAN JACINTO AMILPAS'),\n" +
                "  (20,158,'SAN JACINTO TLACOTEPEC'),\n" +
                "  (20,159,'SAN JERÓNIMO COATLÁN'),\n" +
                "  (20,160,'SAN JERÓNIMO SILACAYOAPILLA'),\n" +
                "  (20,161,'SAN JERÓNIMO SOSOLA'),\n" +
                "  (20,162,'SAN JERÓNIMO TAVICHE'),\n" +
                "  (20,163,'SAN JERÓNIMO TECÓATL'),\n" +
                "  (20,164,'SAN JORGE NUCHITA'),\n" +
                "  (20,165,'SAN JOSÉ AYUQUILA'),\n" +
                "  (20,166,'SAN JOSÉ CHILTEPEC'),\n" +
                "  (20,167,'SAN JOSÉ DEL PEÑASCO'),\n" +
                "  (20,168,'SAN JOSÉ ESTANCIA GRANDE'),\n" +
                "  (20,169,'SAN JOSÉ INDEPENDENCIA'),\n" +
                "  (20,170,'SAN JOSÉ LACHIGUIRI'),\n" +
                "  (20,171,'SAN JOSÉ TENANGO'),\n" +
                "  (20,172,'SAN JUAN ACHIUTLA'),\n" +
                "  (20,173,'SAN JUAN ATEPEC'),\n" +
                "  (20,174,'ÁNIMAS TRUJANO'),\n" +
                "  (20,175,'SAN JUAN BAUTISTA ATATLAHUCA'),\n" +
                "  (20,176,'SAN JUAN BAUTISTA COIXTLAHUACA'),\n" +
                "  (20,177,'SAN JUAN BAUTISTA CUICATLÁN'),\n" +
                "  (20,178,'SAN JUAN BAUTISTA GUELACHE'),\n" +
                "  (20,179,'SAN JUAN BAUTISTA JAYACATLÁN'),\n" +
                "  (20,180,'SAN JUAN BAUTISTA LO DE SOTO'),\n" +
                "  (20,181,'SAN JUAN BAUTISTA SUCHITEPEC'),\n" +
                "  (20,182,'SAN JUAN BAUTISTA TLACOATZINTEPEC'),\n" +
                "  (20,183,'SAN JUAN BAUTISTA TLACHICHILCO'),\n" +
                "  (20,184,'SAN JUAN BAUTISTA TUXTEPEC'),\n" +
                "  (20,185,'SAN JUAN CACAHUATEPEC'),\n" +
                "  (20,186,'SAN JUAN CIENEGUILLA'),\n" +
                "  (20,187,'SAN JUAN COATZÓSPAM'),\n" +
                "  (20,188,'SAN JUAN COLORADO'),\n" +
                "  (20,189,'SAN JUAN COMALTEPEC'),\n" +
                "  (20,190,'SAN JUAN COTZOCÓN'),\n" +
                "  (20,191,'SAN JUAN CHICOMEZÚCHIL'),\n" +
                "  (20,192,'SAN JUAN CHILATECA'),\n" +
                "  (20,193,'SAN JUAN DEL ESTADO'),\n" +
                "  (20,194,'SAN JUAN DEL RÍO'),\n" +
                "  (20,195,'SAN JUAN DIUXI'),\n" +
                "  (20,196,'SAN JUAN EVANGELISTA ANALCO'),\n" +
                "  (20,197,'SAN JUAN GUELAVÍA'),\n" +
                "  (20,198,'SAN JUAN GUICHICOVI'),\n" +
                "  (20,199,'SAN JUAN IHUALTEPEC'),\n" +
                "  (20,200,'SAN JUAN JUQUILA MIXES'),\n" +
                "  (20,201,'SAN JUAN JUQUILA VIJANOS'),\n" +
                "  (20,202,'SAN JUAN LACHAO'),\n" +
                "  (20,203,'SAN JUAN LACHIGALLA'),\n" +
                "  (20,204,'SAN JUAN LAJARCIA'),\n" +
                "  (20,205,'SAN JUAN LALANA'),\n" +
                "  (20,206,'SAN JUAN DE LOS CUÉS'),\n" +
                "  (20,207,'SAN JUAN MAZATLÁN'),\n" +
                "  (20,208,'SAN JUAN MIXTEPEC'),\n" +
                "  (20,209,'SAN JUAN MIXTEPEC'),\n" +
                "  (20,210,'SAN JUAN ÑUMÍ'),\n" +
                "  (20,211,'SAN JUAN OZOLOTEPEC'),\n" +
                "  (20,212,'SAN JUAN PETLAPA'),\n" +
                "  (20,213,'SAN JUAN QUIAHIJE'),\n" +
                "  (20,214,'SAN JUAN QUIOTEPEC'),\n" +
                "  (20,215,'SAN JUAN SAYULTEPEC'),\n" +
                "  (20,216,'SAN JUAN TABAÁ'),\n" +
                "  (20,217,'SAN JUAN TAMAZOLA'),\n" +
                "  (20,218,'SAN JUAN TEITA'),\n" +
                "  (20,219,'SAN JUAN TEITIPAC'),\n" +
                "  (20,220,'SAN JUAN TEPEUXILA'),\n" +
                "  (20,221,'SAN JUAN TEPOSCOLULA'),\n" +
                "  (20,222,'SAN JUAN YAEÉ'),\n" +
                "  (20,223,'SAN JUAN YATZONA'),\n" +
                "  (20,224,'SAN JUAN YUCUITA'),\n" +
                "  (20,225,'SAN LORENZO'),\n" +
                "  (20,226,'SAN LORENZO ALBARRADAS'),\n" +
                "  (20,227,'SAN LORENZO CACAOTEPEC'),\n" +
                "  (20,228,'SAN LORENZO CUAUNECUILTITLA'),\n" +
                "  (20,229,'SAN LORENZO TEXMELÚCAN'),\n" +
                "  (20,230,'SAN LORENZO VICTORIA'),\n" +
                "  (20,231,'SAN LUCAS CAMOTLÁN'),\n" +
                "  (20,232,'SAN LUCAS OJITLÁN'),\n" +
                "  (20,233,'SAN LUCAS QUIAVINÍ'),\n" +
                "  (20,234,'SAN LUCAS ZOQUIÁPAM'),\n" +
                "  (20,235,'SAN LUIS AMATLÁN'),\n" +
                "  (20,236,'SAN MARCIAL OZOLOTEPEC'),\n" +
                "  (20,237,'SAN MARCOS ARTEAGA'),\n" +
                "  (20,238,'SAN MARTÍN DE LOS CANSECOS'),\n" +
                "  (20,239,'SAN MARTÍN HUAMELÚLPAM'),\n" +
                "  (20,240,'SAN MARTÍN ITUNYOSO'),\n" +
                "  (20,241,'SAN MARTÍN LACHILÁ'),\n" +
                "  (20,242,'SAN MARTÍN PERAS'),\n" +
                "  (20,243,'SAN MARTÍN TILCAJETE'),\n" +
                "  (20,244,'SAN MARTÍN TOXPALAN'),\n" +
                "  (20,245,'SAN MARTÍN ZACATEPEC'),\n" +
                "  (20,246,'SAN MATEO CAJONOS'),\n" +
                "  (20,247,'CAPULÁLPAM DE MÉNDEZ'),\n" +
                "  (20,248,'SAN MATEO DEL MAR'),\n" +
                "  (20,249,'SAN MATEO YOLOXOCHITLÁN'),\n" +
                "  (20,250,'SAN MATEO ETLATONGO'),\n" +
                "  (20,251,'SAN MATEO NEJÁPAM'),\n" +
                "  (20,252,'SAN MATEO PEÑASCO'),\n" +
                "  (20,253,'SAN MATEO PIÑAS'),\n" +
                "  (20,254,'SAN MATEO RÍO HONDO'),\n" +
                "  (20,255,'SAN MATEO SINDIHUI'),\n" +
                "  (20,256,'SAN MATEO TLAPILTEPEC'),\n" +
                "  (20,257,'SAN MELCHOR BETAZA'),\n" +
                "  (20,258,'SAN MIGUEL ACHIUTLA'),\n" +
                "  (20,259,'SAN MIGUEL AHUEHUETITLÁN'),\n" +
                "  (20,260,'SAN MIGUEL ALOÁPAM'),\n" +
                "  (20,261,'SAN MIGUEL AMATITLÁN'),\n" +
                "  (20,262,'SAN MIGUEL AMATLÁN'),\n" +
                "  (20,263,'SAN MIGUEL COATLÁN'),\n" +
                "  (20,264,'SAN MIGUEL CHICAHUA'),\n" +
                "  (20,265,'SAN MIGUEL CHIMALAPA'),\n" +
                "  (20,266,'SAN MIGUEL DEL PUERTO'),\n" +
                "  (20,267,'SAN MIGUEL DEL RÍO'),\n" +
                "  (20,268,'SAN MIGUEL EJUTLA'),\n" +
                "  (20,269,'SAN MIGUEL EL GRANDE'),\n" +
                "  (20,270,'SAN MIGUEL HUAUTLA'),\n" +
                "  (20,271,'SAN MIGUEL MIXTEPEC'),\n" +
                "  (20,272,'SAN MIGUEL PANIXTLAHUACA'),\n" +
                "  (20,273,'SAN MIGUEL PERAS'),\n" +
                "  (20,274,'SAN MIGUEL PIEDRAS'),\n" +
                "  (20,275,'SAN MIGUEL QUETZALTEPEC'),\n" +
                "  (20,276,'SAN MIGUEL SANTA FLOR'),\n" +
                "  (20,277,'VILLA SOLA DE VEGA'),\n" +
                "  (20,278,'SAN MIGUEL SOYALTEPEC'),\n" +
                "  (20,279,'SAN MIGUEL SUCHIXTEPEC'),\n" +
                "  (20,280,'VILLA TALEA DE CASTRO'),\n" +
                "  (20,281,'SAN MIGUEL TECOMATLÁN'),\n" +
                "  (20,282,'SAN MIGUEL TENANGO'),\n" +
                "  (20,283,'SAN MIGUEL TEQUIXTEPEC'),\n" +
                "  (20,284,'SAN MIGUEL TILQUIÁPAM'),\n" +
                "  (20,285,'SAN MIGUEL TLACAMAMA'),\n" +
                "  (20,286,'SAN MIGUEL TLACOTEPEC'),\n" +
                "  (20,287,'SAN MIGUEL TULANCINGO'),\n" +
                "  (20,288,'SAN MIGUEL YOTAO'),\n" +
                "  (20,289,'SAN NICOLÁS'),\n" +
                "  (20,290,'SAN NICOLÁS HIDALGO'),\n" +
                "  (20,291,'SAN PABLO COATLÁN'),\n" +
                "  (20,292,'SAN PABLO CUATRO VENADOS'),\n" +
                "  (20,293,'SAN PABLO ETLA'),\n" +
                "  (20,294,'SAN PABLO HUITZO'),\n" +
                "  (20,295,'SAN PABLO HUIXTEPEC'),\n" +
                "  (20,296,'SAN PABLO MACUILTIANGUIS'),\n" +
                "  (20,297,'SAN PABLO TIJALTEPEC'),\n" +
                "  (20,298,'SAN PABLO VILLA DE MITLA'),\n" +
                "  (20,299,'SAN PABLO YAGANIZA'),\n" +
                "  (20,300,'SAN PEDRO AMUZGOS'),\n" +
                "  (20,301,'SAN PEDRO APÓSTOL'),\n" +
                "  (20,302,'SAN PEDRO ATOYAC'),\n" +
                "  (20,303,'SAN PEDRO CAJONOS'),\n" +
                "  (20,304,'SAN PEDRO COXCALTEPEC CÁNTAROS'),\n" +
                "  (20,305,'SAN PEDRO COMITANCILLO'),\n" +
                "  (20,306,'SAN PEDRO EL ALTO'),\n" +
                "  (20,307,'SAN PEDRO HUAMELULA'),\n" +
                "  (20,308,'SAN PEDRO HUILOTEPEC'),\n" +
                "  (20,309,'SAN PEDRO IXCATLÁN'),\n" +
                "  (20,310,'SAN PEDRO IXTLAHUACA'),\n" +
                "  (20,311,'SAN PEDRO JALTEPETONGO'),\n" +
                "  (20,312,'SAN PEDRO JICAYÁN'),\n" +
                "  (20,313,'SAN PEDRO JOCOTIPAC'),\n" +
                "  (20,314,'SAN PEDRO JUCHATENGO'),\n" +
                "  (20,315,'SAN PEDRO MÁRTIR'),\n" +
                "  (20,316,'SAN PEDRO MÁRTIR QUIECHAPA'),\n" +
                "  (20,317,'SAN PEDRO MÁRTIR YUCUXACO'),\n" +
                "  (20,318,'SAN PEDRO MIXTEPEC'),\n" +
                "  (20,319,'SAN PEDRO MIXTEPEC'),\n" +
                "  (20,320,'SAN PEDRO MOLINOS'),\n" +
                "  (20,321,'SAN PEDRO NOPALA'),\n" +
                "  (20,322,'SAN PEDRO OCOPETATILLO'),\n" +
                "  (20,323,'SAN PEDRO OCOTEPEC'),\n" +
                "  (20,324,'SAN PEDRO POCHUTLA'),\n" +
                "  (20,325,'SAN PEDRO QUIATONI'),\n" +
                "  (20,326,'SAN PEDRO SOCHIÁPAM'),\n" +
                "  (20,327,'SAN PEDRO TAPANATEPEC'),\n" +
                "  (20,328,'SAN PEDRO TAVICHE'),\n" +
                "  (20,329,'SAN PEDRO TEOZACOALCO'),\n" +
                "  (20,330,'SAN PEDRO TEUTILA'),\n" +
                "  (20,331,'SAN PEDRO TIDAÁ'),\n" +
                "  (20,332,'SAN PEDRO TOPILTEPEC'),\n" +
                "  (20,333,'SAN PEDRO TOTOLÁPAM'),\n" +
                "  (20,334,'VILLA DE TUTUTEPEC'),\n" +
                "  (20,335,'SAN PEDRO YANERI'),\n" +
                "  (20,336,'SAN PEDRO YÓLOX'),\n" +
                "  (20,337,'SAN PEDRO Y SAN PABLO AYUTLA'),\n" +
                "  (20,338,'VILLA DE ETLA'),\n" +
                "  (20,339,'SAN PEDRO Y SAN PABLO TEPOSCOLULA'),\n" +
                "  (20,340,'SAN PEDRO Y SAN PABLO TEQUIXTEPEC'),\n" +
                "  (20,341,'SAN PEDRO YUCUNAMA'),\n" +
                "  (20,342,'SAN RAYMUNDO JALPAN'),\n" +
                "  (20,343,'SAN SEBASTIÁN ABASOLO'),\n" +
                "  (20,344,'SAN SEBASTIÁN COATLÁN'),\n" +
                "  (20,345,'SAN SEBASTIÁN IXCAPA'),\n" +
                "  (20,346,'SAN SEBASTIÁN NICANANDUTA'),\n" +
                "  (20,347,'SAN SEBASTIÁN RÍO HONDO'),\n" +
                "  (20,348,'SAN SEBASTIÁN TECOMAXTLAHUACA'),\n" +
                "  (20,349,'SAN SEBASTIÁN TEITIPAC'),\n" +
                "  (20,350,'SAN SEBASTIÁN TUTLA'),\n" +
                "  (20,351,'SAN SIMÓN ALMOLONGAS'),\n" +
                "  (20,352,'SAN SIMÓN ZAHUATLÁN'),\n" +
                "  (20,353,'SANTA ANA'),\n" +
                "  (20,354,'SANTA ANA ATEIXTLAHUACA'),\n" +
                "  (20,355,'SANTA ANA CUAUHTÉMOC'),\n" +
                "  (20,356,'SANTA ANA DEL VALLE'),\n" +
                "  (20,357,'SANTA ANA TAVELA'),\n" +
                "  (20,358,'SANTA ANA TLAPACOYAN'),\n" +
                "  (20,359,'SANTA ANA YARENI'),\n" +
                "  (20,360,'SANTA ANA ZEGACHE'),\n" +
                "  (20,361,'SANTA CATALINA QUIERÍ'),\n" +
                "  (20,362,'SANTA CATARINA CUIXTLA'),\n" +
                "  (20,363,'SANTA CATARINA IXTEPEJI'),\n" +
                "  (20,364,'SANTA CATARINA JUQUILA'),\n" +
                "  (20,365,'SANTA CATARINA LACHATAO'),\n" +
                "  (20,366,'SANTA CATARINA LOXICHA'),\n" +
                "  (20,367,'SANTA CATARINA MECHOACÁN'),\n" +
                "  (20,368,'SANTA CATARINA MINAS'),\n" +
                "  (20,369,'SANTA CATARINA QUIANÉ'),\n" +
                "  (20,370,'SANTA CATARINA TAYATA'),\n" +
                "  (20,371,'SANTA CATARINA TICUÁ'),\n" +
                "  (20,372,'SANTA CATARINA YOSONOTÚ'),\n" +
                "  (20,373,'SANTA CATARINA ZAPOQUILA'),\n" +
                "  (20,374,'SANTA CRUZ ACATEPEC'),\n" +
                "  (20,375,'SANTA CRUZ AMILPAS'),\n" +
                "  (20,376,'SANTA CRUZ DE BRAVO'),\n" +
                "  (20,377,'SANTA CRUZ ITUNDUJIA'),\n" +
                "  (20,378,'SANTA CRUZ MIXTEPEC'),\n" +
                "  (20,379,'SANTA CRUZ NUNDACO'),\n" +
                "  (20,380,'SANTA CRUZ PAPALUTLA'),\n" +
                "  (20,381,'SANTA CRUZ TACACHE DE MINA'),\n" +
                "  (20,382,'SANTA CRUZ TACAHUA'),\n" +
                "  (20,383,'SANTA CRUZ TAYATA'),\n" +
                "  (20,384,'SANTA CRUZ XITLA'),\n" +
                "  (20,385,'SANTA CRUZ XOXOCOTLÁN'),\n" +
                "  (20,386,'SANTA CRUZ ZENZONTEPEC'),\n" +
                "  (20,387,'SANTA GERTRUDIS'),\n" +
                "  (20,388,'SANTA INÉS DEL MONTE'),\n" +
                "  (20,389,'SANTA INÉS YATZECHE'),\n" +
                "  (20,390,'SANTA LUCÍA DEL CAMINO'),\n" +
                "  (20,391,'SANTA LUCÍA MIAHUATLÁN'),\n" +
                "  (20,392,'SANTA LUCÍA MONTEVERDE'),\n" +
                "  (20,393,'SANTA LUCÍA OCOTLÁN'),\n" +
                "  (20,394,'SANTA MARÍA ALOTEPEC'),\n" +
                "  (20,395,'SANTA MARÍA APAZCO'),\n" +
                "  (20,396,'SANTA MARÍA LA ASUNCIÓN'),\n" +
                "  (20,397,'HEROICA CIUDAD DE TLAXIACO'),\n" +
                "  (20,398,'AYOQUEZCO DE ALDAMA'),\n" +
                "  (20,399,'SANTA MARÍA ATZOMPA'),\n" +
                "  (20,400,'SANTA MARÍA CAMOTLÁN'),\n" +
                "  (20,401,'SANTA MARÍA COLOTEPEC'),\n" +
                "  (20,402,'SANTA MARÍA CORTIJO'),\n" +
                "  (20,403,'SANTA MARÍA COYOTEPEC'),\n" +
                "  (20,404,'SANTA MARÍA CHACHOÁPAM'),\n" +
                "  (20,405,'VILLA DE CHILAPA DE DÍAZ'),\n" +
                "  (20,406,'SANTA MARÍA CHILCHOTLA'),\n" +
                "  (20,407,'SANTA MARÍA CHIMALAPA'),\n" +
                "  (20,408,'SANTA MARÍA DEL ROSARIO'),\n" +
                "  (20,409,'SANTA MARÍA DEL TULE'),\n" +
                "  (20,410,'SANTA MARÍA ECATEPEC'),\n" +
                "  (20,411,'SANTA MARÍA GUELACÉ'),\n" +
                "  (20,412,'SANTA MARÍA GUIENAGATI'),\n" +
                "  (20,413,'SANTA MARÍA HUATULCO'),\n" +
                "  (20,414,'SANTA MARÍA HUAZOLOTITLÁN'),\n" +
                "  (20,415,'SANTA MARÍA IPALAPA'),\n" +
                "  (20,416,'SANTA MARÍA IXCATLÁN'),\n" +
                "  (20,417,'SANTA MARÍA JACATEPEC'),\n" +
                "  (20,418,'SANTA MARÍA JALAPA DEL MARQUÉS'),\n" +
                "  (20,419,'SANTA MARÍA JALTIANGUIS'),\n" +
                "  (20,420,'SANTA MARÍA LACHIXÍO'),\n" +
                "  (20,421,'SANTA MARÍA MIXTEQUILLA'),\n" +
                "  (20,422,'SANTA MARÍA NATIVITAS'),\n" +
                "  (20,423,'SANTA MARÍA NDUAYACO'),\n" +
                "  (20,424,'SANTA MARÍA OZOLOTEPEC'),\n" +
                "  (20,425,'SANTA MARÍA PÁPALO'),\n" +
                "  (20,426,'SANTA MARÍA PEÑOLES'),\n" +
                "  (20,427,'SANTA MARÍA PETAPA'),\n" +
                "  (20,428,'SANTA MARÍA QUIEGOLANI'),\n" +
                "  (20,429,'SANTA MARÍA SOLA'),\n" +
                "  (20,430,'SANTA MARÍA TATALTEPEC'),\n" +
                "  (20,431,'SANTA MARÍA TECOMAVACA'),\n" +
                "  (20,432,'SANTA MARÍA TEMAXCALAPA'),\n" +
                "  (20,433,'SANTA MARÍA TEMAXCALTEPEC'),\n" +
                "  (20,434,'SANTA MARÍA TEOPOXCO'),\n" +
                "  (20,435,'SANTA MARÍA TEPANTLALI'),\n" +
                "  (20,436,'SANTA MARÍA TEXCATITLÁN'),\n" +
                "  (20,437,'SANTA MARÍA TLAHUITOLTEPEC'),\n" +
                "  (20,438,'SANTA MARÍA TLALIXTAC'),\n" +
                "  (20,439,'SANTA MARÍA TONAMECA'),\n" +
                "  (20,440,'SANTA MARÍA TOTOLAPILLA'),\n" +
                "  (20,441,'SANTA MARÍA XADANI'),\n" +
                "  (20,442,'SANTA MARÍA YALINA'),\n" +
                "  (20,443,'SANTA MARÍA YAVESÍA'),\n" +
                "  (20,444,'SANTA MARÍA YOLOTEPEC'),\n" +
                "  (20,445,'SANTA MARÍA YOSOYÚA'),\n" +
                "  (20,446,'SANTA MARÍA YUCUHITI'),\n" +
                "  (20,447,'SANTA MARÍA ZACATEPEC'),\n" +
                "  (20,448,'SANTA MARÍA ZANIZA'),\n" +
                "  (20,449,'SANTA MARÍA ZOQUITLÁN'),\n" +
                "  (20,450,'SANTIAGO AMOLTEPEC'),\n" +
                "  (20,451,'SANTIAGO APOALA'),\n" +
                "  (20,452,'SANTIAGO APÓSTOL'),\n" +
                "  (20,453,'SANTIAGO ASTATA'),\n" +
                "  (20,454,'SANTIAGO ATITLÁN'),\n" +
                "  (20,455,'SANTIAGO AYUQUILILLA'),\n" +
                "  (20,456,'SANTIAGO CACALOXTEPEC'),\n" +
                "  (20,457,'SANTIAGO CAMOTLÁN'),\n" +
                "  (20,458,'SANTIAGO COMALTEPEC'),\n" +
                "  (20,459,'SANTIAGO CHAZUMBA'),\n" +
                "  (20,460,'SANTIAGO CHOÁPAM'),\n" +
                "  (20,461,'SANTIAGO DEL RÍO'),\n" +
                "  (20,462,'SANTIAGO HUAJOLOTITLÁN'),\n" +
                "  (20,463,'SANTIAGO HUAUCLILLA'),\n" +
                "  (20,464,'SANTIAGO IHUITLÁN PLUMAS'),\n" +
                "  (20,465,'SANTIAGO IXCUINTEPEC'),\n" +
                "  (20,466,'SANTIAGO IXTAYUTLA'),\n" +
                "  (20,467,'SANTIAGO JAMILTEPEC'),\n" +
                "  (20,468,'SANTIAGO JOCOTEPEC'),\n" +
                "  (20,469,'SANTIAGO JUXTLAHUACA'),\n" +
                "  (20,470,'SANTIAGO LACHIGUIRI'),\n" +
                "  (20,471,'SANTIAGO LALOPA'),\n" +
                "  (20,472,'SANTIAGO LAOLLAGA'),\n" +
                "  (20,473,'SANTIAGO LAXOPA'),\n" +
                "  (20,474,'SANTIAGO LLANO GRANDE'),\n" +
                "  (20,475,'SANTIAGO MATATLÁN'),\n" +
                "  (20,476,'SANTIAGO MILTEPEC'),\n" +
                "  (20,477,'SANTIAGO MINAS'),\n" +
                "  (20,478,'SANTIAGO NACALTEPEC'),\n" +
                "  (20,479,'SANTIAGO NEJAPILLA'),\n" +
                "  (20,480,'SANTIAGO NUNDICHE'),\n" +
                "  (20,481,'SANTIAGO NUYOÓ'),\n" +
                "  (20,482,'SANTIAGO PINOTEPA NACIONAL'),\n" +
                "  (20,483,'SANTIAGO SUCHILQUITONGO'),\n" +
                "  (20,484,'SANTIAGO TAMAZOLA'),\n" +
                "  (20,485,'SANTIAGO TAPEXTLA'),\n" +
                "  (20,486,'VILLA TEJÚPAM DE LA UNIÓN'),\n" +
                "  (20,487,'SANTIAGO TENANGO'),\n" +
                "  (20,488,'SANTIAGO TEPETLAPA'),\n" +
                "  (20,489,'SANTIAGO TETEPEC'),\n" +
                "  (20,490,'SANTIAGO TEXCALCINGO'),\n" +
                "  (20,491,'SANTIAGO TEXTITLÁN'),\n" +
                "  (20,492,'SANTIAGO TILANTONGO'),\n" +
                "  (20,493,'SANTIAGO TILLO'),\n" +
                "  (20,494,'SANTIAGO TLAZOYALTEPEC'),\n" +
                "  (20,495,'SANTIAGO XANICA'),\n" +
                "  (20,496,'SANTIAGO XIACUÍ'),\n" +
                "  (20,497,'SANTIAGO YAITEPEC'),\n" +
                "  (20,498,'SANTIAGO YAVEO'),\n" +
                "  (20,499,'SANTIAGO YOLOMÉCATL'),\n" +
                "  (20,500,'SANTIAGO YOSONDÚA'),\n" +
                "  (20,501,'SANTIAGO YUCUYACHI'),\n" +
                "  (20,502,'SANTIAGO ZACATEPEC');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_cat_municipios` (`id_estado`, `id_municipio`, `municipio`) VALUES\n" +
                "  (20,503,'SANTIAGO ZOOCHILA'),\n" +
                "  (20,504,'NUEVO ZOQUIÁPAM'),\n" +
                "  (20,505,'SANTO DOMINGO INGENIO'),\n" +
                "  (20,506,'SANTO DOMINGO ALBARRADAS'),\n" +
                "  (20,507,'SANTO DOMINGO ARMENTA'),\n" +
                "  (20,508,'SANTO DOMINGO CHIHUITÁN'),\n" +
                "  (20,509,'SANTO DOMINGO DE MORELOS'),\n" +
                "  (20,510,'SANTO DOMINGO IXCATLÁN'),\n" +
                "  (20,511,'SANTO DOMINGO NUXAÁ'),\n" +
                "  (20,512,'SANTO DOMINGO OZOLOTEPEC'),\n" +
                "  (20,513,'SANTO DOMINGO PETAPA'),\n" +
                "  (20,514,'SANTO DOMINGO ROAYAGA'),\n" +
                "  (20,515,'SANTO DOMINGO TEHUANTEPEC'),\n" +
                "  (20,516,'SANTO DOMINGO TEOJOMULCO'),\n" +
                "  (20,517,'SANTO DOMINGO TEPUXTEPEC'),\n" +
                "  (20,518,'SANTO DOMINGO TLATAYÁPAM'),\n" +
                "  (20,519,'SANTO DOMINGO TOMALTEPEC'),\n" +
                "  (20,520,'SANTO DOMINGO TONALÁ'),\n" +
                "  (20,521,'SANTO DOMINGO TONALTEPEC'),\n" +
                "  (20,522,'SANTO DOMINGO XAGACÍA'),\n" +
                "  (20,523,'SANTO DOMINGO YANHUITLÁN'),\n" +
                "  (20,524,'SANTO DOMINGO YODOHINO'),\n" +
                "  (20,525,'SANTO DOMINGO ZANATEPEC'),\n" +
                "  (20,526,'SANTOS REYES NOPALA'),\n" +
                "  (20,527,'SANTOS REYES PÁPALO'),\n" +
                "  (20,528,'SANTOS REYES TEPEJILLO'),\n" +
                "  (20,529,'SANTOS REYES YUCUNÁ'),\n" +
                "  (20,530,'SANTO TOMÁS JALIEZA'),\n" +
                "  (20,531,'SANTO TOMÁS MAZALTEPEC'),\n" +
                "  (20,532,'SANTO TOMÁS OCOTEPEC'),\n" +
                "  (20,533,'SANTO TOMÁS TAMAZULAPAN'),\n" +
                "  (20,534,'SAN VICENTE COATLÁN'),\n" +
                "  (20,535,'SAN VICENTE LACHIXÍO'),\n" +
                "  (20,536,'SAN VICENTE NUÑÚ'),\n" +
                "  (20,537,'SILACAYOÁPAM'),\n" +
                "  (20,538,'SITIO DE XITLAPEHUA'),\n" +
                "  (20,539,'SOLEDAD ETLA'),\n" +
                "  (20,540,'VILLA DE TAMAZULÁPAM DEL PROGRESO'),\n" +
                "  (20,541,'TANETZE DE ZARAGOZA'),\n" +
                "  (20,542,'TANICHE'),\n" +
                "  (20,543,'TATALTEPEC DE VALDÉS'),\n" +
                "  (20,544,'TEOCOCUILCO DE MARCOS PÉREZ'),\n" +
                "  (20,545,'TEOTITLÁN DE FLORES MAGÓN'),\n" +
                "  (20,546,'TEOTITLÁN DEL VALLE'),\n" +
                "  (20,547,'TEOTONGO'),\n" +
                "  (20,548,'TEPELMEME VILLA DE MORELOS'),\n" +
                "  (20,549,'HEROICA VILLA TEZOATLÁN DE SEGURA Y LUNA, CUNA DE LA INDEPENDENCIA DE OAXACA'),\n" +
                "  (20,550,'SAN JERÓNIMO TLACOCHAHUAYA'),\n" +
                "  (20,551,'TLACOLULA DE MATAMOROS'),\n" +
                "  (20,552,'TLACOTEPEC PLUMAS'),\n" +
                "  (20,553,'TLALIXTAC DE CABRERA'),\n" +
                "  (20,554,'TOTONTEPEC VILLA DE MORELOS'),\n" +
                "  (20,555,'TRINIDAD ZAACHILA'),\n" +
                "  (20,556,'LA TRINIDAD VISTA HERMOSA'),\n" +
                "  (20,557,'UNIÓN HIDALGO'),\n" +
                "  (20,558,'VALERIO TRUJANO'),\n" +
                "  (20,559,'SAN JUAN BAUTISTA VALLE NACIONAL'),\n" +
                "  (20,560,'VILLA DÍAZ ORDAZ'),\n" +
                "  (20,561,'YAXE'),\n" +
                "  (20,562,'MAGDALENA YODOCONO DE PORFIRIO DÍAZ'),\n" +
                "  (20,563,'YOGANA'),\n" +
                "  (20,564,'YUTANDUCHI DE GUERRERO'),\n" +
                "  (20,565,'VILLA DE ZAACHILA'),\n" +
                "  (20,566,'SAN MATEO YUCUTINDOO'),\n" +
                "  (20,567,'ZAPOTITLÁN LAGUNAS'),\n" +
                "  (20,568,'ZAPOTITLÁN PALMAS'),\n" +
                "  (20,569,'SANTA INÉS DE ZARAGOZA'),\n" +
                "  (20,570,'ZIMATLÁN DE ÁLVAREZ'),\n" +
                "  (21,1,'ACAJETE'),\n" +
                "  (21,2,'ACATENO'),\n" +
                "  (21,3,'ACATLÁN'),\n" +
                "  (21,4,'ACATZINGO'),\n" +
                "  (21,5,'ACTEOPAN'),\n" +
                "  (21,6,'AHUACATLÁN'),\n" +
                "  (21,7,'AHUATLÁN'),\n" +
                "  (21,8,'AHUAZOTEPEC'),\n" +
                "  (21,9,'AHUEHUETITLA'),\n" +
                "  (21,10,'AJALPAN'),\n" +
                "  (21,11,'ALBINO ZERTUCHE'),\n" +
                "  (21,12,'ALJOJUCA'),\n" +
                "  (21,13,'ALTEPEXI'),\n" +
                "  (21,14,'AMIXTLÁN'),\n" +
                "  (21,15,'AMOZOC'),\n" +
                "  (21,16,'AQUIXTLA'),\n" +
                "  (21,17,'ATEMPAN'),\n" +
                "  (21,18,'ATEXCAL'),\n" +
                "  (21,19,'ATLIXCO'),\n" +
                "  (21,20,'ATOYATEMPAN'),\n" +
                "  (21,21,'ATZALA'),\n" +
                "  (21,22,'ATZITZIHUACÁN'),\n" +
                "  (21,23,'ATZITZINTLA'),\n" +
                "  (21,24,'AXUTLA'),\n" +
                "  (21,25,'AYOTOXCO DE GUERRERO'),\n" +
                "  (21,26,'CALPAN'),\n" +
                "  (21,27,'CALTEPEC'),\n" +
                "  (21,28,'CAMOCUAUTLA'),\n" +
                "  (21,29,'CAXHUACAN'),\n" +
                "  (21,30,'COATEPEC'),\n" +
                "  (21,31,'COATZINGO'),\n" +
                "  (21,32,'COHETZALA'),\n" +
                "  (21,33,'COHUECAN'),\n" +
                "  (21,34,'CORONANGO'),\n" +
                "  (21,35,'COXCATLÁN'),\n" +
                "  (21,36,'COYOMEAPAN'),\n" +
                "  (21,37,'COYOTEPEC'),\n" +
                "  (21,38,'CUAPIAXTLA DE MADERO'),\n" +
                "  (21,39,'CUAUTEMPAN'),\n" +
                "  (21,40,'CUAUTINCHÁN'),\n" +
                "  (21,41,'CUAUTLANCINGO'),\n" +
                "  (21,42,'CUAYUCA DE ANDRADE'),\n" +
                "  (21,43,'CUETZALAN DEL PROGRESO'),\n" +
                "  (21,44,'CUYOACO'),\n" +
                "  (21,45,'CHALCHICOMULA DE SESMA'),\n" +
                "  (21,46,'CHAPULCO'),\n" +
                "  (21,47,'CHIAUTLA'),\n" +
                "  (21,48,'CHIAUTZINGO'),\n" +
                "  (21,49,'CHICONCUAUTLA'),\n" +
                "  (21,50,'CHICHIQUILA'),\n" +
                "  (21,51,'CHIETLA'),\n" +
                "  (21,52,'CHIGMECATITLÁN'),\n" +
                "  (21,53,'CHIGNAHUAPAN'),\n" +
                "  (21,54,'CHIGNAUTLA'),\n" +
                "  (21,55,'CHILA'),\n" +
                "  (21,56,'CHILA DE LA SAL'),\n" +
                "  (21,57,'HONEY'),\n" +
                "  (21,58,'CHILCHOTLA'),\n" +
                "  (21,59,'CHINANTLA'),\n" +
                "  (21,60,'DOMINGO ARENAS'),\n" +
                "  (21,61,'ELOXOCHITLÁN'),\n" +
                "  (21,62,'EPATLÁN'),\n" +
                "  (21,63,'ESPERANZA'),\n" +
                "  (21,64,'FRANCISCO Z. MENA'),\n" +
                "  (21,65,'GENERAL FELIPE ÁNGELES'),\n" +
                "  (21,66,'GUADALUPE'),\n" +
                "  (21,67,'GUADALUPE VICTORIA'),\n" +
                "  (21,68,'HERMENEGILDO GALEANA'),\n" +
                "  (21,69,'HUAQUECHULA'),\n" +
                "  (21,70,'HUATLATLAUCA'),\n" +
                "  (21,71,'HUAUCHINANGO'),\n" +
                "  (21,72,'HUEHUETLA'),\n" +
                "  (21,73,'HUEHUETLÁN EL CHICO'),\n" +
                "  (21,74,'HUEJOTZINGO'),\n" +
                "  (21,75,'HUEYAPAN'),\n" +
                "  (21,76,'HUEYTAMALCO'),\n" +
                "  (21,77,'HUEYTLALPAN'),\n" +
                "  (21,78,'HUITZILAN DE SERDÁN'),\n" +
                "  (21,79,'HUITZILTEPEC'),\n" +
                "  (21,80,'ATLEQUIZAYAN'),\n" +
                "  (21,81,'IXCAMILPA DE GUERRERO'),\n" +
                "  (21,82,'IXCAQUIXTLA'),\n" +
                "  (21,83,'IXTACAMAXTITLÁN'),\n" +
                "  (21,84,'IXTEPEC'),\n" +
                "  (21,85,'IZÚCAR DE MATAMOROS'),\n" +
                "  (21,86,'JALPAN'),\n" +
                "  (21,87,'JOLALPAN'),\n" +
                "  (21,88,'JONOTLA'),\n" +
                "  (21,89,'JOPALA'),\n" +
                "  (21,90,'JUAN C. BONILLA'),\n" +
                "  (21,91,'JUAN GALINDO'),\n" +
                "  (21,92,'JUAN N. MÉNDEZ'),\n" +
                "  (21,93,'LAFRAGUA'),\n" +
                "  (21,94,'LIBRES'),\n" +
                "  (21,95,'LA MAGDALENA TLATLAUQUITEPEC'),\n" +
                "  (21,96,'MAZAPILTEPEC DE JUÁREZ'),\n" +
                "  (21,97,'MIXTLA'),\n" +
                "  (21,98,'MOLCAXAC'),\n" +
                "  (21,99,'CAÑADA MORELOS'),\n" +
                "  (21,100,'NAUPAN'),\n" +
                "  (21,101,'NAUZONTLA'),\n" +
                "  (21,102,'NEALTICAN'),\n" +
                "  (21,103,'NICOLÁS BRAVO'),\n" +
                "  (21,104,'NOPALUCAN'),\n" +
                "  (21,105,'OCOTEPEC'),\n" +
                "  (21,106,'OCOYUCAN'),\n" +
                "  (21,107,'OLINTLA'),\n" +
                "  (21,108,'ORIENTAL'),\n" +
                "  (21,109,'PAHUATLÁN'),\n" +
                "  (21,110,'PALMAR DE BRAVO'),\n" +
                "  (21,111,'PANTEPEC'),\n" +
                "  (21,112,'PETLALCINGO'),\n" +
                "  (21,113,'PIAXTLA'),\n" +
                "  (21,114,'PUEBLA'),\n" +
                "  (21,115,'QUECHOLAC'),\n" +
                "  (21,116,'QUIMIXTLÁN'),\n" +
                "  (21,117,'RAFAEL LARA GRAJALES'),\n" +
                "  (21,118,'LOS REYES DE JUÁREZ'),\n" +
                "  (21,119,'SAN ANDRÉS CHOLULA'),\n" +
                "  (21,120,'SAN ANTONIO CAÑADA'),\n" +
                "  (21,121,'SAN DIEGO LA MESA TOCHIMILTZINGO'),\n" +
                "  (21,122,'SAN FELIPE TEOTLALCINGO'),\n" +
                "  (21,123,'SAN FELIPE TEPATLÁN'),\n" +
                "  (21,124,'SAN GABRIEL CHILAC'),\n" +
                "  (21,125,'SAN GREGORIO ATZOMPA'),\n" +
                "  (21,126,'SAN JERÓNIMO TECUANIPAN'),\n" +
                "  (21,127,'SAN JERÓNIMO XAYACATLÁN'),\n" +
                "  (21,128,'SAN JOSÉ CHIAPA'),\n" +
                "  (21,129,'SAN JOSÉ MIAHUATLÁN'),\n" +
                "  (21,130,'SAN JUAN ATENCO'),\n" +
                "  (21,131,'SAN JUAN ATZOMPA'),\n" +
                "  (21,132,'SAN MARTÍN TEXMELUCAN'),\n" +
                "  (21,133,'SAN MARTÍN TOTOLTEPEC'),\n" +
                "  (21,134,'SAN MATÍAS TLALANCALECA'),\n" +
                "  (21,135,'SAN MIGUEL IXITLÁN'),\n" +
                "  (21,136,'SAN MIGUEL XOXTLA'),\n" +
                "  (21,137,'SAN NICOLÁS BUENOS AIRES'),\n" +
                "  (21,138,'SAN NICOLÁS DE LOS RANCHOS'),\n" +
                "  (21,139,'SAN PABLO ANICANO'),\n" +
                "  (21,140,'SAN PEDRO CHOLULA'),\n" +
                "  (21,141,'SAN PEDRO YELOIXTLAHUACA'),\n" +
                "  (21,142,'SAN SALVADOR EL SECO'),\n" +
                "  (21,143,'SAN SALVADOR EL VERDE'),\n" +
                "  (21,144,'SAN SALVADOR HUIXCOLOTLA'),\n" +
                "  (21,145,'SAN SEBASTIÁN TLACOTEPEC'),\n" +
                "  (21,146,'SANTA CATARINA TLALTEMPAN'),\n" +
                "  (21,147,'SANTA INÉS AHUATEMPAN'),\n" +
                "  (21,148,'SANTA ISABEL CHOLULA'),\n" +
                "  (21,149,'SANTIAGO MIAHUATLÁN'),\n" +
                "  (21,150,'HUEHUETLÁN EL GRANDE'),\n" +
                "  (21,151,'SANTO TOMÁS HUEYOTLIPAN'),\n" +
                "  (21,152,'SOLTEPEC'),\n" +
                "  (21,153,'TECALI DE HERRERA'),\n" +
                "  (21,154,'TECAMACHALCO'),\n" +
                "  (21,155,'TECOMATLÁN'),\n" +
                "  (21,156,'TEHUACÁN'),\n" +
                "  (21,157,'TEHUITZINGO'),\n" +
                "  (21,158,'TENAMPULCO'),\n" +
                "  (21,159,'TEOPANTLÁN'),\n" +
                "  (21,160,'TEOTLALCO'),\n" +
                "  (21,161,'TEPANCO DE LÓPEZ'),\n" +
                "  (21,162,'TEPANGO DE RODRÍGUEZ'),\n" +
                "  (21,163,'TEPATLAXCO DE HIDALGO'),\n" +
                "  (21,164,'TEPEACA'),\n" +
                "  (21,165,'TEPEMAXALCO'),\n" +
                "  (21,166,'TEPEOJUMA'),\n" +
                "  (21,167,'TEPETZINTLA'),\n" +
                "  (21,168,'TEPEXCO'),\n" +
                "  (21,169,'TEPEXI DE RODRÍGUEZ'),\n" +
                "  (21,170,'TEPEYAHUALCO'),\n" +
                "  (21,171,'TEPEYAHUALCO DE CUAUHTÉMOC'),\n" +
                "  (21,172,'TETELA DE OCAMPO'),\n" +
                "  (21,173,'TETELES DE AVILA CASTILLO'),\n" +
                "  (21,174,'TEZIUTLÁN'),\n" +
                "  (21,175,'TIANGUISMANALCO'),\n" +
                "  (21,176,'TILAPA'),\n" +
                "  (21,177,'TLACOTEPEC DE BENITO JUÁREZ'),\n" +
                "  (21,178,'TLACUILOTEPEC'),\n" +
                "  (21,179,'TLACHICHUCA'),\n" +
                "  (21,180,'TLAHUAPAN'),\n" +
                "  (21,181,'TLALTENANGO'),\n" +
                "  (21,182,'TLANEPANTLA'),\n" +
                "  (21,183,'TLAOLA'),\n" +
                "  (21,184,'TLAPACOYA'),\n" +
                "  (21,185,'TLAPANALÁ'),\n" +
                "  (21,186,'TLATLAUQUITEPEC'),\n" +
                "  (21,187,'TLAXCO'),\n" +
                "  (21,188,'TOCHIMILCO'),\n" +
                "  (21,189,'TOCHTEPEC'),\n" +
                "  (21,190,'TOTOLTEPEC DE GUERRERO'),\n" +
                "  (21,191,'TULCINGO'),\n" +
                "  (21,192,'TUZAMAPAN DE GALEANA'),\n" +
                "  (21,193,'TZICATLACOYAN'),\n" +
                "  (21,194,'VENUSTIANO CARRANZA'),\n" +
                "  (21,195,'VICENTE GUERRERO'),\n" +
                "  (21,196,'XAYACATLÁN DE BRAVO'),\n" +
                "  (21,197,'XICOTEPEC'),\n" +
                "  (21,198,'XICOTLÁN'),\n" +
                "  (21,199,'XIUTETELCO'),\n" +
                "  (21,200,'XOCHIAPULCO'),\n" +
                "  (21,201,'XOCHILTEPEC'),\n" +
                "  (21,202,'XOCHITLÁN DE VICENTE SUÁREZ'),\n" +
                "  (21,203,'XOCHITLÁN TODOS SANTOS'),\n" +
                "  (21,204,'YAONÁHUAC'),\n" +
                "  (21,205,'YEHUALTEPEC'),\n" +
                "  (21,206,'ZACAPALA'),\n" +
                "  (21,207,'ZACAPOAXTLA'),\n" +
                "  (21,208,'ZACATLÁN'),\n" +
                "  (21,209,'ZAPOTITLÁN'),\n" +
                "  (21,210,'ZAPOTITLÁN DE MÉNDEZ'),\n" +
                "  (21,211,'ZARAGOZA'),\n" +
                "  (21,212,'ZAUTLA'),\n" +
                "  (21,213,'ZIHUATEUTLA'),\n" +
                "  (21,214,'ZINACATEPEC'),\n" +
                "  (21,215,'ZONGOZOTLA'),\n" +
                "  (21,216,'ZOQUIAPAN'),\n" +
                "  (21,217,'ZOQUITLÁN'),\n" +
                "  (22,1,'AMEALCO DE BONFIL'),\n" +
                "  (22,2,'PINAL DE AMOLES'),\n" +
                "  (22,3,'ARROYO SECO'),\n" +
                "  (22,4,'CADEREYTA DE MONTES'),\n" +
                "  (22,5,'COLÓN'),\n" +
                "  (22,6,'CORREGIDORA'),\n" +
                "  (22,7,'EZEQUIEL MONTES'),\n" +
                "  (22,8,'HUIMILPAN'),\n" +
                "  (22,9,'JALPAN DE SERRA'),\n" +
                "  (22,10,'LANDA DE MATAMOROS'),\n" +
                "  (22,11,'EL MARQUÉS'),\n" +
                "  (22,12,'PEDRO ESCOBEDO'),\n" +
                "  (22,13,'PEÑAMILLER'),\n" +
                "  (22,14,'QUERÉTARO'),\n" +
                "  (22,15,'SAN JOAQUÍN'),\n" +
                "  (22,16,'SAN JUAN DEL RÍO'),\n" +
                "  (22,17,'TEQUISQUIAPAN'),\n" +
                "  (22,18,'TOLIMÁN'),\n" +
                "  (23,1,'COZUMEL'),\n" +
                "  (23,2,'FELIPE CARRILLO PUERTO'),\n" +
                "  (23,3,'ISLA MUJERES'),\n" +
                "  (23,4,'OTHÓN P. BLANCO'),\n" +
                "  (23,5,'BENITO JUÁREZ'),\n" +
                "  (23,6,'JOSÉ MARÍA MORELOS'),\n" +
                "  (23,7,'LÁZARO CÁRDENAS'),\n" +
                "  (23,8,'SOLIDARIDAD'),\n" +
                "  (23,9,'TULUM'),\n" +
                "  (23,10,'BACALAR'),\n" +
                "  (23,11,'PUERTO MORELOS'),\n" +
                "  (24,1,'AHUALULCO'),\n" +
                "  (24,2,'ALAQUINES'),\n" +
                "  (24,3,'AQUISMÓN'),\n" +
                "  (24,4,'ARMADILLO DE LOS INFANTE'),\n" +
                "  (24,5,'CÁRDENAS'),\n" +
                "  (24,6,'CATORCE'),\n" +
                "  (24,7,'CEDRAL'),\n" +
                "  (24,8,'CERRITOS'),\n" +
                "  (24,9,'CERRO DE SAN PEDRO'),\n" +
                "  (24,10,'CIUDAD DEL MAÍZ'),\n" +
                "  (24,11,'CIUDAD FERNÁNDEZ'),\n" +
                "  (24,12,'TANCANHUITZ'),\n" +
                "  (24,13,'CIUDAD VALLES'),\n" +
                "  (24,14,'COXCATLÁN'),\n" +
                "  (24,15,'CHARCAS'),\n" +
                "  (24,16,'EBANO'),\n" +
                "  (24,17,'GUADALCÁZAR'),\n" +
                "  (24,18,'HUEHUETLÁN'),\n" +
                "  (24,19,'LAGUNILLAS'),\n" +
                "  (24,20,'MATEHUALA'),\n" +
                "  (24,21,'MEXQUITIC DE CARMONA'),\n" +
                "  (24,22,'MOCTEZUMA'),\n" +
                "  (24,23,'RAYÓN'),\n" +
                "  (24,24,'RIOVERDE'),\n" +
                "  (24,25,'SALINAS'),\n" +
                "  (24,26,'SAN ANTONIO'),\n" +
                "  (24,27,'SAN CIRO DE ACOSTA'),\n" +
                "  (24,28,'SAN LUIS POTOSÍ'),\n" +
                "  (24,29,'SAN MARTÍN CHALCHICUAUTLA'),\n" +
                "  (24,30,'SAN NICOLÁS TOLENTINO'),\n" +
                "  (24,31,'SANTA CATARINA'),\n" +
                "  (24,32,'SANTA MARÍA DEL RÍO'),\n" +
                "  (24,33,'SANTO DOMINGO'),\n" +
                "  (24,34,'SAN VICENTE TANCUAYALAB'),\n" +
                "  (24,35,'SOLEDAD DE GRACIANO SÁNCHEZ'),\n" +
                "  (24,36,'TAMASOPO'),\n" +
                "  (24,37,'TAMAZUNCHALE'),\n" +
                "  (24,38,'TAMPACÁN'),\n" +
                "  (24,39,'TAMPAMOLÓN CORONA'),\n" +
                "  (24,40,'TAMUÍN'),\n" +
                "  (24,41,'TANLAJÁS'),\n" +
                "  (24,42,'TANQUIÁN DE ESCOBEDO'),\n" +
                "  (24,43,'TIERRA NUEVA'),\n" +
                "  (24,44,'VANEGAS'),\n" +
                "  (24,45,'VENADO'),\n" +
                "  (24,46,'VILLA DE ARRIAGA'),\n" +
                "  (24,47,'VILLA DE GUADALUPE'),\n" +
                "  (24,48,'VILLA DE LA PAZ'),\n" +
                "  (24,49,'VILLA DE RAMOS'),\n" +
                "  (24,50,'VILLA DE REYES'),\n" +
                "  (24,51,'VILLA HIDALGO'),\n" +
                "  (24,52,'VILLA JUÁREZ'),\n" +
                "  (24,53,'AXTLA DE TERRAZAS'),\n" +
                "  (24,54,'XILITLA'),\n" +
                "  (24,55,'ZARAGOZA'),\n" +
                "  (24,56,'VILLA DE ARISTA'),\n" +
                "  (24,57,'MATLAPA'),\n" +
                "  (24,58,'EL NARANJO'),\n" +
                "  (25,1,'AHOME'),\n" +
                "  (25,2,'ANGOSTURA'),\n" +
                "  (25,3,'BADIRAGUATO'),\n" +
                "  (25,4,'CONCORDIA'),\n" +
                "  (25,5,'COSALÁ'),\n" +
                "  (25,6,'CULIACÁN'),\n" +
                "  (25,7,'CHOIX'),\n" +
                "  (25,8,'ELOTA'),\n" +
                "  (25,9,'ESCUINAPA'),\n" +
                "  (25,10,'EL FUERTE'),\n" +
                "  (25,11,'GUASAVE'),\n" +
                "  (25,12,'MAZATLÁN'),\n" +
                "  (25,13,'MOCORITO'),\n" +
                "  (25,14,'ROSARIO'),\n" +
                "  (25,15,'SALVADOR ALVARADO'),\n" +
                "  (25,16,'SAN IGNACIO'),\n" +
                "  (25,17,'SINALOA'),\n" +
                "  (25,18,'NAVOLATO'),\n" +
                "  (26,1,'ACONCHI'),\n" +
                "  (26,2,'AGUA PRIETA'),\n" +
                "  (26,3,'ALAMOS'),\n" +
                "  (26,4,'ALTAR'),\n" +
                "  (26,5,'ARIVECHI'),\n" +
                "  (26,6,'ARIZPE'),\n" +
                "  (26,7,'ATIL'),\n" +
                "  (26,8,'BACADÉHUACHI'),\n" +
                "  (26,9,'BACANORA'),\n" +
                "  (26,10,'BACERAC'),\n" +
                "  (26,11,'BACOACHI'),\n" +
                "  (26,12,'BÁCUM'),\n" +
                "  (26,13,'BANÁMICHI'),\n" +
                "  (26,14,'BAVIÁCORA'),\n" +
                "  (26,15,'BAVISPE'),\n" +
                "  (26,16,'BENJAMÍN HILL'),\n" +
                "  (26,17,'CABORCA'),\n" +
                "  (26,18,'CAJEME'),\n" +
                "  (26,19,'CANANEA'),\n" +
                "  (26,20,'CARBÓ'),\n" +
                "  (26,21,'LA COLORADA'),\n" +
                "  (26,22,'CUCURPE'),\n" +
                "  (26,23,'CUMPAS'),\n" +
                "  (26,24,'DIVISADEROS'),\n" +
                "  (26,25,'EMPALME'),\n" +
                "  (26,26,'ETCHOJOA'),\n" +
                "  (26,27,'FRONTERAS'),\n" +
                "  (26,28,'GRANADOS'),\n" +
                "  (26,29,'GUAYMAS'),\n" +
                "  (26,30,'HERMOSILLO'),\n" +
                "  (26,31,'HUACHINERA'),\n" +
                "  (26,32,'HUÁSABAS'),\n" +
                "  (26,33,'HUATABAMPO'),\n" +
                "  (26,34,'HUÉPAC'),\n" +
                "  (26,35,'IMURIS'),\n" +
                "  (26,36,'MAGDALENA'),\n" +
                "  (26,37,'MAZATÁN'),\n" +
                "  (26,38,'MOCTEZUMA'),\n" +
                "  (26,39,'NACO'),\n" +
                "  (26,40,'NÁCORI CHICO'),\n" +
                "  (26,41,'NACOZARI DE GARCÍA'),\n" +
                "  (26,42,'NAVOJOA'),\n" +
                "  (26,43,'NOGALES'),\n" +
                "  (26,44,'ONAVAS'),\n" +
                "  (26,45,'OPODEPE'),\n" +
                "  (26,46,'OQUITOA'),\n" +
                "  (26,47,'PITIQUITO'),\n" +
                "  (26,48,'PUERTO PEÑASCO'),\n" +
                "  (26,49,'QUIRIEGO'),\n" +
                "  (26,50,'RAYÓN'),\n" +
                "  (26,51,'ROSARIO'),\n" +
                "  (26,52,'SAHUARIPA'),\n" +
                "  (26,53,'SAN FELIPE DE JESÚS'),\n" +
                "  (26,54,'SAN JAVIER'),\n" +
                "  (26,55,'SAN LUIS RÍO COLORADO'),\n" +
                "  (26,56,'SAN MIGUEL DE HORCASITAS'),\n" +
                "  (26,57,'SAN PEDRO DE LA CUEVA'),\n" +
                "  (26,58,'SANTA ANA'),\n" +
                "  (26,59,'SANTA CRUZ'),\n" +
                "  (26,60,'SÁRIC'),\n" +
                "  (26,61,'SOYOPA'),\n" +
                "  (26,62,'SUAQUI GRANDE'),\n" +
                "  (26,63,'TEPACHE'),\n" +
                "  (26,64,'TRINCHERAS'),\n" +
                "  (26,65,'TUBUTAMA'),\n" +
                "  (26,66,'URES'),\n" +
                "  (26,67,'VILLA HIDALGO'),\n" +
                "  (26,68,'VILLA PESQUEIRA'),\n" +
                "  (26,69,'YÉCORA'),\n" +
                "  (26,70,'GENERAL PLUTARCO ELÍAS CALLES'),\n" +
                "  (26,71,'BENITO JUÁREZ'),\n" +
                "  (26,72,'SAN IGNACIO RÍO MUERTO'),\n" +
                "  (27,1,'BALANCÁN'),\n" +
                "  (27,2,'CÁRDENAS'),\n" +
                "  (27,3,'CENTLA'),\n" +
                "  (27,4,'CENTRO'),\n" +
                "  (27,5,'COMALCALCO'),\n" +
                "  (27,6,'CUNDUACÁN'),\n" +
                "  (27,7,'EMILIANO ZAPATA'),\n" +
                "  (27,8,'HUIMANGUILLO'),\n" +
                "  (27,9,'JALAPA'),\n" +
                "  (27,10,'JALPA DE MÉNDEZ'),\n" +
                "  (27,11,'JONUTA'),\n" +
                "  (27,12,'MACUSPANA'),\n" +
                "  (27,13,'NACAJUCA'),\n" +
                "  (27,14,'PARAÍSO'),\n" +
                "  (27,15,'TACOTALPA'),\n" +
                "  (27,16,'TEAPA'),\n" +
                "  (27,17,'TENOSIQUE'),\n" +
                "  (28,1,'ABASOLO'),\n" +
                "  (28,2,'ALDAMA'),\n" +
                "  (28,3,'ALTAMIRA'),\n" +
                "  (28,4,'ANTIGUO MORELOS'),\n" +
                "  (28,5,'BURGOS'),\n" +
                "  (28,6,'BUSTAMANTE'),\n" +
                "  (28,7,'CAMARGO'),\n" +
                "  (28,8,'CASAS'),\n" +
                "  (28,9,'CIUDAD MADERO'),\n" +
                "  (28,10,'CRUILLAS'),\n" +
                "  (28,11,'GÓMEZ FARÍAS'),\n" +
                "  (28,12,'GONZÁLEZ'),\n" +
                "  (28,13,'GÜÉMEZ'),\n" +
                "  (28,14,'GUERRERO'),\n" +
                "  (28,15,'GUSTAVO DÍAZ ORDAZ'),\n" +
                "  (28,16,'HIDALGO'),\n" +
                "  (28,17,'JAUMAVE'),\n" +
                "  (28,18,'JIMÉNEZ'),\n" +
                "  (28,19,'LLERA'),\n" +
                "  (28,20,'MAINERO'),\n" +
                "  (28,21,'EL MANTE');";
        db.execSQL(sql);
        sql = "INSERT INTO `qa_cat_municipios` (`id_estado`, `id_municipio`, `municipio`) VALUES\n" +
                "  (28,22,'MATAMOROS'),\n" +
                "  (28,23,'MÉNDEZ'),\n" +
                "  (28,24,'MIER'),\n" +
                "  (28,25,'MIGUEL ALEMÁN'),\n" +
                "  (28,26,'MIQUIHUANA'),\n" +
                "  (28,27,'NUEVO LAREDO'),\n" +
                "  (28,28,'NUEVO MORELOS'),\n" +
                "  (28,29,'OCAMPO'),\n" +
                "  (28,30,'PADILLA'),\n" +
                "  (28,31,'PALMILLAS'),\n" +
                "  (28,32,'REYNOSA'),\n" +
                "  (28,33,'RÍO BRAVO'),\n" +
                "  (28,34,'SAN CARLOS'),\n" +
                "  (28,35,'SAN FERNANDO'),\n" +
                "  (28,36,'SAN NICOLÁS'),\n" +
                "  (28,37,'SOTO LA MARINA'),\n" +
                "  (28,38,'TAMPICO'),\n" +
                "  (28,39,'TULA'),\n" +
                "  (28,40,'VALLE HERMOSO'),\n" +
                "  (28,41,'VICTORIA'),\n" +
                "  (28,42,'VILLAGRÁN'),\n" +
                "  (28,43,'XICOTÉNCATL'),\n" +
                "  (29,1,'AMAXAC DE GUERRERO'),\n" +
                "  (29,2,'APETATITLÁN DE ANTONIO CARVAJAL'),\n" +
                "  (29,3,'ATLANGATEPEC'),\n" +
                "  (29,4,'ATLTZAYANCA'),\n" +
                "  (29,5,'APIZACO'),\n" +
                "  (29,6,'CALPULALPAN'),\n" +
                "  (29,7,'EL CARMEN TEQUEXQUITLA'),\n" +
                "  (29,8,'CUAPIAXTLA'),\n" +
                "  (29,9,'CUAXOMULCO'),\n" +
                "  (29,10,'CHIAUTEMPAN'),\n" +
                "  (29,11,'MUÑOZ DE DOMINGO ARENAS'),\n" +
                "  (29,12,'ESPAÑITA'),\n" +
                "  (29,13,'HUAMANTLA'),\n" +
                "  (29,14,'HUEYOTLIPAN'),\n" +
                "  (29,15,'IXTACUIXTLA DE MARIANO MATAMOROS'),\n" +
                "  (29,16,'IXTENCO'),\n" +
                "  (29,17,'MAZATECOCHCO DE JOSÉ MARÍA MORELOS'),\n" +
                "  (29,18,'CONTLA DE JUAN CUAMATZI'),\n" +
                "  (29,19,'TEPETITLA DE LARDIZÁBAL'),\n" +
                "  (29,20,'SANCTÓRUM DE LÁZARO CÁRDENAS'),\n" +
                "  (29,21,'NANACAMILPA DE MARIANO ARISTA'),\n" +
                "  (29,22,'ACUAMANALA DE MIGUEL HIDALGO'),\n" +
                "  (29,23,'NATÍVITAS'),\n" +
                "  (29,24,'PANOTLA'),\n" +
                "  (29,25,'SAN PABLO DEL MONTE'),\n" +
                "  (29,26,'SANTA CRUZ TLAXCALA'),\n" +
                "  (29,27,'TENANCINGO'),\n" +
                "  (29,28,'TEOLOCHOLCO'),\n" +
                "  (29,29,'TEPEYANCO'),\n" +
                "  (29,30,'TERRENATE'),\n" +
                "  (29,31,'TETLA DE LA SOLIDARIDAD'),\n" +
                "  (29,32,'TETLATLAHUCA'),\n" +
                "  (29,33,'TLAXCALA'),\n" +
                "  (29,34,'TLAXCO'),\n" +
                "  (29,35,'TOCATLÁN'),\n" +
                "  (29,36,'TOTOLAC'),\n" +
                "  (29,37,'ZILTLALTÉPEC DE TRINIDAD SÁNCHEZ SANTOS'),\n" +
                "  (29,38,'TZOMPANTEPEC'),\n" +
                "  (29,39,'XALOZTOC'),\n" +
                "  (29,40,'XALTOCAN'),\n" +
                "  (29,41,'PAPALOTLA DE XICOHTÉNCATL'),\n" +
                "  (29,42,'XICOHTZINCO'),\n" +
                "  (29,43,'YAUHQUEMEHCAN'),\n" +
                "  (29,44,'ZACATELCO'),\n" +
                "  (29,45,'BENITO JUÁREZ'),\n" +
                "  (29,46,'EMILIANO ZAPATA'),\n" +
                "  (29,47,'LÁZARO CÁRDENAS'),\n" +
                "  (29,48,'LA MAGDALENA TLALTELULCO'),\n" +
                "  (29,49,'SAN DAMIÁN TEXÓLOC'),\n" +
                "  (29,50,'SAN FRANCISCO TETLANOHCAN'),\n" +
                "  (29,51,'SAN JERÓNIMO ZACUALPAN'),\n" +
                "  (29,52,'SAN JOSÉ TEACALCO'),\n" +
                "  (29,53,'SAN JUAN HUACTZINCO'),\n" +
                "  (29,54,'SAN LORENZO AXOCOMANITLA'),\n" +
                "  (29,55,'SAN LUCAS TECOPILCO'),\n" +
                "  (29,56,'SANTA ANA NOPALUCAN'),\n" +
                "  (29,57,'SANTA APOLONIA TEACALCO'),\n" +
                "  (29,58,'SANTA CATARINA AYOMETLA'),\n" +
                "  (29,59,'SANTA CRUZ QUILEHTLA'),\n" +
                "  (29,60,'SANTA ISABEL XILOXOXTLA'),\n" +
                "  (30,1,'ACAJETE'),\n" +
                "  (30,2,'ACATLÁN'),\n" +
                "  (30,3,'ACAYUCAN'),\n" +
                "  (30,4,'ACTOPAN'),\n" +
                "  (30,5,'ACULA'),\n" +
                "  (30,6,'ACULTZINGO'),\n" +
                "  (30,7,'CAMARÓN DE TEJEDA'),\n" +
                "  (30,8,'ALPATLÁHUAC'),\n" +
                "  (30,9,'ALTO LUCERO DE GUTIÉRREZ BARRIOS'),\n" +
                "  (30,10,'ALTOTONGA'),\n" +
                "  (30,11,'ALVARADO'),\n" +
                "  (30,12,'AMATITLÁN'),\n" +
                "  (30,13,'NARANJOS AMATLÁN'),\n" +
                "  (30,14,'AMATLÁN DE LOS REYES'),\n" +
                "  (30,15,'ANGEL R. CABADA'),\n" +
                "  (30,16,'LA ANTIGUA'),\n" +
                "  (30,17,'APAZAPAN'),\n" +
                "  (30,18,'AQUILA'),\n" +
                "  (30,19,'ASTACINGA'),\n" +
                "  (30,20,'ATLAHUILCO'),\n" +
                "  (30,21,'ATOYAC'),\n" +
                "  (30,22,'ATZACAN'),\n" +
                "  (30,23,'ATZALAN'),\n" +
                "  (30,24,'TLALTETELA'),\n" +
                "  (30,25,'AYAHUALULCO'),\n" +
                "  (30,26,'BANDERILLA'),\n" +
                "  (30,27,'BENITO JUÁREZ'),\n" +
                "  (30,28,'BOCA DEL RÍO'),\n" +
                "  (30,29,'CALCAHUALCO'),\n" +
                "  (30,30,'CAMERINO Z. MENDOZA'),\n" +
                "  (30,31,'CARRILLO PUERTO'),\n" +
                "  (30,32,'CATEMACO'),\n" +
                "  (30,33,'CAZONES DE HERRERA'),\n" +
                "  (30,34,'CERRO AZUL'),\n" +
                "  (30,35,'CITLALTÉPETL'),\n" +
                "  (30,36,'COACOATZINTLA'),\n" +
                "  (30,37,'COAHUITLÁN'),\n" +
                "  (30,38,'COATEPEC'),\n" +
                "  (30,39,'COATZACOALCOS'),\n" +
                "  (30,40,'COATZINTLA'),\n" +
                "  (30,41,'COETZALA'),\n" +
                "  (30,42,'COLIPA'),\n" +
                "  (30,43,'COMAPA'),\n" +
                "  (30,44,'CÓRDOBA'),\n" +
                "  (30,45,'COSAMALOAPAN DE CARPIO'),\n" +
                "  (30,46,'COSAUTLÁN DE CARVAJAL'),\n" +
                "  (30,47,'COSCOMATEPEC'),\n" +
                "  (30,48,'COSOLEACAQUE'),\n" +
                "  (30,49,'COTAXTLA'),\n" +
                "  (30,50,'COXQUIHUI'),\n" +
                "  (30,51,'COYUTLA'),\n" +
                "  (30,52,'CUICHAPA'),\n" +
                "  (30,53,'CUITLÁHUAC'),\n" +
                "  (30,54,'CHACALTIANGUIS'),\n" +
                "  (30,55,'CHALMA'),\n" +
                "  (30,56,'CHICONAMEL'),\n" +
                "  (30,57,'CHICONQUIACO'),\n" +
                "  (30,58,'CHICONTEPEC'),\n" +
                "  (30,59,'CHINAMECA'),\n" +
                "  (30,60,'CHINAMPA DE GOROSTIZA'),\n" +
                "  (30,61,'LAS CHOAPAS'),\n" +
                "  (30,62,'CHOCAMÁN'),\n" +
                "  (30,63,'CHONTLA'),\n" +
                "  (30,64,'CHUMATLÁN'),\n" +
                "  (30,65,'EMILIANO ZAPATA'),\n" +
                "  (30,66,'ESPINAL'),\n" +
                "  (30,67,'FILOMENO MATA'),\n" +
                "  (30,68,'FORTÍN'),\n" +
                "  (30,69,'GUTIÉRREZ ZAMORA'),\n" +
                "  (30,70,'HIDALGOTITLÁN'),\n" +
                "  (30,71,'HUATUSCO'),\n" +
                "  (30,72,'HUAYACOCOTLA'),\n" +
                "  (30,73,'HUEYAPAN DE OCAMPO'),\n" +
                "  (30,74,'HUILOAPAN DE CUAUHTÉMOC'),\n" +
                "  (30,75,'IGNACIO DE LA LLAVE'),\n" +
                "  (30,76,'ILAMATLÁN'),\n" +
                "  (30,77,'ISLA'),\n" +
                "  (30,78,'IXCATEPEC'),\n" +
                "  (30,79,'IXHUACÁN DE LOS REYES'),\n" +
                "  (30,80,'IXHUATLÁN DEL CAFÉ'),\n" +
                "  (30,81,'IXHUATLANCILLO'),\n" +
                "  (30,82,'IXHUATLÁN DEL SURESTE'),\n" +
                "  (30,83,'IXHUATLÁN DE MADERO'),\n" +
                "  (30,84,'IXMATLAHUACAN'),\n" +
                "  (30,85,'IXTACZOQUITLÁN'),\n" +
                "  (30,86,'JALACINGO'),\n" +
                "  (30,87,'XALAPA'),\n" +
                "  (30,88,'JALCOMULCO'),\n" +
                "  (30,89,'JÁLTIPAN'),\n" +
                "  (30,90,'JAMAPA'),\n" +
                "  (30,91,'JESÚS CARRANZA'),\n" +
                "  (30,92,'XICO'),\n" +
                "  (30,93,'JILOTEPEC'),\n" +
                "  (30,94,'JUAN RODRÍGUEZ CLARA'),\n" +
                "  (30,95,'JUCHIQUE DE FERRER'),\n" +
                "  (30,96,'LANDERO Y COSS'),\n" +
                "  (30,97,'LERDO DE TEJADA'),\n" +
                "  (30,98,'MAGDALENA'),\n" +
                "  (30,99,'MALTRATA'),\n" +
                "  (30,100,'MANLIO FABIO ALTAMIRANO'),\n" +
                "  (30,101,'MARIANO ESCOBEDO'),\n" +
                "  (30,102,'MARTÍNEZ DE LA TORRE'),\n" +
                "  (30,103,'MECATLÁN'),\n" +
                "  (30,104,'MECAYAPAN'),\n" +
                "  (30,105,'MEDELLÍN DE BRAVO'),\n" +
                "  (30,106,'MIAHUATLÁN'),\n" +
                "  (30,107,'LAS MINAS'),\n" +
                "  (30,108,'MINATITLÁN'),\n" +
                "  (30,109,'MISANTLA'),\n" +
                "  (30,110,'MIXTLA DE ALTAMIRANO'),\n" +
                "  (30,111,'MOLOACÁN'),\n" +
                "  (30,112,'NAOLINCO'),\n" +
                "  (30,113,'NARANJAL'),\n" +
                "  (30,114,'NAUTLA'),\n" +
                "  (30,115,'NOGALES'),\n" +
                "  (30,116,'OLUTA'),\n" +
                "  (30,117,'OMEALCA'),\n" +
                "  (30,118,'ORIZABA'),\n" +
                "  (30,119,'OTATITLÁN'),\n" +
                "  (30,120,'OTEAPAN'),\n" +
                "  (30,121,'OZULUAMA DE MASCAREÑAS'),\n" +
                "  (30,122,'PAJAPAN'),\n" +
                "  (30,123,'PÁNUCO'),\n" +
                "  (30,124,'PAPANTLA'),\n" +
                "  (30,125,'PASO DEL MACHO'),\n" +
                "  (30,126,'PASO DE OVEJAS'),\n" +
                "  (30,127,'LA PERLA'),\n" +
                "  (30,128,'PEROTE'),\n" +
                "  (30,129,'PLATÓN SÁNCHEZ'),\n" +
                "  (30,130,'PLAYA VICENTE'),\n" +
                "  (30,131,'POZA RICA DE HIDALGO'),\n" +
                "  (30,132,'LAS VIGAS DE RAMÍREZ'),\n" +
                "  (30,133,'PUEBLO VIEJO'),\n" +
                "  (30,134,'PUENTE NACIONAL'),\n" +
                "  (30,135,'RAFAEL DELGADO'),\n" +
                "  (30,136,'RAFAEL LUCIO'),\n" +
                "  (30,137,'LOS REYES'),\n" +
                "  (30,138,'RÍO BLANCO'),\n" +
                "  (30,139,'SALTABARRANCA'),\n" +
                "  (30,140,'SAN ANDRÉS TENEJAPAN'),\n" +
                "  (30,141,'SAN ANDRÉS TUXTLA'),\n" +
                "  (30,142,'SAN JUAN EVANGELISTA'),\n" +
                "  (30,143,'SANTIAGO TUXTLA'),\n" +
                "  (30,144,'SAYULA DE ALEMÁN'),\n" +
                "  (30,145,'SOCONUSCO'),\n" +
                "  (30,146,'SOCHIAPA'),\n" +
                "  (30,147,'SOLEDAD ATZOMPA'),\n" +
                "  (30,148,'SOLEDAD DE DOBLADO'),\n" +
                "  (30,149,'SOTEAPAN'),\n" +
                "  (30,150,'TAMALÍN'),\n" +
                "  (30,151,'TAMIAHUA'),\n" +
                "  (30,152,'TAMPICO ALTO'),\n" +
                "  (30,153,'TANCOCO'),\n" +
                "  (30,154,'TANTIMA'),\n" +
                "  (30,155,'TANTOYUCA'),\n" +
                "  (30,156,'TATATILA'),\n" +
                "  (30,157,'CASTILLO DE TEAYO'),\n" +
                "  (30,158,'TECOLUTLA'),\n" +
                "  (30,159,'TEHUIPANGO'),\n" +
                "  (30,160,'ÁLAMO TEMAPACHE'),\n" +
                "  (30,161,'TEMPOAL'),\n" +
                "  (30,162,'TENAMPA'),\n" +
                "  (30,163,'TENOCHTITLÁN'),\n" +
                "  (30,164,'TEOCELO'),\n" +
                "  (30,165,'TEPATLAXCO'),\n" +
                "  (30,166,'TEPETLÁN'),\n" +
                "  (30,167,'TEPETZINTLA'),\n" +
                "  (30,168,'TEQUILA'),\n" +
                "  (30,169,'JOSÉ AZUETA'),\n" +
                "  (30,170,'TEXCATEPEC'),\n" +
                "  (30,171,'TEXHUACÁN'),\n" +
                "  (30,172,'TEXISTEPEC'),\n" +
                "  (30,173,'TEZONAPA'),\n" +
                "  (30,174,'TIERRA BLANCA'),\n" +
                "  (30,175,'TIHUATLÁN'),\n" +
                "  (30,176,'TLACOJALPAN'),\n" +
                "  (30,177,'TLACOLULAN'),\n" +
                "  (30,178,'TLACOTALPAN'),\n" +
                "  (30,179,'TLACOTEPEC DE MEJÍA'),\n" +
                "  (30,180,'TLACHICHILCO'),\n" +
                "  (30,181,'TLALIXCOYAN'),\n" +
                "  (30,182,'TLALNELHUAYOCAN'),\n" +
                "  (30,183,'TLAPACOYAN'),\n" +
                "  (30,184,'TLAQUILPA'),\n" +
                "  (30,185,'TLILAPAN'),\n" +
                "  (30,186,'TOMATLÁN'),\n" +
                "  (30,187,'TONAYÁN'),\n" +
                "  (30,188,'TOTUTLA'),\n" +
                "  (30,189,'TUXPAN'),\n" +
                "  (30,190,'TUXTILLA'),\n" +
                "  (30,191,'URSULO GALVÁN'),\n" +
                "  (30,192,'VEGA DE ALATORRE'),\n" +
                "  (30,193,'VERACRUZ'),\n" +
                "  (30,194,'VILLA ALDAMA'),\n" +
                "  (30,195,'XOXOCOTLA'),\n" +
                "  (30,196,'YANGA'),\n" +
                "  (30,197,'YECUATLA'),\n" +
                "  (30,198,'ZACUALPAN'),\n" +
                "  (30,199,'ZARAGOZA'),\n" +
                "  (30,200,'ZENTLA'),\n" +
                "  (30,201,'ZONGOLICA'),\n" +
                "  (30,202,'ZONTECOMATLÁN DE LÓPEZ Y FUENTES'),\n" +
                "  (30,203,'ZOZOCOLCO DE HIDALGO'),\n" +
                "  (30,204,'AGUA DULCE'),\n" +
                "  (30,205,'EL HIGO'),\n" +
                "  (30,206,'NANCHITAL DE LÁZARO CÁRDENAS DEL RÍO'),\n" +
                "  (30,207,'TRES VALLES'),\n" +
                "  (30,208,'CARLOS A. CARRILLO'),\n" +
                "  (30,209,'TATAHUICAPAN DE JUÁREZ'),\n" +
                "  (30,210,'UXPANAPA'),\n" +
                "  (30,211,'SAN RAFAEL'),\n" +
                "  (30,212,'SANTIAGO SOCHIAPAN'),\n" +
                "  (31,1,'ABALÁ'),\n" +
                "  (31,2,'ACANCEH'),\n" +
                "  (31,3,'AKIL'),\n" +
                "  (31,4,'BACA'),\n" +
                "  (31,5,'BOKOBÁ'),\n" +
                "  (31,6,'BUCTZOTZ'),\n" +
                "  (31,7,'CACALCHÉN'),\n" +
                "  (31,8,'CALOTMUL'),\n" +
                "  (31,9,'CANSAHCAB'),\n" +
                "  (31,10,'CANTAMAYEC'),\n" +
                "  (31,11,'CELESTÚN'),\n" +
                "  (31,12,'CENOTILLO'),\n" +
                "  (31,13,'CONKAL'),\n" +
                "  (31,14,'CUNCUNUL'),\n" +
                "  (31,15,'CUZAMÁ'),\n" +
                "  (31,16,'CHACSINKÍN'),\n" +
                "  (31,17,'CHANKOM'),\n" +
                "  (31,18,'CHAPAB'),\n" +
                "  (31,19,'CHEMAX'),\n" +
                "  (31,20,'CHICXULUB PUEBLO'),\n" +
                "  (31,21,'CHICHIMILÁ'),\n" +
                "  (31,22,'CHIKINDZONOT'),\n" +
                "  (31,23,'CHOCHOLÁ'),\n" +
                "  (31,24,'CHUMAYEL'),\n" +
                "  (31,25,'DZÁN'),\n" +
                "  (31,26,'DZEMUL'),\n" +
                "  (31,27,'DZIDZANTÚN'),\n" +
                "  (31,28,'DZILAM DE BRAVO'),\n" +
                "  (31,29,'DZILAM GONZÁLEZ'),\n" +
                "  (31,30,'DZITÁS'),\n" +
                "  (31,31,'DZONCAUICH'),\n" +
                "  (31,32,'ESPITA'),\n" +
                "  (31,33,'HALACHÓ'),\n" +
                "  (31,34,'HOCABÁ'),\n" +
                "  (31,35,'HOCTÚN'),\n" +
                "  (31,36,'HOMÚN'),\n" +
                "  (31,37,'HUHÍ'),\n" +
                "  (31,38,'HUNUCMÁ'),\n" +
                "  (31,39,'IXIL'),\n" +
                "  (31,40,'IZAMAL'),\n" +
                "  (31,41,'KANASÍN'),\n" +
                "  (31,42,'KANTUNIL'),\n" +
                "  (31,43,'KAUA'),\n" +
                "  (31,44,'KINCHIL'),\n" +
                "  (31,45,'KOPOMÁ'),\n" +
                "  (31,46,'MAMA'),\n" +
                "  (31,47,'MANÍ'),\n" +
                "  (31,48,'MAXCANÚ'),\n" +
                "  (31,49,'MAYAPÁN'),\n" +
                "  (31,50,'MÉRIDA'),\n" +
                "  (31,51,'MOCOCHÁ'),\n" +
                "  (31,52,'MOTUL'),\n" +
                "  (31,53,'MUNA'),\n" +
                "  (31,54,'MUXUPIP'),\n" +
                "  (31,55,'OPICHÉN'),\n" +
                "  (31,56,'OXKUTZCAB'),\n" +
                "  (31,57,'PANABÁ'),\n" +
                "  (31,58,'PETO'),\n" +
                "  (31,59,'PROGRESO'),\n" +
                "  (31,60,'QUINTANA ROO'),\n" +
                "  (31,61,'RÍO LAGARTOS'),\n" +
                "  (31,62,'SACALUM'),\n" +
                "  (31,63,'SAMAHIL'),\n" +
                "  (31,64,'SANAHCAT'),\n" +
                "  (31,65,'SAN FELIPE'),\n" +
                "  (31,66,'SANTA ELENA'),\n" +
                "  (31,67,'SEYÉ'),\n" +
                "  (31,68,'SINANCHÉ'),\n" +
                "  (31,69,'SOTUTA'),\n" +
                "  (31,70,'SUCILÁ'),\n" +
                "  (31,71,'SUDZAL'),\n" +
                "  (31,72,'SUMA'),\n" +
                "  (31,73,'TAHDZIÚ'),\n" +
                "  (31,74,'TAHMEK'),\n" +
                "  (31,75,'TEABO'),\n" +
                "  (31,76,'TECOH'),\n" +
                "  (31,77,'TEKAL DE VENEGAS'),\n" +
                "  (31,78,'TEKANTÓ'),\n" +
                "  (31,79,'TEKAX'),\n" +
                "  (31,80,'TEKIT'),\n" +
                "  (31,81,'TEKOM'),\n" +
                "  (31,82,'TELCHAC PUEBLO'),\n" +
                "  (31,83,'TELCHAC PUERTO'),\n" +
                "  (31,84,'TEMAX'),\n" +
                "  (31,85,'TEMOZÓN'),\n" +
                "  (31,86,'TEPAKÁN'),\n" +
                "  (31,87,'TETIZ'),\n" +
                "  (31,88,'TEYA'),\n" +
                "  (31,89,'TICUL'),\n" +
                "  (31,90,'TIMUCUY'),\n" +
                "  (31,91,'TINUM'),\n" +
                "  (31,92,'TIXCACALCUPUL'),\n" +
                "  (31,93,'TIXKOKOB'),\n" +
                "  (31,94,'TIXMEHUAC'),\n" +
                "  (31,95,'TIXPÉHUAL'),\n" +
                "  (31,96,'TIZIMÍN'),\n" +
                "  (31,97,'TUNKÁS'),\n" +
                "  (31,98,'TZUCACAB'),\n" +
                "  (31,99,'UAYMA'),\n" +
                "  (31,100,'UCÚ'),\n" +
                "  (31,101,'UMÁN'),\n" +
                "  (31,102,'VALLADOLID'),\n" +
                "  (31,103,'XOCCHEL'),\n" +
                "  (31,104,'YAXCABÁ'),\n" +
                "  (31,105,'YAXKUKUL'),\n" +
                "  (31,106,'YOBAÍN'),\n" +
                "  (32,1,'APOZOL'),\n" +
                "  (32,2,'APULCO'),\n" +
                "  (32,3,'ATOLINGA'),\n" +
                "  (32,4,'BENITO JUÁREZ'),\n" +
                "  (32,5,'CALERA'),\n" +
                "  (32,6,'CAÑITAS DE FELIPE PESCADOR'),\n" +
                "  (32,7,'CONCEPCIÓN DEL ORO'),\n" +
                "  (32,8,'CUAUHTÉMOC'),\n" +
                "  (32,9,'CHALCHIHUITES'),\n" +
                "  (32,10,'FRESNILLO'),\n" +
                "  (32,11,'TRINIDAD GARCÍA DE LA CADENA'),\n" +
                "  (32,12,'GENARO CODINA'),\n" +
                "  (32,13,'GENERAL ENRIQUE ESTRADA'),\n" +
                "  (32,14,'GENERAL FRANCISCO R. MURGUÍA'),\n" +
                "  (32,15,'EL PLATEADO DE JOAQUÍN AMARO'),\n" +
                "  (32,16,'GENERAL PÁNFILO NATERA'),\n" +
                "  (32,17,'GUADALUPE'),\n" +
                "  (32,18,'HUANUSCO'),\n" +
                "  (32,19,'JALPA'),\n" +
                "  (32,20,'JEREZ'),\n" +
                "  (32,21,'JIMÉNEZ DEL TEUL'),\n" +
                "  (32,22,'JUAN ALDAMA'),\n" +
                "  (32,23,'JUCHIPILA'),\n" +
                "  (32,24,'LORETO'),\n" +
                "  (32,25,'LUIS MOYA'),\n" +
                "  (32,26,'MAZAPIL'),\n" +
                "  (32,27,'MELCHOR OCAMPO'),\n" +
                "  (32,28,'MEZQUITAL DEL ORO'),\n" +
                "  (32,29,'MIGUEL AUZA'),\n" +
                "  (32,30,'MOMAX'),\n" +
                "  (32,31,'MONTE ESCOBEDO'),\n" +
                "  (32,32,'MORELOS'),\n" +
                "  (32,33,'MOYAHUA DE ESTRADA'),\n" +
                "  (32,34,'NOCHISTLÁN DE MEJÍA'),\n" +
                "  (32,35,'NORIA DE ÁNGELES'),\n" +
                "  (32,36,'OJOCALIENTE'),\n" +
                "  (32,37,'PÁNUCO'),\n" +
                "  (32,38,'PINOS'),\n" +
                "  (32,39,'RÍO GRANDE'),\n" +
                "  (32,40,'SAIN ALTO'),\n" +
                "  (32,41,'EL SALVADOR'),\n" +
                "  (32,42,'SOMBRERETE'),\n" +
                "  (32,43,'SUSTICACÁN'),\n" +
                "  (32,44,'TABASCO'),\n" +
                "  (32,45,'TEPECHITLÁN'),\n" +
                "  (32,46,'TEPETONGO'),\n" +
                "  (32,47,'TEÚL DE GONZÁLEZ ORTEGA'),\n" +
                "  (32,48,'TLALTENANGO DE SÁNCHEZ ROMÁN'),\n" +
                "  (32,49,'VALPARAÍSO'),\n" +
                "  (32,50,'VETAGRANDE'),\n" +
                "  (32,51,'VILLA DE COS'),\n" +
                "  (32,52,'VILLA GARCÍA'),\n" +
                "  (32,53,'VILLA GONZÁLEZ ORTEGA'),\n" +
                "  (32,54,'VILLA HIDALGO'),\n" +
                "  (32,55,'VILLANUEVA'),\n" +
                "  (32,56,'ZACATECAS'),\n" +
                "  (32,57,'TRANCOSO'),\n" +
                "  (32,58,'SANTA MARÍA DE LA PAZ'),\n" +
                "  (27,18,'VILLAHERMOSA');";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion <= 3) {
            //db.execSQL("ALTER TABLE student ADD COLUMN student_rollno INTEGER DEFAULT 0");
            String SqlDelet = "DELETE FROM  qa_area";
            db.execSQL(SqlDelet);
            SqlDelet = "DELETE FROM  qa_serv";
            db.execSQL(SqlDelet);
        }

        /*switch (i) {
            case 0:
                db = this.getWritableDatabase();
                db.execSQL("DROP TABLE IF EXISTS qa_XXXXX");
                onCreate(db);
                break;
            case 1:
                String sql = "SELECT COUNT(*) AS EXISTE FROM pragma_table_info('area') WHERE name='status'";
                Cursor cursor = db.rawQuery(sql, null);
                int existe = 0;
                while (cursor.moveToNext()) {
                    existe = cursor.getInt(0);
                    Log.d("EXISTE", String.valueOf(existe));
                }
                if (existe == 0) {
                    sql = "ALTER TABLE area ADD status char(30) DEFAULT NULL";
                    db.execSQL(sql);
                    Log.d("EXISTE", sql);
                }
                break;
        }*/

    }


}
