	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p41csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p41csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  sum(III)I
	iload	0
	iload	1
	iadd
	iload	2
	iadd
	ireturn
	ldc	0
	ireturn
	.limit	stack  25
	.limit	locals  3
	.end	method
	.method	public static  main()V
	ldc	"Testing Program p41csx"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"1+2+3+4+5+6+7 = "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	1
	ldc	2
	ldc	3
	ldc	4
	ldc	5
	ldc	6
	invokestatic	p41csx/sum(III)I
	invokestatic	p41csx/sum(III)I
	ldc	7
	invokestatic	p41csx/sum(III)I
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Test compeleted"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  0
	.end	method
