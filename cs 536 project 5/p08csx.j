	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p08csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p08csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  main()V
	ldc	"Testing program p08csx\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	1
	istore	0
	bastore
	ldc	0
	istore	1
	bastore
	iload	0
	iload	0
	iand
	istore	2
	bastore
	iload	1
	iload	1
	iand
	istore	3
	bastore
	iload	0
	iload	1
	iand
	istore	4
	bastore
	iload	1
	iload	0
	iand
	istore	5
	bastore
	iload	0
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	1
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	2
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	3
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	4
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	5
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	iload	0
	ior
	istore	2
	bastore
	iload	1
	iload	1
	ior
	istore	3
	bastore
	iload	0
	iload	1
	ior
	istore	4
	bastore
	iload	1
	iload	0
	ior
	istore	5
	bastore
	iload	0
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	1
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	2
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	3
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	4
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	5
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	istore	2
	bastore
	istore	3
	bastore
	iload	0
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	1
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	2
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	3
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  8
	.end	method
