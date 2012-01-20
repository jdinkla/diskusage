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

import javax.swing.tree.DefaultMutableTreeNode

/**
 * Utilities.
 */
class Utilities {
	
   /**
	*	Removes the prefix b of a. This method is needed, because
	*	the Groovy '-' / minux uses regular expressions and these will break
	*	if special characters are used.
	*/
	static String removePrefix(String a, String b) {
		assert null != a
		assert null != b
		String result = a 
		int n = b.size()
		if (a.substring(0, n) == b) result = a.substring(n)
		return result
	}

	static String fixWin2Unix(String a) {
		assert null != a
		return a.replaceAll('\\\\', '/')
	}

	static String fixUnix2Win(String a) {
		assert null != a
		return a.replaceAll('/', '\\\\')
	}

	/**
	 * Transforms the node and it's childs into a swing tree model.
	 */
	static DefaultMutableTreeNode transform(Element element) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(element);
		for (child in element.children) {
			if ( child.isDirectory ) node.add(transform(child))
		}
		return node
	}

}