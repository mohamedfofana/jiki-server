package com.kodakro.jiki.helpers;

import java.sql.Timestamp;
import java.time.Instant;

public class TimeHelper {

	public static Timestamp timestampNow() {
		return Timestamp.from(Instant.now());
	}
}
