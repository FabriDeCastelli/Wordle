package server.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import model.Request;
import model.UserStatistics;
import model.enums.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import server.service.UserStatisticsService;

/**
 * Tests for SendMeStatisticsCommand class.
 */
@DisplayName("The SendMeStatisticsCommand tests ")
public class SendMeStatisticsCommandTests {

    @Mock
    private static UserStatisticsService userStatisticsService;
    @InjectMocks
    private static SendMeStatisticsCommand sendMeStatisticsCommand;
    private Request request;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        request = new Request(RequestType.SENDMESTATISTICS);
    }

    @Test
    @DisplayName(" correctly throws an exception when the request type is not SENDMESTATISTICS")
    public void testHandleThrowsExceptionWhenRequestTypeIsNotSendMeStatistics() {
        assertThrows(IllegalArgumentException.class,
                () -> sendMeStatisticsCommand.handle(new Request(RequestType.LOGIN)));
    }

    @Test
    @DisplayName(" correctly return a response when the logged user is not empty")
    public void testCorrectHandleRequest() {
        final UserStatistics userStatistics = new UserStatistics();
        when(userStatisticsService.getStatisticsByUsername(any()))
                .thenReturn(userStatistics);
        assertEquals(userStatistics, sendMeStatisticsCommand.handle(request).data());
    }



}
