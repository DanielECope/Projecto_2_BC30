package pe.com.nttdata.Contrato.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ClaroUtil {
  private static final Logger log = LoggerFactory.getLogger(ClaroUtil.class);
  
  public static DateFormat getLocalFormat() {
    DateFormat dateFormat = new SimpleDateFormat(Constantes.FORMATOFECHACABECERA);
    dateFormat.setTimeZone(TimeZone.getDefault());
    return dateFormat;
  }

  public static String printPrettyJSONString(Object o) throws JsonProcessingException {
    return new ObjectMapper().setDateFormat(ClaroUtil.getLocalFormat())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writerWithDefaultPrettyPrinter()
        .writeValueAsString(o);
  }

  public static String printJSONString(Object o) {
    try {
      return new ObjectMapper().setDateFormat(ClaroUtil.getLocalFormat())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writeValueAsString(o);
    } catch (JsonProcessingException e) {
      return Constantes.TEXTO_VACIO;
    }
  }

  public static String nuloAVacio(Object object) {

    if (object == null) {
      return Constantes.TEXTO_VACIO;
    } else {
      return object.toString();
    }
  }

  public static Object nuloAVacioObject(Object object) {
    if (object == null) {
      return Constantes.TEXTO_VACIO;
    } else {
      return object;
    }
  }

  public static String verifiyNull(Object object) {
    String a = null;
    if (object != null) {
      a = object.toString();
    }
    return a;
  }

  public static Integer convertirInteger(Object object) {

    Integer res = null;
    if (object != null) {
      if (object instanceof BigDecimal) {
        BigDecimal bd = (BigDecimal) object;
        res = bd.intValueExact();
      } else {
        System.out.println(object.getClass().getSimpleName());
      }
    }
    return res;
  }

  public static Float convertirFloat(Object object) {
    Float res = null;
    if (object != null) {
      if (object instanceof BigDecimal) {
        BigDecimal bd = (BigDecimal) object;
        res = bd.floatValue();
      }
    }
    return res;
  }

  /**
   * Genera un String a partir de un Date, si fecha es NULL retorna "" (vacio).
   *
   * @param fecha
   *          tipo Date
   * @return String de la forma dd/MM/yyyy
   */
  public static String dateAString(Date fecha) {
    if (fecha == null) {
      return Constantes.TEXTO_VACIO;
    }
    return dateAString(fecha, Constantes.FORMATOFECHACABECERA);
  }

  /**
   * Genera un String a partir de un Date de acuerdo al fomrato enviado, si
   * fecha es NULL toma la fecha actual.
   *
   * @param fecha
   * @param formato
   * @return
   */
  public static String dateAString(Date fecha, String formato) {

    if (fecha != null) {
      SimpleDateFormat formatoDF = new SimpleDateFormat(formato, Locale.ROOT);
      return formatoDF.format(fecha);
    } else {
      return null;
    }

  }

  public static Calendar toCalendar(final String iso8601string) {
    Calendar calendar = GregorianCalendar.getInstance();
    String s = iso8601string.replace("Z", "+00:00");
    try {
        s = s.substring(0, 22) + s.substring(23); // to get rid of the ":"
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT).parse(s);
        calendar.setTime(date);
    } catch (IndexOutOfBoundsException e) {
        log.error("Ocurrio un error al recorrer la cadena de Fecha", e);
        calendar = null;
    } catch (ParseException e) {
        log.error("Ocurrio un error al convertir a Date la cadena de la fecha", e);
        calendar = null;
    }
    return calendar;
  }
  
  public static boolean isValidFormat(String format, String value) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ROOT);
      date = sdf.parse(value);
      if (!value.equals(sdf.format(date))) {
        date = null;
      }
    } catch (ParseException ex) {
      date = null;
    }
    return date != null;
  }

  public static Date getValidFormatDate(String format, String value) {
    Date date = null;
    if (value != null && !value.isEmpty()) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ROOT);
        date = sdf.parse(value);
        if (!value.equals(sdf.format(date))) {
          date = null;
        }
      } catch (ParseException ex) {
        date = null;
      }
    }
    return date;
  }

  public static Date stringADate(String fecha, String formatoFecha) {
    Date fechaGenerada = null;
    if (fecha != null) {
      try {
        SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
        fechaGenerada = formato.parse(fecha);
      } catch (Exception e) {

      }
    }

    return fechaGenerada;
  }

  public static String transformacion(String properties, String request) {
    String salida = "";
    String array[] = properties.split("\\|");

    for (String objeto : array) {

      String otro[] = objeto.split("\\:");

      for (int i = 0; i < otro.length; i++) {
        if (request.equals(otro[0])) {
          salida = otro[1];
        }
      }

    }
    return salida;

  }

  public static XMLGregorianCalendar convertDateToGregorianCalendar(Date fecha) {
    GregorianCalendar calender = new GregorianCalendar();
    calender.setTime(fecha);

    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(calender.get(Calendar.YEAR),
          calender.get(Calendar.MONTH) + 1, calender.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getStoredProcedureByParameters(Object owner, Object name) {
    StringBuffer storedProcedure = new StringBuffer();
    if (owner != null && !owner.toString().isEmpty()) {
      storedProcedure.append(owner.toString());
      storedProcedure.append(Constantes.SEPARADORPUNTO);
    }

    if (name != null && !name.toString().isEmpty()) {
      storedProcedure.append(name.toString());
    }
    return storedProcedure.toString();
  }

  public static String removeAccents(String text) {
    return text == null ? null
        : Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }
  
  public static String convertProp(Object object) {
    String a = null;
    if (object != null) {
      a = object.toString();
      try {
        a = new String(a.getBytes(Constantes.DEFAULTENCONDIGPROPERTIES), Constantes.DEFAULTENCONDINGAPI);
      } catch (Exception e) {
        log.error("Error getProperties Encoding Failed, trayendo Encoding por defecto", e);
      }
    }
    return a;
  }
  
  public static String bufferedReaderError(HttpURLConnection connection) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
    String line = null;
    while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
    }
    br.close();
    return sb.toString();
}
}

