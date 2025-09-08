package FiveStage
import chisel3._
import chisel3.util.MuxLookup
import chisel3.experimental.MultiIOModule

class ArithmeticUnit extends MultiIOModule {
  val io = IO(
    new Bundle {
      val op1            = Input(UInt(32.W))
      val op2            = Input(UInt(32.W))
      val ALUop          = Input(UInt(4.W))
      val ALUResult      = Output(UInt(32.W))
    }
  )

  import ALUOps._

  val ALUopMap = Array(
    ADD    -> (io.op1 + io.op2),
    SUB    -> (io.op1 - io.op2),
    AND    -> (io.op1 & io.op2),
    OR     -> (io.op1 | io.op2),
    XOR    -> (io.op1 ^ io.op2),
    SLT    -> (io.op1.asSInt < io.op2.asSInt),
    SLL    -> (io.op1 << io.op2(4, 0)),
    SLTU   -> (io.op1.asUInt < io.op2.asUInt),
    SRL    -> (io.op1 >> io.op2(4, 0)),
    SRA    -> (io.op1.asSInt >> io.op2(4, 0)).asUInt, // some trickery to do arithmetic shift
    COPY_A -> io.op1, 
    COPY_B -> io.op2, 
  )

  io.ALUResult := MuxLookup(io.ALUop, 0.U(32.W), ALUopMap)

  // Debug signals
  KeepSignal(io.ALUResult)
  KeepSignal(io.ALUop)
  KeepSignal(io.op1)
  KeepSignal(io.op2)
} 
  
