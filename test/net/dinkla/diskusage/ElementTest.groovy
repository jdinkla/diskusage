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
	Unit test for class Element
*/

class ElementTest extends GroovyTestCase {

	private Element n1, n2, n3
	
	void setUp() {
		n1 = new Element(name: '/test1/test2/test3', size: 100, isDirectory: true)
		n2 = new Element(name: 'a.txt',size: 123)
		n3 = new Element(name: 'b.txt', size: 234)
		n1.add(n2)
		n1.add(n3)
	}

	void testAdd() {
		shouldFail(AssertionError) {
			n2.add(new Element())
		}
		shouldFail(AssertionError) {
			n3.add(new Element())
		}
	}

	void testReCalc() {
		assertEquals(100, n1.size)
		assertEquals(0, n1.sum)
		n1.reCalc()
		assertEquals(100, n1.size)
		assertEquals(100 + 123 + 234, n1.sum)
	}

	/*
	void testPrettyPrint() {
		assertEquals('/test1/test2/test3\n  a.txt\n  b.txt', n1.prettyPrint())
		assertEquals('b.txt', n3.prettyPrint())
	}
	*/
	
	void testEach() {
		//n1.each { println it.dump() }
		n1.each(0) { node, level -> node.isDirectory }
		//println n1.filter { it.isDirectory }
	}
}
