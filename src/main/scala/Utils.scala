package FiveStage
import chisel3._
import chisel3.experimental.dontTouch

// Small util that forces FIRRTL not to optimize a signal away
// This allows us to see it in the waveform
// So stupid :(
// This was generated bu AI :)

object KeepSignal {
  /** 
   * Keeps a signal alive in FIRRTL so it appears in the VCD.
   * Works for Regs, Wires, or IO signals.
   */
  def apply[T <: Data](signal: T): Unit = {
    val keepAlive = Wire(signal.cloneType)
    keepAlive := signal
    dontTouch(keepAlive)
  }
}
