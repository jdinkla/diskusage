/*
	Copyright (C) 2007 Joern Dinkla, www.dinkla.net, joern@dinkla.net

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

/**
 * These tests will fail for all locales that have a different decimal point than POINT.
 *
 * TODO fix locale , vs. .
 */

class FormatTest extends GroovyTestCase {

	final static String POINT = '.'
	
	private Format f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12

	void setUp() {
		f1 = new Format(Type.NONE)
		f2 = new Format(Type.RELATIVE)
		f3 = new Format(Type.ABSOLUTE, Unit.BYTES, true)
		f4 = new Format(Type.ABSOLUTE, Unit.KILOBYTES, true)
		f5 = new Format(Type.ABSOLUTE, Unit.MEGABYTES, true)
		f6 = new Format(Type.ABSOLUTE, Unit.GIGABYTES, true)
		f7 = new Format(Type.ABSOLUTE, Unit.TERABYTES, true)
		f8 = new Format(Type.ABSOLUTE, Unit.BYTES, false)
		f9 = new Format(Type.ABSOLUTE, Unit.KILOBYTES, false)
		f10 = new Format(Type.ABSOLUTE, Unit.MEGABYTES, false)
		f11 = new Format(Type.ABSOLUTE, Unit.GIGABYTES, false)
		f12 = new Format(Type.ABSOLUTE, Unit.TERABYTES, false)
	}

	void testFormat() {
		def huge = 1127000493261824
		assertEquals('e1', f1.format('e1', 0.0))
		assertEquals('e1 [100' + POINT + '000 %]', f2.format('e1', 100))
		assertEquals('e1 [1127000493261824 Bytes]', f3.format('e1', huge))
		assertEquals('e1 [1100586419201' + POINT + '0 KB]', f4.format('e1', huge))
		assertEquals('e1 [1127000493261824]', f8.format('e1', huge))
		assertEquals('e1 [1100586419201' + POINT + '0]', f9.format('e1', huge))
	}
	
}
