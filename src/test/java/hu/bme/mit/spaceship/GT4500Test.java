package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore tps1;
  private TorpedoStore tps2;

  @BeforeEach
  public void init(){
    tps1 = mock(TorpedoStore.class);
    
    tps2 = mock(TorpedoStore.class);
    
    this.ship = new GT4500(tps1, tps2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(tps1.fire(1)).thenReturn(true);
    when(tps2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(tps1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(tps1.fire(1)).thenReturn(true);
    when(tps2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(tps1, times(1)).fire(1);
    verify(tps2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_failure(){
    // Arrange
    when(tps1.isEmpty()).thenReturn(true);
    when(tps2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(tps1, times(0)).fire(1);
    verify(tps2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_oneEmpty(){
    // Arrange
    when(tps1.fire(1)).thenReturn(true);
    when(tps1.isEmpty()).thenReturn(false);
    when(tps2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(tps1, times(1)).fire(1);
    verify(tps2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_otherEmpty(){
    // Arrange
    when(tps2.fire(1)).thenReturn(true);
    when(tps1.isEmpty()).thenReturn(true);
    when(tps2.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(tps1, times(0)).fire(1);
    verify(tps2, times(1)).fire(1);
  }  

  @Test
  public void fireTorpedo_Single_bothFull(){
    // Arrange
    when(tps2.fire(1)).thenReturn(true);
    when(tps1.fire(1)).thenReturn(true);
    when(tps1.isEmpty()).thenReturn(false);
    when(tps2.isEmpty()).thenReturn(false);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(tps1, times(1)).fire(1);
    verify(tps2, times(1)).fire(1);
  }  

  @Test
  public void fireTorpedo_Single_firstEmpty(){
    // Arrange
    when(tps2.fire(1)).thenReturn(true);
    when(tps1.fire(1)).thenReturn(true);
    when(tps1.isEmpty()).thenReturn(true);
    when(tps2.isEmpty()).thenReturn(false);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(tps1, times(0)).fire(1);
    verify(tps2, times(2)).fire(1);
  }  

  @Test
  public void fireTorpedo_Single_bothEmpty(){
    // Arrange
    when(tps1.isEmpty()).thenReturn(true);
    when(tps2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(tps1, times(0)).fire(1);
    verify(tps2, times(0)).fire(1);
  }  

  @Test
  public void fireTorpedo_Single_secondEmpty(){
    // Arrange
    when(tps1.isEmpty()).thenReturn(false);
    when(tps2.isEmpty()).thenReturn(true);
    when(tps1.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(tps1, times(2)).fire(1);
    verify(tps2, times(0)).fire(1);
  }  

  @Test
  public void fireTorpedo_Single_secondEmptyThenFirst(){
    // Arrange
    when(tps1.isEmpty()).thenReturn(false);
    when(tps2.isEmpty()).thenReturn(true);
    when(tps1.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    when(tps1.isEmpty()).thenReturn(true);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(false, result2);
    verify(tps1, times(1)).fire(1);
    verify(tps2, times(0)).fire(1);
  }  

}
