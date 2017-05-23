package railway;

import org.junit.Before;
import org.junit.Test;
import railway.railway_query_parser.RailwayQueryParser;

import static org.mockito.Mockito.mock;

// TODO:
public class RailwayQueryExecutorTest {
    private RailwayQueryParser queryParser;

    @Before
    public void setup() {
        queryParser = mock(RailwayQueryParser.class);
    }

    @Test
    public void testExecuteLoadQueries() {
        final RailwayQueryExecutor queryExecutor = new RailwayQueryExecutor(queryParser);
    }
}