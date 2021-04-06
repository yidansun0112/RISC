package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.duke.ece651.risc.shared.Constant;
import edu.duke.ece651.risc.shared.GUIPlayerEntity;
import edu.duke.ece651.risc.shared.PlayerEntity;

public class V2GameHostThreadTest {

  @Mock
  private PlayerEntity<String> p0;

  @Test
  public void test_checkWinLose() throws IOException, ClassNotFoundException, InterruptedException, BrokenBarrierException {
    //GUIPlayerEntity<String> p0 = new GUIPlayerEntity<String>(null, null, 0, "lzy", 0, Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS);
    MockitoAnnotations.initMocks(this);
    doNothing().when(p0).sendObject(Constant.NOT_LOSE_INFO);
    doNothing().when(p0).sendObject(Constant.LOSE_INFO);
    // when(p0.getPlayerStatus()).thenReturn(Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS, Constant.SELF_LOSE_NO_ONE_WIN_STATUS,
    //                                       Constant.SELF_WIN_STATUS, Constant.SELF_LOSE_OTHER_WIN_STATUS, -1);
    when(p0.getPlayerStatus()).thenReturn(Constant.SELF_NOT_LOSE_NO_ONE_WIN_STATUS, -1);
    // when(p0.sendObject(Constant.NOT_LOSE_INFO)).thenReturn();
    // when(p0.sendObject(Constant.NOT_LOSE_INFO)).thenReturn();
    V2GameHostThread<String> hst = new V2GameHostThread<>(p0, 3, null, null, null,null, null, null);
    //boolean status = false;
    
    assertEquals(false, hst.checkWinLose());
    // assertEquals(true, hst.checkWinLose());
    // assertEquals(true, hst.checkWinLose());
    // assertEquals(true, hst.checkWinLose());
    assertEquals(false, hst.checkWinLose());
    
    
  }

}
