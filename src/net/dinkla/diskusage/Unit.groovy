/*
	Copyright (c) 2007, 2008 Joern Dinkla, www.dinkla.net, joern@dinkla.net

	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

package net.dinkla.diskusage;

import java.math.BigDecimal;

/**
 * @author jorndinkla
 *
 */

enum Unit {

	    BYTES(new BigDecimal(1)),
	KILOBYTES(new BigDecimal(1024)), 
	MEGABYTES(new BigDecimal((int) Math.pow(1024, 2))), 
	GIGABYTES(new BigDecimal((int) Math.pow(1024, 3))), 
	TERABYTES(new BigDecimal(Math.pow(1024, 4))), 
	PETABYTES(new BigDecimal(Math.pow(1024, 5)));
	    
	protected final BigDecimal bytes;
	
	Unit(BigDecimal bytes) {
		this.bytes = bytes;
	}

	public BigDecimal getBytes() {
		return bytes;
	}
	
	public BigDecimal getValue(BigDecimal value) {
		BigDecimal result;
		if ( Unit.BYTES == this ) {
			result = value;
		} else {
			result = value.divide(bytes); 
		}
		return result;
	}

	public String toString() {
		String result = null;
		switch (this) {
		case BYTES:
			result = "Bytes";
			break;
		case KILOBYTES:
			result = "KB";
			break;
		case MEGABYTES:
			result = "MB";
			break;
		case GIGABYTES:
			result = "GB";
			break;
		case TERABYTES:
			result = "TB";
			break;
		case PETABYTES:
			result = "PB";
			break;
		}
		return result;
	}
	
}
