package FiveStage
import chisel3._
import chisel3.experimental.MultiIOModule
import chisel3.util.{ Fill, Cat }

class Execute extends MultiIOModule {

  val io = IO(
    new Bundle {
      val Instruction = Input(new Instruction)

      val readData1 = Input(UInt(32.W))
      val readData2 = Input(UInt(32.W))

      val op1Select      = Input(UInt(1.W))
      val op2Select      = Input(UInt(1.W))
      val immType        = Input(UInt(3.W))
      val ALUop          = Input(UInt(4.W))

      val ALUResult      = Output(UInt(32.W))
    }
  )

  import Op1Select._
  import Op2Select._
  import ImmFormat._

  val ALU = Module(new ArithmeticUnit).io

  ALU.ALUop := io.ALUop 
  io.ALUResult := ALU.ALUResult

  val imm = Wire(UInt(32.W))

  when(io.immType === ImmFormat.ITYPE) {
    imm := Cat(Fill(20, io.Instruction.immediateIType(11.U)), io.Instruction.immediateIType)
  }.otherwise {
    imm := 0.U
  }

  // operation 1 select
  when(io.op1Select === Op1Select.rs1) {
    ALU.op1 := io.readData1
  }.otherwise { // missing PC as input
    ALU.op1 := 0.U
  }

  // operation 2 select
  when(io.op2Select === Op2Select.rs2) {
    ALU.op2 := io.readData2
  }.elsewhen(io.op2Select === Op2Select.imm) {
    ALU.op2 := imm
  }.otherwise {
    ALU.op2 := 0.U
  }
} 
  
