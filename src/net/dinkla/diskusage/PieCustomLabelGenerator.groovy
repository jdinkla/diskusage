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

import java.text.AttributedString
import org.jfree.chart.labels.PieSectionLabelGenerator
import org.jfree.data.general.PieDataset

/**
	TODO V0.2
*/
class PieCustomLabelGenerator implements PieSectionLabelGenerator {

     public String generateSectionLabel(PieDataset piedataset, Comparable comparable) {
         String s = null
         if ( piedataset != null ) {
             s = comparable.toString();
             if ( !s.endsWith('/') ) {
            	 s = null
             }
         }
         return s;
     }

     public AttributedString generateAttributedSectionLabel(PieDataset piedataset, Comparable comparable)  {
         return null;
     }

     PieCustomLabelGenerator() {
     }

}