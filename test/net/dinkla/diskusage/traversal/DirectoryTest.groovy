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

package net.dinkla.diskusage.traversal

import net.dinkla.diskusage.Utilities
import org.junit.Ignore;

/**
	Unit test for class Directory
*/

class DirectoryTest extends GroovyTestCase {

    static final boolean ISWIN = System.getProperty("os.name").toLowerCase()  =~ /^win/

    static final String DIRECTORY =  ISWIN ? "C:/Windows" : '/usr'

	void testRecurse() {
        def d = new Directory(".")
	}

    @Ignore
    void XtestPerformance() {
        final long t1 = Utilities.getTime()
        def d = new Directory(DIRECTORY)
        final long t2 = Utilities.getTime()
        final long diff = t2 - t1
        println("Seq. takes $diff seconds for ${d.root.size()} num elems")
    }
}

