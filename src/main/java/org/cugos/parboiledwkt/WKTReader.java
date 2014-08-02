package org.cugos.parboiledwkt;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

/**
 * Read a WKT String and return a Geometry
 * @author Jared Erickson
 */
public class WKTReader {

    /**
     * The parboiled WKTParser
     */
    private final WKTParser parser;

    /**
     * Create a new WKTReader
     */
    public WKTReader() {
        this.parser = Parboiled.createParser(WKTParser.class);
    }

    /**
     * Read the WKT and return a Geometry
     * @param wkt The WKT
     * @return A Geometry
     */
    public Geometry read(String wkt) {
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        return result.resultValue;
    }

}
