package brilliant.elf.content

class ELF_Constant {

    object ELFUnit {

        val uint16_t = 0x2
        val uint32_t = 0x4
        val uint64_t = 0x8

        val ELF32_Half = uint16_t
        val ELF32_Word = uint32_t
        val ELF32_Off = uint32_t
        val ELF32_Addr = uint32_t
        val ELF32_Sword = uint32_t

        val ELF64_Half = uint16_t
        val ELF64_Word = uint32_t
        val ELF64_Off = uint64_t
        val ELF64_Addr = uint64_t
        val ELF64_Sword = uint32_t
        val ELF64_Xword = uint64_t
        val ELF64_Sxword = uint64_t

    }

    object HeaderContent {

        val EI_NIDENT = 0x10
        val ELFMagicCode = byteArrayOf(0x7f, 0x45, 0x4c, 0x46)

        val EI_CALSS = 4
        val ELFCLASS32 = 1
        val ELFCLASS64 = 2
        val ELFCLASSNONE = 0

        val EI_DATA = 5
        val ELFDATANONE = 0
        val ELFDATA2LSB = 1
        val ELFDATA2MSB = 2

        val EI_VERSION = 6
        val EV_CURRENT = 1

        /*----------------------------------------------------*/

        val ET_NONE = 0
        val ET_REL = 1
        val ET_EXEC = 2
        val ET_DYN = 3
        val ET_CORE = 4
        val ET_LOPROC = 0Xff00
        val ET_HIPROC = 0xffff

        /*----------------------------------------------------*/

        val EM_NONE = 0 // No machine
        val EM_M32 = 1 // AT&T WE 32100
        val EM_SPARC = 2 // SPARC
        val EM_386 = 3 // Intel 386
        val EM_68K = 4 // Motorola 68000
        val EM_88K = 5 // Motorola 88000
        val EM_486 = 6 // Intel 486 (deprecated)
        val EM_860 = 7 // Intel 80860
        val EM_MIPS = 8 // MIPS R3000
        val EM_S370 = 9 // IBM System/370
        val EM_MIPS_RS3_LE = 10 // MIPS RS3000
        // Little-endian
        val EM_PARISC = 15 // Hewlett-Packard PA-RISC
        val EM_VPP500 = 17 // Fujitsu VPP500
        val EM_SPARC32PLUS = 18 // Enhanced instruction set
        // SPARC
        val EM_960 = 19 // Intel 80960
        val EM_PPC = 20 // PowerPC
        val EM_PPC64 = 21 // PowerPC64
        val EM_S390 = 22 // IBM System/390
        val EM_SPU = 23 // IBM SPU/SPC
        val EM_V800 = 36 // NEC V800
        val EM_FR20 = 37 // Fujitsu FR20
        val EM_RH32 = 38 // TRW RH-32
        val EM_RCE = 39 // Motorola RCE
        val EM_ARM = 40 // ARM
        val EM_ALPHA = 41 // DEC Alpha
        val EM_SH = 42 // Hitachi SH
        val EM_SPARCV9 = 43 // SPARC V9
        val EM_TRICORE = 44 // Siemens TriCore
        val EM_ARC = 45 // Argonaut RISC Core
        val EM_H8_300 = 46 // Hitachi H8/300
        val EM_H8_300H = 47 // Hitachi H8/300H
        val EM_H8S = 48 // Hitachi H8S
        val EM_H8_500 = 49 // Hitachi H8/500
        val EM_IA_64 = 50 // Intel IA-64 processor
        // architecture
        val EM_MIPS_X = 51 // Stanford MIPS-X
        val EM_COLDFIRE = 52 // Motorola ColdFire
        val EM_68HC12 = 53 // Motorola M68HC12
        val EM_MMA = 54 // Fujitsu MMA Multimedia
        // Accelerator
        val EM_PCP = 55 // Siemens PCP
        val EM_NCPU = 56 // Sony nCPU embedded RISC
        // processor
        val EM_NDR1 = 57 // Denso NDR1 microprocessor
        val EM_STARCORE = 58 // Motorola Star*Core
        // processor
        val EM_ME16 = 59 // Toyota ME16 processor
        val EM_ST100 = 60 // STMicroelectronics ST100
        // processor
        val EM_TINYJ = 61 // Advanced Logic Corp. TinyJ
        // embedded processor family
        val EM_X86_64 = 62 // AMD x86-64 architecture
        val EM_PDSP = 63 // Sony DSP Processor
        val EM_PDP10 = 64 // Digital Equipment Corp. PDP-10
        val EM_PDP11 = 65 // Digital Equipment Corp. PDP-11
        val EM_FX66 = 66 // Siemens FX66 microcontroller
        val EM_ST9PLUS = 67 // STMicroelectronics ST9+ 8/16
        // bit microcontroller
        val EM_ST7 = 68 // STMicroelectronics ST7 8-bit
        // microcontroller
        val EM_68HC16 = 69 // Motorola MC68HC16
        // Microcontroller
        val EM_68HC11 = 70 // Motorola MC68HC11
        // Microcontroller
        val EM_68HC08 = 71 // Motorola MC68HC08
        // Microcontroller
        val EM_68HC05 = 72 // Motorola MC68HC05
        // Microcontroller
        val EM_SVX = 73 // Silicon Graphics SVx
        val EM_ST19 = 74 // STMicroelectronics ST19 8-bit
        // microcontroller
        val EM_VAX = 75 // Digital VAX
        val EM_CRIS = 76 // Axis Communications 32-bit
        // embedded processor
        val EM_JAVELIN = 77 // Infineon Technologies 32-bit
        // embedded processor
        val EM_FIREPATH = 78 // Element 14 64-bit DSP
        // Processor
        val EM_ZSP = 79 // LSI Logic 16-bit DSP Processor
        val EM_MMIX = 80 // Donald Knuth's educational
        // 64-bit processor
        val EM_HUANY = 81 // Harvard University
        // machine-independent object
        // files
        val EM_PRISM = 82 // SiTera Prism
        val EM_AVR = 83 // Atmel AVR 8-bit microcontroller
        val EM_FR30 = 84 // Fujitsu FR30
        val EM_D10V = 85 // Mitsubishi D10V
        val EM_D30V = 86 // Mitsubishi D30V
        val EM_V850 = 87 // NEC v850
        val EM_M32R = 88 // Mitsubishi M32R
        val EM_MN10300 = 89 // Matsushita MN10300
        val EM_MN10200 = 90 // Matsushita MN10200
        val EM_PJ = 91 // picoJava
        val EM_OPENRISC = 92 // OpenRISC 32-bit embedded
        // processor
        val EM_ARC_COMPACT = 93 // ARC International
        // ARCompact processor
        // (old
        // spelling/synonym: public static final int EM_ARC_A5)
        val EM_XTENSA = 94 // Tensilica Xtensa Architecture
        val EM_VIDEOCORE = 95 // Alphamosaic VideoCore
        // processor
        val EM_TMM_GPP = 96 // Thompson Multimedia General
        // Purpose Processor
        val EM_NS32K = 97 // National Semiconductor 32000
        // series
        val EM_TPC = 98 // Tenor Network TPC processor
        val EM_SNP1K = 99 // Trebia SNP 1000 processor
        val EM_ST200 = 100 // STMicroelectronics
        // (www.st.com) ST200
        val EM_IP2K = 101 // Ubicom IP2xxx microcontroller
        // family
        val EM_MAX = 102 // MAX Processor
        val EM_CR = 103 // National Semiconductor
        // CompactRISC microprocessor
        val EM_F2MC16 = 104 // Fujitsu F2MC16
        val EM_MSP430 = 105 // Texas Instruments embedded
        // microcontroller msp430
        val EM_BLACKFIN = 106 // Analog Devices Blackfin
        // (DSP) processor
        val EM_SE_C33 = 107 // S1C33 Family of Seiko Epson
        // processors
        val EM_SEP = 108 // Sharp embedded microprocessor
        val EM_ARCA = 109 // Arca RISC Microprocessor
        val EM_UNICORE = 110 // Microprocessor series from
        // PKU-Unity Ltd. and MPRC
        // of Peking University
        val EM_EXCESS = 111 // eXcess: 16/32/64-bit
        // configurable embedded CPU
        val EM_DXP = 112 // Icera Semiconductor Inc. Deep
        // Execution Processor
        val EM_ALTERA_NIOS2 = 113 // Altera Nios II
        // soft-core processor
        val EM_CRX = 114 // National Semiconductor
        // CompactRISC CRX
        val EM_XGATE = 115 // Motorola XGATE embedded
        // processor
        val EM_C166 = 116 // Infineon C16x/XC16x processor
        val EM_M16C = 117 // Renesas M16C series
        // microprocessors
        val EM_DSPIC30F = 118 // Microchip Technology
        // dsPIC30F Digital Signal
        // Controller
        val EM_CE = 119 // Freescale Communication Engine
        // RISC core
        val EM_M32C = 120 // Renesas M32C series
        // microprocessors
        val EM_TSK3000 = 131 // Altium TSK3000 core
        val EM_RS08 = 132 // Freescale RS08 embedded
        // processor
        val EM_SHARC = 133 // Analog Devices SHARC family
        // of 32-bit DSP
        // processors
        val EM_ECOG2 = 134 // Cyan Technology eCOG2
        // microprocessor
        val EM_SCORE7 = 135 // Sunplus S+core7 RISC
        // processor
        val EM_DSP24 = 136 // New Japan Radio (NJR) 24-bit
        // DSP Processor
        val EM_VIDEOCORE3 = 137 // Broadcom VideoCore III
        // processor
        val EM_LATTICEMICO32 = 138 // RISC processor for
        // Lattice FPGA
        // architecture
        val EM_SE_C17 = 139 // Seiko Epson C17 family
        val EM_TI_C6000 = 140 // The Texas Instruments
        // TMS320C6000 DSP family
        val EM_TI_C2000 = 141 // The Texas Instruments
        // TMS320C2000 DSP family
        val EM_TI_C5500 = 142 // The Texas Instruments
        // TMS320C55x DSP family
        val EM_MMDSP_PLUS = 160 // STMicroelectronics 64bit
        // VLIW Data Signal
        // Processor
        val EM_CYPRESS_M8C = 161 // Cypress M8C
        // microprocessor
        val EM_R32C = 162 // Renesas R32C series
        // microprocessors
        val EM_TRIMEDIA = 163 // NXP Semiconductors
        // TriMedia architecture
        // family
        val EM_HEXAGON = 164 // Qualcomm Hexagon processor
        val EM_8051 = 165 // Intel 8051 and variants
        val EM_STXP7X = 166 // STMicroelectronics STxP7x
        // family of configurable
        // and extensible RISC processors
        val EM_NDS32 = 167 // Andes Technology compact code
        // size embedded RISC
        // processor family
        val EM_ECOG1 = 168 // Cyan Technology eCOG1X family
        val EM_ECOG1X = 168 // Cyan Technology eCOG1X
        // family
        val EM_MAXQ30 = 169 // Dallas Semiconductor MAXQ30
        // Core Micro-controllers
        val EM_XIMO16 = 170 // New Japan Radio (NJR) 16-bit
        // DSP Processor
        val EM_MANIK = 171 // M2000 Reconfigurable RISC
        // Microprocessor
        val EM_CRAYNV2 = 172 // Cray Inc. NV2 vector
        // architecture
        val EM_RX = 173 // Renesas RX family
        val EM_METAG = 174 // Imagination Technologies META
        // processor
        // architecture
        val EM_MCST_ELBRUS = 175 // MCST Elbrus general
        // purpose hardware
        // architecture
        val EM_ECOG16 = 176 // Cyan Technology eCOG16
        // family
        val EM_CR16 = 177 // National Semiconductor
        // CompactRISC CR16 16-bit
        // microprocessor
        val EM_ETPU = 178 // Freescale Extended Time
        // Processing Unit
        val EM_SLE9X = 179 // Infineon Technologies SLE9X
        // core
        val EM_L10M = 180 // Intel L10M
        val EM_K10M = 181 // Intel K10M
        val EM_AARCH64 = 183 // ARM AArch64
        val EM_AVR32 = 185 // Atmel Corporation 32-bit
        // microprocessor family
        val EM_STM8 = 186 // STMicroeletronics STM8 8-bit
        // microcontroller
        val EM_TILE64 = 187 // Tilera TILE64 multicore
        // architecture family
        val EM_TILEPRO = 188 // Tilera TILEPro multicore
        // architecture family
        val EM_CUDA = 190 // NVIDIA CUDA architecture
        val EM_TILEGX = 191 // Tilera TILE-Gx multicore
        // architecture family
        val EM_CLOUDSHIELD = 192 // CloudShield
        // architecture family
        val EM_COREA_1ST = 193 // KIPO-KAIST Core-A 1st
        // generation processor
        // family
        val EM_COREA_2ND = 194 // KIPO-KAIST Core-A 2nd
        // generation processor
        // family
        val EM_ARC_COMPACT2 = 195 // Synopsys ARCompact V2
        val EM_OPEN8 = 196 // Open8 8-bit RISC soft
        // processor core
        val EM_RL78 = 197 // Renesas RL78 family
        val EM_VIDEOCORE5 = 198 // Broadcom VideoCore V
        // processor
        val EM_78KOR = 199 // Renesas 78KOR family
        val EM_56800EX = 200 // Freescale 56800EX Digital
        // Signal Controller (DSC)

        /*-----------------------------------------------*/

    }

    object ProgramHeaderContent {

        val PT_NULL = 0
        val PT_LOAD = 1
        val PT_DYNAMIC = 2
        val PT_INTERP = 3
        val PT_NOTE = 4
        val PT_SHLIB = 5
        val PT_PHDR = 6
        val PT_GUN_STACK = 0x6474E551
        val PT_LOPROC = 0x70000000
        val PT_HIPROC = 0x7fffffff

    }

    @Deprecated("")
    object SectionHeaderContent {

        val SHT_NULL = 0
        val SHT_PROGBITS = 1
        val SHT_SYMTAB = 2
        val SHT_STRTAB = 3
        val SHT_RELA = 4
        val SHT_HASH = 5
        val SHT_DYNAMIC = 6
        val SHT_NOTE = 7
        val SHT_NOBITS = 8
        val SHT_REL = 9
        val SHT_SHLIB = 10
        val SHT_DYMSYM = 11
        val SHT_NUM = 12
        val SHT_LOPROC = 0x70000000
        val SHT_HIPROC = 0x7fffffff
        val SHT_LOUSER = 0x80000000.toInt()
        val SHT_HIUSER = 0x8fffffff.toInt()

        /**
         * writable section
         */
        val SHF_WRITE = 0x1
        val SHF_ALLOC = 0x2
        /**
         * executable section
         */
        val SHF_EXECINSTR = 0x4
        val SHF_MASKPROC = 0xf0000000.toInt()
    }

    @Deprecated("")
    object SectionName {
        val BSS = ".bss"
        val COMMENT = ".comment"
        val DATA = ".data"
        val DATAL = ".datal"
        val DEBUG = ".debug"
        val DYNAMIC = ".dynamic"
        val DYNSTR = ".dynstr"
        val FINI = ".fini"
        val GOT = ".got"
        val HASH = ".hash"
        val INIT = ".init"
        val INTERP = ".interp"
        val LINE = ".line"
        val NOTE = ".note"
        val PLT = ".plt"
        val RELNAME = ".relname"
        val RELANAME = ".relaname"
        val RODATA = ".rodata"
        val RODATA1 = ".rodata1"
        val SHSTRTAB = ".shstrtab"
        val STRTAB = ".strtab"
        val SYMTAB = ".symtab"
        val TEXT = ".text"

    }

    object PT_Dynamic {
        val DT_NULL = 0
        val DT_NEEDED = 1
        val DT_PLTRELSZ = 2
        val DT_PLTGOT = 3
        val DT_HASH = 4
        val DT_STRTAB = 5
        val DT_SYMTAB = 6
        val DT_RELA = 7
        val DT_RELASZ = 8
        val DT_RELAENT = 9
        val DT_STRSZ = 10
        val DT_SYMENT = 11
        val DT_INIT = 12
        val DT_FINI = 13
        val DT_SONAME = 14
        val DT_RPATH = 15
        val DT_SYMBOLIC = 16
        val DT_REL = 17
        val DT_RELSZ = 18
        val DT_RELENT = 19
        val DT_PLTREL = 20
        val DT_DEBUG = 21
        val DT_TEXTREL = 22
        val DT_JMPREL = 23
        val DT_INIT_ARRAY = 0x19
        val DT_FINI_ARRAY = 0x1a
        val DT_INIT_ARRAYSZ = 0x1b
        val DT_FINI_ARRAYSZ = 0x1c
        val DT_RELCOUNT = 0x6ffffffa
        val DT_FLAGS_1 = 0x6ffffffb
        val DT_FLAGS = 0x1e
        val DT_LOOS = 0x6000000d
        val DT_ANDROID_REL = DT_LOOS + 2
        val DT_ANDROID_RELSZ = DT_LOOS + 3

        val DT_GNU_HASH = 0x6ffffef5
        val DT_LOPROC = 0x70000000
        val DT_HIPROC = 0x7fffffff
    }

    object DT_RelType {

        val R_ARM_NONE = 0 /* No reloc */
        val R_ARM_PC24 = 1 /* PC relative 26 bit branch */
        val R_ARM_ABS32 = 2 /* Direct 32 bit */
        val R_ARM_REL32 = 3 /* PC relative 32 bit */
        val R_ARM_PC13 = 4
        val R_ARM_ABS16 = 5 /* Direct 16 bit */
        val R_ARM_ABS12 = 6 /* Direct 12 bit */
        val R_ARM_THM_ABS5 = 7
        val R_ARM_ABS8 = 8 /* Direct 8 bit */
        val R_ARM_SBREL32 = 9
        val R_ARM_THM_PC22 = 10
        val R_ARM_THM_PC8 = 11
        val R_ARM_AMP_VCALL9 = 12
        val R_ARM_SWI24 = 13 /*
												 * Obsolete static relocation.
												 */
        val R_ARM_TLS_DESC = 13 /* Dynamic relocation. */
        val R_ARM_THM_SWI8 = 14
        val R_ARM_XPC25 = 15
        val R_ARM_THM_XPC22 = 16
        val R_ARM_TLS_DTPMOD32 = 17 /*
														 * ID of module
														 * containing symbol
														 */
        val R_ARM_TLS_DTPOFF32 = 18 /*
														 * Offset in TLS block
														 */
        val R_ARM_TLS_TPOFF32 = 19 /*
														 * Offset in static TLS
														 * block
														 */
        val R_ARM_COPY = 20 /* Copy symbol at runtime */
        val R_ARM_GLOB_DAT = 21 /* Create GOT entry */
        val R_ARM_JUMP_SLOT = 22 /* Create PLT entry */
        val R_ARM_RELATIVE = 23 /*
													 * Adjust by program base
													 */
        val R_ARM_GOTOFF = 24 /* 32 bit offset to GOT */
        val R_ARM_GOTPC = 25 /*
												 * 32 bit PC relative offset to
												 * GOT
												 */
        val R_ARM_GOT32 = 26 /* 32 bit GOT entry */
        val R_ARM_PLT32 = 27 /* 32 bit PLT address */
        val R_ARM_ALU_PCREL_7_0 = 32
        val R_ARM_ALU_PCREL_15_8 = 33
        val R_ARM_ALU_PCREL_23_15 = 34
        val R_ARM_LDR_SBREL_11_0 = 35
        val R_ARM_ALU_SBREL_19_12 = 36
        val R_ARM_ALU_SBREL_27_20 = 37
        val R_ARM_TLS_GOTDESC = 90
        val R_ARM_TLS_CALL = 91
        val R_ARM_TLS_DESCSEQ = 92
        val R_ARM_THM_TLS_CALL = 93
        val R_ARM_GNU_VTENTRY = 100
        val R_ARM_GNU_VTINHERIT = 101
        val R_ARM_THM_PC11 = 102 /*
													 * thumb unconditional
													 * branch
													 */
        val R_ARM_THM_PC9 = 103 /*
													 * thumb conditional branch
													 */
        val R_ARM_TLS_GD32 = 104 /*
													 * PC-rel 32 bit for global
													 * dynamic thread local data
													 */
        val R_ARM_TLS_LDM32 = 105 /*
														 * PC-rel 32 bit for
														 * local dynamic thread
														 * local data
														 */
        val R_ARM_TLS_LDO32 = 106 /*
														 * 32 bit offset
														 * relative to TLS block
														 */
        val R_ARM_TLS_IE32 = 107 /*
													 * PC-rel 32 bit for GOT
													 * entry of static TLS block
													 * offset
													 */
        val R_ARM_TLS_LE32 = 108 /*
													 * 32 bit offset relative to
													 * static TLS block
													 */
        val R_ARM_THM_TLS_DESCSEQ = 129
        val R_ARM_IRELATIVE = 160
        val R_ARM_RXPC25 = 249
        val R_ARM_RSBREL32 = 250
        val R_ARM_THM_RPC22 = 251
        val R_ARM_RREL32 = 252
        val R_ARM_RABS22 = 253
        val R_ARM_RPC24 = 254
        val R_ARM_RBASE = 255
        /* Keep this the last entry. */
        val R_ARM_NUM = 256

    }

    object STB_Info {
        val STB_LOCAL = 0
        val STB_GLOBAL = 1
        val STB_WEAK = 2
        val STB_LOPROC = 13
        val STB_HIPROC = 15
    }

    object ST_TYPE {
        val STT_NOTYPE = 0
        val STT_OBJECT = 1
        val STT_FUNC = 2
        val STT_SECTION = 3
        val STT_FILE = 4
        val STT_LOPROC = 13
        val STT_HIPROC = 15
    }

    object SHN_Info {
        val SHN_UNDEF = 0
        val SHN_LORESERVE = 0xff00
        val SHN_LOPROC = 0xff00
        val SHN_HIPROC = 0xff1f
        val SHN_ABS = 0xfff1
        val SHN_COMMON = 0xfff2
        val SHN_HIRESERVE = 0xffff
    }
}
