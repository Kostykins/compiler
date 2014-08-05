	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p07csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p07csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  main()V
	ldc	"Testing program p07csx\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	10
	istore	0
	iastore
	ldc	-20
	istore	0
	iastore
	iload	0
	iload	0
	imul
	istore	0
	iastore
	iload	0
	iload	0
	idiv
	ldc	1
	iadd
	istore	0
	iastore
	iload	0
	ldc	1
	isub
	iload	0
	idiv
	ldc	1
	iadd
	istore	0
	iastore
	ldc	1
	ldc	4
	iadd
	ldc	3
	isub
	ldc	2
	isub
	ldc	1
	isub
	ldc	-10
	isub
	ldc	-9
	iadd
	istore	0
	iastore
	iload	0
	iload	0
	isub
	istore	0
	iastore
	iload	0
	iload	0
	idiv
	istore	0
	iastore
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  0
	.end	method
