package pe.com.nttdata.Contrato.util;


public class Constantes {

  public static final String METODO_OBTENER_LINEA = "/obtenerLinea";
  // 1.Constantes Generales de Servicio
  public static final String NOMBRERECURSO = "claro-vent-lineafija";
  public static final String SEPARADORPUNTO = ".";
  public static final String VERSION = "1.0.0";
  public static final String PATH = "vent/lineafija/v1.0.0";
  public static final String BASEPATH = "/claro-vent-lineafija-resource/api";




  public static final String CHARSET = ";charset=UTF-8";
  public static final String APPLICATION_JSON = "application/json";

  public final static String PATH_RESOURCE_PRODUCES = ";charset=UTF-8";

  public static final String DATAAUDIT = "dataAudit";
  public final static String ID_TXT = " idTx=";
  public final static String MSG_ID = " msgid=";
  public static final String SEPARADOR = "-----------------------------------------------------------------------------";
  public final static String SALTO_LINEA = "\n";
  public static final String PARAMETROSENTRADA = " Parametros de entrada: ";
  public static final String PARAMETROSSALIDA = " Parametros de salida: ";
  public static final String PARAMETROSHEADER = " Header Request:";
  public static final String PARAMETROSBODY = "Body Request: ";
  public static final String PARAMETROSOBLIGATORIOS = "----0. Validar parametros obligatorios ------";
  public static final String VALIDACIONPARAMETROSCORRECTOS = " Validacion correcta de parametros de entrada";
  public static final String VALIDACIONPARAMETROSINCORRECTOS = " Parametros de entrada no cumple validacion";
  public static final String DEVOLVERRESPONSE = " Response a devolver: ";
  public static final String ERROR_EXCEPTION = "Error Exception: ";
  public final static String TIEMPO_TOTAL = " ] Tiempo total de proceso (ms): ";
  public final static String MILISEGUNDOS = " milisegundos.";
  public final static String REPLACEMETODO = "";
  public final static String REPLACEWS = "";
  public final static String VALIDAR_ORIGEN_BUSQUEDA = "Verificar: El campo origenBusqueda de buscarLineaRequest es obligatorio.";

  // -------- WS
  public static final String CLASSTIMEOUTCONNECTION = "com.sun.xml.ws.connect.timeout";
  public static final String CLASSTIMEOUTREQUEST = "com.sun.xml.ws.request.timeout";

  // --------
  public static final Object PUNTO = ".";
  public static final String FORMATOFECHACABECERA = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  public static final String CODIGOFLAGACTIVO = "1";

  public static final String ID = "id";
  public static final String TEXTONULL = "null";
  public static final String TEXTOVACIO = "";

  public static final String FORMATOFECHADEFAULT = "dd/MM/yyyy HH:mm:ss";
  public static final String FORMATOFECHA = "dd-MM-yyyy HH:mm:ss";
  public static final String FORMATOFECHAISO = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String FORMATOFECHA_ESTADO = "dd/MM/yyyy";
  public static final String FORMATOFECHADDMMYYYY = "dd-MM-yyyy";

  // ----------------
  public static final String CODIGO200 = "200";
  public static final String CODIGO400 = "400";
  public static final String CODIGO404 = "404";
  public static final String MENSAJEOK = "OK";

  // --------Request---------
  public static final String IDTRANSACCION = "idTransaccion";
  public static final String MSGID = "msgid";
  public static final String USERID = "userId";
  public static final String TIMESTAMP = "timestamp";
  public static final String ACCEPT = "accept";
  public static final String IPAPLICACION = "ipAplicacion";
  public static final String NOMBAPLICACION = "nombreAplicacion";
  public static final String CANAL = "canal";
  public static final String APLICACION = "aplicacion";
  public static final String NOMBREAPLICACION = "nombreAplicacion";

  public static final String API = "api";
  public static final String CHAR_CORCHETE_IZQUIERDO = " [";
  public static final String CORCHETE = "]";
  // -----------------
  public static final String CONFIGPROPERTIES = "config.properties";
  public static final String SWAGGERJAXRSCONFIG = "SwaggerJaxrsConfig";
  public static final String URLSWAGGERJAXRSCONFIG = "/SwaggerJaxrsConfig";
  public static final String HTML5CORSFILTER = "HTML5CorsFilter";
  public static final String URLPATTERNS = "/api/*";
  public static final String ACCESSCONTROLALLOWORIGIN = "Access-Control-Allow-Origin";
  public static final String ACCESSCONTROLALLOWMETHODS = "Access-Control-Allow-Methods";
  public static final String ACCESSCONTROLALLOWHEADERS = "Access-Control-Allow-Headers";
  public static final String ASTERISCO = "*";
  public static final String METODOSPERMITIDOS = "GET, POST, DELETE, PUT";
  public static final String CONTENTTYPE = "Content-Type";
  // -----------------
  public static final String PROPERTIESINTERNOS = "config.properties";
  public static final String PROPERTIESEXTERNOS = ".properties";
  public static final String PROPERTIESKEY = "claro.properties";
  public static final String CONSTANTENOJNDI = "javax.persistence.PersistenceException";
  public static final String CONSTANTENOJNDIWS = "MessageBodyProviderNotFoundException";
  public static final String CONSTANTETIMEOUTWS = "java.net.SocketTimeoutException";
  public static final int NUM_ZERO = 0;
  public static final int NUM_UNO = 1;
  public static final String ZERO = "0";
  public static final String CEROUNO = "01";
  public static final String CERODOS = "02";
  public static final String CEROTRES = "03";
  public static final String CEROCUATRO = "04";
  public static final String CEROSEIS = "06";
  public static final String CEROSIETE = "07";
  public static final String UNO = "1";
  public static final String MENOS_UNO = "-1";
  public static final String DOS = "2";
  public static final String MENOS_DOS = "-2";
  public static final String TRES = "3";
  public static final String CUATRO = "4";
  public static final String CINCO = "5";
  public static final String SEIS = "6";
  public static final String SIETE = "7";
  public static final String OCHO = "8";
  public static final String NUEVE = "9";
  public static final String ONCE = "11";
  public static final String DOCE = "12";
  public static final String TRECE = "13";
  public static final String CATORCE = "14";
  public static final int XXI = 21;
  public static final String DEFAULTENCONDIGPROPERTIES = null;
  public static final String DEFAULTENCONDINGAPI = null;
  public static final String FORMATO_FECHA_SP = null;
  public static final Object NULO = null;
  public static final String VALUE = " value ";
  public static final String METODOPOST = "POST";
  // -----------------

  public final static int STATUS_TIME_OUT = 504;

  // Param DB
  public final static String TIEMPOTOTALPROCESO = "Tiempo TOTAL del proceso";
  public final static String CODIGOIDT1 = "contrato.valor.idt1.codigo";
  public final static String MENSAJEIDT1 = "contrato.valor.idt1.mensaje";
  public final static String CODIGOIDT2 = "contrato.valor.idt2.codigo";
  public final static String MENSAJEIDT2 = "contrato.valor.idt2.mensaje";

  public static final String CONNECT_TIMEOUT = "com.sun.xml.internal.ws.connect.timeout";
  public static final String REQUEST_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";
  public static final String TIMEOUT = "Timeout";
  public static final String EXCEPTION_WS_TIMEOUT_01 = "timed out";
  public static final String EXCEPTION_WS_TIMEOUT_02 = "SocketTimeoutException";
  public static final String EXCEPTION_WS_NO_DISPONIBLE_01 = "RemoteAccessException";
  public static final String EXCEPTION_WS_NO_DISPONIBLE_02 = "404";
  public static final String EXCEPTION_WS_NO_DISPONIBLE_03 = "WebServiceException";
  public static final String EXCEPTION_WS_NO_DISPONIBLE_04 = "ConnectException";
  public static final String WSEXCEPTION_OCURRIDA = "WSException ocurrida ";
  public static final String EXCEPTION_OCURRIDA = "Excepcion ocurrida: ";
  public static final String TEXTO_ERROR = " Ocurrio un error: ";
  public static final String ERROR = "[ERROR]: ";

  public final static String INICIO_METODO = " INICIO METODO: ";
  public final static String FIN_METODO = " FIN METODO: ";
  public final static String INICIO_ACTIVIDAD = " [Inicio Actividad: ";
  public final static String FIN_ACTIVIDAD = " [Fin Actividad: ";

  public static final String METODO_INSUMO = "$metodo";
  public static final String WS = "$ws";
  public static final String EX_CON_CORCHETE = "[$ex]";
  public static final String ESPACIO = " ";
  public static final String IGUAL = "=";
  public static final String DOSPUNTOS = ": ";
  public static final String TIMEOUTEXCEPTION = "Timeout";
  public static final String TRACE = " [TRACE] ";
  public static final String CODE = " [CODE]= ";
  public static final String MSG = " [MSG]= ";
  public static final String VARIABLE_DB = "$bd";
  public static final String VARIABLE_SP = "$sp";
  public static final String PERSISTENCE_CONTEXT_BSCS = "contrato.bscs";
  public static final String BUSCARPIPELINE = "\\|";
  public static final String EJECUTAR_SP = " Ejecutando SP: ";
  public static final String ERROR_EJECUCION_SP = " Error en la ejecucion del SP : ";
  public static final String ERROR_CONVERSION = " Error en la conversion ... ";
  public static final String ERROR_PARS_OBJ = "Error parseando object to xml: ";

  public static final String P_CUSTCODE = "p_custcode";
  public static final String P_COD_PROD = "P_COD_PROD";
  public static final String P_CURSOR = "P_CURSOR";
  public static final String P_RESULTADO = "P_RESULTADO";
  public static final String P_MSGERR = "P_MSGERR";
  public static final String SNCODE = "SNCODE";
  public static final String SPCODE = "SPCODE";
  public static final String CO_SER = "CO_SER";
  public static final String CARGO_FIJO = "CARGO_FIJO";
  public static final String PERIODOS = "PERIODOS";
  public static final String ESTADO = "ESTADO";
  public static final String P_TMCODE = "P_TMCODE";
  public static final String PI_MSISDN = "PI_MSISDN";
  public static final String PI_ICCID = "PI_ICCID";
  public static final String PO_SM_ID = "PO_SM_ID";
  public static final String PO_ERRNUM = "PO_ERRNUM";
  public static final String PO_ERRMSJ = "PO_ERRMSJ";
  public static final String P_CO_ID = "p_co_id";
  public static final String P_RESPUESTA = "p_respuesta";

  public static final String TIEMPO_TOTAL_SP = " Tiempo total de proceso del llamado del SP (ms): ";
  public static final String INVOCO_SP = " Se invoco con exito el SP";
  public static final String PARAMETROS_SALIDA = " Parametros de salida: ";

  public static final String FORMATO_INTERNACIONAL = "51";
  public final static String EMPTY = "";
  public static final String CHAR_CORCHETE_DERECHO = " ] ";
  public final static String MILISEG = " milisegundos.";
  public static final String GUION = "-";
  public static final String PLATAFORMA = " Se valida plataforma: ";
  public static final String DATOS_ENTRADA = " Datos de Entrada: ";
  public static final String DATOS_SALIDA = " Datos de Salida: ";
  public static final String URL_WS_INVOCAR = " URL del Servicio a invocar: ";
  public static final String EXCEPCION_REST = " Excepcion ocurrida en la WS - REST ";
  public static final String P_PATH = "PATH: ";
  public static final String REQUEST_HEADER = " RequestHeader: ";
  public static final String REQUEST_BODY = " RequestBody: ";
  public static final String RESPONSE_HEADER = " ResponseHeader: ";
  public static final String RESPONSE_BODY = " ResponseBody: ";
  public static final String PALOTE = "|";
  public static final String PUNTO_COMA = ";";
  public static final String TEXTO_VACIO = "";
  public static final String BARRA = "\\|";


  public static final String TEXTOJNDI = " JNDI : ";
  public static final String TEXTOOWNER = " OWNER : ";
  public static final String TEXTOFUNCTION = " FUNCTION: ";
  public static final String TEXTOPAQUETE = " PAQUETE : ";
  public static final String TEXTOPROCEDURE = " PROCEDURE : ";
  public static final String CONSULTAEXITO = " Consulta Exitosa";
  public static final String BSCSJNDI = "db.bscs.jndi";
  public static final String CONSULTAINICIO = " Consulta Inicio : ";
  public static final String CALL = "call ";
  public static final String PARAMINPUT = " PARAMETROS [INPUT]: ";
  public static final String PARAMOUTPUT = " PARAMETROS [OUTPUT]: ";
  public static final String SQLTIMEOUTEXCEPTION = "SQLTIMEOUTEXCEPTION";
  public static final String TEXTOERRORTIMEOUT = "[Error De TimeOut en: ";
  public static final String TEXTOERRORDISPONIBILIDAD = "[Error De Disponibilidad en ] ";
  public static final String CHAR_CORCHETE_PUNTOS = ": [";
  public static final String EXCEPTIONTEXT = " Excepcion ocurrida en la BD {";
  public static final String NOMBRESP = "$sp";
  public static final String NOMBREJNDI = "$bd";
  public static final String TEXTOESPACIO = " ";
  public static final String PARAMOUTCURSOR = "PARAMOUTCURSOR";
  public final static String INI_CORCHETE1 = " 1. [";

  public static final String REPLACE_COMPONENTE = "[#COMPONENTE]";
  public static final String REPLACE_METODO = "[#METODO]";
  public static final String TEXTO_CODIGO_ERROR = " Codigo de Error: ";

  public static final String IDAPLICACION = "idAplicacion";
  public static final String USUARIOAPLICACION = "usuarioAplicacion";
  public static final String USUARIOSESION = "usuarioSesion";
  public static final String IDTRANSACCIONESB = "idTransaccionESB";
  public static final String IDTRANSACCIONNEGOCIO = "idTransaccionNegocio";
  public static final String FECHAINICIO = "fechaInicio";
  public static final String NODOADICIONAL = "nodoAdicional";
  public static final String GRUPO = "grupo";

  public static final String CONNECTINTERNALTIMEOUT = "com.sun.xml.internal.ws.connect.timeout";
  public static final String REQUESTINTERNALTIMEOUT = "com.sun.xml.internal.ws.request.timeout";
  public static final int CERO = 0;
  public static final String STR_CERO = "0";

  /* ENCODING */
  public static final String DEFAULENCODINGPROPERTIES = "ISO-8859-1";
  public static final String DEFAULENCODINGAPI = "UTF-8";
  public static final String FORMATO_FECHA_DEFAULT = "dd/MM/yyyy HH:mm:ss";
  public static final String COMA = ",";
  public static final String METODOINICIO = " Inicio Metodo: ";
  public static final String TEXTO_RESPUESTA = "Respuesta: ";
  public static final CharSequence MENSAJE = "mensaje";
  public static final String SEPARATOR = "=========================================================================================";
  public static final String CHARCORCHETEDERECHO = "] ";
  public static final String CHARCORCHETEIZQUIERDO = " [";
  public static final String CONECTIONSERVICIO = "Conectando con el servicio";
  public static final String TEXTOURLSERVICIO = " Url Servicio : ";
  public static final String DATOS_SERVICIO = " Datos Enviados a Servicio : $request";
  public static final String REQUEST = "$request";
  public static final String RESPONSE = "$response";
  public static final String RESPUESTA_SERVICIO = " Respuesta del Servicio : $response";
  public static final String CODIGORESTRICCIONBSCS = " Codigo Restriccion Bscs :";
  public static final String VALOR1 = " Valor1 :";
  public static final String DATOSOBJETODATOSBLOQUEOTYPE = " Datos del objeto DatosBloqueoType :";
  public static final String CODIGOBLOQUEO = " CodigoBloqueo :";
  public static final String TICKLDES = " TickLdes :";
  public static final String TICKNUMBER = " TickNumber :";

  public static final String CODIGO_INTERNACIONAL_PE = "51";
  public static final String LETRA_P_MAYUSCULA = "P";


  
  public static final String COMIENZO_INSUMOS = " ================ [Inicio] En $servicio - $metodo ===================== ";
  public static final String SERVICIO = "$servicio";
  public static final String NOMBREURL = "$nombre_URL";
  public static final String ERROR_MENSAJE = "$error";
  public static final String NOMBREWS = "$nombre_WS";
  public static final String FIN_INSUMOS = " ================ [Fin] En $sevicio - $metodo . Tiempo total de proceso(ms): ($tiempo) ===================== ";
  public static final String TIEMPO = "$tiempo";
  public static final String URL_SERVICIOS = " Conectando al servicio : $ws ";

  
  public static final String USRID = "userId";
 
  
  public static final String CONECTANDO_INSUMOS = " Conectando con el servicio : $metodo ";
  public static final String EXCEPTIONWSTIMEOUT01 = "timed out";
  public static final String EXCEPTIONWSTIMEOUT02 = "SocketTimeoutException";

  public static final String OBTENERLINEA_ACTIVIDAD_4 = "ACTIVIDAD 4. Obtener Telefono Disponible Dth";
  public static final String CONSULTANDO_DB = null;
  public static final String PARAMETROS_INPUT = null;
  public static final String PERSISTENCEEXCEPTION = null;
  public static final String HIBERNATEJDBCEXCEPTION = null;
  public static final String GENERICJDBCEXCEPTION = null;
  
  
  
}
