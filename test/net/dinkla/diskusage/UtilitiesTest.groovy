package net.dinkla.diskusage;


class UtilitiesTest extends GroovyTestCase {

	void testRemovePrefix() {
		assertEquals("def", Utilities.removePrefix("abcdef", "abc"))
		assertEquals("cdef", Utilities.removePrefix("abcdef", "ab"))
		assertEquals("bcdef", Utilities.removePrefix("abcdef", "a"))
		assertEquals("abcdef", Utilities.removePrefix("abcdef", ""))
		assertEquals("abcdef", Utilities.removePrefix("abcdef", "x"))
		assertEquals("a", Utilities.removePrefix("a", "x"))
		assertEquals("", Utilities.removePrefix("a", "a"))
	}

	void testFixWin2Unix() {
		assertEquals("D:/folder/abcdef/abcde", Utilities.fixWin2Unix("D:\\folder\\abcdef\\abcde"))
	}

	void testFixUnix2Win() {
		assertEquals("D:\\folder\\abcdef\\abcde", Utilities.fixUnix2Win("D:/folder/abcdef/abcde"))
	}


}
