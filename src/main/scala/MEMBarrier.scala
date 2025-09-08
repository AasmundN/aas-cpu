package FiveStage
import chisel3._
import chisel3.experimental.MultiIOModule

class MEMBarrier extends Module {
  val io = IO(new Bundle {
    val InstructionIn    = Input(new Instruction)
    val ALUResultIn      = Input(UInt(32.W))
    val controlSignalsIn = Input(new ControlSignals)
    val DMEMDataIn       = Input(UInt(32.W))

    val InstructionOut    = Output(new Instruction)
    val ALUResultOut      = Output(UInt(32.W))
    val controlSignalsOut = Output(new ControlSignals)
    val DMEMDataOut       = Output(UInt(32.W))
  })

  val InstructionReg    = RegInit(0.U.asTypeOf(new Instruction))
  val ALUResultReg      = RegInit(0.U(32.W))
  val controlSignalsReg = RegInit(0.U.asTypeOf(new ControlSignals))

  InstructionReg    := io.InstructionIn
  ALUResultReg      := io.ALUResultIn
  controlSignalsReg := io.controlSignalsIn

  io.InstructionOut    := InstructionReg
  io.ALUResultOut      := ALUResultReg
  io.controlSignalsOut := controlSignalsReg

  io.DMEMDataOut       := io.DMEMDataIn // pass through, retrieving from memory takes a cycle
}
