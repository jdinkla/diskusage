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

/**

	Elements: file or directory.

 */
 
class TreeNode {

	// Is the element a directory ?
	protected boolean isDirectory
	// The name of the element
	protected String name
	// The size of the element
	protected long size
	// The sum of the sizes of the element and its children
	protected long sum
	// The parent of the element
	protected TreeNode parent
	// The children of the element	
	protected List children
	// Is an element hidden
	protected boolean hidden
	// Is an element surrogate
	protected boolean surrogate
	
	// sort functions	
	final static Closure sortNameAscending = { a, b -> a.name <=> b.name }
	final static Closure sortNameDescending = { a, b -> b.name <=> a.name }
	final static Closure sortSizeAscending = { a, b -> a.sum <=> b.sum }
	final static Closure sortSizeDescending = { a, b -> b.sum <=> a.sum }

	/**
		Adds a child to the children.
	*/
	void add(TreeNode child) {
		assert isDirectory
		if (!children) children = new ArrayList()
		children.add(child)
		child.parent = this
	}
	
	/**
		Calculates the sum of the sizes of the element and its children.
	*/
	void reCalc() {
		if ( isDirectory ) {
			long tmpSize = size
			for (child in children) {
				child.reCalc()
				if ( !child.surrogate ) tmpSize += child.sum
			}
			sum = tmpSize
		} else {
			sum = size
		}
	}

	/**
		Runs the closure for each element.
	*/
	void each(Closure closure) {
		closure.call(this)
		for (child in children) {
			child.each(closure)	
		}
	}

	// TODO this is a inject, rewrite it
	void each(int level, Closure closure) {
		closure.call(this, level)
		for (child in children) {
			child.each(level+1, closure)	
		}
	}
	
	/**
		A pretty printer.
	String prettyPrint(int indent = 0) {
		List strings = []
		this.each(indent) { node, level ->
			strings << ( (' ' * 2 * level) + node.name )
		}
		return strings.join('\n')
	}

	String prettyPrint2(int indent = 0) {
		List strings = []
		this.each(indent) { node, level ->
			strings << ( (' ' * 2 * level) + node.name + ( '  [' + node.size + ', ' + node.sum + ']') )
		}
		return strings.join('\n')
	}
	*/

	/**
		Returns the fully qualified name of an element.
	 */
	String getFullyQualifiedName() {
		List parts = []
		TreeNode elem = this
		while ( elem ) {
			parts << elem.name
			elem = elem.parent
		}
		return parts.reverse().join('')
	}

	void clear() {
		hidden = false
		for ( c in children ) {
			c.clear()
		}
		children = children.grep { it.surrogate == false}
	}
	
	void prune(int n) {
		if ( null != children && children.size() > n ) {
//			println "pruning ${name}"
			TreeNode rest = new TreeNode( name: '<various>', surrogate: true)
			List sortedChildren = children.sort(sortSizeDescending)
			rest.sum = sortedChildren[n..-1].sum.inject(0) { a,b -> a+b }
			for ( c in sortedChildren[0..n-2]) {
				c.hidden = false
			}
			for ( c in sortedChildren[n-1..-1]) {
				c.hidden = true
			}
			children << rest
		} else {
//			println "not pruning ${name} with ${children?.size()} elements"
		}
		for ( c in children ) {
			c.prune(n)
		}
	}
	
	public String toString() {
		return name
	}

    int size() {
        int result = 1
        if (children) {
            result += children.collect { it.size()}.sum()
        }
        return result
    }

}