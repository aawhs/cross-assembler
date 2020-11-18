
public class Opcode implements IOpcode {
	
	SymbolTable op = new SymbolTable();
	
	op.
	
	public static final int
	First = 0,
	INHERRENT_BEGIN = 0,
	halt = 0x00,
	pop = 0x01,
	dup = 0x02,
	exit = 0x03,
	ret = 0x04,
	RFU1 = 0x05,
	RFU2 = 0x06,
	RFU3 = 0x07,
	RFU4 = 0x08,
	RFU5 = 0x09,
	RFU7 = 0x0A,
	RFU8 = 0x0B,
	not = 0x0C,
	and = 0x0D,
	or = 0x0E,
	xor = 0x0F,
	neg = 0x10,
	inc = 0x11,
	dec = 0x12,
	add = 0x13,
	sub = 0x14,
	mul = 0x15,
	dov = 0x16,
	rem = 0x17,
	shl = 0x18,
	shr = 0x19,
	teq = 0x1A,
	tne = 0x1B,
	tlt = 0x1C,
	tgt = 0x1D,
	tle = 0x1E,
	tge = 0x1F,
	INHERRENT_END = 0x1F;
	
	public static final int
	
	IMMEDIATE_BEGIN = 0x30,
	br_i5 = 0x30,
	brf_i5 = 0x50,
	enter_u5 = 0x70,
	idc_i3 = 0x90,
	addv_u3 = 0x98,
	idv_u3 = 0xA0,
	stv_u3 = 0xA8,
	IMMEDIATE_END = 0xAF;
	
	public static final int
	
	RELATIVE_BEGIN = 0xB0,
	addv_u8 = 0xB0,
	idv_u8 = 0xB1,
	stv_u8 = 0xB2,
	incv = 0zB3,
	decv = 0xB4,
	enter_u8 = 0xBF,
	
	ida_i16 = 0xD5
	idc_i8 = 0xD9, 
	ido_i16 = 0xDA,
	ido_i32 = 0xDB,
	
	br_i8 = 0xE0,
	br_i16 = 0xE1,
	brf_i8 = 0xE3,
	
	calls_i8 = 0xE6,
	calls_i16 = 0xE7,
	
	br = br_i16,
	brf = brf_i16
	
	trap = 0xFF,
	
	RELATIVE_END = 0xFF,
	LAST = 0xFF;
	
	public static final int
	
	_CSTRING = 0x100,
	_INVALID_TOKEN = 0x200;
}
