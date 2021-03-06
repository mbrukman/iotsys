/*******************************************************************************
 * Copyright (c) 2013
 * Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * This file is part of the IoTSyS project.
 ******************************************************************************/

package com.google.appengine.api.iotsys.comm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import ch.ethz.inf.vs.californium.coap.registries.MediaTypeRegistry;

/**
 * Contains available formats to use when communication with a gateway
 * 
 * @author Clemens Puehringer
 * 
 */
public class Format {

	private static final Logger logger = Logger.getLogger(Format.class
			.getSimpleName());

	public static final int OBIX_PLAINTEXT = MediaTypeRegistry.TEXT_XML;
	public static final int OBIX_BINARY = MediaTypeRegistry.APPLICATION_X_OBIX_BINARY;
	public static final int APPLICATION_LINK_FORMAT = MediaTypeRegistry.APPLICATION_LINK_FORMAT;

	private static Set<Integer> validFormats;

	/* put all valid values in a set */
	static {
		validFormats = new HashSet<Integer>();

		Class<Format> clazz = Format.class;
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())) {
				if (!f.getType().equals(int.class)) {
					logger.info("static variable is of type "
							+ f.getType().getName() + " continuing...");
					continue;
				}
				try {
					validFormats.add(f.getInt(null));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * check if the given format is a valid format that can be used.
	 * 
	 * @param format
	 * @return
	 */
	public static boolean isValid(int format) {
		return validFormats.contains(format);
	}

}
