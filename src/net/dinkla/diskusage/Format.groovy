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

import java.text.NumberFormat
import java.text.DecimalFormat

/**
*/

class Format {

	final Type type
	final Unit unit
	final boolean showUnits
	NumberFormat formatter

	/**
		TODO DecimalFormat should not be constructed, see Javadoc on NumberFormat
	*/
	Format(Type type, Unit unit = null, boolean showUnits = true) {
		assert null != type
		this.type = type
		this.unit = unit		
		this.showUnits = showUnits
		update()
	}
	
	/**
	*/
	String format(name, value) {
		//println "format($name, $value)"
		String result
		def unitString = showUnits ? ' ' + unit.toString() : ''
		switch (type) {
		case Type.NONE:
			result = name
			break
		case Type.ABSOLUTE:
			result = name +  ' [' + formatter.format(unit.getValue(value)) + unitString + ']'
			break
		case Type.RELATIVE:
			result = name +  ' [' + formatter.format(value) + ' %]'
			break
		default:
			throw new RuntimeException("FormattedTreeNode contains format ${format}")
		}
		return result
	}

	/**
	*/
	protected void update() {
		if ( type == Type.NONE ) {
			formatter = null
		} else if ( type == Type.RELATIVE ) {
			formatter = new DecimalFormat("#0.000")			
		} else if ( type == Type.ABSOLUTE ) {
			assert null != unit
			if ( Unit.BYTES == unit ) {
				formatter = new DecimalFormat("############")					
			} else {
				formatter = new DecimalFormat("###########0.0####")
			}
		} else {
			throw new IllegalArgumentException('Format()')
		}
	}
	
	/**
	*/
	String toString() {
		return "Format: type=$type, unit=$unit, $showUnits"
	}
}
