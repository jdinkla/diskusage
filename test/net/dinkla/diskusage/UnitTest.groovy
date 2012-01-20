package net.dinkla.diskusage;


class UnitTest extends GroovyTestCase {

	void testGetValue() {
		def xs = [ 1234, 12345678, 123456789012, 1234567890123456, 12345678901234567890]
		
		for ( x in xs ) {
			def bd = new BigDecimal(x)
			assertEquals(bd,  Unit.BYTES.getValue(bd))	
			assertEquals(bd.divide(1024), Unit.KILOBYTES.getValue(bd))
			assertEquals(bd.divide(1024*1024), Unit.MEGABYTES.getValue(bd))
			assertEquals(bd.divide(1024*1024*1024), Unit.GIGABYTES.getValue(bd))
		}
		
	}
	
	
}
