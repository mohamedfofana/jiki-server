package com.kodakro.jiki.helpers;

import java.sql.Date;
import java.sql.Timestamp;

public class DbHelper {
	public static Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

}
