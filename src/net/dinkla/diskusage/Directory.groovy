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

import static net.dinkla.diskusage.Utilities.fixWin2Unix
import static net.dinkla.diskusage.Utilities.removePrefix

class Directory {

	// the root element
	final public Element root
	
	// the canonical file name of the root (used for detecting symbolic links)
	private String rootCan
	
	// the absolute file name of the root (used for detecting symbolic links)
	private String rootAbs
	
	// The file separator '/'
	final private String sep = '/'
	
	/**
		Reads in the files from directory 'name' and builds up a tree of 'Entry's.
	*/
	Directory(String name) {
		name = fixWin2Unix(name)
		if (name[-1] != sep) name = name + sep
		root = new Element(name: name)
		File directory = new File(name)
		assert directory.isDirectory()
		rootCan = fixWin2Unix(directory.getCanonicalFile().toString())
		rootAbs = fixWin2Unix(directory.getAbsoluteFile().toString())
		recurse(root, directory, name)
		root.reCalc()
		// sort the children by names
		root.each {
			it.children?.sort(Element.sortNameAscending)
		}
	}
	
	/**
		Reads in directory directoryName for element 'parent' as parent.
	*/
	private void recurse(Element parent, File directory, String directoryName) {
		parent.size = directory.size()
		parent.isDirectory = true
		directory.eachFile { File file ->
			String fileNameOrig = fixWin2Unix(file.toString()) 
	    	String fileName = removePrefix(fileNameOrig, directoryName + sep)
	    	def parts = fileName.split(sep)
	    	if ( file.isDirectory() ) {
	    		// do not follow symbolic links
	    		String canon = removePrefix(file.getCanonicalFile().toString(), rootCan)
	    		String absol = removePrefix(file.getAbsolutePath().toString(), rootAbs)
	    		if ( canon == absol ) {  
		    		// recurse into the directory
		    		Element node = new Element(name: parts[-1] + sep)
		    		parent.add(node)
		    		recurse(node, file, fileNameOrig )
	    		}
	    	} else {
	    		// add the file
	        	Element node = new Element(name: parts[-1], size: file.size())
	    		parent.add(node)
	    	}
		}
	}
	
}