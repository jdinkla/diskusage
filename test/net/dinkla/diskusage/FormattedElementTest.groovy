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
TODO locale , vs. .

*/

class FormattedElementTest extends GroovyTestCase {

	final static String POINT = ','
	
	private Format f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12
	private FormattedElement fe1, fe2, fe3, fe4, fe5, fe6, fe7, fe8, fe9, fe10, fe11, fe12
	
	void setUp() {
		def huge = 1127000493261824
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
		fe1 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f1)	
		fe2 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f2)	
		fe3 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f3)	
		fe4 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f4)	
		fe5 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f5)	
		fe6 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f6)	
		fe7 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f7)	
		fe8 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f8)	
		fe9 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f9)	
		fe10 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f10)	
		fe11 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f11)	
		fe12 = new FormattedElement(name: 'e1', size: huge, sum: huge, format: f12)	
	}

	void testFormat() {
		assertEquals('e1', fe1.format())
		assertEquals('e1 [100' + POINT + '000 %]', fe2.format())
		assertEquals('e1 [1127000493261824 Bytes]', fe3.format())
		assertEquals('e1 [1074791425,00098 KB]', fe4.format())
		assertEquals('e1 [1025,00098 MB]', fe5.format())
		assertEquals('e1 [0,00098 GB]', fe6.format())
		assertEquals('e1 [0,0 TB]', fe7.format())
		
		assertEquals('e1 [1127000493261824]', fe8.format())
		assertEquals('e1 [1074791425,00098]', fe9.format())
		assertEquals('e1 [1025,00098]', fe10.format())
		assertEquals('e1 [0,00098]', fe11.format())
		assertEquals('e1 [0,0]', fe12.format())
	}
	
}
