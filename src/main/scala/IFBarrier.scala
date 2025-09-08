package FiveStage
import chisel3._
import chisel3.experimental.MultiIOModule

class IFBarrier extends MultiIOModule {
  val io = IO(
    new Bundle {
      val InstructionIn = Input(new Instruction)
      val PCIn = Input(UInt(32.W))
      val InstructionOut = Output(new Instruction)
      val PCOut = Output(UInt(32.W))
    }
  )

  // delay registers
  val PCReg = RegInit(UInt(32.W), 0.U)

  io.InstructionOut := io.InstructionIn

  PCReg := io.PCIn
  io.PCOut := PCReg

  KeepSignal(io.PCOut)
}
