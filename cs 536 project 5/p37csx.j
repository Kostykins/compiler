	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p37csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p37csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  sum3(III)I
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
	ldc	"Testing Program p37csx"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"The grand sum is "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	1
	ldc	2
	ldc	3
	ldc	4
	ldc	5
	ldc	6
	ldc	7
	ldc	8
	ldc	9
	invokestatic	p37csx/sum3(III)I
	invokestatic	p37csx/sum3(III)I
	invokestatic	p37csx/sum3(III)I
	invokestatic	p37csx/sum3(III)I
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Test completed"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  0
	.end	method
