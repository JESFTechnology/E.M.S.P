package model.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
public class ConverterData {
	public String DateToString(Timestamp datetime, String formated){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formated);
		return simpleDateFormat.format(datetime);
	}
	
	public String DateToString(Timestamp datetime){
		return DateToString(datetime, "HH:mm:ss - dd/MM/yyyy");
	}
}
